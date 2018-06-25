package com.movinghead333.kingsize.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.movinghead333.kingsize.data.database.Card;
import com.movinghead333.kingsize.data.datawrappers.CardWithSymbol;

import java.util.List;

@Dao
public interface CardDao {

    @Insert(/*onConflict = OnConflictStrategy.ABORT*/)
    void insertCard(Card card);

    @Query("DELETE FROM card_table WHERE id == :id")
    void deleteCardById(long id);

    @Query("DELETE FROM card_table")
    void clearCards();

    @Query("SELECT * FROM card_table")
    LiveData<List<Card>> getAllCards();

    @Update
    void updateCard(Card card);

    @Query("SELECT COUNT(id) FROM card_table WHERE source = 'Standard'")
    int getStandardCardAvailable();

    @Query("SELECT id FROM card_table WHERE title LIKE :title")
    long getStandardCardByName(String title);

    @Query("SELECT  R.symbol, C.title, C.type, C.source " +
            "FROM card_table C JOIN cards_to_card_deck R ON C.id = R.card_id "+
            "WHERE R.card_deck_id = :cardDeckId")
    LiveData<List<CardWithSymbol>> getCardsWithSymbolInCardDeckById(long cardDeckId);

}
