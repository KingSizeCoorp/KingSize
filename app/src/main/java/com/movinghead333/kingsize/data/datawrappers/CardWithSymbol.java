package com.movinghead333.kingsize.data.datawrappers;

import android.arch.persistence.room.ColumnInfo;

public class CardWithSymbol {

    @ColumnInfo(name  = "symbol")
    public int symbol;

    @ColumnInfo(name = "title")
    public String cardName;

    @ColumnInfo(name = "type")
    public String cardType;

    @ColumnInfo(name = "source")
    public String cardSource;

    public CardWithSymbol(int symbol, String cardName, String cardType, String cardSource){
        this.symbol = symbol;
        this.cardName = cardName;
        this.cardType = cardType;
        this.cardSource = cardSource;
    }
}
