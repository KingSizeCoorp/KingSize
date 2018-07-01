package com.movinghead333.kingsize.ui.game.gamescreenactivity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.movinghead333.kingsize.data.KingSizeRepository;

public class GameScreenViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    KingSizeRepository mRepository;

    public GameScreenViewModelFactory(KingSizeRepository repository){
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GameScreenViewModel(mRepository);
    }
}
