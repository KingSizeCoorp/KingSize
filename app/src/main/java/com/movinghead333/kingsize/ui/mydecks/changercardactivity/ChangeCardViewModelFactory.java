package com.movinghead333.kingsize.ui.mydecks.changercardactivity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.movinghead333.kingsize.data.KingSizeRepository;

public class ChangeCardViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private final KingSizeRepository mRepository;

    public ChangeCardViewModelFactory(KingSizeRepository repository){
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        return (T) new ChangeCardViewModel(mRepository);
    }
}
