package com.company.graduationproject.stocktakingsystem;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class InventoryActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnStartStocktaking, btnChooseInventoryFields, btnDialogSave, btnDialogExit , btnPostingStockTaking , btnCloseSession , btnDisplayStockTakingTransactions;
    CheckBox chkItemName, chkBarcodeNumber, chkFactoryNumber, chkOriginalNumber;
    Spinner spInventory, spInventorySession, spInventoryIDs, spSessionIDs;
    EditText etInventoryDate, etWarehouse;
    Dialog fieldsDialog;
    TextView tvSessionstatus  , tvStockTakingInfo;
    DB db;
    ProgressDialog pd;
    boolean isItemNameChecked, isBarcodeNumberChecked, isFactoryNumberChecked, isOriginalNumberChecked;
    String[] requiredIDs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        initialize();
        //String where = DB.StockTakingSessionsEntity.KEY_STOCK_TAKING_SESSIONS_CODE + " = '"+spInventoryIDs.getSelectedItem().toString()+"' and "
        //      +DB.StockTakingSessionsEntity.KEY_STOCK_TAKING_SESSIONS_SESSION+" = "+spSessionIDs.getSelectedItem().toString();
        //updateSessionStatus(0,where);

    }


    private void initialize() {

        try {

        isItemNameChecked = isBarcodeNumberChecked = isFactoryNumberChecked = isOriginalNumberChecked = false;
        tvSessionstatus = (TextView)findViewById(R.id.tvSessionStatus);
        tvStockTakingInfo = (TextView)findViewById(R.id.tvStockTakingInfo);
        btnStartStocktaking = (Button) findViewById(R.id.btnStartStocktaking);
        btnStartStocktaking.setOnClickListener(this);
        btnChooseInventoryFields = (Button) findViewById(R.id.btnStocktakingFields);
        btnChooseInventoryFields.setOnClickListener(this);
        btnDisplayStockTakingTransactions = (Button) findViewById(R.id.btnDisplayStockTaking);
        btnDisplayStockTakingTransactions.setOnClickListener(this);
        btnCloseSession = (Button) findViewById(R.id.btnCloseSession);
        btnCloseSession.setOnClickListener(this);
        btnPostingStockTaking = (Button) findViewById(R.id.btnPostingStockTaking);
        btnPostingStockTaking.setOnClickListener(this);

        // btnChooseInventoryFields.setOnCreateContextMenuListener(this);
        // registerForContextMenu(btnChooseInventoryFields);
        spInventory = (Spinner) findViewById(R.id.spInventoryNames);
        spInventorySession = (Spinner) findViewById(R.id.spInventorySessions);
        spInventoryIDs = (Spinner) findViewById(R.id.spInventoryNamesIDs);
        spSessionIDs = (Spinner) findViewById(R.id.spInventorySessionsIDs);
        // etInventoryDate = (EditText) findViewById(R.id.etInventoryDate);
        // etWarehouse = (EditText) findViewById(R.id.etWarehouse);
        // etInventoryDate.setEnabled(false);
        // etWarehouse.setEnabled(false);
        pd = new ProgressDialog(InventoryActivity.this);
        db = new DB(this);

        //  fillInventoryNames();
        // fillInventoryIDs();
        // getRequiredIDs();



            newFillInventoryNames();
            newFillInventoryIDs();
            newFillInventorySessions(spInventoryIDs.getSelectedItem() + "");
            int status = getSessionStatus();
            setCloseOrPostStatus(status);
            //newGetRequiredIDs();


            spInventory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    //  String inventoryId = requiredIDs[spInventory.getSelectedItemPosition()].split("#")[0];

                    spInventoryIDs.setSelection(position);
                    newFillInventorySessions(spInventoryIDs.getSelectedItem() + "");
                    // spInventoryIDs.getSelectedItem() + "";
                    //fillInventorySessions(spInventoryIDs.getSelectedItem() + "");


                    //  Toast.makeText(InventoryActivity.this, spInventoryIDs.getSelectedItem() + "", Toast.LENGTH_SHORT).show();

                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            spInventorySession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    spSessionIDs.setSelection(position);
                    db.open(true);
                    DB.StockTakingSessionsEntity stse = db.new StockTakingSessionsEntity();

                    String where = DB.StockTakingSessionsEntity.KEY_STOCK_TAKING_SESSIONS_CODE + " = '" + spInventoryIDs.getSelectedItem().toString().trim() + "' and " + DB.StockTakingSessionsEntity.KEY_STOCK_TAKING_SESSIONS_SESSION
                            + " = " + spSessionIDs.getSelectedItem().toString().trim();
                    DB.StockTakingSessions sts = stse.getStockTakingSessions(where).get(0);
                    DB.StoreEntity storeE = db.new StoreEntity();
                    DB.Store store = storeE.get(DB.StoreEntity.KEY_ID + " = " + sts.storeID).get(0);
                    db.close();
                    //tvStockTakingInfo.setText(Util.getMsg(InventoryActivity.this, R.string.title_inventory_activity_session_stockTaking_date) + Util.dateAsStr_DDMMYYYY(sts.date));
                    tvStockTakingInfo.setText(Util.getMsg(InventoryActivity.this, R.string.title_inventory_activity_session_stockTaking_date)+sts.date+"");
                    tvStockTakingInfo.append("\n");
                    tvStockTakingInfo.append(Util.getMsg(InventoryActivity.this, R.string.title_inventory_activity_admin_name) + sts.adminName);
                    tvStockTakingInfo.append("\n");
                    tvStockTakingInfo.append(Util.getMsg(InventoryActivity.this, R.string.title_inventory_activity_store_name) + store.name);
                    tvStockTakingInfo.append("\n");

                    //etInventoryDate.setText(Util.getMsg(InventoryActivity.this, R.string.title_inventory_activity_session_stockTaking_date)+Util.dateAsStr_DDMMYYYY(sts.date)+"----"+Util.getMsg(InventoryActivity.this, R.string.title_inventory_activity_admin_name)+sts.adminName);
                    //etWarehouse.setText(Util.getMsg(InventoryActivity.this, R.string.title_inventory_activity_store_name)+store.name);

                    int status = getSessionStatus();
                    setCloseOrPostStatus(status);

                /*
                DB.StockTakingEntity ste = db.new StockTakingEntity();
                int status = ste.getNumberOfAvailableStockTakingRecords("0=0");
                btnPostingStockTaking.setTag("0");
                */
                    //  etInventoryDate.setText(Util.dateAsStr_DDMMYYYY(is.stockTakingDate));

                    //  Toast.makeText(InventoryActivity.this, spSessionIDs.getSelectedItem() + "", Toast.LENGTH_SHORT).show();

                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        catch (Exception ex){
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        }

        /*
        btnPostingStockTaking.setTag("0");
        if (btnPostingStockTaking.getTag().toString().equals("0")) {
            btnPostingStockTaking.setText("إغلاق الجلسة");

        }
        else if (btnPostingStockTaking.getTag().toString().equals("1")) {
            btnPostingStockTaking.setText("ترحيل الجلسة");
        }
        */


    }

    private void setCloseOrPostStatus(int status) {
        switch (status){

            case 0 :
                tvSessionstatus.setText("مفتوح");
                btnStartStocktaking.setVisibility(View.VISIBLE);
                btnCloseSession.setVisibility(View.VISIBLE);
                btnDisplayStockTakingTransactions.setVisibility(View.GONE);
                btnPostingStockTaking.setVisibility(View.GONE);

                break;
            case 1:
                tvSessionstatus.setText("مغلق");
                btnDisplayStockTakingTransactions.setVisibility(View.VISIBLE);
                btnPostingStockTaking.setVisibility(View.VISIBLE);
                btnStartStocktaking.setVisibility(View.GONE);
                btnCloseSession.setVisibility(View.GONE);
                break;
            case 2:
                tvSessionstatus.setText("مرحل");
                btnDisplayStockTakingTransactions.setVisibility(View.VISIBLE);
                btnPostingStockTaking.setVisibility(View.GONE);
                btnStartStocktaking.setVisibility(View.GONE);
                btnCloseSession.setVisibility(View.GONE);
                break;




        }
    }

    private int getSessionStatus() {

        try {

            db.open(true);
            DB.StockTakingSessionsEntity stse = db.new StockTakingSessionsEntity();
            int status = stse.getSessionStatus(DB.StockTakingSessionsEntity.KEY_STOCK_TAKING_SESSIONS_CODE + " = '" + spInventoryIDs.getSelectedItem().toString() + "' and "
                    + DB.StockTakingSessionsEntity.KEY_STOCK_TAKING_SESSIONS_SESSION + " = " + spSessionIDs.getSelectedItem().toString());
            db.close();


            return status;
        }
        catch (Exception ex){
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
        return -1;
    }

/*
    private List<DB.InventoryStock> getInventoryNamesFromDatabase() {


        db.open(true);
        DB.InventoryStockEntity inventoryStockEntity = db.new InventoryStockEntity();
        List<DB.InventoryStock> inventoryStocks = inventoryStockEntity.getAllDistinct(" stockTakingStatus = 0 ");
        db.close();
        return inventoryStocks;
    }


    private void fillInventoryNames() {

        List<DB.InventoryStock> list = getInventoryNamesFromDatabase();

        String[] names = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {

            names[i] = list.get(i).stockTakingName;
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInventory.setAdapter(dataAdapter);

    }
*/

    private List<DB.StockTaking> newGetInventoryNamesFromDatabase() {


        db.open(true);
        DB.StockTakingEntity stockTakingEntity = db.new StockTakingEntity();
        List<DB.StockTaking> inventoryStocks = stockTakingEntity.getStockTakingRecords(/*"0 = 0 "*/DB.StockTakingEntity.KEY_STOCK_TAKING_STATUS + " = 0 ");
        db.close();
        return inventoryStocks;
    }
    private void newFillInventoryNames() {
        List<DB.StockTaking> list = newGetInventoryNamesFromDatabase();

        String[] names = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {

            names[i] = list.get(i).name;
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInventory.setAdapter(dataAdapter);
    }

    /*
    private List<DB.InventoryStock> getInventoryIDsFromDatabase() {


        db.open(true);
        DB.InventoryStockEntity inventoryStockEntity = db.new InventoryStockEntity();
        List<DB.InventoryStock> inventoryStocks = inventoryStockEntity.getAllDistinct(" stockTakingStatus = 0 ");
        db.close();
        return inventoryStocks;
    }*/

    private void fillInventoryIDs() {
/*
        List<DB.InventoryStock> list = getInventoryIDsFromDatabase();

        String[] ids = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {

            ids[i] = String.valueOf(list.get(i).stockTakingNumber);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ids);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInventoryIDs.setAdapter(dataAdapter);
*/

    }

    private List<DB.StockTaking> newGetInventoryIDsFromDatabase() {


        db.open(true);
        DB.StockTakingEntity stockTakingEntity = db.new StockTakingEntity();
        List<DB.StockTaking> inventoryStocks = stockTakingEntity.getStockTakingRecords(/*" 0 = 0 "*/DB.StockTakingEntity.KEY_STOCK_TAKING_STATUS + " = 0 ");
        db.close();
        return inventoryStocks;
    }
    private void newFillInventoryIDs() {
        List<DB.StockTaking> list = newGetInventoryIDsFromDatabase();

        String[] ids = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {

            ids[i] = list.get(i).stockTakingCode;
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ids);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInventoryIDs.setAdapter(dataAdapter);
    }

/*    private void getRequiredIDs() {
        db.open(true);
        DB.InventoryStockEntity inventoryStockEntity = db.new InventoryStockEntity();
        List<DB.InventoryStock> list = inventoryStockEntity.get(" stockTakingStatus = 0 ");
        db.close();
        requiredIDs = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            requiredIDs[i] = list.get(i).stockTakingNumber + "#" + list.get(i).sessionNumber + "#" + list.get(i).stockNumber + "#" + list.get(i).adminNumber;

        }


    }
    */

    private void newGetRequiredIDs() {
        db.open(true);
        DB.StockTakingEntity stockTakingEntity = db.new StockTakingEntity();
        DB.StockTakingSessionsEntity stockTakingSessionsEntity = db.new StockTakingSessionsEntity();
        List<DB.StockTakingSessions> stockTakingSessions = stockTakingSessionsEntity.getStockTakingSessions(" 0 = 0 ");
        int id =-2;
        String stockTakingCode ="";
        requiredIDs = new String[stockTakingSessions.size()];
        for(int j=0;j<stockTakingSessions.size();j++) {
            stockTakingCode = stockTakingSessions.get(j).stockTakingSessionsCode;
            id = stockTakingEntity.checkExistingByID( DB.StockTakingEntity.KEY_STOCK_TAKING_CODE + " =  '"+stockTakingCode+"' and "+DB.StockTakingEntity.KEY_STOCK_TAKING_STATUS +" = 0" );
            if(id!=-1) {
                requiredIDs[j] = stockTakingSessions.get(j).stockTakingSessionsCode + "#" + stockTakingSessions.get(j).session + "#" + stockTakingSessions.get(j).storeID + "#" + stockTakingSessions.get(j).storeID;
            }
            else {
                requiredIDs[j]="";
            }
        }


    }

    /*
    private List<String> getInventorySessionsFromDatabaseByInventoryID(String s) {
        db.open(true);
        DB.InventoryStockEntity inventoryStockEntity = db.new InventoryStockEntity();
        List<String> sessionNames = inventoryStockEntity.getSessionNamesAndIDs(" stockTakingNumber = " + s + " and stockTakingStatus = 0 ");
        db.close();
        return sessionNames;

    }
    private void fillInventorySessions(String s) {

        List<String> list = getInventorySessionsFromDatabaseByInventoryID(s);
        String names[], ids[];
        names = new String[list.size()];
        ids = new String[list.size()];

        String values[];

        for (int i = 0; i < list.size(); i++) {

            values = list.get(i).split("#");

            names[i] = values[0];
            ids[i] = values[1];
        }

        ArrayAdapter<String> namesDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
        namesDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInventorySession.setAdapter(namesDataAdapter);

        ArrayAdapter<String> idsDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ids);
        idsDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSessionIDs.setAdapter(idsDataAdapter);


    }*/


    private List<String> newGetInventorySessionsFromDatabaseByInventoryID(String s) {
        List<String> list = new ArrayList<String>();
        db.open(true);
        DB.StockTakingEntity stockTakingEntity = db.new StockTakingEntity();
        DB.StockTakingSessionsEntity stockTakingSessionsEntity = db.new StockTakingSessionsEntity();
        String sessionWhere ="";
        if(!s.equals("")){sessionWhere=DB.StockTakingSessionsEntity.KEY_STOCK_TAKING_SESSIONS_CODE + "= '"+s+"'";}
        else {sessionWhere=" 0=0";}
        List<DB.StockTakingSessions> stockTakingSessions = stockTakingSessionsEntity.getStockTakingSessions(sessionWhere);
        int id =-2;
        String stockTakingCode ="";
        requiredIDs = new String[stockTakingSessions.size()];
        for(int j=0;j<stockTakingSessions.size();j++) {
            stockTakingCode = stockTakingSessions.get(j).stockTakingSessionsCode;
            String where = "";
            if(!s.equals("")){
                where=DB.StockTakingEntity.KEY_STOCK_TAKING_CODE + " =  '"+s+"' and "+DB.StockTakingEntity.KEY_STOCK_TAKING_STATUS +" = 0";
            }
            else{
                where=DB.StockTakingEntity.KEY_STOCK_TAKING_CODE + " =  '"+stockTakingCode+"' and "+DB.StockTakingEntity.KEY_STOCK_TAKING_STATUS +" = 0";
            }
            id = stockTakingEntity.checkExistingByID( where);
            if(id!=-1) {
                requiredIDs[j] = stockTakingSessions.get(j).session + "#" + stockTakingSessions.get(j).session + "#" + stockTakingSessions.get(j).storeID + "#" + stockTakingSessions.get(j).storeID;
                //requiredIDs[j] = "AAAAA" + "#" +"11111" + "#" + stockTakingSessions.get(j).storeID + "#" + stockTakingSessions.get(j).storeID;
                list.add(requiredIDs[j]);
            }
            else {
                requiredIDs[j]="";
            }
        }
        return list;

        /*
        db.open(true);
        DB.StockTakingEntity stockTakingEntity = db.new StockTakingEntity();
        List<DB.StockTaking> stockTaking = stockTakingEntity.getStockTakingRecords(DB.StockTakingEntity.KEY_STOCK_TAKING_STATUS + " = 0 ");

        DB.StockTakingSessionsEntity stockTakingSessionsEntity = db.new StockTakingSessionsEntity();
        List<DB.StockTakingSessions> stockTakingSessions = stockTakingSessionsEntity.getStockTakingSessions(" 0=0 ");
        DB.StockTakingSessions sts = db.new StockTakingSessions();
        List<DB.StockTakingSessions> tempStockTakings = new ArrayList<DB.StockTakingSessions>();

        db.close();
       List<String> sessionsNames = new ArrayList<String>();
        String stockTakingCode ="";
        for (int i = 0; i < stockTaking.size(); i++) {
            stockTakingCode = stockTaking.get(i).stockTakingCode;
            tempStockTakings = stockTakingSessionsEntity.getStockTakingSessions(DB.StockTakingSessionsEntity.KEY_STOCK_TAKING_SESSIONS_CODE + " = '" +stockTakingCode +"'");
            for(int j=0;j<tempStockTakings.size();j++) {
                sessionsNames.add(tempStockTakings.get(i).session + "#" +  tempStockTakings.get(i).session);
            }

        }//*/
        //return  sessionsNames;
    }

    private void newFillInventorySessions(String s) {
        List<String> list = newGetInventorySessionsFromDatabaseByInventoryID(s);
        String names[], ids[];
        names = new String[list.size()];
        ids = new String[list.size()];

        String values[];

        for (int i = 0; i < list.size(); i++) {

            values = list.get(i).split("#");

            names[i] = values[0];
            ids[i] = values[1];
        }

        ArrayAdapter<String> namesDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
        namesDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInventorySession.setAdapter(namesDataAdapter);

        ArrayAdapter<String> idsDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ids);
        idsDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSessionIDs.setAdapter(idsDataAdapter);

    }





    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnStartStocktaking:
                try {
                    Intent iToStockTakingAvtivity = new Intent(InventoryActivity.this, stockTaking2.class);
                    iToStockTakingAvtivity.putExtra("isItemNameChecked", isItemNameChecked + "");
                    iToStockTakingAvtivity.putExtra("isBarcodeNumberChecked", isBarcodeNumberChecked + "");
                    iToStockTakingAvtivity.putExtra("isFactoryNumberChecked", isFactoryNumberChecked + "");
                    iToStockTakingAvtivity.putExtra("isOriginalNumberChecked", isOriginalNumberChecked + "");
                    iToStockTakingAvtivity.putExtra("stockTakingID", spInventoryIDs.getSelectedItem().toString() + "");
                    iToStockTakingAvtivity.putExtra("sessionID", spSessionIDs.getSelectedItem().toString() + "");
                    startActivity(iToStockTakingAvtivity);
                }
                catch (Exception e){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnStocktakingFields:


                showFieldsDialog();

                break;
            case R.id.btnDisplayStockTaking:
                Intent toInventoryTableActivity = new Intent(InventoryActivity.this, InventoryTableActivity.class);
                toInventoryTableActivity.putExtra("stockTakingID",spInventoryIDs.getSelectedItem() + "");
                toInventoryTableActivity.putExtra("sessionID",spSessionIDs.getSelectedItem() + "");
                startActivityForResult(toInventoryTableActivity, 1);
                break;
            case R.id.btnCloseSession:

                db.open(true);
                String undefinedWhere = DB.UndefinedStockTakingTransactionsEntity.KEY_UNDEFINED_STOCK_TAKING_ID_TRANSACTIONS + " = "+spInventoryIDs.getSelectedItem().toString()+" and "
                        + DB.UndefinedStockTakingTransactionsEntity.KEY_UNDEFINED_SESSION_ID_TRANSACTIONS + " = "+spSessionIDs.getSelectedItem().toString() ;
                DB.UndefinedStockTakingTransactionsEntity ustte = db.new UndefinedStockTakingTransactionsEntity();
                int numOfUstt =ustte.get(undefinedWhere).size();
                if(numOfUstt==0) {

                    String where = DB.StockTakingTransactionsEntity.KEY_STOCK_TAKING_ID_TRANSACTIONS + " = " + Integer.parseInt(spInventoryIDs.getSelectedItem().toString()) + " and "
                            + DB.StockTakingTransactionsEntity.KEY_SESSION_ID_TRANSACTIONS + " = " + Integer.parseInt(spSessionIDs.getSelectedItem().toString());
                    DB.StockTakingTransactionsEntity stte = db.new StockTakingTransactionsEntity();
                    int numOfStt = stte.get(where).size();
                    if (numOfStt > 0) {
                        where = DB.StockTakingSessionsEntity.KEY_STOCK_TAKING_SESSIONS_CODE + " = '"+spInventoryIDs.getSelectedItem().toString()+"' and "
                                +DB.StockTakingSessionsEntity.KEY_STOCK_TAKING_SESSIONS_SESSION+" = "+spSessionIDs.getSelectedItem().toString();
                        updateSessionStatus(1, where);
                        setCloseOrPostStatus(1);
                    } else {
                        Toast.makeText(this, "لا يوجد جرودات في هذه الجلسة ، لذلك لن يتم إغلاقها", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    new android.app.AlertDialog.Builder(this)
                            .setTitle("أصناف غير معرفة ")
                            .setMessage("يوجد هناك أصناف غير معرفة،لا يمكن إغلاق الجلسة قبل معالجتها")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .show();
                }
                db.close();
                break;

            case R.id.btnPostingStockTaking:
                if(Util.isConnectingToInternet(InventoryActivity.this)) {

                /*
                pd.setMessage("جاري ترحيل الجرودات ");
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setCancelable(false);
                pd.show();*/
                    String where2 = DB.StockTakingSessionsEntity.KEY_STOCK_TAKING_SESSIONS_CODE + " = '"+spInventoryIDs.getSelectedItem().toString()+"' and "
                            +DB.StockTakingSessionsEntity.KEY_STOCK_TAKING_SESSIONS_SESSION+" = "+spSessionIDs.getSelectedItem().toString();
                    postStockTakingSessionsToServer();
                    //pd.dismiss();

                }
                else {
                    Toast.makeText(this, "الرجاء التحقق من الاتصال بالانترنت ", Toast.LENGTH_SHORT).show();

                }
                break;



        }
    }

    private void updateSessionStatus(int status, String where) {
        db.open(true);
        DB.StockTakingSessionsEntity stse = db.new StockTakingSessionsEntity();
        stse.updateSessionStatus(status,where);
        db.close();
    }

    private void showAlertDialogForUndefinedStockTakingTransactions() {
        new android.app.AlertDialog.Builder(InventoryActivity.this)
                .setTitle("ترحيل الجرد")
                .setMessage("لا يمكن ترحيل الجرد قبل التأكد من عدم وجود أصناف مجرودة غير معرفة")
                .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();
    }

    private void downloadTransactions(){


    }
    private void postStockTakingSessionsToServer() {


///*

        try {

            List<DB.StockTakingTransactions> stt = new ArrayList<DB.StockTakingTransactions>();
            final ArrayList<StockTakingTransactionsDTO> sttDTO = new ArrayList<StockTakingTransactionsDTO>();
            db.open(true);
            DB.StockEntity stockE = db.new StockEntity();
            DB.StockTakingTransactionsEntity sttE = db.new StockTakingTransactionsEntity();


            String whereFromStockTakingTransactions = DB.StockTakingTransactionsEntity.KEY_IS_POSTED + " = 0 and "
                    + DB.StockTakingTransactionsEntity.KEY_STOCK_TAKING_ID_TRANSACTIONS + " = "+spInventoryIDs.getSelectedItem()+" and "
                    + DB.StockTakingTransactionsEntity.KEY_SESSION_ID_TRANSACTIONS + " = "+Integer.parseInt(spSessionIDs.getSelectedItem().toString()) ;
            stt = sttE.get(whereFromStockTakingTransactions);
            StockTakingTransactionsDTO stDTO = new StockTakingTransactionsDTO();
            final Gson s = new Gson();



            if (stt.size() > 0) {

                pd.setMessage("جاري ترحيل الجرودات ");
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setCancelable(false);
                pd.show();

                for (int i = 0; i < stt.size(); i++) {

                    ///*
                    stDTO.stockTakingCode = stt.get(i).stockTatkingID;
                    stDTO.sessionCode = stt.get(i).sessionID;
                    stDTO.unitID = stt.get(i).unitID;
                    stDTO.itemCode = stockE.getItem(stt.get(i).itemID).code;
                    stDTO.stockTakingDate = Util.dateAsStr_YYYYMMDD(stt.get(i).stockTakingDate);
                    //stDTO.stockTakingDate = Util.dateAsStr_DDMMYYYY(stt.get(i).stockTakingDate);
                    stDTO.quantity = stt.get(i).quantity;
                    stDTO.salesman = Settings.SALESMAN;
                    stDTO.deviceID = Settings.DEVICEID;
                    sttDTO.add(stDTO);
                    stDTO = new StockTakingTransactionsDTO();
                    //*/

                    /*
                    stDTO.stockTakingCode = stt.get(1).stockTatkingID;
                    stDTO.sessionCode = stt.get(1).sessionID;
                    stDTO.unitID = stt.get(1).unitID;
                    stDTO.itemCode = stockE.get_Item(stt.get(1).itemID).code;
                    stDTO.stockTakingDate = Util.dateAsStr_DDMMYYYY(stt.get(1).stockTakingDate);
                    stDTO.quantity = stt.get(1).quantity;
                    stDTO.salesman = Settings.SALESMAN;
                    stDTO.deviceID = Settings.DEVICEID+""+i;
                    sttDTO.add(stDTO);
                    stDTO = new StockTakingTransactionsDTO();*/
                }


                final byte[] c = s.toJson(sttDTO, sttDTO.getClass()).getBytes("UTF-8");

                Ion.with(this)
                        //.load(WebChannel.getURI() + "?type=test")
                        ///*This line is for testing issues only*/.load("http://192.168.222.203:9200/androidpage.aspx?type=SaveStockTakingTransactions")
                        /*This is the correct URI to use the above is for testing issues only*/ .load(WebChannel.getURI()+"?type=SaveStockTakingTransactions")
                        .progressDialog(pd)
                        .setTimeout(180000)
                        .setByteArrayBody(c)
                        .asString()
                        .withResponse()
                        .setCallback(new FutureCallback<Response<String>>() {
                            public void onCompleted(Exception e, Response<String> result) {

                                if (e == null) {

                                    try {
                                        int numberOfStockTakingTransactionsPostedToServer = Integer.parseInt(result.getResult().toString());
                                        if (sttDTO.size() == numberOfStockTakingTransactionsPostedToServer) {
                                            String where = DB.StockTakingSessionsEntity.KEY_STOCK_TAKING_SESSIONS_CODE + " = '"+spInventoryIDs.getSelectedItem().toString()+"' and "
                                                    +DB.StockTakingSessionsEntity.KEY_STOCK_TAKING_SESSIONS_SESSION+" = "+spSessionIDs.getSelectedItem().toString();

                                            updateSessionStatus(2,where);
                                            setCloseOrPostStatus(2);
                                            Toast.makeText(InventoryActivity.this, "تمت عملية الترحيل بنجاح", Toast.LENGTH_SHORT).show();
                                            pd.dismiss();


                                        } else {
                                            Toast.makeText(InventoryActivity.this, "فشلت عملية الترحيل الرجاء المحاولة مرة أخرى", Toast.LENGTH_SHORT).show();
                                            pd.dismiss();
                                        }


                                        // important //  StringBuilder rstBuilder = new StringBuilder(Charset.forName("UTF-8").decode(ByteBuffer.wrap(result.getResult())));
                                    } catch (Exception e1) {
                                        //Toast.makeText(InventoryActivity.this, "Exception \n" + e1.toString(), Toast.LENGTH_SHORT).show();
                                        Util.logException(InventoryActivity.this, e1);
                                        pd.dismiss();
                                        //Toast.makeText(InventoryActivity.this, "فشلت عملية الترحيل الرجاء المحاولة مرة أخرى", Toast.LENGTH_SHORT).show();
                                    }


                                } else {
                                    //Toast.makeText(InventoryActivity.this, "From IONNNNNNNNNNNNN " + e.toString(), Toast.LENGTH_LONG).show();
                                    pd.dismiss();
                                    Util.logException(InventoryActivity.this, e);
                                }
                            }

                        });

            }
            else{
                Toast.makeText(InventoryActivity.this, " لا يوجد جرودات للترحيل بعد", Toast.LENGTH_SHORT).show();
            }

        }


        catch(Exception e){
            // Toast.makeText(InventoryActivity.this,"Fom MobileEEEEEEEEEEEEEE "+e.toString(), Toast.LENGTH_SHORT).show();
            Util.logException(InventoryActivity.this,e);

        }
        finally{
            db.close();
        }


    }
    private void TestpostStockTakingSessionsToServer() {


///*

        try {
            final ArrayList<DB.StockTakingTransactions> stt =  new ArrayList<DB.StockTakingTransactions>();
            db.open(true);
            //  List<DB.StockTakingTransactions> stt =  new ArrayList<DB.StockTakingTransactions>();
            DB.StockTakingTransactions st =  db.new StockTakingTransactions() ;
            File root = new File(Environment.getExternalStorageDirectory(), "ShamelLogExcep");

            if (!root.exists()) {
                root.mkdirs();

            }
            final File gpxfile = new File(root, "aa"+".txt");
            final BufferedWriter bW = new BufferedWriter(new FileWriter(gpxfile, true));
            final StringBuilder message = new StringBuilder();
            final Gson s = new Gson();
            String transactionToBeWritten = "";
            final StringBuilder sb = new StringBuilder();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            b.write(transactionToBeWritten.getBytes());



            for(int i=0;i<50000;i++){


                st.itemName="ItemName"+i;
                st.itemID=i;
                st.unitID=i;
                st.unitName="UnitName"+i;
                st.stockTakingDate=i;
                st.sessionID=i*50;
                st.quantity=i*80;
                st.id=i*70;
                // sb.append(s.toJson(st));
                stt.add(st);




            }


            // bW.write(sb.toString());
            //bW.flush();
            //bW.close();



/*

            File root = new File(Environment.getExternalStorageDirectory(), "ShamelLogExcep");

            if (!root.exists()) {
                root.mkdirs();

            }
            final File gpxfile = new File(root, "aa"+".txt");
            BufferedWriter bW = new BufferedWriter(new FileWriter(gpxfile, true));
            String message = "hello";
            bW.write(message);
            bW.flush();
            bW.close();
*/
            // ByteBuffer bb=Charset.forName("UTF-8").encode(sb.toString());
            final String g = s.toJson(stt,stt.getClass());
            final byte[] c = s.toJson(stt,stt.getClass()).getBytes("UTF-8");
            //final byte[] c = sb.toString().getBytes("UTF-8");



            Ion.with(this)
                    .load(WebChannel.getURI() + "?type=GetUnits")
                    .setByteArrayBody(c)
                    .asByteArray()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<byte[]>>() {
                        public void onCompleted(Exception e, Response<byte[]> result) {

                            if (e == null) {

                                try {
                                    Toast.makeText(InventoryActivity.this,  c.length+"\n"+result.getResult().length+"" , Toast.LENGTH_SHORT).show();
                                    // String stResult = new String(result.getResult(), "UTF-8");
                                    StringBuilder rstBuilder = new StringBuilder(Charset.forName("UTF-8").decode(ByteBuffer.wrap(result.getResult())));
                                    //  bW.write(stResult);
                                    //  bW.flush();
                                    //  bW.close();
                                    if(g.equals(rstBuilder.toString())){
                                        Toast.makeText(InventoryActivity.this, "YESSSSSSSSSSSSSSSSSSS", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(InventoryActivity.this, "", Toast.LENGTH_SHORT).show();
                                    }
                                    ArrayList<DB.StockTakingTransactions> transactionsResult = s.fromJson(g,  stt.getClass());
                                    Toast.makeText(InventoryActivity.this, ""+transactionsResult.size(), Toast.LENGTH_SHORT).show();


                                }
                                catch(OutOfMemoryError ee){
                                    Util.logException(InventoryActivity.this , e);
                                    //Toast.makeText(InventoryActivity.this, "Out of memory Exception \n "+ee.toString(), Toast.LENGTH_SHORT).show();
                                }/*
                                catch (UnsupportedEncodingException e1) {
                                    Toast.makeText(InventoryActivity.this, "Unsupported Encoding Exception \n"+e1.toString(), Toast.LENGTH_SHORT).show();
                                }*/
                                catch (Exception e1) {
                                    Util.logException(InventoryActivity.this , e);
                                    // Toast.makeText(InventoryActivity.this, "Exception \n"+e1.toString(), Toast.LENGTH_SHORT).show();
                                }



                            } else {
                                Util.logException(InventoryActivity.this , e);
                                // Toast.makeText(InventoryActivity.this, "From IONNNNNNNNNNNNN " + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



            /*
            //File Solution using ION
             Ion.with(this)
                    .load(WebChannel.getURI() + "?type=GetUnits")
                   .setMultipartFile("Hi",gpxfile)
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>() {
                        public void onCompleted(Exception e, Response<String> result) {

                            if (e == null) {
                                //Toast.makeText(InventoryActivity.this, result.getResult(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(InventoryActivity.this,  gpxfile.length()+"\n"+result.getResult()+"" , Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(InventoryActivity.this, "From IONNNNNNNNNNNNN " + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            * */



            /*
            Ion.with(this)
                    .load(WebChannel.getURI() + "?type=GetUnits")
                    //.setTimeout(60000)
                    //.setBodyParameter("data",new Gson().toJson(stt))
                    //.setByteArrayBody(bytes)
                    .setStreamBody(null)
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>() {
                        public void onCompleted(Exception e, Response<String> result) {
                            if (e != null) {
                                //Toast.makeText(InventoryActivity.this, result.getResult(), Toast.LENGTH_SHORT).show();

                                Toast.makeText(InventoryActivity.this, bytes.length+"", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(InventoryActivity.this, result.getResult(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            */

        }
        catch(OutOfMemoryError ee){
            // Util.logException(InventoryActivity.this , ee);
            //Toast.makeText(InventoryActivity.this,"Fom MobileEEEEEEEEEEEEEE "+ee.toString(), Toast.LENGTH_SHORT).show();

        }
        catch(Exception e){
            Util.logException(InventoryActivity.this , e);
            //Toast.makeText(InventoryActivity.this,"Fom MobileEEEEEEEEEEEEEE "+e.toString(), Toast.LENGTH_SHORT).show();

        }
        finally{
            db.close();
        }
        //  */
        /*
        try {
            db = new DB(this);
            db.open(true);
            DB.InventoryStockEntity user = db.new InventoryStockEntity();
            DB.InventoryStock is = db.new InventoryStock();
            user.deleteTempInventoryStock(1);

            is.stocktakingStatus=0;
            is.stockTakingNumber = 1;
            is.stockTakingName = "جرد نابلس";
            is.sessionName = "جلسة تجريب";
            is.sessionNumber = 1;
            is.stockName = "مستودع رئيسي";
            is.stockNumber = 1;
            is.adminName = "مسؤول 5";
            is.adminNumber = 5;
            is.stockTakingDate = 2812017;
            user.insert(is);

            is.stocktakingStatus=0;
            is.stockTakingNumber = 1;
            is.stockTakingName = "جرد نابلس";
            is.sessionName = "جلسة ممممممم";
            is.sessionNumber = 2;
            is.stockName = "مستودع عسكر";
            is.stockNumber = 2;
            is.adminName = "مسؤول 2";
            is.adminNumber = 2;
            is.stockTakingDate = 2812017;
            user.insert(is);

            is.stocktakingStatus=0;
            is.stockTakingNumber = 1;
            is.stockTakingName = "جرد نابلس";
            is.sessionName = "جلسة ننننننن";
            is.sessionNumber = 3;
            is.stockName = "مستودع عسكر كككك";
            is.stockNumber = 3;
            is.adminName = "مسؤول 2";
            is.adminNumber = 2;
            is.stockTakingDate = 2812017;
            user.insert(is);

            is.stocktakingStatus=0;
            is.stockTakingNumber = 2;
            is.stockTakingName = "جرد رام الله";
            is.sessionName = "جلسة أأأأأ";
            is.sessionNumber = 4;
            is.stockName = "مستودع الرئيسي";
            is.stockNumber = 4;
            is.adminName = "مسؤول 3";
            is.adminNumber = 3;
            is.stockTakingDate = 2812017;
            user.insert(is);

            is.stocktakingStatus=0;
            is.stockTakingNumber = 2;
            is.stockTakingName = "جرد رام الله";
            is.sessionName = "جلسة بببببببب";
            is.sessionNumber = 5;
            is.stockName = "مستودع الرئيسي";
            is.stockNumber = 5;
            is.adminName = "مسؤول 4";
            is.adminNumber = 4;
            is.stockTakingDate = 2812017;
            user.insert(is);


            List<DB.InventoryStock> users = user.get("0=0");
            db.close();
            String a = "";

            for (int i = 0; i < users.size(); i++) {

                a += users.get(i).stockTakingName + "\n";

            }
            Toast.makeText(this, a, Toast.LENGTH_SHORT).show();



        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }//*/


    }

    private void showFieldsDialog() {

        fieldsDialog = new Dialog(this);
        fieldsDialog.setContentView(R.layout.inventory_fields_dialog);
        chkItemName = (CheckBox) fieldsDialog.findViewById(R.id.chkItemName);
        chkBarcodeNumber = (CheckBox) fieldsDialog.findViewById(R.id.chkBarcodeNumber);
        chkFactoryNumber = (CheckBox) fieldsDialog.findViewById(R.id.chkFactoryNumber);
        chkOriginalNumber = (CheckBox) fieldsDialog.findViewById(R.id.chkOriginalNumber);
        btnDialogSave = (Button) fieldsDialog.findViewById(R.id.btnDialogSave);
        btnDialogExit = (Button) fieldsDialog.findViewById(R.id.btnDialogExit);
        fieldsDialog.show();

        String value=Util.getPrefAsString(InventoryActivity.this,"isItemNameChecked","");
        if(!value.equals("")){
            chkItemName.setChecked(Boolean.parseBoolean(value));
        }
        value =Util.getPrefAsString(InventoryActivity.this,"isBarcodeNumberChecked","");
        if(!value.equals("")){
            chkBarcodeNumber.setChecked(Boolean.parseBoolean(value));
        }
        value =Util.getPrefAsString(InventoryActivity.this,"isFactoryNumberChecked","");
        if(!value.equals("")){
            chkFactoryNumber.setChecked(Boolean.parseBoolean(value));
        }
        value =Util.getPrefAsString(InventoryActivity.this,"isOriginalNumberChecked","");
        if(!value.equals("")){
            chkOriginalNumber.setChecked(Boolean.parseBoolean(value));

        }



        btnDialogSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {



                if (chkItemName.isChecked()) {
                    isItemNameChecked = true;
                } else {
                    isItemNameChecked = false;
                }

                if (chkBarcodeNumber.isChecked()) {

                    isBarcodeNumberChecked = true;

                } else {
                    isBarcodeNumberChecked = false;
                }
                if (chkFactoryNumber.isChecked()) {

                    isFactoryNumberChecked = true;
                } else {
                    isFactoryNumberChecked = false;
                }
                if (chkOriginalNumber.isChecked()) {

                    isOriginalNumberChecked = true;
                } else {
                    isOriginalNumberChecked = false;
                }

                Util.setPrefAsString(InventoryActivity.this,"isItemNameChecked",""+isItemNameChecked);
                Util.setPrefAsString(InventoryActivity.this,"isBarcodeNumberChecked",""+isBarcodeNumberChecked);
                Util.setPrefAsString(InventoryActivity.this,"isFactoryNumberChecked",""+isFactoryNumberChecked);
                Util.setPrefAsString(InventoryActivity.this,"isOriginalNumberChecked",""+isOriginalNumberChecked);


                fieldsDialog.dismiss();

            }
        });

        btnDialogExit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fieldsDialog.dismiss();
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("اختيار الحقول ");
        menu.add(0, Menu.FIRST, 0, "اسم الصنف").setCheckable(true).setChecked(false);
        menu.add(0, Menu.FIRST + 1, 0, "رقم الباركود").setCheckable(true).setChecked(false);
        menu.add(0, Menu.FIRST + 2, 0, "رقم المصنع").setCheckable(true).setChecked(false);
        menu.add(0, Menu.FIRST + 3, 0, "الرقم الأصلي").setCheckable(true).setChecked(false);
    }


}
