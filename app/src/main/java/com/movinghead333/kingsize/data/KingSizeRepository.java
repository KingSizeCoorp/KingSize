package com.movinghead333.kingsize.data;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.movinghead333.kingsize.AppExecutors;
import com.movinghead333.kingsize.ArrayResource;
import com.movinghead333.kingsize.data.database.Card;
import com.movinghead333.kingsize.data.database.CardDao;
import com.movinghead333.kingsize.data.database.CardDeck;
import com.movinghead333.kingsize.data.database.CardDeckDao;
import com.movinghead333.kingsize.data.database.CardInCardDeckRelation;
import com.movinghead333.kingsize.data.database.CardInCardDeckRelationDao;
import com.movinghead333.kingsize.data.network.KingSizeNetworkDataSource;

import java.util.List;

public class KingSizeRepository {

    private static final String LOG_TAG = KingSizeRepository.class.getSimpleName();

    // for Singleton instantiation
    private static final Object LOCK = new Object();
    private static KingSizeRepository sInstance;
    private final CardDao mCardDao;
    private final CardDeckDao mCardDeckDao;
    private final CardInCardDeckRelationDao mCardInCardDeckRelationDao;
    private final KingSizeNetworkDataSource mKingSizeNetworkDataSource;
    private final AppExecutors mExecutors;
    private boolean mInitialized;

    // access variables
    private static long insertionId;
    private static long standardCardId;


    private KingSizeRepository(CardDao cardDao, CardDeckDao cardDeckDao, CardInCardDeckRelationDao
            cardInCardDeckRelationDao, KingSizeNetworkDataSource kingSizeNetworkDataSource,
                               AppExecutors executors){
        mCardDao = cardDao;
        mCardDeckDao = cardDeckDao;
        mCardInCardDeckRelationDao = cardInCardDeckRelationDao;
        mKingSizeNetworkDataSource = kingSizeNetworkDataSource;
        mExecutors = executors;

        // track live data changes from network
    }

    public synchronized static KingSizeRepository getsInstance(
            CardDao cardDao, CardDeckDao cardDeckDao, CardInCardDeckRelationDao
            cardInCardDeckRelationDao, KingSizeNetworkDataSource kingSizeNetworkDataSource,
            AppExecutors executors){
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = new KingSizeRepository(cardDao, cardDeckDao, cardInCardDeckRelationDao,
                        kingSizeNetworkDataSource, executors);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }


    /*
        CardDao interaction
    */
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

    public long getStandardCardByName(){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //standardCardId = mCardDao.getStandardCardByName();
            }
        });
        return standardCardId;
    }


    /*
        CardDeck interaction
     */
    public LiveData<List<CardDeck>> getAllDecks(){
        return mCardDeckDao.getAllCardDecks();
    }

    public void clearCardDecks(){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mCardDeckDao.clearCardDecks();
            }
        });
    }

    public long insertCardDeck(final CardDeck cardDeck){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                insertionId = mCardDeckDao.insertCardDeck(cardDeck);
            }
        });
        return insertionId;
    }

    public void updateCardDeck(final CardDeck cardDeck){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mCardDeckDao.updateCardDeck(cardDeck);
            }
        });
    }

    public void deleteCardDeckById(final long id){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mCardDeckDao.deleteCardDeck(id);
            }
        });
    }

    public void insertFullDeck(CardDeck cardDeck, String[] standardCards){
        new insertAsyncTaskDao(mCardDao, mCardDeckDao, mExecutors, mCardInCardDeckRelationDao,
                standardCards)
                .execute(cardDeck);
    }

    private static class insertAsyncTaskDao extends AsyncTask<CardDeck, Void, Void> {

        private CardDao mAsyncCardDao;
        private CardDeckDao mAsyncTaskDao;
        private CardInCardDeckRelationDao mRelationDao;
        private static long insertionId;
        private AppExecutors executors;
        private static String[] STANDARD_CARDS;

        insertAsyncTaskDao(CardDao cardDao, CardDeckDao cardDeckDao, AppExecutors executors,
                           CardInCardDeckRelationDao relationDao, String[] standardCards){
            mAsyncTaskDao = cardDeckDao;
            mRelationDao = relationDao;
            mAsyncCardDao = cardDao;
            this.executors = executors;
            STANDARD_CARDS = standardCards;
            //mRelation = relation;
        }

        @Override
        protected Void doInBackground(final CardDeck... params){
            insertionId = mAsyncTaskDao.insertCardDeck(params[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            //insert relation-objects after new CardDeck has been inserted (async. on diskIO-thread)
            executors.diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0; i < STANDARD_CARDS.length; i++){
                        CardInCardDeckRelation currentRel = new CardInCardDeckRelation(insertionId,
                                mAsyncCardDao.getStandardCardByName(STANDARD_CARDS[i]),
                                0);
                        mRelationDao.insertSingleRelation(currentRel);
                        Log.d(LOG_TAG, "Relation inserted");
                    }
                }
            });
        }
    }


    /*
        CardInCardDeckRelation interaction
     */
    public LiveData<List<CardInCardDeckRelation>> getCardsInDeck(final long id){
        return mCardInCardDeckRelationDao.getCardsInCardDeck(id);
    }

    public void clearCardsInCardDeckRelations(){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mCardInCardDeckRelationDao.clearCardsInCardDeckRelations();
            }
        });
    }

    public void insertCardToCardDeckRelation(final CardInCardDeckRelation... cardInCardDeckRelations){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mCardInCardDeckRelationDao.insertMultiple(cardInCardDeckRelations);
            }
        });
    }

    public void updateCardInCardDeckRelation(final CardInCardDeckRelation cardInCardDeckRelation){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mCardInCardDeckRelationDao.updateRelation(cardInCardDeckRelation);
            }
        });
    }

    public void deleteCardsInCardDeckRelations(final CardInCardDeckRelation... cardInCardDeckRelations){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mCardInCardDeckRelationDao.deleteCardInCardDeckRelations(cardInCardDeckRelations);
            }
        });
    }


}
