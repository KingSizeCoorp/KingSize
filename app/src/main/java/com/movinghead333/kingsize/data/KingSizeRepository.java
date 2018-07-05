package com.movinghead333.kingsize.data;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.movinghead333.kingsize.AppExecutors;
import com.movinghead333.kingsize.data.database.Card;
import com.movinghead333.kingsize.data.database.CardDao;
import com.movinghead333.kingsize.data.database.CardDeck;
import com.movinghead333.kingsize.data.database.CardDeckDao;
import com.movinghead333.kingsize.data.database.CardInCardDeckRelation;
import com.movinghead333.kingsize.data.database.CardInCardDeckRelationDao;
import com.movinghead333.kingsize.data.database.FeedEntry;
import com.movinghead333.kingsize.data.database.FeedEntryDao;
import com.movinghead333.kingsize.data.datawrappers.CardWithSymbol;
import com.movinghead333.kingsize.data.network.HttpJsonParser;
import com.movinghead333.kingsize.data.network.KingSizeNetworkDataSource;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class KingSizeRepository {

    private static final String LOG_TAG = KingSizeRepository.class.getSimpleName();

    // for Singleton instantiation
    private static final Object LOCK = new Object();
    private static KingSizeRepository sInstance;
    private final CardDao mCardDao;
    private final CardDeckDao mCardDeckDao;
    private final CardInCardDeckRelationDao mCardInCardDeckRelationDao;
    private final FeedEntryDao mFeedEntryDao;
    private final KingSizeNetworkDataSource mKingSizeNetworkDataSource;
    private final AppExecutors mExecutors;
    private boolean mInitialized;


    private ProgressDialog pDialog;

    // access variables
    private static long insertionId;


    private KingSizeRepository(CardDao cardDao, CardDeckDao cardDeckDao, CardInCardDeckRelationDao
            cardInCardDeckRelationDao, FeedEntryDao feedEntryDao,
                               KingSizeNetworkDataSource kingSizeNetworkDataSource,
                               AppExecutors executors){
        mCardDao = cardDao;
        mCardDeckDao = cardDeckDao;
        mCardInCardDeckRelationDao = cardInCardDeckRelationDao;
        mFeedEntryDao = feedEntryDao;
        mKingSizeNetworkDataSource = kingSizeNetworkDataSource;
        mExecutors = executors;

        // track live data changes from network
    }

    public synchronized static KingSizeRepository getsInstance(
            CardDao cardDao, CardDeckDao cardDeckDao, CardInCardDeckRelationDao
            cardInCardDeckRelationDao, FeedEntryDao feedEntryDao,
            KingSizeNetworkDataSource kingSizeNetworkDataSource,
            AppExecutors executors){
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = new KingSizeRepository(cardDao, cardDeckDao, cardInCardDeckRelationDao,
                        feedEntryDao, kingSizeNetworkDataSource, executors);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }



    /*
        Network interaction
     */
    public void updateDatabase(){
        mKingSizeNetworkDataSource.downloadFeed().observeForever(new Observer<List<FeedEntry>>() {
            @Override
            public void onChanged(@Nullable final List<FeedEntry> feedEntries) {
                mExecutors.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mFeedEntryDao.clearEntries();
                        mFeedEntryDao.insertFeedEntries(feedEntries);
                    }
                });
            }
        });
    }

    public void upVote(final String key){
        mExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                HttpJsonParser.setVote(key, "up");
            }
        });
    }

    public void downVote(final String key){
        mExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                HttpJsonParser.setVote(key, "down");
            }
        });
    }


    /*
        Dao-methods
     */

    /*
        FeedEntry interaction
     */
    public LiveData<List<FeedEntry>> getFeedEntries(){
        return mFeedEntryDao.getAllFeedEntries();
    }

    public void voteUpLocally(final int id){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mFeedEntryDao.voteUpLocally(id);
            }
        });
    }

    public void voteDownLocally(final int id){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mFeedEntryDao.voteDownLocally(id);
            }
        });
    }


    /*
        CardDao interaction
    */
    public MutableLiveData<String> uploadCard(Card card){
        try {
            return new uploadCardAsyncTaskDao(mCardDao).execute(card).get();
        } catch (InterruptedException e) {
            Log.d(LOG_TAG, "uploadCard throws InterruptedException");
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.d(LOG_TAG, "uploadCard throws ExecutionException");
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "uploadCard returning null caused by exception");
        return null;
    }



    private static class uploadCardAsyncTaskDao extends AsyncTask<Card, Void, MutableLiveData<String>> {

        private CardDao mAsyncTaskDao;

        uploadCardAsyncTaskDao(CardDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected MutableLiveData<String> doInBackground(final Card... params){
            String result = HttpJsonParser.setCard(params[0]);
            MutableLiveData<String> returnResult = new MutableLiveData<>();
            returnResult.postValue(result);
            return returnResult;
        }
    }

    /**
     * get card list of cards by source attribute
     * @param source
     * @return
     */
    public LiveData<List<Card>> getCardsBySource(String source){
        try {
            return new getCardsBySourceAsyncTaskDao(mCardDao).execute(source).get();
        } catch (InterruptedException e) {
            Log.d(LOG_TAG, "getCardsBySourceAsyncTaskDao throws InterruptedException");
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.d(LOG_TAG, "getCardsBySourceAsyncTaskDao throws ExecutionException");
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "getCardsBySourceAsyncTaskDao returning null caused by exception");
        return null;
    }



    private static class getCardsBySourceAsyncTaskDao extends AsyncTask<String, Void, LiveData<List<Card>>> {

        private CardDao mAsyncTaskDao;

        getCardsBySourceAsyncTaskDao(CardDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<Card>> doInBackground(final String... params){
            return mAsyncTaskDao.getCardsBySource(params[0]);

        }
    }

    public  LiveData<Card> getCardById(long id){
        return  mCardDao.getCardById(id);
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



    /*
        CardDeck interaction
     */
    public LiveData<Card> getCardFromDeckBySymbolAndDeckId(long deckId, int symbol){
        return mCardDao.getCardFromDeckBySymbolAndDeckId(deckId, symbol);
    }

    public LiveData<List<CardDeck>> getAllDecks(){
        return mCardDeckDao.getAllCardDecks();
    }

    public LiveData<List<CardWithSymbol>> getCardsWithSymbolByCardDeckId(long deckId){
        try {
            return (new getCardsWithSymbolAsyncTaskDao(mCardDao).execute(deckId)).get();
        } catch (InterruptedException e) {
            Log.d(LOG_TAG, "getCardsWithSymbol throws InterruptedException");
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.d(LOG_TAG, "getCardsWithSymbol throws ExecutionException");
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "getCardsWithSymbol returning null caused by exception");
        return null;
    }



    private static class getCardsWithSymbolAsyncTaskDao extends AsyncTask<Long, Void, LiveData<List<CardWithSymbol>>> {

        private CardDao mAsyncTaskDao;

        getCardsWithSymbolAsyncTaskDao(CardDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<CardWithSymbol>> doInBackground(final Long... params){
            return mAsyncTaskDao.getCardsWithSymbolInCardDeckById(params[0]);

        }
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
        new insertAsyncTaskDao(mCardDao, mCardDeckDao, mCardInCardDeckRelationDao,
                standardCards)
                .execute(cardDeck);
    }

    private static class insertAsyncTaskDao extends AsyncTask<CardDeck, Void, Void> {

        private CardDao mAsyncCardDao;
        private CardDeckDao mAsyncTaskDao;
        private CardInCardDeckRelationDao mRelationDao;
        private static long insertionId;
        private static String[] STANDARD_CARDS;

        insertAsyncTaskDao(CardDao cardDao, CardDeckDao cardDeckDao,
                           CardInCardDeckRelationDao relationDao, String[] standardCards){
            mAsyncTaskDao = cardDeckDao;
            mRelationDao = relationDao;
            mAsyncCardDao = cardDao;
            STANDARD_CARDS = standardCards;
            //mRelation = relation;
        }

        @Override
        protected Void doInBackground(final CardDeck... params){
            // insert new deck object into database
            insertionId = mAsyncTaskDao.insertCardDeck(params[0]);

            // create relations-array
            CardInCardDeckRelation[] relations = new CardInCardDeckRelation[STANDARD_CARDS.length];

            // insert standard-card objects into array
            for(int i = 0; i < STANDARD_CARDS.length; i++){
                relations[i] = new CardInCardDeckRelation(insertionId,
                        mAsyncCardDao.getStandardCardByName(STANDARD_CARDS[i]),
                        i);
            }

            // insert created array into database
            mRelationDao.insertMultiple(relations);
            Log.d(LOG_TAG, "Deck inserted");
            return null;
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
