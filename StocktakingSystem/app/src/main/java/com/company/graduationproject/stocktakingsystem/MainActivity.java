package com.company.graduationproject.stocktakingsystem;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    
    Button btnGetData , btnGoToStockTaking;
    DB db ;
    Boolean isStockTakingsDone , isSessionsDone , isStocksDone , isUnitsDone , isStoresDone;
    Dialog getDataDialog;
    ImageView ivStockTakingsStatus , ivSessionsStatus , ivStocksStatus , ivUnitsStatus , ivStoresStatus;
    RelativeLayout ivGetStockTakings , ivGetSessions , ivGetStocks , ivGetUnits , ivGetStores;
    RelativeLayout stockTakingsLoadingPanel , sessionsLoadingPanel , stocksLoadingPanel , unitsLoadingPanel , storesLoadingPanel;
    RelativeLayout get ,progress;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        
        isStockTakingsDone=false;
        isSessionsDone=false;
        isStocksDone=false;
        isUnitsDone=false;
        isStoresDone=false;
        
        db= new DB(this);
        btnGetData= (Button)findViewById(R.id.btnGetData);
        btnGoToStockTaking= (Button)findViewById(R.id.btnGoToStockTaking);
        
        btnGetData.setOnClickListener(this);
        btnGoToStockTaking.setOnClickListener(this);

        getDataDialog = new Dialog(MainActivity.this);
        getDataDialog.setContentView(R.layout.get_data_dialog);
        getDataDialog.setTitle("سحب البيانات");

        ivStockTakingsStatus = (ImageView)getDataDialog.findViewById(R.id.ivStockTakingsStatus);
        ivSessionsStatus = (ImageView)getDataDialog.findViewById(R.id.ivSessionsStatus);
        ivStocksStatus = (ImageView)getDataDialog.findViewById(R.id.ivStocksStatus);
        ivStoresStatus = (ImageView)getDataDialog.findViewById(R.id.ivStoreStatus);
        ivUnitsStatus = (ImageView)getDataDialog.findViewById(R.id.ivUnitsStatus);


        ivGetStockTakings = (RelativeLayout)getDataDialog.findViewById(R.id.ivGetStockTakings);
        ivGetSessions = (RelativeLayout)getDataDialog.findViewById(R.id.ivGetSessions);
        ivGetStocks = (RelativeLayout)getDataDialog.findViewById(R.id.ivGetStocks);
        ivGetStores = (RelativeLayout)getDataDialog.findViewById(R.id.ivGetStores);
        ivGetUnits = (RelativeLayout)getDataDialog.findViewById(R.id.ivGetUnits);

        ivGetStockTakings.setOnClickListener(this);
        ivGetSessions .setOnClickListener(this);
        ivGetStocks .setOnClickListener(this);
        ivGetStores .setOnClickListener(this);
        ivGetUnits .setOnClickListener(this);

        stockTakingsLoadingPanel = (RelativeLayout)getDataDialog.findViewById(R.id.stockTakingsloadingPanel);
        sessionsLoadingPanel = (RelativeLayout)getDataDialog.findViewById(R.id.sessionsloadingPanel);
        stocksLoadingPanel = (RelativeLayout)getDataDialog.findViewById(R.id.stocksloadingPanel);
        storesLoadingPanel = (RelativeLayout)getDataDialog.findViewById(R.id.storesloadingPanel);
        unitsLoadingPanel = (RelativeLayout)getDataDialog.findViewById(R.id.unitsloadingPanel);

        initializeGetDataDialog();






    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            
            case R.id.btnGetData:
                //initializeGetDataDialog();
                if(checkNetworkConnection()) {
                    //resetSharedPreferencesFlags();
                    initializeGetDataDialog();
                    getDataDialog.show();
                   // getData();
                }
                else {
                    Toast.makeText(this, "الرجاء التحقق من الاتصال من الانترنت", Toast.LENGTH_SHORT).show();
                }
                //getData();
                break;
            case R.id.btnGoToStockTaking:
                goToStockTaking();
                break;

            case R.id.ivGetStockTakings:
                if(checkNetworkConnection()) {
                    //startLoadingPanel(0);
                    SharedPreferences.Editor editor = getSharedPreferences("StocktakingSystemSP",MODE_PRIVATE).edit();
                    editor.putString("isStockTakingsDone", "false");
                    editor.apply();
                    isStockTakingsDone=false;

                    WebChannel.getStockTakings2(this);
                }
                else {
                    Toast.makeText(this, "الرجاء التحقق من الاتصال من الانترنت", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ivGetSessions:
                if(checkNetworkConnection()) {
                    SharedPreferences.Editor editor = getSharedPreferences("StocktakingSystemSP",MODE_PRIVATE).edit();
                    editor.putString("isSessionsDone", "false");
                    editor.apply();
                    isSessionsDone=false;

                    startLoadingPanel(1);
                    WebChannel.getSessions2(this);
                }
                else {
                    Toast.makeText(this, "الرجاء التحقق من الاتصال من الانترنت", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ivGetStocks:
                if(checkNetworkConnection()) {
                    SharedPreferences.Editor editor = getSharedPreferences("StocktakingSystemSP",MODE_PRIVATE).edit();
                    editor.putString("isStocksDone", "false");
                    editor.apply();
                    isStocksDone=false;

                    startLoadingPanel(2);
                    WebChannel.getStocks2(this);


                    //----------------------------Moved getStores Here

                    editor.putString("isStoresDone", "false");
                    editor.apply();
                    isStoresDone=false;

                    //startLoadingPanel(3);
                    WebChannel.getStores2(this);
                    //------------------------------------------------------
                }
                else {
                    Toast.makeText(this, "الرجاء التحقق من الاتصال من الانترنت", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ivGetStores:
                if(checkNetworkConnection()) {
                    SharedPreferences.Editor editor = getSharedPreferences("StocktakingSystemSP",MODE_PRIVATE).edit();
                    editor.putString("isStoresDone", "false");
                    editor.apply();
                    isStoresDone=false;

                    startLoadingPanel(3);
                    WebChannel.getStores2(this);
                }
                else {
                    Toast.makeText(this, "الرجاء التحقق من الاتصال من الانترنت", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ivGetUnits:
                if(checkNetworkConnection()) {
                    SharedPreferences.Editor editor = getSharedPreferences("StocktakingSystemSP",MODE_PRIVATE).edit();
                    editor.putString("isUnitsDone", "false");
                    editor.apply();
                    isUnitsDone=false;

                    startLoadingPanel(4);
                    WebChannel.getUnits2(this);
                }
                else {
                    Toast.makeText(this, "الرجاء التحقق من الاتصال من الانترنت", Toast.LENGTH_SHORT).show();
                }
                break;

            
        }
    }

    private void resetSharedPreferencesFlags() {
        SharedPreferences.Editor editor = getSharedPreferences("StocktakingSystemSP",MODE_PRIVATE).edit();
        editor.putString("isStockTakingsDone", "false");
        editor.putString("isSessionsDone", "false");
        editor.putString("isStocksDone", "false");
        editor.putString("isStoresDone", "false");
        editor.putString("isUnitsDone", "false");
        editor.apply();
        isStockTakingsDone = false;
        isSessionsDone =  false;
        isStocksDone =  false;
        isStoresDone =  false;
        isUnitsDone =  false;


    }

    private void initializeGetDataDialog() {
        stockTakingsLoadingPanel.setVisibility(View.GONE);
        sessionsLoadingPanel.setVisibility(View.GONE);
        stocksLoadingPanel.setVisibility(View.GONE);
        storesLoadingPanel.setVisibility(View.GONE);
        unitsLoadingPanel.setVisibility(View.GONE);

        ivStockTakingsStatus.setVisibility(View.INVISIBLE);
        ivSessionsStatus.setVisibility(View.INVISIBLE);
        ivStocksStatus.setVisibility(View.INVISIBLE);
        ivStoresStatus.setVisibility(View.INVISIBLE);
        ivUnitsStatus.setVisibility(View.INVISIBLE);

        ivGetStockTakings.setVisibility(View.VISIBLE);
        ivGetSessions.setVisibility(View.VISIBLE);
        ivGetStocks.setVisibility(View.VISIBLE);
        ivGetStores.setVisibility(View.VISIBLE);
        ivGetUnits.setVisibility(View.VISIBLE);



    }


    private void getData() {
        WebChannel.getStockTakings2(this);
        WebChannel.getSessions2(this);
        WebChannel.getStocks2(this);
        WebChannel.getStores2(this);
        WebChannel.getUnits2(this);

    }
    
    private void goToStockTaking() {

        SharedPreferences prefs = getSharedPreferences("StocktakingSystemSP", MODE_PRIVATE);

        isStockTakingsDone =  Boolean.parseBoolean(prefs.getString("isStockTakingsDone", "false"));
        isSessionsDone =  Boolean.parseBoolean(prefs.getString("isSessionsDone", "false"));
        isStocksDone =  Boolean.parseBoolean(prefs.getString("isStocksDone", "false"));
        isStoresDone =  Boolean.parseBoolean(prefs.getString("isStoresDone", "false"));
        isUnitsDone =  Boolean.parseBoolean(prefs.getString("isUnitsDone", "false"));

        Toast.makeText(this, isStockTakingsDone+"\n"+
                        isSessionsDone+"\n"+
                        isStocksDone+"\n"+
                        isStoresDone+"\n"+
                        isUnitsDone+"\n"
                , Toast.LENGTH_SHORT).show();

       // if(isStockTakingsDone=true&&isSessionsDone==true&&
         //  isStocksDone==true&&isStoresDone&&isUnitsDone==true){
            Intent i = new Intent(MainActivity.this,InventoryActivity.class);
            startActivity(i);
        //}
        //else {
            //Toast.makeText(this, "الرجاء التأكد من سحب البيانات", Toast.LENGTH_SHORT).show();
        //}

    }


    public void setStockTakingsFlag(boolean b, List<DB.StockTaking> stockTakings) {
        isStockTakingsDone=b;
        if(isStockTakingsDone&&stockTakings!=null&&!stockTakings.isEmpty()){
            ivStockTakingsStatus.setImageResource(R.drawable.ic_done_black_24dp);
            ivStockTakingsStatus.setVisibility(View.VISIBLE);

            stockTakingsLoadingPanel.setVisibility(View.GONE);

           // if(ivGetStockTakings.getVisibility()==View.VISIBLE){
                ivGetStockTakings.setVisibility(View.INVISIBLE);
            //}
                saveStockTakingsToDB(stockTakings);
        }
        else {
            ivStockTakingsStatus.setImageResource(R.drawable.ic_fail_black_24dp);
            ivStockTakingsStatus.setVisibility(View.VISIBLE);

            stockTakingsLoadingPanel.setVisibility(View.GONE);

           // if(ivGetStockTakings.getVisibility()==View.INVISIBLE){
                ivGetStockTakings.setVisibility(View.VISIBLE);
           // }
            isStockTakingsDone=false;
            Toast.makeText(this, "الرجاء إعادة سحب الجرودات مرة أخرى", Toast.LENGTH_SHORT).show();

        }
        SharedPreferences.Editor editor = getSharedPreferences("StocktakingSystemSP",MODE_PRIVATE).edit();
        editor.putString("isStockTakingsDone", isStockTakingsDone+"");
        editor.apply();
    }
    private void saveStockTakingsToDB(List<DB.StockTaking> stockTakings) {
        db.open(true);
        DB.StockTakingEntity stockTakingE = db.new StockTakingEntity();
        stockTakingE.deleteTemp(0);
        for (int i = 0; i < stockTakings.size(); i++) {
            stockTakingE.insert(stockTakings.get(i));
        }
        Toast.makeText(this, stockTakingE.getStockTakingRecords("1=1").get(0).name, Toast.LENGTH_SHORT).show();
        db.close();
    }

    public void setSessionsFlag(boolean b, List<DB.StockTakingSessions> sessions) {
        isSessionsDone=b;
        if(isSessionsDone&&sessions!=null&&!sessions.isEmpty()){
            ivSessionsStatus.setImageResource(R.drawable.ic_done_black_24dp);
            ivSessionsStatus.setVisibility(View.VISIBLE);

            sessionsLoadingPanel.setVisibility(View.GONE);

            //if(ivGetSessions.getVisibility()==View.VISIBLE){
                ivGetSessions.setVisibility(View.INVISIBLE);
            //}
                saveSessionsToDB(sessions);
        }
        else {
            ivSessionsStatus.setImageResource(R.drawable.ic_fail_black_24dp);
            ivSessionsStatus.setVisibility(View.VISIBLE);

            sessionsLoadingPanel.setVisibility(View.GONE);

            //if(ivGetSessions.getVisibility()==View.INVISIBLE){
            ivGetSessions.setVisibility(View.VISIBLE);
            //}
            isSessionsDone = false;
            Toast.makeText(this, "الرجاء إعادة سحب الجلسات مرة أخرى", Toast.LENGTH_SHORT).show();

        }
        SharedPreferences.Editor editor = getSharedPreferences("StocktakingSystemSP",MODE_PRIVATE).edit();
        editor.putString("isSessionsDone", isSessionsDone+"");
        editor.apply();
    }
    private void saveSessionsToDB(List<DB.StockTakingSessions> sessions) {

        db.open(true);
        DB.StockTakingSessionsEntity stockTakingSessionsE = db.new StockTakingSessionsEntity();
        stockTakingSessionsE.deleteTemp(0);
        for (int i = 0; i < sessions.size(); i++) {
            stockTakingSessionsE.insert(sessions.get(i));
        }
        db.close();
    }


    public void setStocksFlag(boolean b, List<DB.Stock> stocks) {
        isStocksDone=b;
        if(isStocksDone&&stocks!=null&&!stocks.isEmpty()){
            ivStocksStatus.setImageResource(R.drawable.ic_done_black_24dp);
            ivStocksStatus.setVisibility(View.VISIBLE);

            stocksLoadingPanel.setVisibility(View.GONE);

            //if(ivGetStocks.getVisibility()==View.VISIBLE){
                ivGetStocks.setVisibility(View.INVISIBLE);
            //}
                saveStocksToDB(stocks);
        }
        else {
            ivStocksStatus.setImageResource(R.drawable.ic_fail_black_24dp);
            ivStocksStatus.setVisibility(View.VISIBLE);

            stocksLoadingPanel.setVisibility(View.GONE);

            //if(ivGetStocks.getVisibility()==View.INVISIBLE){
                ivGetStocks.setVisibility(View.VISIBLE);
            //}
            isStocksDone=false;
            Toast.makeText(this, "الرجاء إعادة سحب الأصناف مرة أخرى", Toast.LENGTH_SHORT).show();

        }
        SharedPreferences.Editor editor = getSharedPreferences("StocktakingSystemSP",MODE_PRIVATE).edit();
        editor.putString("isStocksDone", isStocksDone+"");
        editor.apply();
    }
    private void saveStocksToDB(List<DB.Stock> stocks) {

        db.open(true);
        DB.StockEntity stockE = db.new StockEntity();
        stockE.deleteTemp(0);
        for (int i = 0; i < stocks.size(); i++) {
            stockE.insert(stocks.get(i));
        }
        Toast.makeText(this, stockE.getItem(1).barcode, Toast.LENGTH_SHORT).show();
        db.close();
    }



    public void setStoresFlag(boolean b, List<DB.Store> stores) {
        isStoresDone=b;
        if(isStoresDone&&stores!=null&&!stores.isEmpty()){
            ivStoresStatus.setImageResource(R.drawable.ic_done_black_24dp);
            ivStoresStatus.setVisibility(View.VISIBLE);

            storesLoadingPanel.setVisibility(View.GONE);

            //if(ivGetStores.getVisibility()==View.VISIBLE){
                ivGetStores.setVisibility(View.INVISIBLE);
            //}
                saveStoresToDB(stores);
        }
        else {
            ivStoresStatus.setImageResource(R.drawable.ic_fail_black_24dp);
            ivStoresStatus.setVisibility(View.VISIBLE);

            storesLoadingPanel.setVisibility(View.GONE);

            //if(ivGetStores.getVisibility()==View.INVISIBLE){
                ivGetStores.setVisibility(View.VISIBLE);
            //}
            isStoresDone=false;
            Toast.makeText(this, "الرجاء إعادة سحب المستودعات مرة أخرى", Toast.LENGTH_SHORT).show();

        }
        SharedPreferences.Editor editor = getSharedPreferences("StocktakingSystemSP",MODE_PRIVATE).edit();
        editor.putString("isUnitsDone", isUnitsDone+"");
        editor.apply();
    }
    private void saveStoresToDB(List<DB.Store> stores) {

        db.open(true);
        DB.StoreEntity storeE = db.new StoreEntity();
        storeE.deleteTemp(0);
        for (int i = 0; i < stores.size(); i++) {
            storeE.insert(stores.get(i));
        }
        db.close();
    }


    public void setUnitsFlag(boolean b, List<DB.Units> units) {
        isUnitsDone=b;
        if(isUnitsDone&&units!=null&&!units.isEmpty()){
            ivUnitsStatus.setImageResource(R.drawable.ic_done_black_24dp);
            ivUnitsStatus.setVisibility(View.VISIBLE);

            unitsLoadingPanel.setVisibility(View.GONE);

            //if(ivGetUnits.getVisibility()==View.VISIBLE){
                ivGetUnits.setVisibility(View.INVISIBLE);
            //}

                saveUnitsToDB(units);
        }
        else {
            ivUnitsStatus.setImageResource(R.drawable.ic_fail_black_24dp);
            ivUnitsStatus.setVisibility(View.VISIBLE);

            unitsLoadingPanel.setVisibility(View.GONE);

            //if(ivGetUnits.getVisibility()==View.INVISIBLE){
                ivGetUnits.setVisibility(View.VISIBLE);
            //}
            isUnitsDone=false;
            Toast.makeText(this, "الرجاء إعادة سحب الوحدات مرة أخرى", Toast.LENGTH_SHORT).show();
        }

        SharedPreferences.Editor editor = getSharedPreferences("StocktakingSystemSP",MODE_PRIVATE).edit();
        editor.putString("isStoresDone", isStoresDone+"");
        editor.apply();
    }
    private void saveUnitsToDB(List<DB.Units> units) {
        db.open(true);
        DB.UnitsEntity unitsE = db.new UnitsEntity();
        unitsE.deleteTemp(0);
        for (int i = 0; i < units.size(); i++) {
            unitsE.insert(units.get(i));
        }
        db.close();
    }



    public void startLoadingPanel(int field) {

        switch (field){

            case 0:
                ivGetStockTakings.setVisibility(View.GONE);
                stockTakingsLoadingPanel.setVisibility(View.VISIBLE);
                break;

            case 1:
                ivGetSessions.setVisibility(View.GONE);
                sessionsLoadingPanel.setVisibility(View.VISIBLE);
                break;

            case 2:
                ivGetStocks.setVisibility(View.GONE);
                stocksLoadingPanel.setVisibility(View.VISIBLE);
                break;

            case 3:
                ivGetStores.setVisibility(View.GONE);
                storesLoadingPanel.setVisibility(View.VISIBLE);
                break;

            case 4:
                ivGetUnits.setVisibility(View.GONE);
                unitsLoadingPanel.setVisibility(View.VISIBLE);
                break;
        }
    }
    private boolean checkNetworkConnection() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getActiveNetworkInfo();


        if (mWifi!=null&&mWifi.isConnected()) {
            // Do whatever
            return true;
        }
        else {
            return  false;

        }
    }
}
