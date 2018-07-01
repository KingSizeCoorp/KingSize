package com.movinghead333.kingsize.ui.mydecks.showdecks;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.movinghead333.kingsize.ArrayResource;
import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.database.Card;
import com.movinghead333.kingsize.data.database.CardDeck;
import com.movinghead333.kingsize.data.database.CardInCardDeckRelation;

import java.util.List;

public class ShowMyDecksViewModel extends ViewModel {

    private KingSizeRepository mRepository;
    private LiveData<List<CardDeck>> allCardDecks;

    ShowMyDecksViewModel(KingSizeRepository repository){
        this.mRepository = repository;
        allCardDecks = mRepository.getAllDecks();
    }

    public long insertCardDeck(CardDeck cardDeck){
        return mRepository.insertCardDeck(cardDeck);
    }

    public void updateCardDeck(CardDeck cardDeck){
        mRepository.updateCardDeck(cardDeck);
    }

    public void deleteCardDeckById(long id){
        mRepository.deleteCardDeckById(id);
    }

    public void clearCardDecks(){
        mRepository.clearCardDecks();
    }

    public LiveData<List<CardDeck>> getAllCardDecks() {
        return allCardDecks;
    }

    public void insertCardInCardDeckRelation(CardInCardDeckRelation cardInCardDeckRelation){
        mRepository.insertCardToCardDeckRelation(cardInCardDeckRelation);
    }

    public void createDeck(CardDeck cardDeck, String[] standardCards){
        mRepository.insertFullDeck(cardDeck, standardCards);
    }






}