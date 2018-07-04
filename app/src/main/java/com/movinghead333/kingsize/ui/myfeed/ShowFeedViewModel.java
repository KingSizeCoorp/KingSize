package com.movinghead333.kingsize.ui.myfeed;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.movinghead333.kingsize.data.ArrayResource;
import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.database.Card;
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

    void upvote(int itemId){
        List<FeedEntry> temp = feedEntries.getValue();
        int entryId = temp.get(itemId).getId();
        mRepository.upVote(String.valueOf(entryId));
        mRepository.voteUpLocally(entryId);
    }

    void downvote(int itemId){
        List<FeedEntry> temp = feedEntries.getValue();
        int entryId = temp.get(itemId).getId();
        mRepository.downVote(String.valueOf(entryId));
        mRepository.voteDownLocally(entryId);
    }

    void insertCard(int itemId){
        List<FeedEntry> temp = feedEntries.getValue();
        FeedEntry entry = temp.get(itemId);

        Card card = new Card(entry.getCardName(), entry.getType(), entry.getDescription(),
                entry.getUpvotes(), entry.getDownvotes(), ArrayResource.CARD_SOURCES[2]);
        mRepository.insertCard(card);
    }
}
