package com.movinghead333.kingsize.data.datawrappers;

public class CardWithSymbol {
    public int symbol;
    public String cardName;
    public String cardType;
    public String cardSource;

    public CardWithSymbol(int symbol, String cardName, String cardType, String cardSource){
        this.symbol = symbol;
        this.cardName = cardName;
        this.cardType = cardType;
        this.cardSource = cardSource;
    }
}
