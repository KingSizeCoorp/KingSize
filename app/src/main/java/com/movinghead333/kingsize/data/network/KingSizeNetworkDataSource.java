package com.movinghead333.kingsize.data.network;

import android.app.ProgressDialog;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.movinghead333.kingsize.AppExecutors;
import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.database.Card;
import com.movinghead333.kingsize.data.database.FeedEntry;
import com.movinghead333.kingsize.ui.myfeed.CardAdapter;
import com.movinghead333.kingsize.ui.myfeed.ShowFeed2Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class KingSizeNetworkDataSource {


    private static final String LOG_TAG = KingSizeNetworkDataSource.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static KingSizeNetworkDataSource sInstance;
    private final Context mContext;

    private final AppExecutors mExecutors;


    private KingSizeNetworkDataSource(Context context, AppExecutors executors){
        mContext = context;
        mExecutors = executors;
        // instantiate live data objects
    }

    public static KingSizeNetworkDataSource getsInstance(Context context, AppExecutors executors){
        Log.d(LOG_TAG, "Getting the network data soruce");
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new KingSizeNetworkDataSource(context, executors);
            }
        }
        return sInstance;
    }

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_TYPE = "type";
    private static final String KEY_TITLE= "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_POSITIVE_VOTES = "positive_votes";
    private static final String KEY_NEGATIVE_VOTES = "negative_votes";
    private static final String KEY_ID = "id";
    private String url = "http://pureanarchy.eu:82/data.php";
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private ProgressDialog pDialog;


    private int success;
    private CardAdapter adapter;
    private JSONObject jsonObject;



    public MutableLiveData<List<FeedEntry>> downloadFeed(){
        try {
            return new FetchCardDetails().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "null returned by downloadFeed");
        return null;
    }

    private class FetchCardDetails extends AsyncTask<String, String, MutableLiveData<List<FeedEntry>>> {
        JSONArray response;
        MutableLiveData<List<FeedEntry>> downloadedData = new MutableLiveData<>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Daten werden geladen...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            */
        }



        @Override
        protected MutableLiveData<List<FeedEntry>> doInBackground(String... params) {
            connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

            if(connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null) {
                    HttpJsonParser jsonParser = new HttpJsonParser();
                    try {

                        response = jsonParser.makeHttpRequest(url, "GET", null);
                        jsonObject = (JSONObject) response.get(0);
                        success = jsonObject.getInt(KEY_SUCCESS);

                        // insert values into MutableLiveData
                        List<FeedEntry> tempList = new ArrayList<>();
                        for (int i = 1; i < response.length(); i++) {
                            jsonObject = (JSONObject) response.get(i);

                            FeedEntry tempEntry = new FeedEntry(
                                    jsonObject.getInt(KEY_ID),
                                    jsonObject.getString(KEY_TITLE),
                                    jsonObject.getString(KEY_TYPE),
                                    jsonObject.getString(KEY_DESCRIPTION),
                                    jsonObject.getInt(KEY_POSITIVE_VOTES),
                                    jsonObject.getInt(KEY_NEGATIVE_VOTES)
                            );
                            tempList.add(tempEntry);
                            downloadedData.postValue(tempList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    success = 0;
                }
            }
            return downloadedData;
        }

        @Override
        protected void onPostExecute(MutableLiveData<List<FeedEntry>> result) {
            //pDialog.dismiss();
        }
    }
}
