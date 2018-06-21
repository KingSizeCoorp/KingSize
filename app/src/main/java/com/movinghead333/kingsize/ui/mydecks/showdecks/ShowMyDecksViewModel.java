package com.movinghead333.kingsize.ui.mydecks.showdecks;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.movinghead333.kingsize.data.database.CardDeck;

import java.util.List;

public class ShowMyDecksViewModel extends AndroidViewModel{

    private LocalCardDeckRepository localCardDeckRepository;
    private LiveData<List<CardDeck>> allCardDecks;

    public ShowMyDecksViewModel(Application application){
        super(application);

        localCardDeckRepository = new LocalCardDeckRepository(application);
        allCardDecks = localCardDeckRepository.getAllCardDecks();
    }

    public void insertCardDeck(CardDeck cardDeck){
        localCardDeckRepository.insertCardDeck(cardDeck);
    }

    public void updateCardDeck(CardDeck cardDeck){
        localCardDeckRepository.updateCardDeck(cardDeck);
    }

    public void deleteCardDeckById(long id){
        localCardDeckRepository.deleteCarddDeckById(id);
    }

    public void clearCardDecks(){
        localCardDeckRepository.clearCardDecks();
    }

    public LiveData<List<CardDeck>> getAllCardDecks() {
        return allCardDecks;
    }
}
