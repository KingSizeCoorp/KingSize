package com.movinghead333.kingsize.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
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

    @Query("UPDATE feed_entries SET upvotes = upvotes + 1 WHERE id = :id")
    void voteUpLocally(int id);

    @Query("UPDATE feed_entries SET downvotes = downvotes + 1 WHERE id = :id")
    void voteDownLocally(int id);
}
