package com.movinghead333.kingsize;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CardDeckDao {

    @Insert
    long insertCardDeck(CardDeck cardDeck);

    @Update
    void updateCardDeck(CardDeck cardDeck);

    @Query("DELETE FROM card_deck_table WHERE id == :id")
    void deleteCardDeck(long id);

    @Query("DELETE FROM card_deck_table")
    void clearCardDecks();

    @Query("SELECT * FROM card_deck_table")
    LiveData<List<CardDeck>> getAllCardDecks();
}
