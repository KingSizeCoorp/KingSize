package com.movinghead333.kingsize.ui.game.choosedeckactivity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.movinghead333.kingsize.data.KingSizeRepository;

public class ChooseDeckViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private KingSizeRepository mRepository;

    public ChooseDeckViewModelFactory(KingSizeRepository repository){
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ChooseDeckViewModel(mRepository);
    }
}
