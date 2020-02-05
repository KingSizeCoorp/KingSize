package com.movinghead333.kingsize.data.network;

import android.net.Uri;
import android.util.Log;

import com.movinghead333.kingsize.data.database.Card;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;


public class HttpJsonParser {

    private static JSONArray jsonArray;
    private static InputStream inputStream = null;
    private HttpURLConnection urlConnection = null;
    String data="";



    public static String setVote(String key, String method){
        String result = "";

        try {


            URL url = new URL("http://[2a02:810d:8dc0:e2d:bc55:696c:2522:6d0d]/vote.php");

            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("key", "UTF-8") + "=" +URLEncoder.encode(key, "UTF-8")+"&"+
                    URLEncoder.encode("method", "UTF-8") + "=" +URLEncoder.encode(method, "UTF-8");


            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String line = "";

            while ((line = bufferedReader.readLine()) != null){
                result += line;
            }
            bufferedReader.close();
            inputStream.close();

            httpURLConnection.disconnect();



        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }



    public static String setCard(Card card){
        String result = "";

        try {

            String type, title, description;
            type = card.type;
            title = card.title;
            description = card.description;

            URL url = new URL("http://[2a02:810d:8dc0:e2d:bc55:696c:2522:6d0d]/insertcard.php");

            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "iso-8859-1"));
            String post_data = URLEncoder.encode("type", "UTF-8") + "=" +URLEncoder.encode(type, "iso-8859-1")+"&"+
                               URLEncoder.encode("title", "UTF-8") + "=" +URLEncoder.encode(title, "iso-8859-1")+"&"+
                               URLEncoder.encode("description", "UTF-8") + "=" +URLEncoder.encode(description, "iso-8859-1");


            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String line = "";

            while ((line = bufferedReader.readLine()) != null){
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();



        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }




    public JSONArray makeHttpRequest(String url, String method,
                                      Map<String, String> params) {

        try {
            Uri.Builder builder = new Uri.Builder();
            URL urlObj;
            String encodedParams = "";
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.appendQueryParameter(entry.getKey(), entry.getValue());
                }
            }
            if (builder.build().getEncodedQuery() != null) {
                encodedParams = builder.build().getEncodedQuery();

            }

            if ("GET".equals(method)) {
                url = url + "?" + encodedParams;
                urlObj = new URL(url);
                urlConnection = (HttpURLConnection) urlObj.openConnection();
                urlConnection.setRequestMethod(method);


            }
            else {
                urlObj = new URL(url);
                urlConnection = (HttpURLConnection) urlObj.openConnection();
                urlConnection.setRequestMethod(method);
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Content-Length", String.valueOf(encodedParams.getBytes().length));
                urlConnection.getOutputStream().write(encodedParams.getBytes());
            }
            //Connect to the server
            urlConnection.connect();
            //Read the response
            inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            //Parse the response

            while (line != null){
                line = reader.readLine();
                //TODO: optimize with Stringbuilder
                data = data + line;
            }

            jsonArray = new JSONArray(data);

            inputStream.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("Exception", "Error parsing data " + e.toString());
        }

        return jsonArray;
    }

}