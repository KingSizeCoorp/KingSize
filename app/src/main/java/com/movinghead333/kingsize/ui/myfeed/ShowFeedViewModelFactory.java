package com.movinghead333.kingsize.ui.myfeed;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.movinghead333.kingsize.data.KingSizeRepository;

public class ShowFeedViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final KingSizeRepository mRepository;

    public ShowFeedViewModelFactory(KingSizeRepository repository){
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        return (T) new ShowFeedViewModel(mRepository);
    }
}
