package com.movinghead333.kingsize.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface CardInCardDeckRelationDao {

    @Insert
    void insertMultiple(CardInCardDeckRelation... relations);

    @Update
    void updateRelation(CardInCardDeckRelation newRelation);

    @Query("DELETE FROM cards_to_card_deck WHERE card_deck_id = :id")
    void deleteCardDeckRelations(long id);
}
