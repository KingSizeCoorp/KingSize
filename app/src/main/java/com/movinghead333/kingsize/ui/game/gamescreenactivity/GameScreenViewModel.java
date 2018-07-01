package com.movinghead333.kingsize.ui.game.gamescreenactivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.movinghead333.kingsize.ArrayResource;
import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.datawrappers.CardWithSymbol;
import com.movinghead333.kingsize.data.datawrappers.PlayerWithAttribute;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class GameScreenViewModel extends AndroidViewModel{

    // data source
    private KingSizeRepository mRepository;

    // game-data
    private String[] players;
    private long deckId;
    private List<CardWithSymbol> cards;
    private int amountOfPlayers;
    private boolean isStarted = false;
    private int currentPlayer;
    private int nextPlayer;
    private ArrayList<Integer> cardIndexList = new ArrayList<>();
    Random random;

    // ui-data-holders for:
    // GameScreenActivity
    private String currentPlayerName;
    private String nextPlayerName;
    private String currentCardSymbol;
    private String currentCardType;
    private String currentlyDrawnCardName;

    // ShowStatusEffectsActivity
    List<PlayerWithAttribute> playerStatusEffects = new ArrayList<PlayerWithAttribute>();

    // ShowTokensActivity
    List<PlayerWithAttribute> playerTokens = new ArrayList<PlayerWithAttribute>();

    // constructor
    GameScreenViewModel(KingSizeRepository repository, Application application){
        super(application);
        this.mRepository = repository;
    }

    // game-logic
    void startGame(){
        if(!isStarted){
            // setup players
            currentPlayer = 0;
            currentPlayerName = players[currentPlayer];
            nextPlayer = 1;
            nextPlayerName = players[nextPlayer];

            // setup cards
            for(int x = 0; x < cards.size(); x++){
                for(int y = 0; y < 4; y++){
                    cardIndexList.add(x);
                }
            }

            // setup random object
            random = new Random();

            // draw first card
            CardWithSymbol drawnCardWithSymbol = cards.get(drawCard());
            currentCardSymbol = ArrayResource.CARDS_IN_36_CARDSDECK[drawnCardWithSymbol.symbol];
            currentCardType = drawnCardWithSymbol.cardType;
            currentlyDrawnCardName = drawnCardWithSymbol.cardName;
        }
    }

    void moveToNextPlayer(){
        currentPlayer = (currentPlayer + 1) % amountOfPlayers;
        currentPlayerName = players[currentPlayer];
        nextPlayer = (nextPlayer + 1) % amountOfPlayers;
        nextPlayerName = players[nextPlayer];

        // get Card which is currently drawn
        CardWithSymbol drawnCardWithSymbol = cards.get(drawCard());

        // set data-holders for GameScreenActivity
        currentCardSymbol = ArrayResource.CARDS_IN_36_CARDSDECK[drawnCardWithSymbol.symbol];
        currentCardType = drawnCardWithSymbol.cardType;
        currentlyDrawnCardName = drawnCardWithSymbol.cardName;

        // check if cardType is Token
        if(drawnCardWithSymbol.cardType == getApplication().getResources().getString(R.string.card_type_token)){
            playerTokens.add(new PlayerWithAttribute(players[currentPlayer], drawnCardWithSymbol.cardName));
        }

        // check if cardType is Status
        if(drawnCardWithSymbol.cardType == getApplication().getResources().getString(R.string.card_type_status)){
            for(int i = 0; i < playerStatusEffects.size(); i++){
                if(playerStatusEffects.get(i).getStatusEffekt() == drawnCardWithSymbol.cardType){

                }
            }
        }
    }

    /**
     * Randomly draws a card from the cardIndexList and returns the index and removes the element
     * after finding it
     * @return the index of the drawn card (e.g. with 9 cards in deck a int between 0-8
     */
    private int drawCard(){
        int randomDraw = random.nextInt(cardIndexList.size());
        int drawnCardIndex = cardIndexList.get(randomDraw);
        cardIndexList.remove(randomDraw);
        return drawnCardIndex;
    }


    // getters and setter
    String getCurrentCardSymbol(){
        return currentCardSymbol;
    }

    String getCurrentCardType(){
        return currentCardType;
    }

    String getCurrentlyDrawnCardName(){
        return currentlyDrawnCardName;
    }

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
