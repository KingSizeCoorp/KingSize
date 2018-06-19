package com.movinghead333.kingsize.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.movinghead333.kingsize.data.database.Card;

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
}
