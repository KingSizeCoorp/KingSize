package com.movinghead333.kingsize.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CardInCardDeckRelationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultiple(CardInCardDeckRelation... relations);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSingleRelation(CardInCardDeckRelation relation);

    @Update
    void updateRelation(CardInCardDeckRelation newRelation);

    @Delete
    public void deleteCardInCardDeckRelations(CardInCardDeckRelation... cardInCardDeckRelations);

    @Query("DELETE FROM cards_to_card_deck WHERE card_deck_id = :id")
    void deleteCardDeckRelations(long id);

    @Query("SELECT * FROM cards_to_card_deck WHERE card_deck_id = :id")
    LiveData<List<CardInCardDeckRelation>> getCardsInCardDeck(long id);

    @Query("DELETE FROM cards_to_card_deck")
    void clearCardsInCardDeckRelations();
}
