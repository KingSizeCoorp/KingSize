package com.movinghead333.kingsize.ui.mydecks.showcardindeckactivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.database.Card;

public class ShowCardInDeckViewModel extends ViewModel{
    KingSizeRepository mRepository;

    public ShowCardInDeckViewModel(KingSizeRepository repository){
        this.mRepository = repository;
    }

    public LiveData<Card> getCardById(long id){
        return mRepository.getCardById(id);
    }

    LiveData<Card> getCardFromDeckBySymbolAndDeckId(long deckId, int symbol){
        return mRepository.getCardFromDeckBySymbolAndDeckId(deckId, symbol);
    }
}

