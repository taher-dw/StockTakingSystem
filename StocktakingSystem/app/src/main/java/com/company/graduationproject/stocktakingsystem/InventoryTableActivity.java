package com.company.graduationproject.stocktakingsystem;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InventoryTableActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    RecyclerView recyclerView , undefinedBarcodeRecyclerView;
    RecyclerView.Adapter recyclerView_Adapter, undefined_barcode_recyclerView_Adapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager , undefined_barcode_recyclerViewLayoutManager;
    EditText etNameInventoryTable;
    Button btnNewStockTaking , btnBackToStockTakingActivity , btnUpdateUndefinedBarcodes ;
    RadioButton rbDefinedItems, rbUnDefinedItems;
    String[] values;
    DB db;
    int itemsStatus;
    int stockTakingID , sessionID ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_table);

        initialize();


    }

    private void initialize() {

        Intent fromStockTakingActivity = getIntent();
        stockTakingID = Integer.parseInt(fromStockTakingActivity.getStringExtra("stockTakingID"));
        sessionID = Integer.parseInt(fromStockTakingActivity.getStringExtra("sessionID"));

        db = new DB(this);
        db.open(true);
        itemsStatus = 0;
        recyclerView = (RecyclerView) findViewById(R.id.rvInventoryTable);
        undefinedBarcodeRecyclerView = (RecyclerView) findViewById(R.id.rvUndefinedInventoryTable);
        rbDefinedItems = (RadioButton) findViewById(R.id.rbDefinedStockItems);
        btnNewStockTaking = (Button) findViewById(R.id.btnNewStockTaking);
        btnUpdateUndefinedBarcodes = (Button)findViewById(R.id.btnUpdateUndefinedBarcodes);
        btnBackToStockTakingActivity = (Button) findViewById(R.id.btnBackToStockTakingActivity);
        rbDefinedItems.setChecked(true);
        rbUnDefinedItems = (RadioButton) findViewById(R.id.rbUnDefinedStockItems);
        etNameInventoryTable = (EditText)findViewById(R.id.etName_InventoryTable);
        etNameInventoryTable.addTextChangedListener(this);
        rbDefinedItems.setOnClickListener(this);
        rbUnDefinedItems.setOnClickListener(this);
        btnUpdateUndefinedBarcodes.setOnClickListener(this);

        btnNewStockTaking.setOnClickListener(this);
        btnBackToStockTakingActivity.setOnClickListener(this);
        getAllItemsFromStockTakingTransactions();

        checkItemsStatus();
    }

    private int checkItemsStatus() {
        return itemsStatus;
    }

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.rbDefinedStockItems:
                recyclerView.setVisibility(View.VISIBLE);
                undefinedBarcodeRecyclerView.setVisibility(View.GONE);
                itemsStatus = 0;
                getAllItemsFromStockTakingTransactions();
                break;

            case R.id.rbUnDefinedStockItems:
                recyclerView.setVisibility(View.GONE);
                undefinedBarcodeRecyclerView.setVisibility(View.VISIBLE);
                itemsStatus = 1;
                //  getAllItemsFromUndefinedItemsTable();
                getAllItemsFromUndefinedItemsTableWithColors();
                break;
            case R.id.btnNewStockTaking :
                backToStockTakingActivity();
                break;
            case R.id.btnBackToStockTakingActivity:
                backToStockTakingActivity();
                break;
            case R.id.btnUpdateUndefinedBarcodes:

                DB.UndefinedStockTakingTransactionsEntity ustte = db.new UndefinedStockTakingTransactionsEntity();
                int numOfUstt = ustte.get("0=0").size();
                if(numOfUstt>0) {
                    newUpdateUndefinedBarcodes();
                    if (rbDefinedItems.isChecked()) {
                        getAllItemsFromStockTakingTransactions();
                    } else if (rbUnDefinedItems.isChecked()) {
                        getAllItemsFromUndefinedItemsTableWithColors();
                    }
                }
                else{
                    Toast.makeText(this, "لا يوجد أصناف غير معرفة ليتم معالجتها", Toast.LENGTH_SHORT).show();
                }
                // manipulateUndefinedItems();
                break;




        }

    }

    private void getAllItemsFromUndefinedItemsTableWithColors() {

        try {
            DB.UnitsEntity unitsE = db.new UnitsEntity();
            DB.UndefinedStockTakingTransactionsEntity ustte = db.new UndefinedStockTakingTransactionsEntity();
            String where = DB.UndefinedStockTakingTransactionsEntity.KEY_UNDEFINED_STOCK_TAKING_ID_TRANSACTIONS + " = " + stockTakingID + " and "
                    + DB.UndefinedStockTakingTransactionsEntity.KEY_UNDEFINED_SESSION_ID_TRANSACTIONS + " = " + sessionID;
            List<DB.UndefinedStockTakingTransactions> ustts = ustte.get(where);
            //List<DB.UndefinedStockTakingTransactions> ustts = ustte.get("0=0");
            DB.StockEntity stockE = db.new StockEntity();
            DB.Stock item = db.new Stock();
            int colors[] = new int[ustts.size()];
            String units[] = new String[ustts.size()];
            String code = "";
            for (int i = 0; i < ustts.size(); i++) {
                //code = unitsE.checkExisting(" LTRIM(RTRIM("+DB.UnitsEntity.KEY_ITEM_BARCODE_UNITS+")"+" = '"+ustts.get(i).itemBarcode.trim()+"'");
                code = unitsE.checkExisting("LTRIM(RTRIM(" + DB.UnitsEntity.KEY_ITEM_BARCODE_UNITS + ")) = '" + ustts.get(i).itemBarcode.trim() + "'");

                if (!code.equals("-1")) {
                    colors[i] = 1;
                    item = stockE.getItemByCode(code);
                    // units[i]=getItemUnit(item,unitsE.get(DB.UnitsEntity.KEY_ITEM_BARCODE_UNITS+" = '"+ustts.get(i).itemBarcode+"'").get(0).shamelUnitID);
                    units[i] = getItemUnit(item, unitsE.get("LTRIM(RTRIM(" + DB.UnitsEntity.KEY_ITEM_BARCODE_UNITS + ")) = '" + ustts.get(i).itemBarcode.trim() + "'").get(0).shamelUnitID);
                    ustts.get(i).undefinedBarcodeName = item.name + "#" + ustts.get(i).undefinedBarcodeName;

                } else {
                    colors[i] = 0;
                    units[i] = "الوحدة غير معرفة";
                    ustts.get(i).undefinedBarcodeName = ustts.get(i).undefinedBarcodeName + "#" + ustts.get(i).undefinedBarcodeName;
                }

            }

            undefined_barcode_recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
            undefinedBarcodeRecyclerView.setLayoutManager(undefined_barcode_recyclerViewLayoutManager);
            undefined_barcode_recyclerView_Adapter = new UndefinedBarcodeRecyclerViewAdapter(getApplicationContext(), ustts, colors, units, this);
            undefinedBarcodeRecyclerView.setAdapter(undefined_barcode_recyclerView_Adapter);
        }
        catch (Exception ex){
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }

    }
    private void manipulateUndefinedItems() {

        DB.UnitsEntity unitsE = db.new UnitsEntity();
        DB.UndefinedStockTakingTransactionsEntity ustte = db.new UndefinedStockTakingTransactionsEntity();
        List<DB.UndefinedStockTakingTransactions> ustts = ustte.get("0=0");

        DB.StockEntity stockE = db.new StockEntity();

        DB.StockTakingTransactionsEntity stockTakingTransactionsE = db.new StockTakingTransactionsEntity();

        DB.Stock item = db.new Stock();
        int colors[] = new int[ustts.size()];
        String units[] = new String[ustts.size()];
        String code="";
        DB.StockTakingTransactions stt = db.new StockTakingTransactions();
        int id =-1;
        String where ="";


        try{

            for(int i=0;i<ustts.size();i++){
                //code = unitsE.checkExisting(DB.UnitsEntity.KEY_ITEM_BARCODE_UNITS+" = '"+ustts.get(i).itemBarcode.trim()+"'");
                code = unitsE.checkExisting("LTRIM(RTRIM("+DB.UnitsEntity.KEY_ITEM_BARCODE_UNITS + ")) = '" +ustts.get(i).itemBarcode.trim()  + "'");
                if(!code.equals("-1")) {
                    int itemID=stockE.getItemByCode(code).id;
                    where = DB.StockTakingTransactionsEntity.KEY_ITEM_ID_TRANSACTIONS+" = "+itemID;
                    id = stockTakingTransactionsE.checkExisting(where);

                    if (id != -1) {
                        stt = stockTakingTransactionsE.get(where).get(0);
                        double quantity = stt.quantity + ustts.get(i).quantity;
                        stockTakingTransactionsE.update(quantity, where);
                        ustte.deleteUndefinedStockTakingTransaction(DB.UndefinedStockTakingTransactionsEntity.KEY_UNDEFINED_ITEM_BARCODE_TRANSACTIONS + " = '" + ustts.get(i).itemBarcode + "'");

                    } else {
                        item = stockE.getItemByCode(code);
                        stt.itemID = item.id;
                        stt.itemName = item.name.trim();
                        stt.quantity = ustts.get(i).quantity;
                        stt.stockTatkingID = ustts.get(0).stockTatkingID;
                        stt.sessionID = ustts.get(0).sessionID;
                        stt.stockTakingDate = Util.getCurrentDate();
                        stt.isPosted = 0;
                        stt.unitID = ustts.get(i).unitID;
                        stt.unitName = ustts.get(i).unitName;
                        stockTakingTransactionsE.insert(stt);
                        ustte.deleteUndefinedStockTakingTransaction(DB.UndefinedStockTakingTransactionsEntity.KEY_UNDEFINED_ITEM_BARCODE_TRANSACTIONS + " = '" + ustts.get(i).itemBarcode + "'");
                    }


                }

            }
            getAllItemsFromUndefinedItemsTableWithColors();
            /*
            ustts = ustte.get("0=0");
            undefined_barcode_recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
            undefinedBarcodeRecyclerView.setLayoutManager(undefined_barcode_recyclerViewLayoutManager);
            undefined_barcode_recyclerView_Adapter = new UndefinedBarcodeRecyclerViewAdapter(getApplicationContext(), ustts,colors,units ,this);
            undefinedBarcodeRecyclerView.setAdapter(undefined_barcode_recyclerView_Adapter);*/

        }
        catch (Exception e){
            Util.logException(this , e);
            //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }




    }

    private String getItemUnit( DB.Stock item,int unitID) {

        switch (unitID){
            case 0:
                return item.unit_1;

            case 1:
                return item.unit_2;

            case 2:
                return item.unit_3;

            case 3:
                return item.unit_4;

            case 4:
                return item.unit_5;

            case 5:
                return item.unit_6;



        }
        return "";
    }

    private void updateUndefinedBarcodes() {

        DB.UndefinedStockTakingTransactionsEntity ustte = db.new UndefinedStockTakingTransactionsEntity();
        List< DB.UndefinedStockTakingTransactions>ustts= ustte.get("0=0");
        DB.UndefinedStockTakingTransactions ustt = db.new UndefinedStockTakingTransactions();
        DB.StockEntity stockE = db.new StockEntity();
        DB.Stock stock = db.new Stock();
        int id = -1 ;

        for(int i=0;i<ustts.size();i++){
            ustt = ustts.get(i);
            id = stockE.checkExisting(DB.StockEntity.KEY_BARCODE + " = '"+ustt.itemBarcode+"'" );
            if(id!=-1){
                stock = stockE.getItem(id);
                //  db.beginTransaction();
                boolean transactionSuccess =true;
                try{
                    db.beginTransaction();
                    if(insertIntoStockTransactions(stock,ustt,1)) {
                        String where = DB.UndefinedStockTakingTransactionsEntity.KEY_UNDEFINED_ITEM_BARCODE_TRANSACTIONS + " = '" + ustt.itemBarcode + "'";
                        if (deleteUndefinedBarcode(where)) {
                            //  db.setTransactionSuccessful();
                            Toast.makeText(this, "تم تحديث الباركود بنجاح" + ustt.itemBarcode, Toast.LENGTH_SHORT).show();

                        }
                        else {
                            transactionSuccess = false;
                        }
                    }
                    else{
                        transactionSuccess = false;
                    }
                    if(transactionSuccess){db.setTransactionSuccessful();}


                }catch (Exception e){Util.logException(this,e);}
                finally {
                    db.endTransaction();
                }


            }

        }
    }
    private void newUpdateUndefinedBarcodes() {

        DB.UndefinedStockTakingTransactionsEntity ustte = db.new UndefinedStockTakingTransactionsEntity();
        List< DB.UndefinedStockTakingTransactions>ustts= ustte.get("0=0");
        DB.UndefinedStockTakingTransactions ustt = db.new UndefinedStockTakingTransactions();
        DB.StockEntity stockE = db.new StockEntity();
        DB.StockTakingTransactionsEntity stte = db.new StockTakingTransactionsEntity();
        DB.StockTakingTransactions stt = db.new StockTakingTransactions();
        DB.Stock stock = db.new Stock();
        DB.UnitsEntity unitsE = db.new UnitsEntity();
        DB.Units units = db.new Units();
        int id = -1 ;
        int stteID =-1;
        String stteWhere = "";

        for(int i=0;i<ustts.size();i++){
            ustt = ustts.get(i);

            //id = unitsE.checkExistingByID(DB.UnitsEntity.KEY_ITEM_BARCODE_UNITS + " = '"+ustt.itemBarcode+"'" );
            id=unitsE.checkExistingByID("LTRIM(RTRIM("+DB.UnitsEntity.KEY_ITEM_BARCODE_UNITS + ")) = '" +ustt.itemBarcode.trim()  + "'");

            if(id!=-1) {
                //units = unitsE.get(DB.UnitsEntity.KEY_ITEM_BARCODE_UNITS + " = '"+ustt.itemBarcode+"'").get(0);
                units =  unitsE.get("LTRIM(RTRIM("+DB.UnitsEntity.KEY_ITEM_BARCODE_UNITS + ")) = '" +ustt.itemBarcode.trim()  + "'").get(0);

                stock = stockE.getItemByCode(units.itemCodeUnits);
                stteWhere=DB.StockTakingTransactionsEntity.KEY_ITEM_ID_TRANSACTIONS+"  = " + stock.id + " and  "+DB.StockTakingTransactionsEntity.KEY_UNIT_ID_TRANSACTIONS + " = " + units.shamelUnitID ;
                //  db.beginTransaction();
                stteID = stte.checkExisting(stteWhere);
                if (stteID != -1) {
                    stt = stte.get(stteWhere).get(0);
                    //stteWhere=DB.StockTakingTransactionsEntity.KEY_ITEM_ID_TRANSACTIONS+"  = " + stock.id + " and  "+DB.StockTakingTransactionsEntity.KEY_UNIT_ID_TRANSACTIONS + " = " + units.shamelUnitID ;
                    double quantityToUpdate = ustt.quantity+stt.quantity;

                    boolean transactionSuccess =true;
                    try{
                        db.beginTransaction();
                        if(updateStockTransactions(quantityToUpdate,stte,stteWhere)){
                            String where = DB.UndefinedStockTakingTransactionsEntity.KEY_UNDEFINED_ITEM_BARCODE_TRANSACTIONS + " = '" + ustt.itemBarcode + "'";
                            if (deleteUndefinedBarcode(where)) {
                                //  db.setTransactionSuccessful();
                                Toast.makeText(this, "تم تحديث الباركود بنجاح" + ustt.itemBarcode, Toast.LENGTH_SHORT).show();

                            }
                            else{
                                transactionSuccess = false;
                            }
                        }
                        else {
                            transactionSuccess = false;
                        }
                        if(transactionSuccess){db.setTransactionSuccessful();}
                    }catch (Exception e){Util.logException(this,e);}
                    finally {
                        db.endTransaction();
                    }
                }
                else{
                    boolean transactionSuccess =true;
                    try{
                        db.beginTransaction();

                        if (insertIntoStockTransactions(stock, ustt, units.shamelUnitID)) {
                            String where = DB.UndefinedStockTakingTransactionsEntity.KEY_UNDEFINED_ITEM_BARCODE_TRANSACTIONS + " = '" + ustt.itemBarcode + "'";
                            if (deleteUndefinedBarcode(where)) {
                                //  db.setTransactionSuccessful();
                                Toast.makeText(this, "تم تحديث الباركود بنجاح" + ustt.itemBarcode, Toast.LENGTH_SHORT).show();

                            }
                            else{
                                transactionSuccess = false;
                            }
                        }
                        else{
                            transactionSuccess = false;
                        }
                        if(transactionSuccess){db.setTransactionSuccessful();}
                    }catch (Exception e){Util.logException(this,e);}
                    finally {
                        db.endTransaction();
                    }
                }
            }

        }
    }

    private boolean insertIntoStockTransactions(DB.Stock s , DB.UndefinedStockTakingTransactions ustt ,int unitID) {

        DB.StockTakingTransactionsEntity stte = db.new StockTakingTransactionsEntity();
        DB.StockTakingTransactions stt = db.new StockTakingTransactions();
        stt.itemID = s.id;
        stt.itemName = s.name.trim();
        stt.quantity = ustt.quantity;
        stt.stockTatkingID = stockTakingID;
        stt.sessionID = sessionID;
        stt.unitID = unitID;
        stt.unitName = ustt.unitName;
        stt.stockTakingDate = ustt.stockTakingDate;
        stt.isPosted=0;
        switch (unitID){
            case 0:
                stt.unitName = s.unit_1;
                break;
            case 1:
                stt.unitName = s.unit_2;
                break;
            case 2:
                stt.unitName = s.unit_3;
                break;
            case 3:
                stt.unitName = s.unit_4;
                break;
            case 4:
                stt.unitName = s.unit_5;
                break;
            case 5:
                stt.unitName = s.unit_6;
                break;
        }
        if(stte.insert(stt)){
            return  true;
        }
        else{
            return false;
        }


    }
    private boolean updateStockTransactions(double quantityToUpdate, DB.StockTakingTransactionsEntity stte, String where) {
        if(stte.update(quantityToUpdate, where)){
            return true ;
        }
        else{
            return false;
        }

    }
    private boolean deleteUndefinedBarcode(String where) {
        DB.UndefinedStockTakingTransactionsEntity ustte = db.new UndefinedStockTakingTransactionsEntity();
        if(ustte.deleteUndefinedStockTakingTransaction(where)){

            return true;
        }
        else{

            return false;
        }
    }
    private void backToStockTakingActivity() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("updateMode", "3");
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void getAllItemsFromUndefinedItemsTable() {
        DB.UndefinedStockTakingTransactionsEntity ustte = db.new UndefinedStockTakingTransactionsEntity();
        List<DB.UndefinedStockTakingTransactions> userr = ustte.get("0=0");
        undefined_barcode_recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        undefinedBarcodeRecyclerView.setLayoutManager(undefined_barcode_recyclerViewLayoutManager);
        undefined_barcode_recyclerView_Adapter = new UndefinedBarcodeRecyclerViewAdapter(getApplicationContext(), userr, this);
        undefinedBarcodeRecyclerView.setAdapter(undefined_barcode_recyclerView_Adapter);


    }

    private void getAllItemsFromStockTakingTransactions() {

        DB.StockTakingTransactionsEntity stte2 = db.new StockTakingTransactionsEntity();
        // stte2.deleteTemp(1);
        String whereFromStockTakingTransactions = DB.StockTakingTransactionsEntity.KEY_IS_POSTED + " = 0 and "
                + DB.StockTakingTransactionsEntity.KEY_STOCK_TAKING_ID_TRANSACTIONS + " = "+stockTakingID+" and "
                + DB.StockTakingTransactionsEntity.KEY_SESSION_ID_TRANSACTIONS + " = "+sessionID ;
        List<DB.StockTakingTransactions> serr = stte2.get(whereFromStockTakingTransactions);
        //List<DB.StockTakingTransactions> serr = stte2.get("0=0");
        //if(serr.size()>0) {
        recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView_Adapter = new RecyclerViewAdapter(getApplicationContext(), serr, this);
        recyclerView.setAdapter(recyclerView_Adapter);
        //}
        //else{

        //}
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    public void afterTextChanged(Editable editable) {

        String s = etNameInventoryTable.getText().toString().trim();
        int status = checkItemsStatus();

        if(!s.equals("")) {
            if (status == 0) {
                getItemsByStatusWhere(status, s);
            } else {
                getItemsByStatusWhere(status, s);
            }
        }
        else{
            getAllItemsByStatus(status);
        }
    }

    private void getAllItemsByStatus(int status) {

        if(status==0)
            getAllItemsFromStockTakingTransactions();
        else
            // getAllItemsFromUndefinedItemsTable();
            getAllItemsFromUndefinedItemsTableWithColors();
    }

    private void getItemsByStatusWhere(int status, String dataToSearch) {
        String where="";


        dataToSearch=dataToSearch.replace(' ', '%');
        DB.Stock item = db.new Stock();
        if(status==0){

            where += DB.StockTakingTransactionsEntity.KEY_IS_POSTED + " = 0 and "
                    + DB.StockTakingTransactionsEntity.KEY_STOCK_TAKING_ID_TRANSACTIONS + " = "+stockTakingID+" and "
                    + DB.StockTakingTransactionsEntity.KEY_SESSION_ID_TRANSACTIONS + " = "+sessionID+" and " ;

            where+="UPPER("+ DB.StockTakingTransactionsEntity.KEY_ITEM_NAME_TRANSACTIONS+") like '%"+dataToSearch+"%'";
            DB.StockTakingTransactionsEntity stte = db.new StockTakingTransactionsEntity();
            List<DB.StockTakingTransactions> serr = stte.get(where);
            recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
            recyclerView.setLayoutManager(recyclerViewLayoutManager);
            recyclerView_Adapter = new RecyclerViewAdapter(getApplicationContext(), serr, this);
            recyclerView.setAdapter(recyclerView_Adapter);

        }
        else{

            where += DB.UndefinedStockTakingTransactionsEntity.KEY_UNDEFINED_STOCK_TAKING_ID_TRANSACTIONS + " = "+stockTakingID+" and "
                    + DB.UndefinedStockTakingTransactionsEntity.KEY_UNDEFINED_SESSION_ID_TRANSACTIONS + " = "+sessionID ;

            DB.UnitsEntity unitsE = db.new UnitsEntity();
            //where+="UPPER("+ DB.UndefinedStockTakingTransactionsEntity.KEY_UNDEFINED_ITEM_BARCODE_TRANSACTIONS+") like '%"+dataToSearch+"%'";
            where+=" and UPPER("+ DB.UndefinedStockTakingTransactionsEntity.KEY_UNDEFINED_BARCODE_NAME+") like '%"+dataToSearch+"%'";
            DB.UndefinedStockTakingTransactionsEntity ustte = db.new UndefinedStockTakingTransactionsEntity();
            DB.StockEntity stockE = db.new StockEntity();
            List<DB.UndefinedStockTakingTransactions> userr = ustte.get(where);
            int colors[] = new int[userr.size()];
            String units[] = new String[userr.size()];
            String code="";
            for(int i=0;i<userr.size();i++){
                //code = unitsE.checkExisting(DB.UnitsEntity.KEY_ITEM_BARCODE_UNITS+" = '"+userr.get(i).itemBarcode+"'");
                code =unitsE.checkExisting("LTRIM(RTRIM("+DB.UnitsEntity.KEY_ITEM_BARCODE_UNITS + ")) = '" +userr.get(i).itemBarcode.trim()  + "'");
                if(!code.equals("-1")){
                    colors[i]=1;
                    item = stockE.getItemByCode(code);
                    //units[i]=getItemUnit(item,unitsE.get(DB.UnitsEntity.KEY_ITEM_BARCODE_UNITS+" = '"+userr.get(i).itemBarcode+"'").get(0).shamelUnitID);
                    units[i]=getItemUnit(item,unitsE.get("LTRIM(RTRIM("+DB.UnitsEntity.KEY_ITEM_BARCODE_UNITS + ")) = '"+userr.get(i).itemBarcode+"'").get(0).shamelUnitID);

                    userr.get(i).undefinedBarcodeName=item.name+"#"+userr.get(i).undefinedBarcodeName;
                }
                else{
                    colors[i]=0;
                    units[i]="الكمية غير معرفة";
                    userr.get(i).undefinedBarcodeName=userr.get(i).undefinedBarcodeName+"#"+userr.get(i).undefinedBarcodeName;
                }

            }
            undefined_barcode_recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
            undefinedBarcodeRecyclerView.setLayoutManager(undefined_barcode_recyclerViewLayoutManager);
            undefined_barcode_recyclerView_Adapter = new UndefinedBarcodeRecyclerViewAdapter(getApplicationContext(), userr,colors,units, this);
            undefinedBarcodeRecyclerView.setAdapter(undefined_barcode_recyclerView_Adapter);


        }

    }
}
