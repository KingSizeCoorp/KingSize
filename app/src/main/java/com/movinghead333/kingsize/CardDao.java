package com.movinghead333.kingsize;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CardDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertCard(Card card);

    @Query("DELETE FROM card_table WHERE id == :id")
    void delete(long id);

    @Query("DELETE FROM card_table")
    public void clearCards();

    @Query("SELECT * FROM card_table")
    LiveData<List<Card>> getAllCards();

    @Update
    public void updateCard(Card card);
}
