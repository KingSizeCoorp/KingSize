package com.movinghead333.kingsize.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FeedEntryDao{

    @Insert
    void insertFeedEntry(FeedEntry entry);

    @Insert
    void insertFeedEntries(List<FeedEntry> entries);

    @Query("SELECT * FROM feed_entries")
    LiveData<List<FeedEntry>> getAllFeedEntries();

    @Query("DELETE FROM feed_entries")
    void clearEntries();
}
