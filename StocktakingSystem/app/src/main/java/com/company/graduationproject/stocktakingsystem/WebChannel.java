package com.company.graduationproject.stocktakingsystem;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nizam on 9/28/2017.
 */

public class WebChannel {

    private HttpConnection httpConnection ;
    private static final String TAG = "WebChannel";
    private static  MainActivity mainActivity ;

    public static String getURI() {

        String urlStr = Settings.uri;
        if (Settings.USE_INTERNAL_URL)
            urlStr = Settings.uri_internal;
        return urlStr;
    }



    public static List<DB.StockTaking> getStockTakings(Context c) {

        try {
           // mainActivity=(MainActivity)c;
            //mainActivity.startLoadingPanel(0);

            String responseString = "There is an exception";
            String urlStr = getURI();
            urlStr += "?type=GetStockTakings";
            ServerConnection serverConnection = new ServerConnection(c,0);
            //responseString =serverConnection.execute("GET",urlStr).get();
            /*responseString =*/serverConnection.execute("GET",urlStr);
            List<DB.StockTaking> stockTakings = new ArrayList<DB.StockTaking>();
            if (!responseString.contentEquals("There is an exception")) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<DB.StockTaking>>(){}.getType();
                stockTakings = gson.fromJson(responseString,type);

                //mainActivity.setStockTakingsFlag(true, stockTakings);


            } else {
                Util.logInformation( c ,"WebChannel.getUnits response from server is invalid=" + responseString);
                //mainActivity.setStockTakingsFlag(false, stockTakings);
            }
            return stockTakings;
        } catch (Exception e) {
            Util.logInformation( c ,"get units has exception");
            Util.logException( c , e);
            //mainActivity.setStockTakingsFlag(false, null);
            return null;
        }
    }
    public static List<DB.StockTakingSessions> getSessions(Context c) {

        try {
            //mainActivity=(MainActivity)c;
            //mainActivity.startLoadingPanel(1);

            String responseString = "";
            String urlStr = getURI();
            urlStr += "?type=GetSessions";
            ServerConnection serverConnection = new ServerConnection(c,1);
            responseString =serverConnection.execute("GET",urlStr).get();

            List<DB.StockTakingSessions> sessions = new ArrayList<DB.StockTakingSessions>();
            if (!responseString.contentEquals("There is an exception")) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<DB.StockTakingSessions>>(){}.getType();
                sessions = gson.fromJson(responseString,type);
                //mainActivity.setSessionsFlag(true, sessions);

            } else {
                Util.logInformation( c ,"WebChannel.getUnits response from server is invalid=" + responseString);
                //mainActivity.setSessionsFlag(false, sessions);
            }
            return sessions;
        } catch (Exception e) {
            Util.logInformation( c ,"get units has exception");
            Util.logException( c , e);
            //mainActivity.setSessionsFlag(false, null);
            return null;
        }
    }
    public static List<DB.Stock> getStocks(Context c) {

        try {
            //mainActivity=(MainActivity)c;
            //mainActivity.startLoadingPanel(2);
            String responseString = "";
            String urlStr = getURI();
            urlStr += "?type=GetStocks";
            ServerConnection serverConnection = new ServerConnection(c,2);
            responseString =serverConnection.execute("GET",urlStr).get();

            List<DB.Stock> stocks = new ArrayList<DB.Stock>();
            if (!responseString.contentEquals("There is an exception")) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<DB.Stock>>(){}.getType();
                stocks = gson.fromJson(responseString,type);
                //mainActivity.setStocksFlag(true, stocks);

            } else {
                Util.logInformation( c ,"WebChannel.getUnits response from server is invalid=" + responseString);
                //mainActivity.setStocksFlag(false, stocks);
            }
            return stocks;
        } catch (Exception e) {
            Util.logInformation( c ,"get units has exception");
            Util.logException( c , e);
            //mainActivity.setStocksFlag(false, null);
            return null;
        }
    }
    public static List<DB.Store> getStores(Context c) {
        String responseString = "";
        try {
            //mainActivity=(MainActivity)c;
            //mainActivity.startLoadingPanel(3);
            String urlStr = getURI();
            urlStr += "?type=GetStores";

            ServerConnection serverConnection = new ServerConnection(c,3);
            responseString =serverConnection.execute("GET",urlStr).get();

            List<DB.Store> stores = new ArrayList<DB.Store>();
            if (!responseString.contentEquals("There is an exception")) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<DB.Store>>(){}.getType();
                stores = gson.fromJson(responseString,type);

                //mainActivity.setStoresFlag(true, stores);



            } else {
                Util.logInformation( c ,"WebChannel.getUnits response from server is invalid=" + responseString);
                //mainActivity.setStoresFlag(false, stores);
            }
            return stores;

        } catch (Exception e) {
            Util.logException( c , e);
            //mainActivity.setStoresFlag(false, null);
            return null;
        }

    }
    public static List<DB.Units> getUnits(Context c) {

        try {
            //mainActivity=(MainActivity)c;
            //mainActivity.startLoadingPanel(4);
            String responseString = "";
            String urlStr = getURI();
            urlStr += "?type=GetUnits";
            ServerConnection serverConnection = new ServerConnection(c,4);
            responseString =serverConnection.execute("GET",urlStr).get();

            List<DB.Units> units = new ArrayList<DB.Units>();
            if (!responseString.contentEquals("There is an exception")) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<DB.Units>>(){}.getType();
                units = gson.fromJson(responseString,type);
                mainActivity.setUnitsFlag(true, units);

            } else {
                Util.logInformation( c ,"WebChannel.getUnits response from server is invalid=" + responseString);
                //mainActivity.setUnitsFlag(false, units);
            }
            return units;
        } catch (Exception e) {
            Util.logInformation( c ,"get units has exception");
            Util.logException( c , e);
            //mainActivity.setUnitsFlag(false, null);
            return null;
        }
    }

    public static void getStockTakings2(Context c) {

        try {
            mainActivity=(MainActivity)c;

            String urlStr = getURI();
            urlStr += "?type=GetStockTakings";

            ServerConnection serverConnection = new ServerConnection(c,0);
            serverConnection.execute("GET",urlStr);

        } catch (Exception e) {
            Util.logInformation( c ,"get units has exception");
            Util.logException( c , e);
            mainActivity.setStockTakingsFlag(false, null);
        }
    }
    public static void getSessions2(Context c) {

        try {
            mainActivity=(MainActivity)c;

            String urlStr = getURI();
            urlStr += "?type=GetSessions";

            ServerConnection serverConnection = new ServerConnection(c,1);
            serverConnection.execute("GET",urlStr);
        } catch (Exception e) {
            Util.logInformation( c ,"get units has exception");
            Util.logException( c , e);
            mainActivity.setSessionsFlag(false, null);
        }
    }
    public static void getStocks2(Context c) {

        try {
            mainActivity=(MainActivity)c;

            String urlStr = getURI();
            urlStr += "?type=GetStocks";

            ServerConnection serverConnection = new ServerConnection(c,2);
            serverConnection.execute("GET",urlStr);
        } catch (Exception e) {
            Util.logInformation( c ,"get units has exception");
            Util.logException( c , e);
            mainActivity.setStocksFlag(false, null);
        }
    }
    public static void getStores2(Context c) {
        String responseString = "";
        try {
            mainActivity=(MainActivity)c;

            String urlStr = getURI();
            urlStr += "?type=GetStores";

            ServerConnection serverConnection = new ServerConnection(c,3);
            serverConnection.execute("GET",urlStr);

        } catch (Exception e) {
            Util.logException( c , e);
            mainActivity.setStoresFlag(false, null);

        }

    }
    public static void getUnits2(Context c) {

        try {
            mainActivity=(MainActivity)c;

            String urlStr = getURI();
            urlStr += "?type=GetUnits";

            ServerConnection serverConnection = new ServerConnection(c,4);
            serverConnection.execute("GET",urlStr);

        } catch (Exception e) {
            Util.logInformation( c ,"get units has exception");
            Util.logException( c , e);
            mainActivity.setUnitsFlag(false, null);

        }
    }







}
