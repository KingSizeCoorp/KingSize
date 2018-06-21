package com.movinghead333.kingsize.data.network;

import android.content.Context;
import android.util.Log;

import com.movinghead333.kingsize.AppExecutors;

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
}
