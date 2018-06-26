package com.movinghead333.kingsize.ui.mydecks.changercardactivity;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.movinghead333.kingsize.data.KingSizeRepository;

public class ChangeCardViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private final KingSizeRepository mRepository;
    private final Application mApplication;

    public ChangeCardViewModelFactory(KingSizeRepository repository, Application application){
        this.mRepository = repository;
        this.mApplication = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        return (T) new ChangeCardViewModel(mRepository, mApplication);
    }
}
