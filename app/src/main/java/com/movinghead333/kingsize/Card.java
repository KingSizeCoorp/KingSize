package com.movinghead333.kingsize;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "card_table")
public class Card {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "typ")
    public String typ;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "upvotes")
    public int upvotes;

    @ColumnInfo(name = "downvotes")
    public int downvotes;


    /**
     * Constructor for room database integration
     * @param title
     * @param typ
     * @param description
     * @param upvotes
     * @param downvotes
     */
    public Card(String title, String typ, String description, int upvotes, int downvotes){
        this.title = title;
        this.typ = typ;
        this.description = description;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }
}
