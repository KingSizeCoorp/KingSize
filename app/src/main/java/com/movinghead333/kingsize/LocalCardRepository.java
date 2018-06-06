package com.movinghead333.kingsize;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class LocalCardRepository {

    private CardDao cardDao;
    private LiveData<List<Card>> allCards;

    LocalCardRepository(Application application){
        KingSizeLocalDatabase db = KingSizeLocalDatabase.getDatabase(application);
        cardDao = db.cardDao();
        allCards = cardDao.getAllCards();
    }

    public LiveData<List<Card>> getAllCards() {
        return allCards;
    }

    public void deleteCardById(long id){
        new deleteAsyncTaskDao(cardDao).execute(id);
    }

    public void insertCard(Card card){
        new insertAsyncTaskDao(cardDao).execute(card);
    }

    private static class insertAsyncTaskDao extends AsyncTask<Card, Void, Void> {

        private CardDao mAsyncTaskDao;

        insertAsyncTaskDao(CardDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Card... params){
            mAsyncTaskDao.insertCard(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTaskDao extends AsyncTask<Long, Void, Void> {

        private CardDao mAsyncTaskDao;

        deleteAsyncTaskDao(CardDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Long... params){
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void clearCards(){
        cardDao.clearCards();
    }
}
