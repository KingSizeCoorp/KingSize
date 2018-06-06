package com.movinghead333.kingsize;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Card.class}, version = 1)
public abstract class KingSizeLocalDatabase extends RoomDatabase{

    private static final String DATABASE_NAME = "kingsize_database";

    private static KingSizeLocalDatabase INSTANCE;

    public static KingSizeLocalDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (KingSizeLocalDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            KingSizeLocalDatabase.class, DATABASE_NAME)
                            .addCallback(sKingSizeDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract CardDao cardDao();

    private static RoomDatabase.Callback sKingSizeDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final CardDao cardDao;

        PopulateDbAsync(KingSizeLocalDatabase db){
            cardDao = db.cardDao();
        }

        @Override
        protected Void doInBackground(final Void... params){
                Card card = new Card("Spiegel","token","Schlücke zurückwerfen", 0, 0);
                cardDao.insertCard(card);
            return null;
        }
    }
}
