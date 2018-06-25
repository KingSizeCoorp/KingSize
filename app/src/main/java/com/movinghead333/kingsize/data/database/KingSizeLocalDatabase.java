package com.movinghead333.kingsize.data.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.movinghead333.kingsize.R;

@Database(entities = {Card.class, CardDeck.class, CardInCardDeckRelation.class}, version = 10)
public abstract class KingSizeLocalDatabase extends RoomDatabase{

    private static final String DATABASE_NAME = "kingsize_database";

    private static Card[] standardCards;

    private static KingSizeLocalDatabase INSTANCE;

    public static KingSizeLocalDatabase getDatabase(final Context context){
        setUpStandardCards(context);
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
    public abstract CardDeckDao cardDeckDao();
    public abstract CardInCardDeckRelationDao cardInCardDeckRelationDao();

    private static RoomDatabase.Callback sKingSizeDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute(standardCards);
                }
            };


    private static class PopulateDbAsync extends AsyncTask<Card, Void, Void> {

        private final CardDao cardDao;
        private final CardDeckDao cardDeckDao;
        private final CardInCardDeckRelationDao cardInCardDeckRelationDao;

        PopulateDbAsync(KingSizeLocalDatabase db){
            cardDao = db.cardDao();
            cardDeckDao = db.cardDeckDao();
            cardInCardDeckRelationDao = db.cardInCardDeckRelationDao();
        }

        @Override
        protected Void doInBackground(final Card... params){
            //cardDao.clearCards();
            cardDeckDao.clearCardDecks();

            if(cardDao.getStandardCardAvailable() == 0){
                for(int i = 0; i < params.length; i++){
                    cardDao.insertCard(params[i]);
                }
            }
            /*
            CardDeck cardDeck = new CardDeck("Kingseis", 36);
            long idd = cardDeckDao.insertCardDeck(cardDeck);

            cardDeck = new CardDeck("Poker lol", 52);
            cardDeckDao.insertCardDeck(cardDeck);
            */
            return null;
        }
    }

    private static void setUpStandardCards(Context context){
        if(standardCards == null){
            standardCards = new Card[9];
            Resources res = context.getResources();
            standardCards[0] = new Card(res.getString(R.string.card_title_waterfall),
                    res.getString(R.string.card_type_simple_action),
                    res.getString(R.string.card_description_no_description), 0, 0,
                    res.getString(R.string.source_standard));
            standardCards[1] = new Card(res.getString(R.string.card_title_joker),
                    res.getString(R.string.card_type_token),
                    res.getString(R.string.card_description_no_description), 0, 0,
                    res.getString(R.string.source_standard));
            standardCards[2] = new Card(res.getString(R.string.card_title_right_mate_drinks),
                    res.getString(R.string.card_type_simple_action),
                    res.getString(R.string.card_description_no_description), 0, 0,
                    res.getString(R.string.source_standard));
            standardCards[3] = new Card(res.getString(R.string.card_title_distribute_two_shots),
                    res.getString(R.string.card_type_simple_action),
                    res.getString(R.string.card_description_no_description), 0, 0,
                    res.getString(R.string.source_standard));
            standardCards[4] = new Card(res.getString(R.string.card_title_drinking_rule),
                    res.getString(R.string.card_type_simple_action),
                    res.getString(R.string.card_description_no_description), 0, 0,
                    res.getString(R.string.source_standard));
            standardCards[5] = new Card(res.getString(R.string.card_title_thumb_master),
                    res.getString(R.string.card_type_status),
                    res.getString(R.string.card_description_no_description), 0, 0,
                    res.getString(R.string.source_standard));
            standardCards[6] = new Card(res.getString(R.string.card_title_category),
                    res.getString(R.string.card_type_simple_action),
                    res.getString(R.string.card_description_no_description), 0, 0,
                    res.getString(R.string.source_standard));
            standardCards[7] = new Card(res.getString(R.string.card_title_question_master),
                    res.getString(R.string.card_type_status),
                    res.getString(R.string.card_description_no_description), 0, 0,
                    res.getString(R.string.source_standard));
            standardCards[8] = new Card(res.getString(R.string.card_title_rhymetime),
                    res.getString(R.string.card_type_simple_action),
                    res.getString(R.string.card_description_no_description), 0, 0,
                    res.getString(R.string.source_standard));
        }
    }
}
