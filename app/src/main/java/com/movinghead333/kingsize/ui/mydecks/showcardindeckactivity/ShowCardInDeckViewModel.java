package com.movinghead333.kingsize.ui.mydecks.showcardindeckactivity;

import android.arch.lifecycle.ViewModel;

import com.movinghead333.kingsize.data.KingSizeRepository;

public class ShowCardInDeckViewModel extends ViewModel{
    KingSizeRepository mRepository;

    public ShowCardInDeckViewModel(KingSizeRepository repository){
        this.mRepository = repository;
    }
}

