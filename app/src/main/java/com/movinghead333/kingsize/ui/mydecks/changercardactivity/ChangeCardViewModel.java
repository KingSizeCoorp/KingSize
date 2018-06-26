package com.movinghead333.kingsize.ui.mydecks.changercardactivity;

import android.arch.lifecycle.ViewModel;

import com.movinghead333.kingsize.data.KingSizeRepository;

public class ChangeCardViewModel extends ViewModel{

    KingSizeRepository mRepository;

    public ChangeCardViewModel(KingSizeRepository repository){
        this.mRepository = repository;
    }
}
