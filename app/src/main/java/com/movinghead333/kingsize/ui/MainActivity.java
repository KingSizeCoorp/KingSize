package com.movinghead333.kingsize.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.ui.mycards.showmycards.ShowMyCardsActivity;
import com.movinghead333.kingsize.ui.mydecks.showdecks.ShowMyDecksActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startNewGame(View view){

    }

    public void showFeed(View view){

    }

    public void showDecks(View view){
        Intent intent = new Intent(MainActivity.this, ShowMyDecksActivity.class);
        startActivity(intent);
    }

    public void showMyCards(View view){
        Intent intent = new Intent(MainActivity.this, ShowMyCardsActivity.class);
        startActivity(intent);
    }
}