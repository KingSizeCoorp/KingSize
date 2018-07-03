package com.movinghead333.kingsize.ui.game.showstatuseffects;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.datawrappers.PlayerWithAttribute;
import com.movinghead333.kingsize.ui.game.gamescreenactivity.GameScreenActivity;

import java.util.ArrayList;

public class ShowStatusEffectsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_status_effects);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //todo fix object passing
        ArrayList<PlayerWithAttribute> playerWithAttributes =
                bundle.getParcelableArrayList(GameScreenActivity.EXTRA_STATUS_EFFECTS);

        ShowStatusEffectsListAdapter adapter = new ShowStatusEffectsListAdapter(playerWithAttributes);

        RecyclerView  mRecyclerView = (RecyclerView)findViewById(R.id.ssea_recyclerview);
        mRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }
}
