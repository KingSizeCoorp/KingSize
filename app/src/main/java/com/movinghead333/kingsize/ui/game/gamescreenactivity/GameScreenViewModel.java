package com.movinghead333.kingsize.ui.game.gamescreenactivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.datawrappers.CardWithSymbol;

import java.util.List;

class GameScreenViewModel extends ViewModel{

    private String[] players;
    private long deckId;
    private List<CardWithSymbol> cards;
    private int amountOfPlayers;
    private boolean isStarted = false;
    private int currentPlayer;
    private int nextPlayer;

    // ui-data-holders
    private String currentPlayerName;
    private String nextPlayerName;

    private KingSizeRepository mRepository;

    // constructor
    GameScreenViewModel(KingSizeRepository repository){
        this.mRepository = repository;
    }

    // game-logic
    void startGame(){
        if(!isStarted){
            currentPlayer = 0;
            currentPlayerName = players[currentPlayer];
            nextPlayer = 1;
            nextPlayerName = players[nextPlayer];
            //Todo set first card
        }
    }

    void moveToNextPlayer(){
        currentPlayer = (currentPlayer + 1) % amountOfPlayers;
        currentPlayerName = players[currentPlayer];
        nextPlayer = (nextPlayer + 1) % amountOfPlayers;
        nextPlayerName = players[nextPlayer];
    }


    // getters and setter
    String getCurrentPlayerName(){
        return this.currentPlayerName;
    }

    String getNextPlayerName(){
        return this.nextPlayerName;
    }

    boolean isStarted(){
        return this.isStarted;
    }

    void setDeckId(long deckId){
        this.deckId = deckId;
    }

    void setPlayers(String[] players){
        this.players = players;
        this.amountOfPlayers = this.players.length;
    }

    LiveData<List<CardWithSymbol>> getCardsByDeckId(){
        return mRepository.getCardsWithSymbolByCardDeckId(deckId);
    }

    void setCards(List<CardWithSymbol> cards){
        this.cards = cards;
    }
}
