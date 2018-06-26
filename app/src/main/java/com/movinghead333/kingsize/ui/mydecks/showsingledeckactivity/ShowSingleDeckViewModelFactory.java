package com.movinghead333.kingsize.ui.mydecks.showsingledeckactivity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.movinghead333.kingsize.data.KingSizeRepository;

public class ShowSingleDeckViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final KingSizeRepository mRepository;
    private long id;

    public ShowSingleDeckViewModelFactory(KingSizeRepository repository, long id){
        this.mRepository = repository;
        this.id = id;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        return (T) new ShowSingleDeckViewModel(mRepository, id);
    }
}
