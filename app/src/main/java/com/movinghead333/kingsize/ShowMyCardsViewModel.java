package com.movinghead333.kingsize;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ShowMyCardsViewModel extends AndroidViewModel{

    private LocalCardRepository  localCardRepository;
    private LiveData<List<Card>> allCards;

    public ShowMyCardsViewModel(Application application){
        super(application);

        localCardRepository = new LocalCardRepository(application);
        allCards = localCardRepository.getAllCards();
    }

    public void deleteCardById(long id){
        localCardRepository.deleteCardById(id);
    }

    LiveData<List<Card>> getAllCards(){
        return allCards;
    }

    public void insertCard(Card card){
        localCardRepository.insertCard(card);
    }

    public void clearCards(){
        localCardRepository.clearCards();
    }
}
