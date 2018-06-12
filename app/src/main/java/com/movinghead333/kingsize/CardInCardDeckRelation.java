package com.movinghead333.kingsize;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "cards_to_card_deck",primaryKeys = {"card_deck_id", "card_id"}, indices = {
        @Index(value = {"card_deck_id","card_id"})},
foreignKeys = {@ForeignKey(entity = CardDeck.class, parentColumns = "id", childColumns = "card_deck_id"),
               @ForeignKey(entity = Card.class, parentColumns = "id", childColumns = "card_id")})
public class CardInCardDeckRelation {

    @ColumnInfo(name = "card_deck_id")
    public long cardDeckId;

    @ColumnInfo(name = "card_id")
    public long cardId;
}
