package com.movinghead333.kingsize.ui.mydecks.showdecks;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.database.CardDeck;

import java.util.List;

public class ShowMyDecksViewModel extends ViewModel {

    private KingSizeRepository mRepository;
    private LiveData<List<CardDeck>> allCardDecks;

    ShowMyDecksViewModel(KingSizeRepository repository){
        this.mRepository = repository;
        allCardDecks = mRepository.getAllDecks();
    }

    public void insertCardDeck(CardDeck cardDeck){
        mRepository.insertCardDeck(cardDeck);
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
}