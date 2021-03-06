package com.movinghead333.kingsize.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.ui.game.setupplayersactivity.SetupPlayersActivity;
import com.movinghead333.kingsize.ui.mycards.showmycards.ShowMyCardsActivity;
import com.movinghead333.kingsize.ui.mydecks.showdecks.ShowMyDecksActivity;
import com.movinghead333.kingsize.ui.myfeed.ShowFeedActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // start game
    public void startNewGame(View view){
        Intent intent = new Intent(MainActivity.this, SetupPlayersActivity.class);
        startActivity(intent);
    }

    // show the user's feed
    public void showFeed(View view){
        Intent intent = new Intent(MainActivity.this, ShowFeedActivity.class);
        startActivity(intent);
    }

    // shows the user's decks
    public void showDecks(View view){
        Intent intent = new Intent(MainActivity.this, ShowMyDecksActivity.class);
        startActivity(intent);
    }

    // shows the user's locally saved cards
    public void showMyCards(View view){
        Intent intent = new Intent(MainActivity.this, ShowMyCardsActivity.class);
        startActivity(intent);
    }
}