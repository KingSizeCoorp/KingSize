package com.movinghead333.kingsize.ui.mycards.showmycards;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.movinghead333.kingsize.data.KingSizeRepository;

public class ShowMyCardsViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final KingSizeRepository mRepository;

    public ShowMyCardsViewModelFactory(KingSizeRepository repository){
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ShowMyCardsActivityViewModel(mRepository);
    }
}
