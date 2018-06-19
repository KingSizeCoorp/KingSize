package com.movinghead333.kingsize.ui.mycards.showmycards;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.database.Card;

import java.util.List;

public class ShowMyCardsActivityViewModel extends ViewModel {

    private KingSizeRepository mRepository;
    private LiveData<List<Card>> allCards;

    ShowMyCardsActivityViewModel(KingSizeRepository repository){
        this.mRepository = repository;
        allCards = repository.getAllCards();
    }

    public void deleteCardById(long id){
        mRepository.deleteCardById(id);
    }

    public LiveData<List<Card>> getAllCards(){
        return allCards;
    }

    public void insertCard(Card card){
        mRepository.insertCard(card);
    }

    public void clearCards(){
        mRepository.clearCards();
    }

    public void updateCard(Card card){
        mRepository.updateCard(card);
    }
}
