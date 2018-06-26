package com.movinghead333.kingsize.ui.mydecks.showcardindeckactivity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.movinghead333.kingsize.data.KingSizeRepository;

public class ShowCardInDeckViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    KingSizeRepository mRepository;

    public ShowCardInDeckViewModelFactory(KingSizeRepository repository){
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        return (T) new ShowCardInDeckViewModel(mRepository);
    }
}
