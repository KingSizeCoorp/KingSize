package com.movinghead333.kingsize.ui.mydecks.showsingledeckactivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.database.Card;
import com.movinghead333.kingsize.data.datawrappers.CardWithSymbol;

import java.util.List;

public class ShowSingleDeckViewModel extends ViewModel{

    private KingSizeRepository mRepository;
    private LiveData<List<CardWithSymbol>> cardsInDeck;

    ShowSingleDeckViewModel(KingSizeRepository repository, long cardDeckId){
        this.mRepository = repository;
        cardsInDeck = mRepository.getCardsWithSymbolByCardDeckId(cardDeckId);
    }

    public LiveData<List<CardWithSymbol>> getCardsWithSymbol(){
        return cardsInDeck;
    }

    public LiveData<Card> getCardById(long id){
        return mRepository.getCardById(id);
    }

    void deleteDeckById(long deckId){
        mRepository.deleteCardDeckById(deckId);
    }
}
