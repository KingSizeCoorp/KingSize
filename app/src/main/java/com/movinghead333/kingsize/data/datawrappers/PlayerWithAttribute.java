package com.movinghead333.kingsize.data.datawrappers;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * class for wrapping the status from status-card together with the corresponding player
 */
public class PlayerWithAttribute implements Parcelable{

    private String playerName;
    private String attribute;

    public PlayerWithAttribute(String playerName, String attribute){
        this.playerName = playerName;
        this.attribute = attribute;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public String getAttribute() {
        return this.attribute;
    }

    public void setPlayerName(String playerName){
        this.playerName  = playerName;
    }

    // Parcelable implementation
    public PlayerWithAttribute(Parcel in){
        this.playerName = in.readString();
        this.attribute = in.readString();
    }

    public PlayerWithAttribute(){
        this.playerName = "";
        this.attribute = "";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.playerName);
        dest.writeString(this.attribute);
    }

    public static final Parcelable.Creator<PlayerWithAttribute> CREATOR =
            new Parcelable.Creator<PlayerWithAttribute>(){

                @Override
                public PlayerWithAttribute createFromParcel(Parcel source) {
                    return new PlayerWithAttribute(source);
                }

                @Override
                public PlayerWithAttribute[] newArray(int size) {
                    return new PlayerWithAttribute[size];
                }
            };
}
