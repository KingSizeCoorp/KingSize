package com.movinghead333.kingsize.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.movinghead333.kingsize.AppExecutors;
import com.movinghead333.kingsize.data.database.Card;
import com.movinghead333.kingsize.data.database.CardDao;
import com.movinghead333.kingsize.data.network.KingSizeNetworkDataSource;

import java.util.List;

public class KingSizeRepository {

    private static final String LOG_TAG = KingSizeRepository.class.getSimpleName();

    // for Singleton instantiation
    private static final Object LOCK = new Object();
    private static KingSizeRepository sInstance;
    private final CardDao mCardDao;
    private final KingSizeNetworkDataSource mKingSizeNetworkDataSource;
    private final AppExecutors mExecutors;
    private boolean mInitialized;


    private KingSizeRepository(CardDao cardDao, KingSizeNetworkDataSource kingSizeNetworkDataSource,
                               AppExecutors executors){
        mCardDao = cardDao;
        mKingSizeNetworkDataSource = kingSizeNetworkDataSource;
        mExecutors = executors;

        // track live data changes from network
    }

    public synchronized static KingSizeRepository getsInstance(
            CardDao cardDao, KingSizeNetworkDataSource kingSizeNetworkDataSource,
            AppExecutors executors){
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = new KingSizeRepository(cardDao, kingSizeNetworkDataSource, executors);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    public LiveData<List<Card>> getAllCards(){
        return mCardDao.getAllCards();
    }

    public void deleteCardById(final long id){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mCardDao.deleteCardById(id);
            }
        });
    }

    public void insertCard(final Card card){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mCardDao.insertCard(card);
            }
        });
    }

    public void updateCard(final Card card){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mCardDao.updateCard(card);
            }
        });
    }

    public void clearCards(){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mCardDao.clearCards();
            }
        });
    }
}
