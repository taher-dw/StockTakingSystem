package com.company.graduationproject.stocktakingsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nizam on 9/29/2017.
 */

public class ServerConnection  extends AsyncTask<String , Void ,String> {
    URL url;
    HttpConnection httpConnection = null;
    String jsonServerResponse ;
    Context context;
    int field;
    MainActivity mainActivity;



    public ServerConnection(Context c, int f){
        context=c;
        field=f;
        mainActivity=(MainActivity)c;
        /*
        dialog = new ProgressDialog(mainActivity);
        dialog.setCancelable(false);*/
    }

    @Override
    protected void onPreExecute() {
        //Toast.makeText(this, server_response, Toast.LENGTH_SHORT).show();
       mainActivity.startLoadingPanel(field);
    }

    @Override
    protected String doInBackground(String... params) {

        String result ="";
        httpConnection = new HttpConnection();

        if(params[0].equals("GET")){
            result = httpConnection.executeUsingGET(params[1]);
        }
        else {
            //result = httpConnection.executeUsingPOST(params[1]);
        }
        return result;

    }


    @Override
    protected void onPostExecute(String responseString) {
        super.onPostExecute(responseString);

        switch (field){

            case 0:
                //responseString="There is an exception";
                List<DB.StockTaking> stockTakings = new ArrayList<DB.StockTaking>();
                if (!responseString.contentEquals("There is an exception")) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<DB.StockTaking>>(){}.getType();
                    stockTakings = gson.fromJson(responseString,type);

                    mainActivity.setStockTakingsFlag(true,stockTakings);

                } else {
                    Util.logInformation(context ,"WebChannel.getUnits response from server is invalid=" + responseString);
                    mainActivity.setStockTakingsFlag(false,stockTakings);
                }
                break;
            case 1:
                try{
                    List<DB.StockTakingSessions> sessions = new ArrayList<DB.StockTakingSessions>();
                    if (!responseString.contentEquals("There is an exception")) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<DB.StockTakingSessions>>(){}.getType();
                        sessions = gson.fromJson(responseString,type);
                        mainActivity.setSessionsFlag(true,sessions);

                    } else {
                        Util.logInformation(context ,"WebChannel.getUnits response from server is invalid=" + responseString);
                        mainActivity.setSessionsFlag(false,sessions);
                    }
                }catch (Exception e){
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }

                break;
            case 2:
                List<DB.Stock> stocks = new ArrayList<DB.Stock>();
                if (!responseString.contentEquals("There is an exception")) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<DB.Stock>>(){}.getType();
                    stocks = gson.fromJson(responseString,type);
                    mainActivity.setStocksFlag(true,stocks);

                } else {
                    Util.logInformation(context ,"WebChannel.getUnits response from server is invalid=" + responseString);
                    mainActivity.setStocksFlag(false,stocks);
                }
                break;
            case 3:
                List<DB.Store> stores = new ArrayList<DB.Store>();
                if (!responseString.contentEquals("There is an exception")) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<DB.Store>>(){}.getType();
                    stores = gson.fromJson(responseString,type);

                    mainActivity.setStoresFlag(true,stores);

                } else {
                    Util.logInformation(context ,"WebChannel.getUnits response from server is invalid=" + responseString);
                    mainActivity.setStoresFlag(false,stores);
                }
                break;
            case 4:
                List<DB.Units> units = new ArrayList<DB.Units>();
                if (!responseString.contentEquals("There is an exception")) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<DB.Units>>(){}.getType();
                    units = gson.fromJson(responseString,type);
                    mainActivity.setUnitsFlag(true,units);

                } else {
                    Util.logInformation(context ,"WebChannel.getUnits response from server is invalid=" + responseString);
                    mainActivity.setUnitsFlag(false,units);
                }
                break;

        }

        /*
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }*/
        //Toast.makeText(this, server_response, Toast.LENGTH_SHORT).show();

    }
}
