package com.movinghead333.kingsize.data.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "card_deck_table")
public class CardDeck {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "deck_name")
    public String deckName;

    @ColumnInfo(name = "card_count")
    public int cardCount;

    public CardDeck(String deckName, int cardCount){
        this.deckName  = deckName;
        this.cardCount = cardCount;
    }
}
