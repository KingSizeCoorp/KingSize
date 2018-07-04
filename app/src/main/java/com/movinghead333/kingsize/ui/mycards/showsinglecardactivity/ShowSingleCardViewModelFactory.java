package com.movinghead333.kingsize.ui.mycards.showsinglecardactivity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.movinghead333.kingsize.data.KingSizeRepository;

public class ShowSingleCardViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final KingSizeRepository mRepository;

    public ShowSingleCardViewModelFactory(KingSizeRepository repository){
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ShowSingleCardViewModel(mRepository);
    }
}
