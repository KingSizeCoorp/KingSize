package com.movinghead333.kingsize.data.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "card_table")
public class Card implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "upvotes")
    public int upvotes;

    @ColumnInfo(name = "downvotes")
    public int downvotes;

    @ColumnInfo(name = "source")
    public String source;


    /**
     * Constructor for room database integration
     * @param title
     * @param type
     * @param description
     * @param upvotes
     * @param downvotes
     */
    public Card(String title, String type, String description, int upvotes, int downvotes, String source){
        this.title = title;
        this.type = type;
        this.description = description;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.source = source;
    }

    @Ignore
    public Card(Parcel in){
        this.id = in.readLong();
        this.title = in.readString();
        this.type = in.readString();
        this.description = in.readString();
        this.upvotes = in.readInt();
        this.downvotes = in.readInt();
        this.source = in.readString();
    }

    /*
    @Ignore
    public Card(){
        this.playerName = "";
        this.attribute = "";
    }
    */

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    public void writeToParcel(Parcel dest, int flags){
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.type);
        dest.writeString(this.description);
        dest.writeInt(this.upvotes);
        dest.writeInt(this.downvotes);
        dest.writeString(this.source);
    }

    @Ignore
    public static final Parcelable.Creator<Card> CREATOR =
            new Parcelable.Creator<Card>(){

                @Override
                public Card createFromParcel(Parcel source) {
                    return new Card(source);
                }

                @Override
                public Card[] newArray(int size) {
                    return new Card[size];
                }
            };
}
