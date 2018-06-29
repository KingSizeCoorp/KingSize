package com.movinghead333.kingsize.ui.mydecks.changercardactivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.database.Card;
import com.movinghead333.kingsize.data.database.CardInCardDeckRelation;

import java.util.List;

public class ChangeCardViewModel extends AndroidViewModel {

    KingSizeRepository mRepository;

    private List<Card> standardCards;
    private List<Card> customCards;
    private List<Card> feedCards;

    // holdes
    private LiveData<List<CardInCardDeckRelation>> cardRelations;
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

    public List<Card> getStandardCards() {
        return standardCards;
    }

    public List<Card> getCardSetBySource(int source){
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

    public boolean checkIfNewCardSelected(long id){
        List<CardInCardDeckRelation> cards = cardRelations.getValue();
        for(int i = 0; i < cards.size(); i++){
            if(cards.get(i).cardId == id){
                return false;
            }
        }
        return true;
    }

    public void replaceCardInDeck(long newCardId, int symbol){
        CardInCardDeckRelation newRelation
                = new CardInCardDeckRelation(currentDeckId, newCardId, symbol);
        mRepository.insertCardToCardDeckRelation(newRelation);
    }
}
