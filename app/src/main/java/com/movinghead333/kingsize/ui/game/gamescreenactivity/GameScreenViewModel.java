package com.movinghead333.kingsize.ui.game.gamescreenactivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.movinghead333.kingsize.data.ArrayResource;
import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.datawrappers.CardWithSymbol;
import com.movinghead333.kingsize.data.datawrappers.PlayerWithAttribute;

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
    private CardWithSymbol drawnCardWithSymbol;
    Random random;

    // ui-data-holders for:
    // GameScreenActivity
    private String currentPlayerName;
    private String nextPlayerName;
    private String currentCardSymbol;
    private String currentCardType;
    private String currentlyDrawnCardName;

    private Drawable currentImage;

    List<List<Drawable>> graphicsList;

    // ShowStatusEffectsActivity
    private ArrayList<PlayerWithAttribute> playerStatusEffects = new ArrayList<PlayerWithAttribute>();

    // ShowTokensActivity
    private ArrayList<PlayerWithAttribute> playerTokens = new ArrayList<PlayerWithAttribute>();

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

            // setup card images
            setupGraphics();

            // setup random object
            random = new Random();

            // draw first card
            drawnCardWithSymbol = cards.get(drawCard());
            currentCardSymbol = ArrayResource.CARDS_IN_36_CARDSDECK[drawnCardWithSymbol.symbol];
            currentCardType = drawnCardWithSymbol.cardType;
            currentlyDrawnCardName = drawnCardWithSymbol.cardName;

            // get first image
            selectCurrentImage();
        }
    }

    void moveToNextPlayer(){
        currentPlayer = (currentPlayer + 1) % amountOfPlayers;
        currentPlayerName = players[currentPlayer];
        nextPlayer = (nextPlayer + 1) % amountOfPlayers;
        nextPlayerName = players[nextPlayer];

        // get Card which is currently drawn
        drawnCardWithSymbol = cards.get(drawCard());

        // get image
        selectCurrentImage();

        // set data-holders for GameScreenActivity
        currentCardSymbol = ArrayResource.CARDS_IN_36_CARDSDECK[drawnCardWithSymbol.symbol];
        currentCardType = drawnCardWithSymbol.cardType;
        currentlyDrawnCardName = drawnCardWithSymbol.cardName;

        // check if cardType is Token
        if(drawnCardWithSymbol.cardType.equals(getApplication().getResources().getString(R.string.card_type_token))){
            playerTokens.add(new PlayerWithAttribute(players[currentPlayer], drawnCardWithSymbol.cardName));
            return;
        }

        // check if cardType is Status
        if(drawnCardWithSymbol.cardType.equals(getApplication().getResources().getString(R.string.card_type_status))){
            for(int i = 0; i < playerStatusEffects.size(); i++){
                if(playerStatusEffects.get(i).getAttribute().equals(drawnCardWithSymbol.cardName)){
                    playerStatusEffects.get(i).setPlayerName(players[currentPlayer]);
                    return;
                }
            }
            playerStatusEffects.add(new PlayerWithAttribute(players[currentPlayer], drawnCardWithSymbol.cardName));
            return;
        }

        // todo: check for rule
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

    void removeTokenByIndex(int index){
        playerTokens.remove(index);
    }


    // getters and setter
    int getRemainingCards(){
        return cardIndexList.size();
    }

    ArrayList<PlayerWithAttribute> getPlayerStatusEffects(){
        return this.playerStatusEffects;
    }

    ArrayList<PlayerWithAttribute> getPlayerTokens(){
        return this.playerTokens;
    }

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

    private void setupGraphics(){
        Resources res = getApplication().getResources();
        graphicsList = new ArrayList<>();

        // 6
        ArrayList<Drawable> temp1 = new ArrayList<>();
        temp1.add(res.getDrawable(R.drawable.leim6));
        temp1.add(res.getDrawable(R.drawable.huppi6));
        temp1.add(res.getDrawable(R.drawable.guender6));
        temp1.add(res.getDrawable(R.drawable.gamp6));
        graphicsList.add(temp1);

        // 7
        ArrayList<Drawable> temp2 = new ArrayList<>();
        temp2.add(res.getDrawable(R.drawable.leim7));
        temp2.add(res.getDrawable(R.drawable.huppi7));
        temp2.add(res.getDrawable(R.drawable.guender7));
        temp2.add(res.getDrawable(R.drawable.gamp7));
        graphicsList.add(temp2);

        // 8
        ArrayList<Drawable> temp3 = new ArrayList<>();
        temp3.add(res.getDrawable(R.drawable.leim8));
        temp3.add(res.getDrawable(R.drawable.huppi8));
        temp3.add(res.getDrawable(R.drawable.guender8));
        temp3.add(res.getDrawable(R.drawable.gamp8));
        graphicsList.add(temp3);

        // 9
        ArrayList<Drawable> temp4 = new ArrayList<>();
        temp4.add(res.getDrawable(R.drawable.leim9));
        temp4.add(res.getDrawable(R.drawable.huppi9));
        temp4.add(res.getDrawable(R.drawable.guender9));
        temp4.add(res.getDrawable(R.drawable.gamp9));
        graphicsList.add(temp4);

        // 6
        ArrayList<Drawable> temp5 = new ArrayList<>();
        temp5.add(res.getDrawable(R.drawable.leim10));
        temp5.add(res.getDrawable(R.drawable.huppi10));
        temp5.add(res.getDrawable(R.drawable.guender10));
        temp5.add(res.getDrawable(R.drawable.gamp10));
        graphicsList.add(temp5);

        // U
        ArrayList<Drawable> temp6 = new ArrayList<>();
        temp6.add(res.getDrawable(R.drawable.leim6));
        temp6.add(res.getDrawable(R.drawable.huppi6));
        temp6.add(res.getDrawable(R.drawable.guender6));
        temp6.add(res.getDrawable(R.drawable.gamp6));
        graphicsList.add(temp6);

        // O
        ArrayList<Drawable> temp7 = new ArrayList<>();
        temp7.add(res.getDrawable(R.drawable.leim6));
        temp7.add(res.getDrawable(R.drawable.huppi6));
        temp7.add(res.getDrawable(R.drawable.guender6));
        temp7.add(res.getDrawable(R.drawable.gamp6));
        graphicsList.add(temp7);

        // K
        ArrayList<Drawable> temp8 = new ArrayList<>();
        temp8.add(res.getDrawable(R.drawable.leimk));
        temp8.add(res.getDrawable(R.drawable.huppik));
        temp8.add(res.getDrawable(R.drawable.guenderk));
        temp8.add(res.getDrawable(R.drawable.gambk));
        graphicsList.add(temp8);

        // A
        ArrayList<Drawable> temp9 = new ArrayList<>();
        temp9.add(res.getDrawable(R.drawable.leim6));
        temp9.add(res.getDrawable(R.drawable.huppi6));
        temp9.add(res.getDrawable(R.drawable.guender6));
        temp9.add(res.getDrawable(R.drawable.gamp6));
        graphicsList.add(temp9);
    }

    void selectCurrentImage(){
        List<Drawable> list = graphicsList.get(drawnCardWithSymbol.symbol);
        Random random  = new Random();
        int index = random.nextInt(list.size());
        currentImage = list.get(index);
        list.remove(index);
    }

    Drawable getCurrentImage(){
        return currentImage;
    }
}
