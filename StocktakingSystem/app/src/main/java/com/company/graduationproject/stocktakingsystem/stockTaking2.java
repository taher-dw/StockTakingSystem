package com.company.graduationproject.stocktakingsystem;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class stockTaking2 extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    Button btnSave, btnClearFields, btnDeleteStockTakingRecord, btnDisplayTransactions , btnEnterUndefinedBarcodeName , btnCancelUndefinedBarcodeNameDialog;
    AutoCompleteTextView acItemNumber, acItemName, acBarcodeNumber, acFactoryNumber, acOriginalNumber ;
    EditText etItemQuantity, etBarcodeNumber , etUndefinedBarcodeName;
    LinearLayout llItemName, llBarcodeNumber, llFactoryNumber, llOriginalNumber, llSpRequiredIDs;
    Spinner spInventoryUnit, spRequiredIDs;
    CheckBox chkarcodeStockTaking;
    RadioGroup rgBarcodeStockTakingGroup;
    RadioButton rbAutomaticBarcodeStockTaking, rbManualBarcodeStockTaking;
    Boolean barcodeStockTakingChkStatus;
    String requiredID;
    DB db;
    ArrayAdapter<String> requiredIDsAdapter;
    AutoCompleteAdapter3 adapter ;
    String result;
    int hasFocus;
    ArrayList<String> items;
    ArrayList<String> requiredValues;
    ArrayList<String> requiredIds;
    int stockTakingID, sessionID;
    int mode, isItemChosen, fromInventoryTableA, updateMode, lastFieldToSearch;
    LinearLayout llItem;
    int colorStatus;
    String undefinedBarcode , undefinedBarcodeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_taking);
        initialize();
        getSettingsFromInventoryActivityAndPrepareFields();

    }
    private void getSettingsFromInventoryActivityAndPrepareFields() {


        String value=Util.getPrefAsString(this,"isItemNameChecked","");
        if(!value.equals("")&&Boolean.parseBoolean(value)){
            llItemName.setVisibility(View.VISIBLE);
        } else {
            llItemName.setVisibility(View.GONE);
        }


        value =Util.getPrefAsString(this,"isBarcodeNumberChecked","");
        if(!value.equals("")&&Boolean.parseBoolean(value)){
            llBarcodeNumber.setVisibility(View.VISIBLE);
        } else {
            llBarcodeNumber.setVisibility(View.GONE);
        }

        value =Util.getPrefAsString(this,"isFactoryNumberChecked","");
        if(!value.equals("")&&Boolean.parseBoolean(value)){
            llFactoryNumber.setVisibility(View.VISIBLE);
        } else {
            llFactoryNumber.setVisibility(View.GONE);
        }


        value =Util.getPrefAsString(this,"isOriginalNumberChecked","");
        if(!value.equals("")&&Boolean.parseBoolean(value)){
            llOriginalNumber.setVisibility(View.VISIBLE);
        } else {
            llOriginalNumber.setVisibility(View.GONE);
        }



        value =Util.getPrefAsString(this,"isStockTakingBarcodeModeChecked","");
        int barcodeMode =Util.getPrefAsInteger(this,"barcodeStockTakingMode",-1);
        if(!value.equals("")&&Boolean.parseBoolean(value)){
            chkarcodeStockTaking.setChecked(true);
            //----------------------------------------this bunch of code is the same as the code that is performed when wh check the checkbox
            barcodeStockTakingChkStatus = true;
            performWhenChkBarcodeStocktakingModeisChecked();
            //----------------------------------
            if(barcodeMode!=-1 && barcodeMode==1){
                performWhenRbAutomaticBarcodeStockTaakingClicked();
                rbAutomaticBarcodeStockTaking.setChecked(true);
                rbManualBarcodeStockTaking.setChecked(false);

            } else if(barcodeMode!=-1 && barcodeMode==2) {
                performWhenRbManualBarcodeStockTaakingClicked();
                //Toast.makeText(this, barcodeMode+"", Toast.LENGTH_SHORT).show();
                rbAutomaticBarcodeStockTaking.setChecked(false);
                rbManualBarcodeStockTaking.setChecked(true);
            }

        } else {
            chkarcodeStockTaking.setChecked(false);
            barcodeStockTakingChkStatus = false;
            performWhenChkBarcodeStocktakingModeisUnChecked();
        }


        checkAndSetBarcodeStockTakingMode();

        Intent fromInventoryActivity = getIntent();
        /*
        if (Boolean.parseBoolean(fromInventoryActivity.getStringExtra("isItemNameChecked"))) {
            llItemName.setVisibility(View.VISIBLE);
        } else {
            llItemName.setVisibility(View.GONE);
        }

        if (Boolean.parseBoolean(fromInventoryActivity.getStringExtra("isBarcodeNumberChecked"))) {
            llBarcodeNumber.setVisibility(View.VISIBLE);
        } else {
            llBarcodeNumber.setVisibility(View.GONE);
        }

        if (Boolean.parseBoolean(fromInventoryActivity.getStringExtra("isFactoryNumberChecked"))) {
            llFactoryNumber.setVisibility(View.VISIBLE);
        } else {
            llFactoryNumber.setVisibility(View.GONE);
        }

        if (Boolean.parseBoolean(fromInventoryActivity.getStringExtra("isOriginalNumberChecked"))) {
            llOriginalNumber.setVisibility(View.VISIBLE);
        } else {
            llOriginalNumber.setVisibility(View.GONE);
        }*/

        stockTakingID = Integer.parseInt(fromInventoryActivity.getStringExtra("stockTakingID"));
        sessionID = Integer.parseInt(fromInventoryActivity.getStringExtra("sessionID"));



        /*
String s="";
       s+= fromInventoryActivity.getStringExtra("isItemNameChecked");
        s+="\n";
        s+=fromInventoryActivity.getStringExtra("isBarcodeNumberChecked");
        s+="\n";
        s+=fromInventoryActivity.getStringExtra("isFactoryNumberChecked");
        s+="\n";
        s+=fromInventoryActivity.getStringExtra("isOriginalNumberChecked");
        s+="\n";
        s+=fromInventoryActivity.getStringExtra("stockTakingID");
        s+="\n";
        s+=fromInventoryActivity.getStringExtra("sessionID");
        s+="\n";
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        */


    }

    public void onFocusChange(View view, boolean hasFocus) {

        switch (view.getId()) {

            case R.id.acItemNumber:
                if (!hasFocus) {
                    lastFieldToSearch = 0;

                }
                break;
            case R.id.acItemName:
                if (!hasFocus)
                    lastFieldToSearch = 1;

                break;
            case R.id.acBarcodeNumber:
                if (!hasFocus && acBarcodeNumber.getVisibility() == View.VISIBLE)
                    lastFieldToSearch = 2;

                break;
            case R.id.acFactoryNumber:
                if (!hasFocus)
                    lastFieldToSearch = 3;

                break;
            case R.id.acOriginalNumber:
                if (!hasFocus)
                    lastFieldToSearch = 4;

                break;
            case R.id.etBarcodeNumber:
                if (!hasFocus)
                    lastFieldToSearch = 5;

                break;


        }


    }


    public enum FieldType {

        fromItemNameField,
        fromBarcodeNumberField,
        fromFactoryNumberField,
        fromOriginalNumberField
    }

    private void initialize() {

        mode = 0;
        colorStatus=-1;
        requiredID="";
        updateMode = 0;
        isItemChosen = 0;
        fromInventoryTableA = 0;
        lastFieldToSearch = 0;
        result = "";
        undefinedBarcodeName ="";
        barcodeStockTakingChkStatus = false;
        db = new DB(this);
        db.open(true);
        hasFocus = -1;
        btnSave = (Button) findViewById(R.id.btnInventorySave);
        btnClearFields = (Button) findViewById(R.id.btnInventoryClearFields);
        btnDeleteStockTakingRecord = (Button) findViewById(R.id.btnDeleteStocktakingRecord);
        btnDisplayTransactions = (Button) findViewById(R.id.btnInventoryDisplayTransactions);
        btnDisplayTransactions.setOnClickListener(this);
        llItemName = (LinearLayout) findViewById(R.id.llItemName);
        llBarcodeNumber = (LinearLayout) findViewById(R.id.llBarcodeNumber);
        llFactoryNumber = (LinearLayout) findViewById(R.id.llFactoryNumber);
        llOriginalNumber = (LinearLayout) findViewById(R.id.llOriginalNumber);
        llSpRequiredIDs = (LinearLayout) findViewById(R.id.llRequiredIDsStocktaking);
        acItemNumber = (AutoCompleteTextView) findViewById(R.id.acItemNumber);
        acItemName = (AutoCompleteTextView) findViewById(R.id.acItemName);
        acBarcodeNumber = (AutoCompleteTextView) findViewById(R.id.acBarcodeNumber);
        acFactoryNumber = (AutoCompleteTextView) findViewById(R.id.acFactoryNumber);
        acOriginalNumber = (AutoCompleteTextView) findViewById(R.id.acOriginalNumber);
        etItemQuantity = (EditText) findViewById(R.id.etItemQuantity);
        etBarcodeNumber = (EditText) findViewById(R.id.etBarcodeNumber);
        spRequiredIDs = (Spinner) findViewById(R.id.spRequiredIDsStocktaking);
        chkarcodeStockTaking = (CheckBox) findViewById(R.id.chkBarcodeStocktakingMode);
        chkarcodeStockTaking.setOnClickListener(this);
        rgBarcodeStockTakingGroup = (RadioGroup) findViewById(R.id.rgBarcodeStockTakingGroup);
        rbAutomaticBarcodeStockTaking = (RadioButton) findViewById(R.id.rbAutomaticBarcodeStockTaaking);
        rbManualBarcodeStockTaking = (RadioButton) findViewById(R.id.rbManualBarcodeStockTaaking);
        rbAutomaticBarcodeStockTaking.setOnClickListener(this);
        rbManualBarcodeStockTaking.setOnClickListener(this);



        spInventoryUnit = (Spinner) findViewById(R.id.spInventoryUnit);
        btnSave.setOnClickListener(this);
        btnClearFields.setOnClickListener(this);
        btnDeleteStockTakingRecord.setOnClickListener(this);

        rgBarcodeStockTakingGroup.setVisibility(View.INVISIBLE);


/*
        acItemNumber.setThreshold(1);
        acItemName.setThreshold(1);
        acBarcodeNumber.setThreshold(1);
        acFactoryNumber.setThreshold(1);
        acOriginalNumber.setThreshold(1);*/


        addRequiredListenersForAutoCompleteTextviews();

        acItemNumber.setOnFocusChangeListener(this);
        acItemName.setOnFocusChangeListener(this);
        acBarcodeNumber.setOnFocusChangeListener(this);
        acFactoryNumber.setOnFocusChangeListener(this);
        acOriginalNumber.setOnFocusChangeListener(this);
        etBarcodeNumber.setOnFocusChangeListener(this);
    }

    private void addRequiredListenersForAutoCompleteTextviews() {


        acItemNumber.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mode == 0 &&acItemNumber.getText().toString().endsWith(" ")) {
                    fillAutoCompleTetextviewList(DB.StockEntity.KEY_CODE, acItemNumber.getText().toString().trim());
                    if(acItemNumber.isPopupShowing()){

                    }
                }
                else{

                    if(acItemNumber.getText().toString().endsWith(System.getProperty("line.separator"))){
                        Toast.makeText(stockTaking2.this,Util.getMsg(stockTaking2.this, R.string.msg_manual_stockTaking_mode_no_barcode), Toast.LENGTH_SHORT).show();

                        resetValues();
                    }
                    items = new ArrayList<String>();
                    adapter = new AutoCompleteAdapter3(stockTaking2.this, android.R.layout.simple_spinner_item, items);
                    acItemNumber.setAdapter(adapter);
                    if(acItemNumber.isPopupShowing()){
                        acItemNumber.dismissDropDown();

                    }

                }
            }

            public void afterTextChanged(Editable editable) {

            }
        });
        acItemName.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (mode == 0 && acItemName.getText().toString().endsWith(" ")) {
                        fillAutoCompleTetextviewList(DB.StockEntity.KEY_NAME, acItemName.getText().toString().trim());

                        if(acItemName.isPopupShowing()){

                        }

                        //fillAutoCompleTetextviewList(1, acItemName.getText().toString().trim());
                    } else {

                        if (acItemNumber.getText().toString().endsWith(System.getProperty("line.separator"))) {
                            Toast.makeText(stockTaking2.this,Util.getMsg(stockTaking2.this, R.string.msg_manual_stockTaking_mode_no_barcode), Toast.LENGTH_SHORT).show();
                            // Toast.makeText(stockTaking2.this, "أنت في وضع الجرد اليدوي و ممنوع استخدام قارئ الباركود ", Toast.LENGTH_SHORT).show();
                            resetValues();
                        }

                        items = new ArrayList<String>();
                        adapter = new AutoCompleteAdapter3(stockTaking2.this, android.R.layout.simple_spinner_item, items);
                        acItemName.setAdapter(adapter);
                        if(acItemName.isPopupShowing()){
                            acItemName.dismissDropDown();

                        }


                    }
                }catch(Exception e){
                    Util.logException(stockTaking2.this, e);
                    //Toast.makeText(stockTaking2.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }


            public void afterTextChanged(Editable editable) {

            }
        });
        acBarcodeNumber.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mode == 0&&acBarcodeNumber.getText().toString().endsWith(" ")) {
                    fillAutoCompleTetextviewList(DB.StockEntity.KEY_BARCODE, acBarcodeNumber.getText().toString().trim());
                    if(acBarcodeNumber.isPopupShowing()){

                    }
                }
                else{

                    if(acBarcodeNumber.getText().toString().endsWith(System.getProperty("line.separator"))){
                        Toast.makeText(stockTaking2.this,Util.getMsg(stockTaking2.this, R.string.msg_manual_stockTaking_mode_no_barcode), Toast.LENGTH_SHORT).show();
                        // Toast.makeText(stockTaking2.this, "أنت في وضع الجرد اليدوي و ممنوع استخدام قارئ الباركود ", Toast.LENGTH_SHORT).show();
                        resetValues();
                    }
                    items = new ArrayList<String>();
                    adapter = new AutoCompleteAdapter3(stockTaking2.this, android.R.layout.simple_spinner_item, items);
                    acBarcodeNumber.setAdapter(adapter);
                    if(acBarcodeNumber.isPopupShowing()){
                        acBarcodeNumber.dismissDropDown();

                    }
                }


            }

            public void afterTextChanged(Editable editable) {

            }
        });
        acFactoryNumber.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mode == 0 &&acFactoryNumber.getText().toString().endsWith(" ")) {
                    fillAutoCompleTetextviewList(DB.StockEntity.KEY_FACTORYNO, acFactoryNumber.getText().toString().trim());
                    if(acFactoryNumber.isPopupShowing()){

                    }
                }
                else{

                    if(acFactoryNumber.getText().toString().endsWith(System.getProperty("line.separator"))){
                        Toast.makeText(stockTaking2.this,Util.getMsg(stockTaking2.this, R.string.msg_manual_stockTaking_mode_no_barcode), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(stockTaking2.this, "أنت في وضع الجرد اليدوي و ممنوع استخدام قارئ الباركود ", Toast.LENGTH_SHORT).show();
                        resetValues();
                    }
                    items = new ArrayList<String>();
                    adapter = new AutoCompleteAdapter3(stockTaking2.this, android.R.layout.simple_spinner_item, items);
                    acFactoryNumber.setAdapter(adapter);
                    if(acFactoryNumber.isPopupShowing()){
                        acFactoryNumber.dismissDropDown();

                    }
                }
            }

            public void afterTextChanged(Editable editable) {

            }
        });
        acOriginalNumber.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mode == 0 &&acOriginalNumber.getText().toString().endsWith(" ")) {
                    fillAutoCompleTetextviewList(DB.StockEntity.KEY_ORIGNALNO, acOriginalNumber.getText().toString().trim());
                    if(acOriginalNumber.isPopupShowing()){

                    }
                }
                else{

                    if(acOriginalNumber.getText().toString().endsWith(System.getProperty("line.separator"))){
                        Toast.makeText(stockTaking2.this,Util.getMsg(stockTaking2.this, R.string.msg_manual_stockTaking_mode_no_barcode), Toast.LENGTH_SHORT).show();
                        //  Toast.makeText(stockTaking2.this, "أنت في وضع الجرد اليدوي و ممنوع استخدام قارئ الباركود ", Toast.LENGTH_SHORT).show();
                        resetValues();
                    }
                    items = new ArrayList<String>();
                    adapter = new AutoCompleteAdapter3(stockTaking2.this, android.R.layout.simple_spinner_item, items);
                    acOriginalNumber.setAdapter(adapter);
                    if(acOriginalNumber.isPopupShowing()){
                        acOriginalNumber.dismissDropDown();

                    }
                }
            }

            public void afterTextChanged(Editable editable) {

            }
        });


        acItemNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mode = 1;
                isItemChosen = 1;
                llItem = (LinearLayout)view;
                TextView item =(TextView)llItem.getChildAt(0);
                requiredID = item.getTag().toString();
                DB.Stock requiredItem = getItemDetails();
                fillItemDetailsInFields(requiredItem);
                mode = 0;
                etItemQuantity.requestFocus();

            }
        });
        acItemName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mode = 1;
                isItemChosen = 1;
                llItem = (LinearLayout)view;
                TextView item =(TextView)llItem.getChildAt(0);
                requiredID = item.getTag().toString();
                DB.Stock requiredItem = getItemDetails();
                fillItemDetailsInFields(requiredItem);
                mode = 0;
                etItemQuantity.requestFocus();
            }


        });
        acBarcodeNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mode = 1;
                isItemChosen = 1;
                llItem = (LinearLayout)view;
                TextView item =(TextView)llItem.getChildAt(0);
                requiredID = item.getTag().toString();
                DB.Stock requiredItem = getItemDetails();
                fillItemDetailsInFields(requiredItem);
                mode = 0;
                etItemQuantity.requestFocus();

            }
        });
        acFactoryNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mode = 1;
                isItemChosen = 1;
                llItem = (LinearLayout)view;
                TextView item =(TextView)llItem.getChildAt(0);
                requiredID = item.getTag().toString();
                DB.Stock requiredItem = getItemDetails();
                fillItemDetailsInFields(requiredItem);
                mode = 0;
                etItemQuantity.requestFocus();

            }
        });
        acOriginalNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mode = 1;
                isItemChosen = 1;
                llItem = (LinearLayout)view;
                TextView item =(TextView)llItem.getChildAt(0);
                requiredID = item.getTag().toString();
                DB.Stock requiredItem = getItemDetails();
                fillItemDetailsInFields(requiredItem);
                mode = 0;
                etItemQuantity.requestFocus();

            }
        });


        etBarcodeNumber.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void afterTextChanged(Editable editable) {



                if (checkAndSetBarcodeStockTakingMode() == 2 && mode==0) {
                    if (etBarcodeNumber.getText().toString().endsWith(System.getProperty("line.separator"))) {
                        db.open(true);
                        DB.StockEntity stockE = db.new StockEntity();
                        String where = "LTRIM(RTRIM("+" barcode)) = " + etBarcodeNumber.getText().toString().replace("\n", "").trim();
                        int id = stockE.checkExisting(where);
                        DB.StockTakingTransactionsEntity stte = db.new StockTakingTransactionsEntity();
                        DB.StockTakingTransactions stt = db.new StockTakingTransactions();
                        mode=1;
                        if (id != -1) {
                            DB.Stock s = stockE.getItem(id);
                            mode = 1;
                            // int unitID = 0;
                            fillItemDetailsInFields(s);

                        }
                        etItemQuantity.requestFocus();
                        etBarcodeNumber.setText(etBarcodeNumber.getText().toString().replace("\n", "").trim());
                        mode=0;
                    }


                }
                /*
                if(mode==1){
                    etItemQuantity.requestFocus();
                    mode=0;
                }*/




                if (checkAndSetBarcodeStockTakingMode() == 1) {

                    if (etBarcodeNumber.getText().toString().endsWith(System.getProperty("line.separator"))) {

                        // saveStockTakingTransactionForAutomaticBarcodeMode();
                        newSaveStockTakingTransactionForAutomaticBarcodeMode();
                        /*
                        DB.StockEntity stockE = db.new StockEntity();
                        String where = " barcode = " + etBarcodeNumber.getText().toString().replace("\n", "").trim();
                        int id = stockE.checkExisting(where);
                        DB.StockTakingTransactionsEntity stte = db.new StockTakingTransactionsEntity();
                        DB.StockTakingTransactions stt = db.new StockTakingTransactions();

                        if (id != -1) {
                            DB.Stock s = stockE.get_Item(id);
                            mode = 1;
                            int unitID = 0;
                            fillItemDetailsInFields(s);


                            String strQuantity = etItemQuantity.getText().toString().trim();
                            stt.itemID = s.id;
                            stt.itemName = s.name.trim();
                            stt.quantity = Integer.parseInt(strQuantity.trim());
                            stt.stockTatkingID = stockTakingID;
                            stt.sessionID = sessionID;
                            if (spInventoryUnit.getAdapter().getCount() > 0 && !spInventoryUnit.getSelectedItem().toString().trim().equals("")) {
                                stt.unitID = spInventoryUnit.getSelectedItemPosition();
                                stt.unitName = spInventoryUnit.getSelectedItem().toString().trim();
                            } else {
                                stt.unitID = 0;
                                stt.unitName = s.unit_1.trim();
                            }
                            unitID = stt.unitID;
                            where = " item_id_transactions = " + s.id + " and  unit_id_transactions = " + unitID;
                            if (updateMode == 0) {
                                if (stte.checkExisting(where) != -1) {
                                    int updatedQuantity = stte.get(where).get(0).quantity;
                                    updatedQuantity += Integer.parseInt(strQuantity);
                                    updateStockTransactions(updatedQuantity, stte, where);
                                } else {
                                    insertIntoStockTransactions(s, stte, strQuantity);
                                }
                            } else {
                                int updatedQuantity = Integer.parseInt(strQuantity.trim());
                                updateStockTransactions(updatedQuantity, stte, where);
                            }
                            // stte.insert(stt);
                            etBarcodeNumber.requestFocus();
                            Toast.makeText(stockTaking2.this, "تم الجرد", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(stockTaking2.this, "No barcode here \n in the future we will add table", Toast.LENGTH_SHORT).show();
                        }
                        //  resetValues();
                        etBarcodeNumber.setText("");
                        updateMode = 0;
                              */

                    }


                }

            }
        });

    }
    private void newSaveStockTakingTransactionForAutomaticBarcodeMode() {

        try {

            db.open(true);
            DB.StockEntity stockE = db.new StockEntity();
            DB.UnitsEntity unitsE = db.new UnitsEntity();
            DB.Units units = db.new Units();
            DB.StockTakingTransactionsEntity stte = db.new StockTakingTransactionsEntity();
            DB.StockTakingTransactions stt = db.new StockTakingTransactions();
            final String itemBarcodeNumber = etBarcodeNumber.getText().toString().replace("\n", "").trim();
            // final String itemBarcodeNumber = "123                 ";
            final String quantity = etItemQuantity.getText().toString().trim();

            //String where = DB.UnitsEntity.KEY_ITEM_BARCODE_UNITS+" = '" + etBarcodeNumber.getText().toString().replace("\n", "").trim()+"'";
            String where = "LTRIM(RTRIM("+DB.UnitsEntity.KEY_ITEM_BARCODE_UNITS + ")) = '" + itemBarcodeNumber + "'";
            String code = unitsE.checkExisting(where);
            if (!code.equals("-1")) {
                DB.Stock s = stockE.getItemByCode(code);
                units = unitsE.get(where).get(0);
                mode = 1;
                int unitID = units.shamelUnitID;
                fillItemDetailsInFields(s);
                spInventoryUnit.setSelection(unitID);
                String strQuantity = etItemQuantity.getText().toString().trim();
                stt.itemID = s.id;
                stt.itemName = s.name.trim();
                stt.quantity = Double.parseDouble(strQuantity.trim());
                stt.stockTatkingID = stockTakingID;
                stt.sessionID = sessionID;
                stt.unitID = units.shamelUnitID;
                stt.stockTakingDate = Util.getCurrentDate();
                stt.isPosted = 0;

                switch (unitID) {
                    case 0:
                        stt.unitName = spInventoryUnit.getItemAtPosition(unitID).toString().trim();
                        break;
                    case 1:
                        stt.unitName = spInventoryUnit.getItemAtPosition(unitID).toString().trim();
                        break;
                    case 2:
                        stt.unitName = spInventoryUnit.getItemAtPosition(unitID).toString().trim();
                        break;
                    case 3:
                        stt.unitName = spInventoryUnit.getItemAtPosition(unitID).toString().trim();
                        break;
                    case 4:
                        stt.unitName = spInventoryUnit.getItemAtPosition(unitID).toString().trim();
                        break;
                    case 5:
                        stt.unitName = spInventoryUnit.getItemAtPosition(unitID).toString().trim();
                        break;
                }

                where = " item_id_transactions = " + s.id + " and  unit_id_transactions = " + unitID;
                if (updateMode == 0) {
                    if (stte.checkExisting(where) != -1) {
                        double updatedQuantity = stte.get(where).get(0).quantity;
                        updatedQuantity += Double.parseDouble(strQuantity);
                        updateStockTransactions(updatedQuantity, stte, where);
                    } else {
                        stte.insert(stt);
                        //insertIntoStockTransactions(s, stte, strQuantity);
                    }
                } else {

                    if (colorStatus == -1) {
                        double updatedQuantity = Double.parseDouble(strQuantity.trim());
                        updateStockTransactions(updatedQuantity, stte, where);
                    } else if (colorStatus == 1) {
                        boolean isTransactionDone = false;
                        if (stte.checkExisting(where) != -1) {
                            double updatedQuantity = stte.get(where).get(0).quantity;
                            updatedQuantity += Double.parseDouble(strQuantity);
                            isTransactionDone = updateStockTransactions(updatedQuantity, stte, where);


                        } else {
                            isTransactionDone = stte.insert(stt);
                            //insertIntoStockTransactions(s, stte, strQuantity);
                        }
                        if (isTransactionDone) {
                            deleteUndefinedBarcode(DB.UndefinedStockTakingTransactionsEntity.KEY_UNDEFINED_ITEM_BARCODE_TRANSACTIONS + " = '" + undefinedBarcode + "'");
                        }

                    }

                }
                // stte.insert(stt);
                etBarcodeNumber.requestFocus();

                Toast.makeText(stockTaking2.this, Util.getMsg(stockTaking2.this, R.string.msg_stockTaking_done), Toast.LENGTH_SHORT).show();
                db.close();
            } else {





                final MediaPlayer mp = MediaPlayer.create(this, R.raw.beep);
                mp.setLooping(true);
                mp.setVolume(100, 100);
                mp.start();


                /*
                final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                long pattern[] = {0, 1000, 100};
                //start vibration with repeated count, use -1 if you don't want to repeat the vibration
                vibrator.vibrate(pattern, 0);*/


                if (chkarcodeStockTaking.isChecked()/*&&rbManualBarcodeStockTaking.isChecked()*/) {
                    new AlertDialog.Builder(this)
                            .setCancelable(false)
                            .setTitle(Util.getMsg(stockTaking2.this, R.string.msg_barcode_doesnt_exist))
                            .setMessage(Util.getMsg(stockTaking2.this, R.string.msg_barcode_doesnt_exist_but_willBeHandeled))
                            .setPositiveButton(R.string.btn_continue_saving_undefined_barcode, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    mp.stop();
                                    //vibrator.cancel();
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                                    final Dialog enterUndefinedBarcodeNameDialog = new Dialog(stockTaking2.this);
                                    // Include dialog.xml file
                                    enterUndefinedBarcodeNameDialog.setContentView(R.layout.enter_undefined_barcode_name_dialog);
                                    // Set dialog title
                                    enterUndefinedBarcodeNameDialog.setTitle(R.string.msg_undefined_barcode_name_dialog_title);

                                    etUndefinedBarcodeName = (EditText) enterUndefinedBarcodeNameDialog.findViewById(R.id.etCdUndefinedBarcodeName);
                                    btnEnterUndefinedBarcodeName = (Button) enterUndefinedBarcodeNameDialog.findViewById(R.id.btnCdEnterUndefinedBarcodeName);
                                    btnCancelUndefinedBarcodeNameDialog = (Button) enterUndefinedBarcodeNameDialog.findViewById(R.id.btnCdCancelUndefinedBarcodeNameDialog);
                                    btnEnterUndefinedBarcodeName.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View view) {

                                            if (!etUndefinedBarcodeName.getText().toString().trim().equals("")) {
                                                undefinedBarcodeName = etUndefinedBarcodeName.getText().toString().trim();
                                                enterUndefinedBarcodeNameDialog.dismiss();
                                                saveUndefinedStockTakingTransactionForAutomaticBarcodeMode(itemBarcodeNumber, quantity);
                                            } else {
                                                Toast.makeText(stockTaking2.this, R.string.msg_please_enter_undefined_barcode_name, Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                                    btnCancelUndefinedBarcodeNameDialog.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View view) {
                                            Toast.makeText(stockTaking2.this, R.string.msg_undefined_barcode_has_not_been_saved, Toast.LENGTH_SHORT).show();
                                            enterUndefinedBarcodeNameDialog.dismiss();


                                        }
                                    });


                                    enterUndefinedBarcodeNameDialog.show();
                                    enterUndefinedBarcodeNameDialog.setCancelable(false);


                                }
                            })
                            .show();

                }


                //saveUndefinedStockTakingTransactionForAutomaticBarcodeMode();
                //saveUndefinedStockTakingTransactionForAutomaticBarcodeMode();
                //Toast.makeText(stockTaking2.this, "No barcode here \n in the future we will add table", Toast.LENGTH_SHORT).show();
            }
            //  resetValues();
            etBarcodeNumber.setText("");
            updateMode = 0;
            colorStatus = -1;
        }
        catch (Exception e){
            Util.logException(this,e);
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
    private void saveStockTakingTransactionForAutomaticBarcodeMode() {

        db.open(true);
        DB.StockEntity stockE = db.new StockEntity();
        String where = "LTRIM(RTRIM("+DB.StockEntity.KEY_BARCODE + ")) = " + etBarcodeNumber.getText().toString().replace("\n", "").trim();
        String barcodeNumber = etBarcodeNumber.getText().toString().replace("\n", "").trim();
        String strQuantity="0.0";
        //String where = " barcode = " + etBarcodeNumber.getText().toString().replace("\n", "").trim();
        int id = stockE.checkExisting(where);
        DB.StockTakingTransactionsEntity stte = db.new StockTakingTransactionsEntity();
        DB.StockTakingTransactions stt = db.new StockTakingTransactions();

        if (id != -1) {
            DB.Stock s = stockE.getItem(id);
            mode = 1;
            int unitID = 0;
            fillItemDetailsInFields(s);
            strQuantity = etItemQuantity.getText().toString().trim();
            stt.itemID = s.id;
            stt.itemName = s.name.trim();
            stt.quantity = Double.parseDouble(strQuantity.trim());
            stt.stockTatkingID = stockTakingID;
            stt.sessionID = sessionID;
            stt.stockTakingDate = Util.getCurrentDate();
            stt.isPosted=0;
            if (spInventoryUnit.getAdapter().getCount() > 0 && !spInventoryUnit.getSelectedItem().toString().trim().equals("")) {
                stt.unitID = spInventoryUnit.getSelectedItemPosition();
                stt.unitName = spInventoryUnit.getSelectedItem().toString().trim();
            } else {
                stt.unitID = 0;
                stt.unitName = s.unit_1.trim();
            }
            unitID = stt.unitID;
            where =   DB.StockTakingTransactionsEntity.KEY_ITEM_ID_TRANSACTIONS+" = " + s.id + " and "+DB.StockTakingTransactionsEntity.KEY_UNIT_ID_TRANSACTIONS +"  = " + unitID;
            //where = " item_id_transactions = " + s.id + " and  unit_id_transactions = " + unitID;
            if (updateMode == 0) {
                if (stte.checkExisting(where) != -1) {
                    double updatedQuantity = stte.get(where).get(0).quantity;
                    updatedQuantity += Double.parseDouble(strQuantity);
                    updateStockTransactions(updatedQuantity, stte, where);
                } else {
                    insertIntoStockTransactions(s, stte, strQuantity);
                }
            } else {
                double updatedQuantity = Double.parseDouble(strQuantity.trim());
                updateStockTransactions(updatedQuantity, stte, where);
            }
            // stte.insert(stt);
            etBarcodeNumber.requestFocus();
            Toast.makeText(stockTaking2.this, R.string.msg_stocktaking_done, Toast.LENGTH_SHORT).show();
            db.close();
        } else {
            saveUndefinedStockTakingTransactionForAutomaticBarcodeMode(barcodeNumber,strQuantity);
            //Toast.makeText(stockTaking2.this, "No barcode here \n in the future we will add table", Toast.LENGTH_SHORT).show();
        }
        //  resetValues();
        etBarcodeNumber.setText("");
        updateMode = 0;
        colorStatus=-1;


    }

    private void saveUndefinedStockTakingTransactionForAutomaticBarcodeMode(String barcodeNumber,String quantity) {

        db.open(true);
        DB.UndefinedStockTakingTransactionsEntity ustte = db.new UndefinedStockTakingTransactionsEntity();
        DB.UndefinedStockTakingTransactions ustt = db.new UndefinedStockTakingTransactions();
        mode = 1;
        int unitID = 0;
        //String barcodeNumber= etBarcodeNumber.getText().toString().replace("\n", "").trim();
        //String strQuantity = etItemQuantity.getText().toString().trim();
        String strQuantity = quantity;
        ustt.itemBarcode =  barcodeNumber;
        ustt.quantity = Double.parseDouble(strQuantity);
        ustt.stockTatkingID = stockTakingID;
        ustt.sessionID = sessionID;
        ustt.stockTakingDate = Util.getCurrentDate();
        ustt.undefinedBarcodeName = undefinedBarcodeName;
        String where =  "LTRIM(RTRIM("+DB.UndefinedStockTakingTransactionsEntity.KEY_UNDEFINED_ITEM_BARCODE_TRANSACTIONS +"))  = '" + barcodeNumber+"'";
        //String where = " undefined_item_barcode_transactions = '" + barcodeNumber+"'";
        if (updateMode == 0) {
            if (ustte.checkExisting(where) != -1) {
                double updatedQuantity = ustte.get(where).get(0).quantity;
                updatedQuantity += Double.parseDouble(strQuantity);
                updateUndefinedStockTransactions(updatedQuantity, ustte, where);
            } else {
                insertIntoUndefinedStockTransactions(ustt);
            }
        } else {
            double updatedQuantity = Double.parseDouble(strQuantity.trim());
            updateUndefinedStockTransactions(updatedQuantity, ustte, where);
        }
        etBarcodeNumber.requestFocus();
        Toast.makeText(stockTaking2.this, R.string.msg_stocktaking_done, Toast.LENGTH_SHORT).show();
        db.close();
        etBarcodeNumber.setText("");
        updateMode = 0;
        colorStatus=-1;


    }

    private void fillAutoCompleTetextviewList(String fieldType, String dataToSearch) {


        try {

            items = getItems(fieldType, dataToSearch);
            if (items.size() != 0) {
                //AutoCompleteAdapter3 adapter = new AutoCompleteAdapter3(stockTaking2.this, android.R.layout.simple_spinner_item, items);
                adapter = new AutoCompleteAdapter3(stockTaking2.this, android.R.layout.simple_spinner_item, items);
                ///*
                if(fieldType.equals(DB.StockEntity.KEY_CODE)){  acItemNumber.setAdapter(adapter);}
                else if(fieldType.equals(DB.StockEntity.KEY_NAME)){ acItemName.setAdapter(adapter);}
                else if(fieldType.equals(DB.StockEntity.KEY_BARCODE)){acBarcodeNumber.setAdapter(adapter);}
                else if(fieldType.equals(DB.StockEntity.KEY_FACTORYNO)){ acFactoryNumber.setAdapter(adapter);}
                else if(fieldType.equals(DB.StockEntity.KEY_ORIGNALNO)){acOriginalNumber.setAdapter(adapter);}
                else {
                    // resetValues();
                }//*/

               /*
                switch (fieldType) {
                    case 0:
                        acItemNumber.setAdapter(adapter);
                        break;
                    case 1:
                        acItemName.setAdapter(adapter);
                        break;
                    case 2:
                        acBarcodeNumber.setAdapter(adapter);
                        break;
                    case 3:
                        acFactoryNumber.setAdapter(adapter);
                        break;
                    case 4:
                        acOriginalNumber.setAdapter(adapter);
                        break;
                }*/


            } else {
                // resetValues();
            }



        } catch (Exception e) {
            Util.logException(stockTaking2.this, e);
            // Toast.makeText(this, "--->> \n" + e.toString(), Toast.LENGTH_LONG).show();
        }


    }

    private ArrayList<String> getItems(String fieldType, String dataToSearch) {


        if (!dataToSearch.trim().equals("")) {
            db = new DB(this);
            db.open(true);
            DB.StockEntity stockE = db.new StockEntity();
            dataToSearch = dataToSearch.trim().toUpperCase();
            dataToSearch = dataToSearch.replace(' ', '%');
            ArrayList<String> listOfItems = stockE.getSearchByAnyFieldValue(fieldType, dataToSearch);
            return listOfItems;
        } else {
            ArrayList<String> listToClear = new ArrayList<String>();
            listToClear.clear();
            ArrayAdapter<String> adapterToClear = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listToClear);
            spRequiredIDs.setAdapter(adapterToClear);
            return listToClear;
        }


    }

    private boolean checkIfItemExsists() {

        try {
            //String id = spRequiredIDs.getSelectedItem().toString();

            if (!requiredID.trim().equals("")) {
                int intID = Integer.parseInt(requiredID.trim());
                DB.StockEntity stockE = db.new StockEntity();
                if (stockE.checkExisting(intID)) {

                    return true;

                } else {
                    return false;

                }


            } else {
                return false;

            }
        } catch (Exception e) {
            Util.logException(stockTaking2.this, e);
            //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private DB.Stock getItemDetails() {


        DB.StockEntity stockE = db.new StockEntity();
        if (checkIfItemExsists()) {

            String id = requiredID.trim();

            int intID = Integer.parseInt(id);
            DB.Stock requiredItem = stockE.getItem(intID);
            return requiredItem;

        } else {

            return null;
        }




/*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, stockE.get_itemID_search("0=0", fieldType,false ));
        acItemNumber.setAdapter(adapter);*/


    }

    private void fillItemDetailsInFields(DB.Stock requiredItem) {

        acItemNumber.setText(requiredItem.code + "");
        acItemName.setText(requiredItem.name);
        acBarcodeNumber.setText(requiredItem.barcode);
        acFactoryNumber.setText(requiredItem.factoryNo);
        acOriginalNumber.setText(requiredItem.orignalNo);


        ArrayList<String> list = new ArrayList<String>();
        list.add(requiredItem.unit_1);
        if (!requiredItem.unit_2.toString().trim().equals(""))
            list.add(requiredItem.unit_2);
        if (!requiredItem.unit_3.toString().trim().equals(""))
            list.add(requiredItem.unit_3);
        if (!requiredItem.unit_4.toString().trim().equals(""))
            list.add(requiredItem.unit_4);
        if (!requiredItem.unit_5.toString().trim().equals(""))
            list.add(requiredItem.unit_5);
        if (!requiredItem.unit_6.toString().trim().equals(""))
            list.add(requiredItem.unit_6);
        clearSpinner(1);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spInventoryUnit.setAdapter(dataAdapter);


    }

    private void clearSpinner(int whichSpinner) {

        ArrayList<String> listToClear = new ArrayList<String>();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listToClear);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (whichSpinner == 0) {
            spRequiredIDs.setAdapter(dataAdapter);
        }
        if (whichSpinner == 1) {
            spInventoryUnit.setAdapter(dataAdapter);
        }


    }

    private int checkAndSetBarcodeStockTakingMode() {

        if (chkarcodeStockTaking.isChecked()) {

            setAutoCompleteTextviewsHints(1);
            if (rbAutomaticBarcodeStockTaking.isChecked()) {

                lockAutocompletetextviews();
                etItemQuantity.setEnabled(false);
                etItemQuantity.setText("1.0");
                return 1;
            } else if(rbManualBarcodeStockTaking.isChecked()) {
                lockAutocompletetextviews();
                etItemQuantity.setEnabled(true);
                //etItemQuantity.setText("");
                return 2;
            }

        } else {
            unLockAutocompletetextviews();
            setAutoCompleteTextviewsHints(0);
            return 0;
        }
        return -1;
    }

    private void setAutoCompleteTextviewsHints(int stockTakingMode) {

        if (stockTakingMode == 0) {

            acItemNumber.setHint("أدخل رقم الصنف يديويا");
            acItemName.setHint("أدخل اسم الصنف يديويا");
            acBarcodeNumber.setHint("أدخل رقم الباركود يدويا");
            acFactoryNumber.setHint("أدخل رقم المصنع يديويا");
            acOriginalNumber.setHint("أدخل الرقم الأصلي يدويا");
        } else {
            //  acItemNumber.setHint("أدخل رقم الصنف باستخدام الباركود");
            // acItemName.setHint("أدخل اسم الصنف باستخدام الباركود");
            //acBarcodeNumber.setHint("أدخل رقم الباركود باستخدام الباركود");
            acItemNumber.setHint("");
            acItemName.setHint("");
            acBarcodeNumber.setHint("");
            acFactoryNumber.setHint("");
            acOriginalNumber.setHint("");
            etBarcodeNumber.setHint("أدخل رقم الباركود باستخدام قارئ الباركود");
            // acFactoryNumber.setHint("أدخل رقم المصنع باستخدام الباركود");
            // acOriginalNumber.setHint("أدخل الرقم الأصلي باستخدام الباركود");


        }

    }

    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.btnInventorySave:
                int inventoryMode = checkAndSetBarcodeStockTakingMode();
                if(inventoryMode==0){
                    db.open(true);
                    newSaveStockTakingTransaction();
                    db.close();
                }
                else if(inventoryMode==2){
                    String strQuantity = etItemQuantity.getText().toString().trim();
                    if(!etBarcodeNumber.getText().toString().trim().equals("")) {
                        if (!strQuantity.equals("") && Double.parseDouble(strQuantity) > 0) {
                            // saveStockTakingTransactionForAutomaticBarcodeMode();
                            newSaveStockTakingTransactionForAutomaticBarcodeMode();
                            etItemQuantity.setText("");
                            etBarcodeNumber.requestFocus();
                            resetValues();
                        } else {

                            Util.showToast(stockTaking2.this, " الرجاء إدخال كمية موجبة", true);
                        }
                    }
                    else{
                        Util.showToast(stockTaking2.this, "الرجاء إدخال قيمة في حقل رقم الباركود باستخدام قارئ الباركود", true);
                    }
                }
                break;
            //----------------------------------------
            case R.id.btnInventoryClearFields:
                resetValues();
                setFieldFocus();


                /*
                DB.StockEntity stockE = db.new StockEntity();
               //   stockE.delete("0=0");

                DB.Stock s = db.new Stock();
                s.code = "0001";
                s.name = "كت كات";
                s.barcode = "1001";
                s.factoryNo = "f1000";
                s.orignalNo = "o1000";
                s.unit_1 = "حبة";
                s.unit_2 = "علبة";
                s.unit_3 = "كرتونة";
                stockE.insert(s);

                s.code = "0002";
                s.name = "عصير عنب";
                s.barcode = "2001";
                s.factoryNo = "f1001";
                s.orignalNo = "o1001";
                s.unit_1 = "حبة";
                s.unit_2 = "علبة";
                stockE.insert(s);

                   /* ###################
                s = db.new Stock();
                s.code = "1002";
                s.name = "CCC";
                s.barcode = "3330";
                s.factoryNo = "f1002";
                s.orignalNo = "o1002";
                s.unit_1 = "unit1";
                s.unit_2 = "unit2";
                s.unit_3 = "unit3";
                stockE.insert(s);

                s.code = "1003";
                s.name = "DDD";
                s.barcode = "4440";
                s.factoryNo = "f1003";
                s.orignalNo = "o1003";
                s.unit_1 = "unit1";
                s.unit_2 = "unit2";
                s.unit_3 = "unit3";
                s.unit_4 = "unit4";
                stockE.insert(s);

                s = db.new Stock();
                s.code = "1004";
                s.name = "EEE";
                s.barcode = "5550";
                s.factoryNo = "f1004";
                s.orignalNo = "o1004";
                s.unit_1 = "unit1";
                s.unit_2 = "unit2";
                s.unit_3 = "unit3";
                s.unit_4 = "unit4";
                s.unit_5 = "unit5";
                stockE.insert(s);

                s.code = "1005";
                s.name = "FFF";
                s.barcode = "6660";
                s.factoryNo = "f1005";
                s.orignalNo = "o1005";
                s.unit_1 = "unit1";
                s.unit_2 = "unit2";
                s.unit_3 = "unit3";
                s.unit_4 = "unit4";
                s.unit_5 = "unit5";
                s.unit_6 = "unit6";
                stockE.insert(s);
                 ################################33 */


                    /*
                DB.UnitsEntity unitsE = db.new UnitsEntity();
                DB.Units u = db.new Units();
                u.itemCodeUnits="0001";
                u.itemBarcodeUnits="1001";
                u.numUnits=0;
                u.shamelUnitID=0;
                unitsE.insert(u);

                u.itemCodeUnits="0001";
                u.itemBarcodeUnits="1002";
                u.numUnits=1;
                u.shamelUnitID=0;
                unitsE.insert(u);

                u.itemCodeUnits="0001";
                u.itemBarcodeUnits="1003";
                u.numUnits=2;
                u.shamelUnitID=0;
                unitsE.insert(u);

                u.itemCodeUnits="0001";
                u.itemBarcodeUnits="1004";
                u.numUnits=3;
                u.shamelUnitID=1;
                unitsE.insert(u);

                u.itemCodeUnits="0001";
                u.itemBarcodeUnits="1005";
                u.numUnits=4;
                u.shamelUnitID=1;
                unitsE.insert(u);

                u.itemCodeUnits="0001";
                u.itemBarcodeUnits="1006";
                u.numUnits=5;
                u.shamelUnitID=2;
                unitsE.insert(u);

                u.itemCodeUnits="0002";
                u.itemBarcodeUnits="2001";
                u.numUnits=0;
                u.shamelUnitID=0;
                unitsE.insert(u);

                u.itemCodeUnits="0002";
                u.itemBarcodeUnits="2002";
                u.numUnits=1;
                u.shamelUnitID=0;
                unitsE.insert(u);

                u.itemCodeUnits="0002";
                u.itemBarcodeUnits="2003";
                u.numUnits=2;
                u.shamelUnitID=1;
                unitsE.insert(u);


                Toast.makeText(this, "DONEEEEEEEEEE", Toast.LENGTH_SHORT).show();

              //*/
                 /*
                DB.UnitsEntity unitsE = db.new UnitsEntity();
                DB.Units u = db.new Units();
                u.itemCodeUnits="0001";
                u.itemBarcodeUnits="1007";
                u.numUnits=3;
                u.shamelUnitID=0;
                unitsE.insert(u);

                u.itemCodeUnits="0001";
                u.itemBarcodeUnits="1008";
                u.numUnits=3;
                u.shamelUnitID=1;
                unitsE.insert(u);

                u.itemCodeUnits="0001";
                u.itemBarcodeUnits="1009";
                u.numUnits=3;
                u.shamelUnitID=2;
                unitsE.insert(u);

                u.itemCodeUnits="0002";
                u.itemBarcodeUnits="2004";
                u.numUnits=3;
                u.shamelUnitID=0;
                unitsE.insert(u);

                u.itemCodeUnits="0002";
                u.itemBarcodeUnits="2005";
                u.numUnits=3;
                u.shamelUnitID=1;
                unitsE.insert(u);
                *///----------------------------
               /*
                DB.StockEntity stockE = db.new StockEntity();
               //   stockE.delete("0=0");

                DB.Stock s = db.new Stock();
                s.code = "1000";
                s.name = "Clamoxin";
                s.barcode = "6251586018000";
                s.factoryNo = "f1000";
                s.orignalNo = "o1000";
                s.unit_1 = "unit1";
                s.unit_2 = "unit2";
                stockE.insert(s);

                s.code = "1001";
                s.name = "Miflonide";
                s.barcode = "5010678913064";
                s.factoryNo = "f1001";
                s.orignalNo = "o1001";
                s.unit_1 = "unit1";
                s.unit_2 = "unit2";
                stockE.insert(s);


                s = db.new Stock();
                s.code = "1002";
                s.name = "Amoxitid";
                s.barcode = "6251581120197";
                s.factoryNo = "f1002";
                s.orignalNo = "o1002";
                s.unit_1 = "unit3";
                stockE.insert(s);

                s.code = "1003";
                s.name = "Zaroxolyn";
                s.barcode = "A024488064";
                s.factoryNo = "f1003";
                s.orignalNo = "o1003";
                s.unit_1 = "unit1";
                s.unit_2 = "unit2";
                stockE.insert(s);

                s = db.new Stock();
                s.code = "1004";
                s.name = "Partane";
                s.barcode = "7290000810157";
                s.factoryNo = "f1004";
                s.orignalNo = "o1004";
                s.unit_1 = "unit8";
                stockE.insert(s);

                s.code = "1005";
                s.name = "Movex";
                s.barcode = "7290008068345";
                s.factoryNo = "f1005";
                s.orignalNo = "o1005";
                s.unit_1 = "unitI";
                s.unit_2 = "unitHH";
                stockE.insert(s);
                Toast.makeText(this, "DONEEEEEEEEEE", Toast.LENGTH_SHORT).show();

              //*/





                break;
//--------------------------------------------------------------
            case R.id.btnDeleteStocktakingRecord:
                newDeleteFromStockTakingTransaction();
                break;
//------------------------------------------------------------
            case R.id.btnInventoryDisplayTransactions:
                Intent toInventoryTableActivity = new Intent(stockTaking2.this, InventoryTableActivity.class);
                toInventoryTableActivity.putExtra("stockTakingID",""+stockTakingID);
                toInventoryTableActivity.putExtra("sessionID",""+sessionID);
                startActivityForResult(toInventoryTableActivity, 1);
                break;
//------------------------------------------------------------------
            case R.id.chkBarcodeStocktakingMode:
                barcodeStockTakingChkStatus = !barcodeStockTakingChkStatus;
                Util.setPrefAsString(stockTaking2.this,"isStockTakingBarcodeModeChecked",barcodeStockTakingChkStatus+"");
                resetValues();
                if (barcodeStockTakingChkStatus) {
                    performWhenChkBarcodeStocktakingModeisChecked();

                } else {
                    performWhenChkBarcodeStocktakingModeisUnChecked();
                }
                break;
//--------------------------------------------------------------------
            case R.id.rbAutomaticBarcodeStockTaaking:
                performWhenRbAutomaticBarcodeStockTaakingClicked();
                break;
//-----------------------------------------------------------------------
            case R.id.rbManualBarcodeStockTaaking:
                performWhenRbManualBarcodeStockTaakingClicked();
                break;

        }

    }

    private void performWhenChkBarcodeStocktakingModeisUnChecked() {
        if(updateMode==0) {
            Toast.makeText(this, R.string.msg_manual_stocktaking_mode, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, R.string.msg_stocktaking_update_mode, Toast.LENGTH_SHORT).show();
        }
        String  value =Util.getPrefAsString(this,"isBarcodeNumberChecked","");
        if(!value.equals("")&&Boolean.parseBoolean(value)){
            llBarcodeNumber.setVisibility(View.VISIBLE);
        } else {
            llBarcodeNumber.setVisibility(View.GONE);
        }
        etItemQuantity.setEnabled(true);
        barcodeStockTakingChkStatus=false;
        etItemQuantity.setText("");
        rgBarcodeStockTakingGroup.setVisibility(View.INVISIBLE);
        etBarcodeNumber.setVisibility(View.GONE);
        acBarcodeNumber.setVisibility(View.VISIBLE);
        setAutoCompleteTextviewsHints(0);
    }

    private void performWhenChkBarcodeStocktakingModeisChecked() {


        if(updateMode==0) {
            Toast.makeText(this, R.string.msg_barcode_stocktaking_mode, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, R.string.msg_stocktaking_update_mode, Toast.LENGTH_SHORT).show();
        }
        barcodeStockTakingChkStatus=true;
        if(llBarcodeNumber.getVisibility()==View.GONE){llBarcodeNumber.setVisibility(View.VISIBLE);}
        rgBarcodeStockTakingGroup.setVisibility(View.VISIBLE);
        etBarcodeNumber.setVisibility(View.VISIBLE);
        acBarcodeNumber.setVisibility(View.GONE);
        etBarcodeNumber.requestFocus();
        setAutoCompleteTextviewsHints(1);




    }

    private void performWhenRbManualBarcodeStockTaakingClicked() {
        etItemQuantity.setText("");
        etItemQuantity.setEnabled(true);
        etBarcodeNumber.requestFocus();
        Util.setPrefAsInteger(stockTaking2.this,"barcodeStockTakingMode",2);

    }

    private void performWhenRbAutomaticBarcodeStockTaakingClicked() {
        etItemQuantity.setText("1.0");
        etItemQuantity.setEnabled(false);
        etBarcodeNumber.requestFocus();
        Util.setPrefAsInteger(stockTaking2.this,"barcodeStockTakingMode",1);
    }

    private void deleteFromStockTakingTransaction() {
        try {


            if (checkAndSetBarcodeStockTakingMode() == 0) {
                if (checkIfAllFieldsAreEmpty()) {

                    if (checkIfItemExsists() && isItemChosen == 1) {

                        db.open(true);
                        DB.StockEntity stockE = db.new StockEntity();
                        DB.Stock s = db.new Stock();
                        s = stockE.getItem(Integer.parseInt(spRequiredIDs.getSelectedItem().toString().trim()));

                        DB.StockTakingTransactionsEntity stte = db.new StockTakingTransactionsEntity();

                        int unitID = spInventoryUnit.getSelectedItemPosition();

                        String where = DB.StockTakingTransactionsEntity.KEY_ITEM_ID_TRANSACTIONS+"  = " + s.id + " and "+DB.StockTakingTransactionsEntity.KEY_UNIT_ID_TRANSACTIONS+"  = " + unitID;
                        //String where = " item_id_transactions = " + s.id + " and  unit_id_transactions = " + unitID;

                        if (deleteStockTakingTransaction(stte, where)) {

                            Toast.makeText(this, R.string.msg_stocktaking_done, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(this, "هناك خظأ الرجاء المحاولة مرة أخرى", Toast.LENGTH_SHORT).show();

                        }
                        db.close();
                        resetValues();


                    } else {
                        if (isItemChosen == 0) {
                            Util.showToast(stockTaking2.this, "الرجاء اختيار الصنف من القائمة ", true);

                        } else {
                            Util.showToast(stockTaking2.this, "الرجاءالتأكد من الصنف المدخل ", true);
                        }
                    }
                } else {
                    Util.showToast(stockTaking2.this, "الرجاء البحث في أحد الحقول", true);
                }
            }
            else if(checkAndSetBarcodeStockTakingMode()==1 || checkAndSetBarcodeStockTakingMode()==2){
                if(!etBarcodeNumber.getText().toString().trim().equals("")) {
                    db.open(true);
                    DB.StockEntity stockE = db.new StockEntity();
                    String where = "LTRIM(RTRIM("+DB.StockEntity.KEY_BARCODE+")) = '" + etBarcodeNumber.getText().toString().replace("\n", "").trim()+"'";
                    int id = stockE.checkExisting(where);
                    DB.StockTakingTransactionsEntity stte = db.new StockTakingTransactionsEntity();
                    DB.StockTakingTransactions stt = db.new StockTakingTransactions();

                    if (id != -1) {
                        int unitID = spInventoryUnit.getSelectedItemPosition();
                        where = DB.StockTakingTransactionsEntity.KEY_ITEM_ID_TRANSACTIONS+"  = " + id + " and "+DB.StockTakingTransactionsEntity.KEY_UNIT_ID_TRANSACTIONS+"  = " + unitID;
                        //where = " item_id_transactions = " + id + " and  unit_id_transactions = " + unitID;
                        deleteStockTakingTransaction(stte, where);
                    } else {
                        DB.UndefinedStockTakingTransactionsEntity ustte = db.new UndefinedStockTakingTransactionsEntity();
                        DB.UndefinedStockTakingTransactions ustt = db.new UndefinedStockTakingTransactions();
                        where = DB.UndefinedStockTakingTransactionsEntity.KEY_UNDEFINED_ITEM_BARCODE_TRANSACTIONS+" = '"+etBarcodeNumber.getText().toString().replace("\n", "").trim()+"'";
                        if(ustte.checkExisting(where)!=-1){
                            Toast.makeText(this, "سيتم حذف الصنف غير المعرف", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(this, "هذا الصنف غير مجرود ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


            }
        }catch (Exception e) {
            Util.logException(stockTaking2.this, e);
            // Util.showToast(stockTaking2.this, e.toString(), true);
        }
    }
    private void newDeleteFromStockTakingTransaction() {

        int inventoryMode = checkAndSetBarcodeStockTakingMode();
        if(inventoryMode==0){
            if (!requiredID.trim().equals("")) {

                DB.StockEntity stockE = db.new StockEntity();
                DB.Stock s = db.new Stock();
                db.open(true);
                s = stockE.getItem(Integer.parseInt(requiredID.trim()));

                DB.StockTakingTransactionsEntity stte = db.new StockTakingTransactionsEntity();

                int unitID = spInventoryUnit.getSelectedItemPosition();
                String where = DB.StockTakingTransactionsEntity.KEY_ITEM_ID_TRANSACTIONS+"  = " + s.id + " and "+DB.StockTakingTransactionsEntity.KEY_UNIT_ID_TRANSACTIONS+"  = " + unitID;
                //String where = " item_id_transactions = " + s.id + " and  unit_id_transactions = " + unitID;

                if (deleteStockTakingTransaction(stte, where)) {

                    Toast.makeText(this, R.string.msg_stocktaking_delete_done, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, R.string.msg_item_does_not_exist, Toast.LENGTH_SHORT).show();

                }
                db.close();
                resetValues();
                setFieldFocus();
            } else {

                if (!checkIfAllFieldsAreEmpty()) {
                    Toast.makeText(this, R.string.msg_please_search_using_any_field, Toast.LENGTH_SHORT).show();
                }
                else if (isItemChosen == 0) {
                    Toast.makeText(this, R.string.msg_please_choose_item_from_list, Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if(inventoryMode==1||inventoryMode==2){


            if(!etBarcodeNumber.getText().toString().trim().equals("")) {
                db.open(true);
                DB.StockEntity stockE = db.new StockEntity();
                String where = "LTRIM(RTRIM("+DB.StockEntity.KEY_BARCODE+"))  = '" + etBarcodeNumber.getText().toString().replace("\n", "").trim()+"'";
                //String where = " barcode = '" + etBarcodeNumber.getText().toString().replace("\n", "").trim()+"'";
                int id = stockE.checkExisting(where);
                DB.StockTakingTransactionsEntity stte = db.new StockTakingTransactionsEntity();
                DB.StockTakingTransactions stt = db.new StockTakingTransactions();

                if (id != -1) {
                    int unitID = spInventoryUnit.getSelectedItemPosition();
                    where = DB.StockTakingTransactionsEntity.KEY_ITEM_ID_TRANSACTIONS+"  = " + id + " and  "+DB.StockTakingTransactionsEntity.KEY_UNIT_ID_TRANSACTIONS +" = " + unitID;
                    //where = " item_id_transactions = " + id + " and  unit_id_transactions = " + unitID;
                    deleteStockTakingTransaction(stte, where);
                } else {
                    DB.UndefinedStockTakingTransactionsEntity ustte = db.new UndefinedStockTakingTransactionsEntity();
                    DB.UndefinedStockTakingTransactions ustt = db.new UndefinedStockTakingTransactions();
                    where = DB.UndefinedStockTakingTransactionsEntity.KEY_UNDEFINED_ITEM_BARCODE_TRANSACTIONS+" = '"+etBarcodeNumber.getText().toString().replace("\n", "").trim()+"'";
                    if(ustte.checkExisting(where)!=-1){
                        where = DB.UndefinedStockTakingTransactionsEntity.KEY_UNDEFINED_ITEM_BARCODE_TRANSACTIONS+"  = '" + etBarcodeNumber.getText().toString().replace("\n", "").trim()+"'";
                        //where = " undefined_item_barcode_transactions = '" + etBarcodeNumber.getText().toString().replace("\n", "").trim()+"'";
                        if(deleteUndefinedBarcode(where)){
                            Toast.makeText(this, R.string.msg_stocktaking_delete_done, Toast.LENGTH_SHORT).show();
                            resetValues();
                        }

                    }
                    else{
                        Toast.makeText(this, R.string.msg_item_does_not_exist, Toast.LENGTH_SHORT).show();
                    }
                }
            }


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

    private boolean deleteStockTakingTransaction(DB.StockTakingTransactionsEntity stte, String where) {

        if (stte.deleteStockTakingTransaction(where)) {

            return true;
        } else {
            return false;
        }


    }

    private void saveStockTakingTransaction() {

        try {

            if(checkAndSetBarcodeStockTakingMode()==2){
                String strQuantity = etItemQuantity.getText().toString().trim();
                if(!etBarcodeNumber.getText().toString().trim().equals("")) {
                    if (!strQuantity.equals("") && Double.parseDouble(strQuantity) > 0) {
                        saveStockTakingTransactionForAutomaticBarcodeMode();
                        etItemQuantity.setText("");
                    } else {

                        Util.showToast(stockTaking2.this, " الرجاء إدخال كمية موجبة", true);
                    }
                }
                else{
                    Util.showToast(stockTaking2.this, "الرجاء إدخال قيمة في حقل رقم الباركود باستخدام قارئ الباركود", true);
                }
            }

            else if (checkIfAllFieldsAreEmpty()) {



                if (checkIfItemExsists() && isItemChosen == 1) {


                    String strQuantity = etItemQuantity.getText().toString().trim();
                    if (!strQuantity.equals("") && Double.parseDouble(strQuantity) > 0) {


                        //db.open(true);
                        DB.StockEntity stockE = db.new StockEntity();
                        DB.Stock s = db.new Stock();
                        s = stockE.getItem(Integer.parseInt(spRequiredIDs.getSelectedItem().toString().trim()));

                        DB.StockTakingTransactionsEntity stte = db.new StockTakingTransactionsEntity();

                        int unitID = spInventoryUnit.getSelectedItemPosition();
                        String where = " item_id_transactions = " + s.id + " and  unit_id_transactions = " + unitID;

                        if (updateMode == 0) {
                            if (stte.checkExisting(where) != -1) {
                                double updatedQuantity = stte.get(where).get(0).quantity;
                                updatedQuantity += Double.parseDouble(strQuantity);
                                updateStockTransactions(updatedQuantity, stte, where);
                            } else {
                                insertIntoStockTransactions(s, stte, strQuantity);
                            }
                        } else {
                            double updatedQuantity = Double.parseDouble(strQuantity.trim());
                            updateStockTransactions(updatedQuantity, stte, where);
                        }
                        Toast.makeText(this, "تمت العملية بنجاح", Toast.LENGTH_SHORT).show();
                        /*
                        DB.StockTakingTransactions stt = db.new StockTakingTransactions();
                        stt.itemID = s.id;
                        stt.itemName = s.name.trim();
                        stt.quantity = Integer.parseInt(strQuantity.trim());
                        stt.stockTatkingID = stockTakingID;
                        stt.sessionID = sessionID;
                        if (spInventoryUnit.getAdapter().getCount()>0&&!spInventoryUnit.getSelectedItem().toString().trim().equals("")) {
                            stt.unitID = spInventoryUnit.getSelectedItemPosition();
                            stt.unitName = spInventoryUnit.getSelectedItem().toString().trim();
                        } else {
                            stt.unitID = 0;
                            stt.unitName = s.unit_1.trim();
                        }
                        stte.insert(stt);*/



                            /*
                            List<DB.StockTakingTransactions> ser = stte.get(" id_transactions = " + 1);

                            Toast.makeText(this, ser.get(0).id + "\n" +
                                    ser.get(0).itemID + "\n" +
                                    ser.get(0).itemName + "\n" +
                                    ser.get(0).quantity + "\n" +
                                    ser.get(0).stockTatkingID + "\n" +
                                    ser.get(0).sessionID + "\n" +
                                    ser.get(0).unitID + "\n"+
                                    ser.get(0).unitName + "\n"
                                    , Toast.LENGTH_SHORT).show();
                                    */
                        db.close();


                        resetValues();

                        setFieldFocus();

                    } else {

                        Util.showToast(stockTaking2.this, " الرجاء إدخال كمية موجبة", true);
                    }


                } else {
                    if (isItemChosen == 0) {
                        Util.showToast(stockTaking2.this, "الرجاء اختيار الصنف من القائمة ", true);

                    } else {
                        Util.showToast(stockTaking2.this, "الرجاءالتأكد من الصنف المدخل ", true);
                    }
                }
            } else {
                Util.showToast(stockTaking2.this, "الرجاء البحث في أحد الحقول", true);
            }
        } catch (Exception e) {
            Util.logException(stockTaking2.this, e);
            //Util.showToast(stockTaking2.this, e.toString(), true);
        }

    }

    private void newSaveStockTakingTransaction(){

        String strQuantity = etItemQuantity.getText().toString().trim();

        if(!requiredID.trim().equals("")&& !strQuantity.trim().equals("")){
            double quantity = Double.parseDouble(strQuantity);
            if(quantity>0.0) {
                DB.StockEntity stockE = db.new StockEntity();
                DB.Stock s = db.new Stock();
                s = stockE.getItem(Integer.parseInt(requiredID.toString().trim()));

                DB.StockTakingTransactionsEntity stte = db.new StockTakingTransactionsEntity();
                int unitID = spInventoryUnit.getSelectedItemPosition();
                String where =  DB.StockTakingTransactionsEntity.KEY_ITEM_ID_TRANSACTIONS+"  = " + s.id + " and  "+DB.StockTakingTransactionsEntity.KEY_UNIT_ID_TRANSACTIONS+" = " + unitID;
                //String where = " item_id_transactions = " + s.id + " and  unit_id_transactions = " + unitID;
                if (updateMode == 0) {
                    if (stte.checkExisting(where) != -1) {
                        double updatedQuantity = stte.get(where).get(0).quantity;
                        updatedQuantity += Double.parseDouble(strQuantity);
                        updateStockTransactions(updatedQuantity, stte, where);
                    } else {
                        insertIntoStockTransactions(s, stte, strQuantity);
                    }
                } else {
                    double updatedQuantity = Double.parseDouble(strQuantity.trim());
                    updateStockTransactions(updatedQuantity, stte, where);
                }
                Toast.makeText(this,  R.string.msg_stocktaking_done, Toast.LENGTH_SHORT).show();
                resetValues();
                setFieldFocus();
            }
            else {
                Toast.makeText(this, R.string.msg_quantity_must_be_gt_zero, Toast.LENGTH_SHORT).show();
            }
        }
        else{

            if(!checkIfAllFieldsAreEmpty()){
                Toast.makeText(this, R.string.msg_please_search_using_any_field, Toast.LENGTH_SHORT).show();
            }
            if(isItemChosen==0){
                Toast.makeText(this,R.string.msg_please_choose_item_from_list, Toast.LENGTH_SHORT).show();
            }
            if(strQuantity.trim().equals("")){
                Toast.makeText(this, R.string.msg_please_enter_quantity, Toast.LENGTH_SHORT).show();
            }

        }


    }


    private void setFieldFocus() {

        switch (lastFieldToSearch) {

            case 0:
                acItemNumber.requestFocus();
                break;
            case 1:
                acItemName.requestFocus();
                break;
            case 2:
                if (acBarcodeNumber.getVisibility() == View.VISIBLE)
                    acBarcodeNumber.requestFocus();

                break;
            case 3:
                acFactoryNumber.requestFocus();
                break;
            case 4:
                acOriginalNumber.requestFocus();
                break;
            case 5:

                break;

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
    private void updateUndefinedStockTransactions(double quantityToUpdate, DB.UndefinedStockTakingTransactionsEntity ustte, String where) {
        ustte.update(quantityToUpdate, where);

    }

    private void insertIntoStockTransactions(DB.Stock s, DB.StockTakingTransactionsEntity stte, String strQuantity) {

        DB.StockTakingTransactions stt = db.new StockTakingTransactions();
        stt.itemID = s.id;
        stt.itemName = s.name.trim();
        stt.quantity = Double.parseDouble(strQuantity.trim());
        stt.stockTatkingID = stockTakingID;
        stt.sessionID = sessionID;
        stt.stockTakingDate = Util.getCurrentDate();
        stt.isPosted=0;
        if (spInventoryUnit.getAdapter().getCount() > 0 && !spInventoryUnit.getSelectedItem().toString().trim().equals("")) {
            stt.unitID = spInventoryUnit.getSelectedItemPosition();
            stt.unitName = spInventoryUnit.getSelectedItem().toString().trim();
        } else {
            stt.unitID = 0;
            stt.unitName = s.unit_1.trim();
        }
        stte.insert(stt);


    }
    private void newInsertIntoStockTransactions(DB.Stock s, DB.StockTakingTransactionsEntity stte, String strQuantity) {

        DB.StockTakingTransactions stt = db.new StockTakingTransactions();
        stt.itemID = s.id;
        stt.itemName = s.name.trim();
        stt.quantity = Double.parseDouble(strQuantity.trim());
        stt.stockTatkingID = stockTakingID;
        stt.sessionID = sessionID;
        stt.stockTakingDate = Util.getCurrentDate();
        stt.isPosted=0;
        if (spInventoryUnit.getAdapter().getCount() > 0 && !spInventoryUnit.getSelectedItem().toString().trim().equals("")) {
            stt.unitID = spInventoryUnit.getSelectedItemPosition();
            stt.unitName = spInventoryUnit.getSelectedItem().toString().trim();
        } else {
            stt.unitID = 0;
            stt.unitName = s.unit_1.trim();
        }
        stte.insert(stt);


    }
    private void insertIntoUndefinedStockTransactions(DB.UndefinedStockTakingTransactions ustt) {

        DB.UndefinedStockTakingTransactionsEntity ustte = db.new UndefinedStockTakingTransactionsEntity();
        ustte.insert(ustt);

    }

    private void resetValues() {

        //updateMode = 0;

        mode = 1;
        updateMode = 0;
        colorStatus=-1;
        etBarcodeNumber.setText("");
        acItemNumber.setText("");
        acItemName.setText("");
        acBarcodeNumber.setText("");
        acFactoryNumber.setText("");
        acOriginalNumber.setText("");
        if(checkAndSetBarcodeStockTakingMode()!=1 ){
            etItemQuantity.setText("");

        }


        unLockAutocompletetextviews();
        requiredID="";
        checkAndSetBarcodeStockTakingMode();
        clearSpinner(0);
        clearSpinner(1);
        mode = 0;
        isItemChosen = 0;
        setFieldFocus();
    }

    private void unLockAutocompletetextviews() {
        acItemNumber.setEnabled(true);
        acItemName.setEnabled(true);
        acBarcodeNumber.setEnabled(true);
        acFactoryNumber.setEnabled(true);
        acOriginalNumber.setEnabled(true);
    }

    private boolean checkIfAllFieldsAreEmpty() {


        if (acItemNumber.getText().toString().trim().equals("") &&
                acItemName.getText().toString().trim().equals("") &&
                acBarcodeNumber.getText().toString().trim().equals("") &&
                acFactoryNumber.getText().toString().trim().equals("") &&
                acOriginalNumber.getText().toString().trim().equals("")
                ) {
            return false;
        } else {

            return true;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                try{

                    updateMode =Integer.parseInt(data.getStringExtra("updateMode"));
                    if(updateMode==1) {
                        performWhenChkBarcodeStocktakingModeisUnChecked();
                        chkarcodeStockTaking.setChecked(false);
                        int result = Integer.parseInt(data.getStringExtra("itemID"));
                        requiredID = data.getStringExtra("itemID");
                        int unitID = Integer.parseInt(data.getStringExtra("unitID"));
                        String quantity = data.getStringExtra("quantity");
                        db.open(true);
                        DB.StockEntity stockE = db.new StockEntity();
                        DB.Stock item = stockE.getItem(result);
                        db.close();
                        String code = item.code;
                        mode = 0;
                        colorStatus=-1;
                        updateMode = 1;
                        isItemChosen = 1;
                        acItemNumber.setText(code);
                        if(checkAndSetBarcodeStockTakingMode()==2){
                            etBarcodeNumber.setText(item.barcode+"\n");
                        }
                        mode = 1;
                        db.open(true);
                        DB.Stock requiredItem = getItemDetails();
                        db.close();
                        fillItemDetailsInFields(requiredItem);
                        spInventoryUnit.setSelection(unitID);
                        etItemQuantity.setText(quantity);
                        lockAutocompletetextviews();
                        etItemQuantity.requestFocus();
                        if (checkAndSetBarcodeStockTakingMode() == 1) {
                            etBarcodeNumber.requestFocus();
                        }
                    }
                    if(updateMode==2) {
                        resetValues();
                        updateMode=2;

                        int cs = Integer.parseInt(data.getStringExtra("colorStatus"));

                        //           if(cs==0) {

                        if (checkAndSetBarcodeStockTakingMode() != 2) {
                            chkarcodeStockTaking.setChecked(true);
                            performWhenChkBarcodeStocktakingModeisChecked();
                            rbManualBarcodeStockTaking.setChecked(true);
                            performWhenRbManualBarcodeStockTaakingClicked();
                            checkAndSetBarcodeStockTakingMode();
                        }
                        undefinedBarcode = data.getStringExtra("undefinedItemBarcode");
                        double quantity = Double.parseDouble(data.getStringExtra("undefinedBarcodeQuantity"));
                        colorStatus = Integer.parseInt(data.getStringExtra("colorStatus"));
                        mode = 0;

                        // original until 18/5/2017 the code was   updateMode = 1;
                        updateMode = 1;
                        // updateMode = 2;
                        etBarcodeNumber.setText(undefinedBarcode);
                        etItemQuantity.setText("" + quantity);
                        etItemQuantity.requestFocus();
                        mode = 1;
                    }

                    //     }
                    if(updateMode==3) {
                        resetValues();
                        setFieldFocus();
                    }

                }catch(Exception e){
                    Util.logException(stockTaking2.this, e);
                    //Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show();
                }



            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void lockAutocompletetextviews() {

        acItemNumber.setEnabled(false);
        acItemName.setEnabled(false);
        acBarcodeNumber.setEnabled(false);
        acFactoryNumber.setEnabled(false);
        acOriginalNumber.setEnabled(false);


    }


}
