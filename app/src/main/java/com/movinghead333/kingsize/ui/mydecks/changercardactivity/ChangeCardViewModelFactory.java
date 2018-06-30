package com.movinghead333.kingsize.ui.mydecks.changercardactivity;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.movinghead333.kingsize.data.KingSizeRepository;

public class ChangeCardViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private final KingSizeRepository mRepository;
    private final Application mApplication;
    private final long deckId;

    public ChangeCardViewModelFactory(KingSizeRepository repository, Application application,
                                      long deckId){
        this.mRepository = repository;
        this.mApplication = application;
        this.deckId = deckId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        return (T) new ChangeCardViewModel(mRepository, mApplication, deckId);
    }
}
