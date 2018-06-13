package com.movinghead333.kingsize;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class ShowMyDecksActivity extends AppCompatActivity {

    private MyDecksListAdapter myDecksListAdapter;
    private ShowMyDecksViewModel showMyDecksViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_decks);

        showMyDecksViewModel = ViewModelProviders.of(this).get(ShowMyDecksViewModel.class);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.smd_recycler_view);

        myDecksListAdapter = new MyDecksListAdapter(new CustomListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

        recyclerView.setAdapter(myDecksListAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        showMyDecksViewModel.getAllCardDecks().observe(this, new Observer<List<CardDeck>>() {
            @Override
            public void onChanged(@Nullable List<CardDeck> cardDecks) {
                myDecksListAdapter.setCardDecks(cardDecks);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_card_deck);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(ShowMyCardsActivity.this, AddOrEditCardActivity.class);
                //TODO start AlertDialog for creating a new deck
            }
        });
    }
}
