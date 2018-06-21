package com.movinghead333.kingsize.ui.mydecks.showdecks;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.movinghead333.kingsize.data.database.CardDeck;
import com.movinghead333.kingsize.data.database.CardDeckDao;
import com.movinghead333.kingsize.data.database.KingSizeLocalDatabase;

import java.util.List;

public class LocalCardDeckRepository {

    private CardDeckDao cardDeckDao;
    private LiveData<List<CardDeck>> allCardDecks;

    LocalCardDeckRepository(Application application){
        KingSizeLocalDatabase db = KingSizeLocalDatabase.getDatabase(application);
        cardDeckDao = db.cardDeckDao();
        allCardDecks = cardDeckDao.getAllCardDecks();
    }

    public LiveData<List<CardDeck>> getAllCardDecks() {
        return allCardDecks;
    }

    public void deleteCarddDeckById(long id){
        new LocalCardDeckRepository.deleteAsyncTaskDao(cardDeckDao).execute(id);
    }

    public void insertCardDeck(CardDeck cardDeck){
        new LocalCardDeckRepository.insertAsyncTaskDao(cardDeckDao).execute(cardDeck);
    }

    public void updateCardDeck(CardDeck cardDeck){
        new LocalCardDeckRepository.updateAsyncTaskDao(cardDeckDao).execute(cardDeck);
    }

    public void clearCardDecks(){
        cardDeckDao.clearCardDecks();
    }

    private static class insertAsyncTaskDao extends AsyncTask<CardDeck, Void, Void> {

        private CardDeckDao mAsyncTaskDao;

        insertAsyncTaskDao(CardDeckDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CardDeck... params){
            mAsyncTaskDao.insertCardDeck(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTaskDao extends AsyncTask<Long, Void, Void> {

        private CardDeckDao mAsyncTaskDao;

        deleteAsyncTaskDao(CardDeckDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Long... params){
            mAsyncTaskDao.deleteCardDeck(params[0]);
            return null;
        }
    }

    private static class updateAsyncTaskDao extends AsyncTask<CardDeck, Void, Void> {

        private CardDeckDao mAsyncTaskDao;

        updateAsyncTaskDao(CardDeckDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CardDeck... params){
            mAsyncTaskDao.updateCardDeck(params[0]);
            return null;
        }
    }
}
