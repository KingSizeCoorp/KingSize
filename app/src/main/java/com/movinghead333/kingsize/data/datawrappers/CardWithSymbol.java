package com.movinghead333.kingsize.data.datawrappers;

import android.arch.persistence.room.ColumnInfo;

public class CardWithSymbol {

    @ColumnInfo(name = "id")
    public long cardId;

    @ColumnInfo(name  = "symbol")
    public int symbol;

    @ColumnInfo(name = "title")
    public String cardName;

    @ColumnInfo(name = "type")
    public String cardType;

    @ColumnInfo(name = "source")
    public String cardSource;

    @ColumnInfo(name = "description")
    public String description;

    public CardWithSymbol(long cardId, int symbol, String cardName, String cardType,
                          String cardSource, String description){
        this.cardId = cardId;
        this.symbol = symbol;
        this.cardName = cardName;
        this.cardType = cardType;
        this.cardSource = cardSource;
        this.description = description;
    }
}
