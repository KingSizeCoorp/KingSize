package com.movinghead333.kingsize.data.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import com.movinghead333.kingsize.data.database.Card;
import com.movinghead333.kingsize.data.database.CardDeck;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "cards_to_card_deck",
        primaryKeys = {"card_deck_id", "card_id"},
        foreignKeys = {
            @ForeignKey(
                    entity = CardDeck.class,
                    parentColumns = "id",
                    childColumns = "card_deck_id",
                    onDelete = CASCADE),
            @ForeignKey(
                    entity = Card.class,
                    parentColumns = "id",
                    childColumns = "card_id",
                    onDelete = CASCADE)
        },
        indices = {
            @Index(value = "card_deck_id"),
            @Index(value = "card_id")
        })
public class CardInCardDeckRelation {

    @NonNull
    @ColumnInfo(name = "card_deck_id")
    public final long cardDeckId;

    @NonNull
    @ColumnInfo(name = "card_id")
    public final long cardId;

    @ColumnInfo(name = "symbol")
    public String symbol;

    public CardInCardDeckRelation(long cardDeckId, long cardId, String symbol){
        this.cardDeckId = cardDeckId;
        this.cardId = cardId;
        this.symbol = symbol;
    }
}
