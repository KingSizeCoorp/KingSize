package com.movinghead333.kingsize.ui.game.choosedeckactivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.database.CardDeck;

import java.util.List;

class ChooseDeckViewModel extends ViewModel{

    private KingSizeRepository mRepository;
    private LiveData<List<CardDeck>> cardDecks;

    ChooseDeckViewModel(KingSizeRepository repository){
        this.mRepository = repository;
        this.cardDecks = mRepository.getAllDecks();
    }

    LiveData<List<CardDeck>> getCardDecks(){
        return cardDecks;
    }
}
