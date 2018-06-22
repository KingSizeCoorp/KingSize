package com.movinghead333.kingsize.ui.mydecks.showsingledeckactivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.database.Card;

public class ShowSingleDeckViewModel extends ViewModel{

    private KingSizeRepository mRepository;
    private LiveData<Card> cardsInDeck;
    private long deckId;

    ShowSingleDeckViewModel(KingSizeRepository repository){
        this.mRepository = repository;
    }
}
