package com.movinghead333.kingsize.data.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "feed_entries")
public class FeedEntry {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String cardName;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "upvotes")
    private int upvotes;

    @ColumnInfo(name = "downvotes")
    private int downvotes;

    public FeedEntry(int id, String cardName, String type, String description, int upvotes, int downvotes){
        this.id = id;
        this.cardName = cardName;
        this.type = type;
        this.description = description;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }

    public int getId() {
        return id;
    }

    public String getCardName() {
        return cardName;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void incrementUpvotes(){
        this.upvotes++;
    }

    public void incrementDownvotes(){
        this.downvotes++;
    }
}
