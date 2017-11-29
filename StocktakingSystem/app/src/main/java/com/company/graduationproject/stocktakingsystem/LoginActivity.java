package com.company.graduationproject.stocktakingsystem;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    Button btnLogin ,btnSaveURL,btnCloseSettingsDialog;
    private TextInputLayout inputLayoutUsername , inputLayoutPassword;
    private EditText etUsername , etPassword,etURL;
    ImageView ivSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();

        try {
            /*
            DB db = new DB(this);
            db.open(true);
            List<DB.Store> stores = WebChannel.getStores(this);
            DB.StoreEntity storeE = db.new StoreEntity();
            storeE.deleteTemp(0);
            for (int i = 0; i < stores.size(); i++) {
                storeE.insert(stores.get(i));
            }

            List<DB.StockTaking> stockTakings = WebChannel.getStockTakings(this);
            DB.StockTakingEntity stockTakingE = db.new StockTakingEntity();
            stockTakingE.deleteTemp(0);
            for (int i = 0; i < stockTakings.size(); i++) {
                stockTakingE.insert(stockTakings.get(i));
            }
            //Toast.makeText(this, stockTakings.get(0).name, Toast.LENGTH_SHORT).show();

            List<DB.StockTakingSessions> sessions = WebChannel.getSessions(this);
            DB.StockTakingSessionsEntity stockTakingSessionsE = db.new StockTakingSessionsEntity();
            stockTakingSessionsE.deleteTemp(0);
            for (int i = 0; i < sessions.size(); i++) {
                stockTakingSessionsE.insert(sessions.get(i));
            }
            //Toast.makeText(this, sessions.get(0).adminName, Toast.LENGTH_SHORT).show();

            List<DB.Stock> stocks = WebChannel.getStocks(this);
            DB.StockEntity stockE = db.new StockEntity();
            stockE.deleteTemp(0);
            for (int i = 0; i < stocks.size(); i++) {
                stockE.insert(stocks.get(i));
            }
            //Toast.makeText(this, stocks.get(0).name, Toast.LENGTH_SHORT).show();
            List<DB.Units> units = WebChannel.getUnits(this);
            DB.UnitsEntity unitsE = db.new UnitsEntity();
            unitsE.deleteTemp(0);
            for (int i = 0; i < stocks.size(); i++) {
                unitsE.insert(units.get(i));
            }
            //Toast.makeText(this, units.get(0).itemCodeUnits, Toast.LENGTH_SHORT).show();
            */
        }
        catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        /*
        Intent i = new Intent(LoginActivity.this,InventoryActivity.class);
        startActivity(i);*/
    }

    private void initialize() {


       inputLayoutUsername = (TextInputLayout)findViewById(R.id.input_layout_username);
       inputLayoutPassword = (TextInputLayout)findViewById(R.id.input_layout_password);


        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);


        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        ivSettings=(ImageView)findViewById(R.id.ivSettings);
        ivSettings.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnLogin:
                if(checkNetworkConnection()){
                    if(checkUsernameAndPassword()) {
                       // if (checkURL()) {
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        //}
                        //else {
                          //  Toast.makeText(this, "الرجاء إدخال عنوان الويب من الاعدادات", Toast.LENGTH_SHORT).show();
                       // }
                    }
                    else {
                        Toast.makeText(this, "الرجاء التحقق من اسم المستخدم أو كلمة المرور ", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "الرجاء التحقق من الاتصال من الانترنت", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ivSettings:
                showSettingsDialog();
                break;

        }
    }

    private boolean checkURL() {
        boolean isValid =false;
        SharedPreferences prefs = getSharedPreferences("StocktakingSystemSP", MODE_PRIVATE);
        String restoredURL = prefs.getString("url", null);
        if (restoredURL != null&&!restoredURL.isEmpty()) {
            Settings.uri=restoredURL;
            isValid = true;
        }
        return isValid;
    }

    private void showSettingsDialog() {
        final Dialog settingsDialog = new Dialog(LoginActivity.this);
        // Include dialog.xml file
        settingsDialog.setContentView(R.layout.settings_dialog);
        // Set dialog title
        settingsDialog.setTitle(R.string.msg_settings_dialog_title);
        settingsDialog.setCancelable(false);
        etURL = (EditText) settingsDialog.findViewById(R.id.etURL);
        SharedPreferences prefs = getSharedPreferences("StocktakingSystemSP", MODE_PRIVATE);
        String restoredURL = prefs.getString("url", null);
        if (restoredURL != null&&!restoredURL.isEmpty()) {
            etURL.setText(restoredURL);

        }
        else {
        etURL.setText("http://");
        }
        btnSaveURL = settingsDialog.findViewById(R.id.btnSaveURL);
        btnSaveURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=etURL.getText().toString().trim();
                if(!url.isEmpty()){
                    Settings.uri=url.trim();
                    SharedPreferences.Editor editor = getSharedPreferences("StocktakingSystemSP",MODE_PRIVATE).edit();
                    editor.putString("url", url.trim());
                    editor.apply();
                    settingsDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "تمت عملية الحفظ بنجاح", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "الرجاء إدخال عنوان الويب", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCloseSettingsDialog = settingsDialog.findViewById(R.id.btnCloseSettingsDialog);
        btnCloseSettingsDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsDialog.dismiss();
            }
        });
        settingsDialog.show();
    }

    private boolean checkUsernameAndPassword() {
        return true;
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
