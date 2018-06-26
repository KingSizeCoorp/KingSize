package com.movinghead333.kingsize.ui.mydecks.changercardactivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.database.Card;

import java.util.List;

public class ChangeCardViewModel extends AndroidViewModel {

    KingSizeRepository mRepository;

    private List<Card> standardCards;
    private List<Card> customCards;
    private List<Card> feedCards;

    public ChangeCardViewModel(KingSizeRepository repository, Application app){
        super(app);
        this.mRepository = repository;
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
}
