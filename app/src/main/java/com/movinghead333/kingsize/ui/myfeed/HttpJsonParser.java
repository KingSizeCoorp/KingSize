package com.movinghead333.kingsize.ui.myfeed;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class HttpJsonParser {

    private static JSONArray JA  ;
    private static InputStream is = null;
    private static JSONObject jObj = null;
    private static JSONObject JArray = null;
    private static String json = "";
    private HttpURLConnection urlConnection = null;
    String data="";
    String dataParsed = "";
    String singleParsed = "";





    /**
     * This method helps in retrieving data from HTTP server using HttpURLConnection.
     *
     * @param url    The HTTP URL where JSON data is exposed
     * @param method HTTP method: GET or POST
     * @param params Query parameters for the request
     * @return This method returns the JSON object fetched from the server
     */
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
                //url = url + "?" + encodedParams;
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
            is = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = "";
            String sooos = "";

            //Parse the response

            while (line != null){
                line = reader.readLine();
                data = data + line;
            }

            JA = new JSONArray(data);

            is.close();

            //Convert the response to JSON Object
            //jObj = new JSONObject(sooos);





        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("Exception", "Error parsing data " + e.toString());
        }


        /*
        try {

        //{"id":"1","data":[{"negative_votes":"0","positive_votes":"0","title":"Spiegel","type":"tokens","description":"Looooooooolllllllllllllll"}]}
        maaaan = new JSONObject(" {\"id\":\"1\",\"data\":[{\"negative_votes\":\"0\",\"positive_votes\":\"0\",\"title\":\"Spiegel\",\"type\":\"tokens\",\"description\":\"Looooooooolllllllllllllll\"}]}");
        } catch (JSONException e) {
        e.printStackTrace();
        }
        return JSON Object
        */


        return JA;



    }
}