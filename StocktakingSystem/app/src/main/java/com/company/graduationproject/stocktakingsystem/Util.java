package com.company.graduationproject.stocktakingsystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.format.Time;
import android.view.Display;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.Toast;



import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;




public class Util {



	public static ArrayList<String> stringArrG=null;

	public static String deliveryFromServerStatus = "";

	public static int ZeroTime = 1200;
	public static int ZeroDate = 19900101;	
	public static int INPUTTYPE_DOUBLE = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL;

	public enum LogType {
		logException ,
		logInformation
	}


	


	static public Point getScreenSize(Activity c) {
		Display display = c.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		size.x = display.getWidth();
		size.y = display.getHeight();
		// display.getSize(size);
		return size;
	}

	public static String getSDPath() {
		return Environment.getExternalStorageDirectory().getPath();
	}


	public static String getPrefAsString(Context c, String key, String defaultValue) {
		try {
			SharedPreferences getPref = PreferenceManager.getDefaultSharedPreferences(((ContextWrapper) c).getBaseContext());

			String prefValue = defaultValue;
			if (getPref.contains(key)) {
				prefValue = getPref.getString(key, defaultValue);
			}
			return prefValue;
		} catch (Exception ex) {
			logInformation(c,"getPrefAsString key=" + key + " defaultValue=" + defaultValue + " throw an exception");
			logException(c,ex);
			return "";
		}
	}

	public static boolean setPrefAsString(Context c, String key, String value) {
		try {
			SharedPreferences getPref = PreferenceManager.getDefaultSharedPreferences(c);

			SharedPreferences.Editor ed = getPref.edit();
			ed.putString(key, value);
			ed.apply();
			return true;
		} catch (Exception ex) {
			logInformation(c,"setPrefAsString key=" + key + " value=" + value + " throw an exception");
			logException(c,ex);
			return false;
		}
	}

	public static boolean turnOnGpsTracking(){
		return false;
		//return (Settings.Permissions1 & Settings.PERMISSION_ENABLE_GPS) != 0;
	}

	public static int getPrefAsInteger(Context c, String key, int defaultValue) {
		try {
			SharedPreferences getPref = PreferenceManager.getDefaultSharedPreferences(((ContextWrapper) c).getBaseContext());

			int prefValue = defaultValue;
			if (getPref.contains(key)) {
				prefValue = getPref.getInt(key, defaultValue);
			}
			return prefValue;
		} catch (Exception ex) {
			logInformation(c,"getPrefAsInteger key=" + key + " defaultValue=" + defaultValue + " throw an exception");
			logException(c,ex);
			return 0;
		}
	}

	public static boolean setPrefAsInteger(Context c, String key, int value) {
		try {
			SharedPreferences getPref = PreferenceManager.getDefaultSharedPreferences(((ContextWrapper) c).getBaseContext());

			SharedPreferences.Editor ed = getPref.edit();
			ed.putInt(key, value);
			ed.commit();
			return true;
		} catch (Exception ex) {
			logInformation(c,"setPrefAsInteger key=" + key + " value=" + value + " throw an exception");
			logException(c,ex);
			return false;
		}
	}

	public static float getPrefAsFloat(Context c, String key, float defaultValue) {
		try {
			SharedPreferences getPref = PreferenceManager.getDefaultSharedPreferences(((ContextWrapper) c).getBaseContext());

			float prefValue = defaultValue;
			if (getPref.contains(key)) {
				prefValue = getPref.getFloat(key, defaultValue);
			}
			return prefValue;
		} catch (Exception ex) {
			logInformation(c,"getPrefAsFloat key=" + key + " defaultValue=" + defaultValue + " throw an exception");
			logException(c,ex);
			return 0;
		}
	}

	public static boolean setPrefAsFloat(Context c, String key, float value) {
		try {
			SharedPreferences getPref = PreferenceManager.getDefaultSharedPreferences(((ContextWrapper) c).getBaseContext());

			SharedPreferences.Editor ed = getPref.edit();
			ed.putFloat(key, value);
			ed.commit();
			return true;
		} catch (Exception ex) {
			logInformation(c,"getPrefAsFloat key=" + key + " value=" + value + " throw an exception");
			logException(c,ex);
			return false;
		}
	}

	public static ProgressDialog getProgressDialog(Context c) {
		ProgressDialog prog = new ProgressDialog(c);
		prog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		return prog;
	}

	public static void throwException(Exception e) throws Exception {
		throw e;
	}

	public static void scrollToRight(final Context c, final HorizontalScrollView hScroll) {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				// my events
				scrollTo(c, hScroll);
			}
		}, 100); // time in millis

	}

	private static void scrollTo(Context c, HorizontalScrollView hScroll) {
		int scrollMax = 0;
		scrollMax = hScroll.getChildAt(0).getMeasuredWidth() - ((Activity) c).getWindowManager().getDefaultDisplay().getWidth() + 40;
		hScroll.scrollTo(scrollMax, 0);
	}

	public static String getDoubleRounded(double value) {
		// return String.format(Locale.US,"%.2f", value);
		String str=value+"";
		if(str.endsWith(".0") || !str.contains("."))
			return String.format(Locale.US, "%.0f", value);
		return String.format(Locale.US, "%.2f", value);
	}

	public static String incCode(String code) {
		int i;
		char[] code2 = new char[code.length()];
		code.getChars(0, code.length(), code2, 0);
		i = code2.length - 1;
		while (i > 0 && code2[i] == 32)
			i--;
		if (code2[i] == '9') {
			while (code2[i] == '9' && i > 0) {
				code2[i] = '0';
				i--;
			}
			if (code2[i] == '9')
				code2[i] = 'A';
			else
				code2[i]++;
		} else {
			if (code2[i] == '9')
				code2[i] = 'A';
			else
				code2[i]++;
		}
		String newCode = new String(code2);
		return newCode;
	}

	public static int getCurrentDate() {
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		int dtAsInt = today.monthDay;
		dtAsInt += (today.month + 1) * 100;
		dtAsInt += today.year * 10000;
		return dtAsInt;
	}

	public static String getCurrentDateAsString() {

		return dateAsStr_YYYYMMDD(getCurrentDate());
	}

	public static int getCurrentTime() {
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		int timAsInt = today.minute;
		timAsInt += (today.hour) * 100;
		return timAsInt;
	}

	public static String getCurrentTimeAsString() {
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		return String.format(Locale.US, "%d:%d", today.hour, today.minute);
	}

	public static int getDateFirstOfTheMonth() {
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		int dtAsInt = 1;
		dtAsInt += (today.month + 1) * 100;
		dtAsInt += today.year * 10000;
		return dtAsInt;
	}

	public static String getMethodName()
	{
	  final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	  
	  String str="";
	  for(int y=ste.length-1;y>3;y--){
		  str+=ste[y].getMethodName();
		  str+="-";
	  }
	  return str;
	}	
	
	public static String getCurrentDateTime() {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = formatter.format(Calendar.getInstance().getTime());
		return s;
	}

	public static String getText(double value) {
		return String.format(Locale.US, "%.2f", value);
	}

	public static void hideSoftInputPanel(Context c) {
		InputMethodManager imm = (InputMethodManager) c.getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	}

	public static String dateAsStr_DDMMYYYY(int date) {
		String str = "" + date;
		// String result=str.substring(0, 4)+"-"+str.substring(4,
		// 6)+"-"+str.substring(6, 8);
		String result = str.substring(6, 8) + "/" + str.substring(4, 6) + "/" + str.substring(0, 4);
		return result;
	}

	public static String dateAsStr_YYYYMMDD(int date) {
		String str = "" + date;
		// String result=str.substring(0, 4)+"-"+str.substring(4,
		// 6)+"-"+str.substring(6, 8);
		String result = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
		return result;
	}

	public static String timeAsStr_HHMM(int time) {
		int minute, hour;
		minute = time % 100;
		hour = time / 100;
		return hour + ":" + minute;
	}
	
	// must be replaced by convertStringToDateYYYYMMDD
	public static int convertDateFromString(String date) {
		String str = date;

		// if the string has date and time
		if (str.length() > 10) {
			if (str.contains(" "))
				str = str.split(" ")[0];
		}
		str = str.replace("/", "-");
		str = str.replace("\\", "-");

		str = str.replace("-", "");
		// str = str.replace("\\", "");
		return Integer.parseInt(str.trim());
	}

	public static int convertStringToDate_YYYYMMDD(String dateStr) {
		String dateToDeliver = dateStr;
		String dateArr[] = dateToDeliver.split("-");
		int year, month, day;
		year = Integer.parseInt(dateArr[0].trim());
		month = Integer.parseInt(dateArr[1].trim());
		day = Integer.parseInt(dateArr[2].trim());

		int date = day;
		date += month * 100;
		date += year * 10000;
		return date;
	}

	public static int convertStringToTime_HHMM(String timeStr) {

		String timeArr[] = timeStr.split(":");
		int hours, minutes;
		hours = Integer.parseInt(timeArr[0].trim());
		minutes = Integer.parseInt(timeArr[1].trim());
		int time = minutes;
		time += hours * 100;
		return time;
	}

	public static String convertDateToStr(int year, int month, int day) {
		String result = String.format(Locale.US, "%4d-%2d-%2d", year, month, day);
		result = result.trim();
		result = result.replace(" ", "0");
		return result;
	}

	public static String convertTimeToStr(int hour, int minut) {
		String result = String.format(Locale.US, "%d:%d", hour, minut);
		return result;
	}

	public static void emptyLogFile() {
		try {
			File root = new File(Environment.getExternalStorageDirectory(), "ShamelAnd");

			if (!root.exists())
				root.mkdirs();

			File gpxfile = new File(root, "SANDLOG.txt");

			BufferedWriter bW;

			bW = new BufferedWriter(new FileWriter(gpxfile));
			bW.write(getCurrentDateTime());
			bW.newLine();
			bW.write("-----");
			bW.newLine();
			bW.flush();
			bW.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	//---------------------------------------------
	public static void logInformation2(String info) {
		try {

			//Logger.i(info);
			File root = new File(Environment.getExternalStorageDirectory(), "ShamelAnd");

			if (!root.exists()) {
				root.mkdirs();

			}

			File gpxfile = new File(root, "SANDLOG.txt");

			BufferedWriter bW;

			bW = new BufferedWriter(new FileWriter(gpxfile, true));
			bW.write(getCurrentDateTime());
			bW.newLine();
			bW.write(info);
			bW.newLine();
			bW.write(getMethodName());
			bW.write("-----");
			bW.newLine();
			bW.flush();
			bW.close();
		} catch (IOException e) {
		}
	}

	public static void logInformation(Context c,String info) {
		try {

			DB db = new DB(c);
			db.open(true);
			logInformation(db,info);
			db.close();



		} catch (Exception e) {
		}
	}
	public static void logInformation(DB db,String info) {
		try {

Exception e = null;

			writeToLogTable(LogType.logInformation , db ,e, info);


			File root = new File(Environment.getExternalStorageDirectory(), "ShamelLogInfo");

			if (!root.exists()) {
				root.mkdirs();

			}

			File gpxfile = new File(root, getCurrentDateAsString()+".txt");

			BufferedWriter bW;

			bW = new BufferedWriter(new FileWriter(gpxfile, true));
			bW.write(getCurrentDateTime());
			bW.newLine();
			bW.write(info);
			bW.newLine();
			bW.write(getMethodName());
			bW.write("-----");
			bW.newLine();
			bW.flush();
			bW.close();
		} catch (IOException e) {
		}
	}

	public static void logException2(Exception e) {
		try {

			File root = new File(Environment.getExternalStorageDirectory(), "ShamelAnd");

			if (!root.exists())
				root.mkdirs();

			File gpxfile = new File(root, "SANDLOG.txt");
			Date date = new Date();
			String now = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US).format(date);

			BufferedWriter bW = new BufferedWriter(new FileWriter(gpxfile, true));
			String message = "\n" + now + " " + exceptionStacktraceToString(e);
            //Logger.e(message);
			bW.write(message);
			bW.flush();
			bW.close();
		} catch (IOException ee) {
			//Logger.e("Util", ee.toString());
		}
	}

	public static void logException(DB db,Exception e) {
        try {

			writeToLogTable(LogType.logException,db,e,"");


            File root = new File(Environment.getExternalStorageDirectory(), "ShamelLogExcep");

            if (!root.exists()) {
                root.mkdirs();

            }
            File gpxfile = new File(root, getCurrentDateAsString()+".txt");
            Date date = new Date();
            String now = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US).format(date);

            BufferedWriter bW = new BufferedWriter(new FileWriter(gpxfile, true));
            String message = "\n" + now + " " + exceptionStacktraceToString(e)+"\n"+"--------------------------";
            bW.write(message);
            bW.flush();
            bW.close();

        } catch (IOException ex) {

		}


	}

	public static void logException(Context c, Exception e) {

		try {



        DB db = new DB(c);
        db.open(true);
        logException(db,e);
        db.close();}
		catch (Exception ex){
			Toast.makeText(c, "------------------------------------------------", Toast.LENGTH_SHORT).show();



		}
    }


	private static void writeToLogTable(LogType type , DB db , Exception e , String info ){


		try {
		/*
			DB.LoggingEntity le = db.new LoggingEntity();
			DB.Logging lo = db.new Logging();
			lo.userID = Settings.USERID;
			lo.date = Util.getCurrentDate();
			lo.time = Util.getCurrentTime();
			if (type == LogType.logException) {
				lo.desc = e.toString();
				lo.processType = "Exception";
				lo.processName = "Exception";
			}
			if (type == LogType.logInformation) {
				lo.desc = info;
				lo.processType = "Information";
				lo.processName = "Information";

			}

			le.insert(lo);*/
		}
		catch (Exception ex){
		}
	}

    private static String exceptionStacktraceToString(Exception e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        ps.close();
        return baos.toString();
    }

	public static void showToast(Context c, String msg, boolean isLongDuration) {
		int duration = Toast.LENGTH_SHORT;
		if (isLongDuration)
			duration = Toast.LENGTH_LONG;
		Toast t = Toast.makeText(c, msg, duration);
		t.show();
	}

	// get resource string
	public static String getMsg(Context c, int resourceID) {
		return c.getResources().getString(resourceID);
	}


	// public static boolean MessageBoxResult;
	public static void messageBox(Context c, String msg, int requestCode) {
		/*
		Intent i = new Intent(c, MessageBox.class);
		if (msg != null && msg.trim().length() > 0)
			i.putExtra("MessageBoxMsg", msg);
		Activity a = (Activity) c;
		a.startActivityForResult(i, requestCode);
		*/



	}

	public static void messageBox(Context c, String msg) {
		/*
		Intent i = new Intent(c, MessageBox.class);
		i.putExtra("NoBtnVisible", false);
		i.putExtra("MessageBoxMsg", msg);
		Activity a = (Activity) c;
		a.startActivity(i);*/
	}

	public static void messageBox(Context c, int resourceID) {
		/*
		String msg = c.getResources().getString(resourceID);
		Intent i = new Intent(c, MessageBox.class);
		i.putExtra("NoBtnVisible", false);
		i.putExtra("MessageBoxMsg", msg);
		Activity a = (Activity) c;
		a.startActivity(i);*/
	}

	// this method used with the override method OnActivityResult
	public static boolean checkMessageBoxResult(int resultCode, Intent data) {
		// could be used later
		// Bundle b=data.getExtras();
		// b.getBoolean("Key");

		if (resultCode == Activity.RESULT_OK)
			return true;
		else
			return false;
	}

	public static String EncryptPassword(String password) {
		if (password.trim().length() == 0)
			return "";
		else {
			String key = "mjfs456lklncv51a15xvkb156as1dvlnvad1as1a";// 40 chars

			// make them upper
			key = key.toUpperCase();
			password = password.toUpperCase();

			// convert key to chars
			char[] keyArr = new char[key.length()];
			key.getChars(0, key.length(), keyArr, 0);

			// if the password exceed the key length
			if (password.length() > 40)
				password = password.substring(0, 40);

			// convert password to chars
			char[] passwordArr = new char[password.length()];
			password.getChars(0, password.length(), passwordArr, 0);

			int pos = 0;
			password = "";
			for (int x = 0; x < 40; x++) {
				keyArr[x] = (char) (((int) keyArr[x] + (int) passwordArr[pos]) / 2);
				keyArr[x] += (char) 12;
				if (keyArr[x] == '\'')
					keyArr[x] = '>';
				pos++;
				if (pos >= passwordArr.length)
					pos = 0;
				password += keyArr[x];
			}
			return password;

		}
	}
	public static String EncryptPasswordNew(String password) {
		if (password.trim().length() == 0)
			return "";
		else {
			String map[]={
					"484","214","743","144","754","224","646","114","512","887",
					"444","741","516","111","515","741","541","622","005","047",
					"084","995","136","187","626","222","841","047","800","100",
					"480","106","704","990","150","878","180","011","900","990",
					"100","804","560","700","660","707","955","474","666","606",
					"150","804","089","480","627","599","180","155","500","101",
					"111","877","188","880","014","511","663","120","087","602",
					"156","519","516","215","147","515","955","515","544","699",
					"157","156","165","151","515","777","166","484","236","221",
					"058","167","621","163","148","251","166","774","515","515",//10
					"484","214","743","144","754","224","646","114","512","887",
					"444","741","516","111","515","741","541","622","005","047",
					"084","995","136","187","626","222","841","047","800","100",
					"480","106","704","990","150","878","180","011","900","990",
					"100","804","560","700","660","707","955","474","666","606",
					"150","804","089","480","627","599","180","155","500","101",
					"111","877","188","880","014","511","663","120","087","602",
					"156","519","516","215","147","515","955","515","544","699",
					"157","156","165","151","515","777","166","484","236","221",
					"058","167","621","163","148","251","166","774","515","515",//20
					"484","214","743","144","754","224","646","114","512","887",
					"444","741","516","111","515","741","541","622","005","047",
					"084","995","136","187","626","222","841","047","800","100",
					"480","106","704","990","150","878","180","011","900","990",
					"100","804","560","700","660","707","955","474","666","606",
					"150","804","089","480","627","599","180","155","500","101",
					"111","877","188","880","014","511","663","120","087","602",
					"156","519","516","215","147","515","955","515","544","699",
					"157","156","165","151","515","777","166","484","236","221",
					"058","167","621","163","148","251","166","774","515","515",//30
					"484","214","743","144","754","224","646","114","512","887",
					"444","741","516","111","515","741","541","622","005","047",
					"084","995","136","187","626","222","841","047","800","100",
					"480","106","704","990","150","878","180","011","900","990",
					"100","804","560","700","660","707","955","474","666","606",
					"150","804","089","480","627","599","180","155","500","101",
					"111","877","188","880","014","511","663","120","087","602",
					"156","519","516","215","147","515","955","515","544","699",
					"157","156","165","151","515","777","166","484","236","221",
					"058","167","621","163","148","251","166","774","515","515",//40
					};
			if (password.length() > 40)
				password = password.substring(0, 40);
			char[] passwordArr = new char[password.length()];
			password.getChars(0, password.length(), passwordArr, 0);

			int pos = 0;
			password = "";
			for (int x = 0; x < 40 && x<passwordArr.length; x++) {
				pos=((int)passwordArr[x])-48;
				
				password+=map[pos+x*10];
			}
			return password;
			
			
			/*
			String key = "mjfs456lklncv51a15xvkb156as1dvlnvad1as1a";// 40 chars

			// make them upper
			key = key.toUpperCase();
			password = password.toUpperCase();

			// convert key to chars
			char[] keyArr = new char[key.length()];
			key.getChars(0, key.length(), keyArr, 0);

			// if the password exceed the key length
			if (password.length() > 40)
				password = password.substring(0, 40);

			// convert password to chars
			char[] passwordArr = new char[password.length()];
			password.getChars(0, password.length(), passwordArr, 0);

			int pos = 0;
			password = "";
			for (int x = 0; x < 40; x++) {
				keyArr[x] = (char) (((int) keyArr[x] + (int) passwordArr[pos]) / 2);
				keyArr[x] += (char) 12;
				if (keyArr[x] == '\'')
					keyArr[x] = '>';
				pos++;
				if (pos >= passwordArr.length)
					pos = 0;
				password += keyArr[x];
			}
			return password;*/

		}
	}

	public static void startAnActivity(Context c, Class<?> theClass) {

		Intent i = new Intent(c, theClass);
		c.startActivity(i);
	}

	public static void GoToBarcodeReader(Activity activity) {

		/*
		IntentIntegrator.initiateScan(activity, R.layout.capture,
				R.id.viewfinder_view, R.id.preview_view, true);*/
	}

	public static int getCurrentDateMinusDays(int dELETE_ORDERS_DAYS) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1 * dELETE_ORDERS_DAYS);

		int dtAsInt = cal.get(Calendar.DAY_OF_MONTH);
		dtAsInt += (cal.get(Calendar.MONTH) + 1) * 100;
		dtAsInt += cal.get(Calendar.YEAR) * 10000;
		return dtAsInt;
	}

	public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}


	public static String getImagePathToPrint() {
		return getSDPath()+"//ShamelAnd//test.jpg";
	}

	public static String RemoveSymbols(String str) {
		str.replace('*', ' ');
		str.replace('\\', ' ');
		str.replace('/', ' ');
		str.replace('-', ' ');
		str.replace('+', ' ');
		str.replace('!', ' ');
		str.replace('@', ' ');
		str.replace('#', ' ');
		str.replace('$', ' ');
		str.replace('%', ' ');
		str.replace('^', ' ');
		str.replace('&', ' ');
		str.replace('(', ' ');
		str.replace(')', ' ');
		str.replace('%', ' ');
		str.replace('[', ' ');
		str.replace(']', ' ');
		str.replace(';', ' ');
		str.replace(',', ' ');
		str.replace('.', ' ');
		str.replace('>', ' ');
		str.replace('<', ' ');
		str.replace('{', ' ');
		str.replace('}', ' ');
		return str;
	}
	
	public static boolean isConnectingToInternet(Context context) {

		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
						return true;					
		}
		return false;
	}

	
	public static boolean contain(String[] list,int toFind)
	{
		for(String obj : list)
		{
			if(Integer.parseInt(obj)== toFind)
				return true;			
		}
		return false;
	}


    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean hasServerAccess(Context context) {
		/*
        if (isNetworkAvailable(context)) {
            try {
				String urlStr = WebChannel.getURI();
				urlStr += "?type=generate_204";
                HttpURLConnection urlc = (HttpURLConnection) (new URL(urlStr).openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 204 && urlc.getContentLength() == 0);
            } catch (IOException e) {
                Logger.e("Util", e.getMessage());
            }
        } else {
            Logger.d("Util", "No network available!");
        }*/
        return false;
    }

	public static boolean hasInternetAccess(Context context) {
		if (isNetworkAvailable(context)) {
			try {
				HttpURLConnection urlc = (HttpURLConnection) (new URL("http://clients3.google.com/generate_204").openConnection());
				urlc.setRequestProperty("User-Agent", "Android");
				urlc.setRequestProperty("Connection", "close");
				urlc.setConnectTimeout(1500);
				urlc.connect();
				return (urlc.getResponseCode() == 204 && urlc.getContentLength() == 0);
			} catch (IOException e) {

                //Logger.e("Util", e.getMessage());
			}
		} else {
            //Logger.d("Util", "No network available!");
		}
		return false;
	}


}
