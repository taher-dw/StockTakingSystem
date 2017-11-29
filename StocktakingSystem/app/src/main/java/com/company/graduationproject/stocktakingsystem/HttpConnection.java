package com.company.graduationproject.stocktakingsystem;

/**
 * Created by Nizam on 9/28/2017.
 */

import com.google.gson.JsonObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpConnection {

    private static HttpURLConnection urlConnection;
    private static URL url;
   // private static BaseResponse baseResponse ;
    private static String jsonServerResponse;


    public static String executeUsingGET(String strURL){


        try {
            url = new URL(strURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setAllowUserInteraction(true);
            urlConnection.setDoInput(true);

            //urlConnection.setDoOutput(true);
            jsonServerResponse= getResponseFromServerAsJSONString();
            urlConnection.disconnect();

            return jsonServerResponse;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return  null;

    }

    public static String executeUsingPOST(String strURL,String requestToSend){


        try {
            url = new URL(strURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setAllowUserInteraction(true);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            writeStream(requestToSend);
            jsonServerResponse= getResponseFromServerAsJSONString();
           // parseToBaseResponse(jsonServerResponse);
            urlConnection.disconnect();
            return jsonServerResponse;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return  null;

    }


    private static String getResponseFromServerAsJSONString() {

        int responseCode = 0;
        String serverResponse ="";
        try {
            responseCode = urlConnection.getResponseCode();
            // if(responseCode == HttpURLConnection.HTTP_OK){

            String s = urlConnection.getRequestMethod();
            InputStream in = urlConnection.getInputStream();
            serverResponse = readStream(in);
            return  serverResponse;
            //editor.putString("ResponseFromLogin", server_response);
            //editor.commit();
            //  GsonBuilder gsonBuilder = new GsonBuilder();
            // Gson gson = gsonBuilder.create();
            // UserInfo login = gson.fromJson(server_response, UserInfo.class);
            // }

            // else if(responseCode!=600){
            // editor.putString("ResponseFromLogin", responseCode+"#"+server_response);
            // editor.commit();
            // }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return  serverResponse ;
    }

    private static void parseToBaseResponse(String jsonServerResponse) {
        try {

            /*
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            baseResponse = gson.fromJson(jsonServerResponse, BaseResponse.class);
            */

            /*
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            baseResponse = new BaseResponse();
            //baseResponse.setStatus("success");
            String three[] = jsonServerResponse.split(",");
            String statusCode = three[1].split(":")[1];
            baseResponse.setStatus_code(statusCode);
            baseResponse.setData(gson.fromJson(jsonServerResponse, JsonObject.class));*/

        }catch (Exception ex){
            String str = ex.toString();
        }

    }

    private static String readStream(InputStream in) {

        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    return response.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;

    }

    private static void writeStream(String data) {
        // OutputStream os = null;
        // BufferedWriter writer = null;
        OutputStreamWriter out = null;
        try {

            OutputStream outputstream2 = new BufferedOutputStream(urlConnection.getOutputStream());
            out = new OutputStreamWriter(outputstream2);
            out.write(data);
            out.flush();

            /*
            os = urlConnection.getOutputStream();
            writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);
            writer.close();
            os.close();*/

        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
