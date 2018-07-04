package com.movinghead333.kingsize.ui.mycards.showsinglecardactivity;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.database.Card;

public class ShowSingleCardViewModel extends ViewModel{

    private KingSizeRepository mRepository;

    ShowSingleCardViewModel(KingSizeRepository repository){
        this.mRepository = repository;
    }

    MutableLiveData<String> uploadCard(Card card){
        return mRepository.uploadCard(card);
    }
}
