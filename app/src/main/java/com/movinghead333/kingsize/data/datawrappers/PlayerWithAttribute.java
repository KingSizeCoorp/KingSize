package com.movinghead333.kingsize.data.datawrappers;

/**
 * class for wrapping the status from status-card together with the corresponding player
 */
public class PlayerWithAttribute {

    private String playerName;
    private String statusEffekt;

    public PlayerWithAttribute(String playerName, String statusEffekt){
        this.playerName = playerName;
        this.statusEffekt = statusEffekt;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public String getStatusEffekt() {
        return this.statusEffekt;
    }

    public void setPlayerName(String playerName){
        this.playerName  = playerName;
    }
}
