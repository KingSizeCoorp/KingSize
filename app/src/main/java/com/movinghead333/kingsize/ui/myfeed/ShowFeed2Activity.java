package com.movinghead333.kingsize.ui.myfeed;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.database.FeedEntry;
import com.movinghead333.kingsize.ui.CustomListItemClickListener;
import com.movinghead333.kingsize.ui.UpAndDownVotesListener;
import com.movinghead333.kingsize.utilities.InjectorUtils;

import java.util.List;

public class ShowFeed2Activity extends AppCompatActivity {

    private ShowFeedViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_feed2);

        ShowFeedViewModelFactory factory =
                InjectorUtils.provideShowFeedViewModelFactory(this.getApplicationContext());
        mViewModel = ViewModelProviders.of(this, factory).get(ShowFeedViewModel.class);

        RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.sfa_recyclerview);

        final ShowFeedListAdapter adapter = new ShowFeedListAdapter(
                new CustomListItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(ShowFeed2Activity.this, "Eintrag ausgewählt",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                new UpAndDownVotesListener() {
                    @Override
                    public void onUpVote() {
                        Toast.makeText(ShowFeed2Activity.this, "1 upvote",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDownVote() {
                        Toast.makeText(ShowFeed2Activity.this, "1 downvote",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );

        mRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mViewModel.getFeedEntries().observe(this, new Observer<List<FeedEntry>>() {
            @Override
            public void onChanged(@Nullable List<FeedEntry> feedEntries) {
                adapter.updateList(feedEntries);
            }
        });

        // observe data changes
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("ShowFeed2Activity","feed is being updated");
        mViewModel.syncFeedEntries();
        return true;
    }
}
