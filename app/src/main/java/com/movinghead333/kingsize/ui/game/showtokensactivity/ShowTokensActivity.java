package com.movinghead333.kingsize.ui.game.showtokensactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.datawrappers.PlayerWithAttribute;
import com.movinghead333.kingsize.ui.game.gamescreenactivity.GameScreenActivity;

import java.util.ArrayList;

public class ShowTokensActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tokens);

        // get ui-data
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<PlayerWithAttribute> uiData =
                bundle.getParcelableArrayList(GameScreenActivity.EXTRA_TOKENS);

        // Setup RecyclerView
        RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.sta_recyclerview);

        // set RecyclerView adapter
        ShowTokensListAdapter adapter = new ShowTokensListAdapter(uiData);
        mRecyclerView.setAdapter(adapter);

        // set LayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

    }
}
