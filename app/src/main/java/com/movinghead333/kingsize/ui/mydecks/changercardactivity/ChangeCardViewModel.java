package com.movinghead333.kingsize.ui.mydecks.changercardactivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.database.Card;
import com.movinghead333.kingsize.data.database.CardInCardDeckRelation;

import java.util.List;

public class ChangeCardViewModel extends AndroidViewModel {
    private static final String TAG = "ChangeCardViewModelLog";

    KingSizeRepository mRepository;

    private LiveData<List<Card>> standardCards;
    private LiveData<List<Card>> customCards;
    private LiveData<List<Card>> feedCards;

    private int currentSymbol;
    private long currentId;

    // fields saving temporary data in order to swap two cards in the deck
    private int exchangeSymbol = -1;
    private long exchangeId = -1;

    // holdes
    LiveData<List<CardInCardDeckRelation>> cardRelations;
    private long currentDeckId;

    /**
     *
     * @param repository receives the reference to singleton repository class
     * @param app receives an instance of the app in order to make the use of AndroidViewModel possible
     * @param deckId receives the currently selected deckId for later queries
     */
    public ChangeCardViewModel(KingSizeRepository repository, Application app, long deckId){
        super(app);
        this.mRepository = repository;
        this.currentDeckId = deckId;

        // get all cards in the current deck
        Log.d(TAG, String.valueOf(deckId));
        cardRelations = mRepository.getCardsInDeck(deckId);

        // get all standard cards
        standardCards = mRepository.getCardsBySource(
                getApplication().getResources().getString(R.string.source_standard));

        // get all custom cards
        customCards = mRepository.getCardsBySource(
                getApplication().getResources().getString(R.string.source_my_cards));

        // get all feed cards
        feedCards = mRepository.getCardsBySource(
                getApplication().getResources().getString(R.string.source_feed));
    }

    public LiveData<List<Card>> getCardSetBySource(int source){
        switch(source){
            case R.string.source_standard:
                return standardCards;
            case R.string.source_my_cards:
                return customCards;
            case R.string.source_feed:
                return feedCards;
            default: return null;
        }
    }

    public boolean checkIfNewCardSelected(long id, List<CardInCardDeckRelation> rel){
        List<CardInCardDeckRelation> cards = rel;
        Log.d(TAG, rel.toString());
        for(int i = 0; i < cards.size(); i++){
            Log.d(TAG, String.valueOf(cards.get(i).cardId));
            if(cards.get(i).cardId == id){
                exchangeId = id;
                exchangeSymbol = cards.get(i).symbol;
                return false;
            }
        }
        return true;
    }

    public void replaceCardInDeck(long newCardId){
        CardInCardDeckRelation newRelation
                = new CardInCardDeckRelation(currentDeckId, newCardId, currentSymbol);
        mRepository.insertCardToCardDeckRelation(newRelation);
    }

    public void swapCardsInDeck(){
        CardInCardDeckRelation[] array = new CardInCardDeckRelation[2];
        Log.d(TAG, String.valueOf(currentId)+" "+String.valueOf(currentSymbol)+"/n"+
                String.valueOf(exchangeId+" "+String.valueOf(exchangeSymbol)));
        array[0] = new CardInCardDeckRelation(currentDeckId, currentId, exchangeSymbol);
        Log.d(TAG,"0 worked");
        array[1] = new CardInCardDeckRelation(currentDeckId, exchangeId, currentSymbol);
        Log.d(TAG,"1 worked");
        mRepository.insertCardToCardDeckRelation(array);
    }

    public void setCurrentSymbol(int symbol){
        currentSymbol = symbol;
    }

    void setCurrentId(long id){
        this.currentId = id;
    }
}
