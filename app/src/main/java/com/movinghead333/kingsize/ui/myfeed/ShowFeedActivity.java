package com.movinghead333.kingsize.ui.myfeed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import com.movinghead333.kingsize.R;

public class ShowFeedActivity extends AppCompatActivity {
    // search parameters for json-array
    private static final String KEY_SUCCESS = "id";
    private static final String KEY_DATA = "data";
    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_TITLE= "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_POSITIVE_VOTES = "positive_votes";
    private static final String KEY_NEGATIVE_VOTES = "negative_votes";
    private String url = "http://pureanarchy.eu:82/data.php";
    private ProgressDialog pDialog;
    //Display progress bar

    private int success;
    private CardAdapter adapter;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_feed);

        new FetchCardDetails().execute();


    }

    public class FetchCardDetails extends AsyncTask<String, String, String> {
        JSONArray response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ShowFeedActivity.this);
            pDialog.setMessage("Loading Data.. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser jsonParser = new HttpJsonParser();

            response = jsonParser.makeHttpRequest(url,"GET",null);

            return null;

        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {

                    ListView listView =(ListView)findViewById(R.id.employeeList);
                    //TODO: check if data was transmitted
                    //if (success == 1) {
                        try {
                            List<CardDetails> employeeList = new ArrayList<>();
                            //Populate the EmployeeDetails list from response
                            for (int i = 0; i < response.length(); i++) {
                                CardDetails carddetails = new CardDetails();
                                jsonObject = (JSONObject) response.get(i);
                                carddetails.setId(jsonObject.getInt(KEY_ID));
                                carddetails.setType(jsonObject.getString(KEY_TYPE));
                                carddetails.setTitle(jsonObject.getString(KEY_TITLE));
                                carddetails.setDescription(jsonObject.getString(KEY_DESCRIPTION));
                                carddetails.setPositive_votes(jsonObject.getString(KEY_POSITIVE_VOTES));
                                carddetails.setNegative_votes(jsonObject.getString(KEY_NEGATIVE_VOTES));
                                employeeList.add(carddetails);
                            }
                            //Create an adapter with the EmployeeDetails List and set it to the LstView
                            adapter = new CardAdapter(employeeList, getApplicationContext());
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        /*
                    } else {
                        Toast.makeText(ShowFeedActivity.this,
                                "Some error occurred while loading data",
                                Toast.LENGTH_LONG).show();

                    }
                    */
                }
            });
        }
    }
}
