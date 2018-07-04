package com.movinghead333.kingsize.ui.myfeed;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.database.FeedEntry;

import java.util.List;

public class ShowFeedViewModel extends ViewModel{

    private KingSizeRepository mRepository;
    private LiveData<List<FeedEntry>> feedEntries;

    ShowFeedViewModel(KingSizeRepository repository){
        this.mRepository = repository;
        feedEntries = mRepository.getFeedEntries();
    }

    LiveData<List<FeedEntry>> getFeedEntries(){
        return feedEntries;
    }

    void syncFeedEntries(){
        mRepository.updateDatabase();
    }
}
