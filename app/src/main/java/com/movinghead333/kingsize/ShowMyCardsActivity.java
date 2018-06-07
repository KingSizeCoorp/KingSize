package com.movinghead333.kingsize;

import android.app.Activity;
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

public class ShowMyCardsActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";
    public static final String EXTRA_IS_EDIT = "EXTRA_IS_EDIT";
    public static final int REQUEST_CODE_SHOW_SINGLE_CARD_ACTIVITY = 1;
    public static final int REQUEST_CODE_ADD_NEW_CARD = 2;
    public static final int REQUEST_CODE_EDIT_CARD = 3;

    private MyCardsListAdapter myCardsListAdapter;
    private ShowMyCardsViewModel showMyCardsViewModel;

    // last card picked from the recycler-view
    private Card currentCard;

    // id of currentCard
    private long currentCardId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_cards);

        showMyCardsViewModel = ViewModelProviders.of(this).get(ShowMyCardsViewModel.class);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.smc_recycler_view);

        myCardsListAdapter = new MyCardsListAdapter(new CustomListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currentCard = showMyCardsViewModel.getAllCards().getValue().get(position);
                currentCardId = currentCard.id;

                // create intent and start ShowSingleCardActivity
                Intent intent = new Intent(ShowMyCardsActivity.this, ShowSingleCardActivity.class);
                intent.putExtra(EXTRA_TITLE, currentCard.title);
                intent.putExtra(EXTRA_TYPE, currentCard.type);
                intent.putExtra(EXTRA_DESCRIPTION, currentCard.description);
                startActivityForResult(intent, REQUEST_CODE_SHOW_SINGLE_CARD_ACTIVITY);


                //Toast.makeText(getApplicationContext(), "Card with index "+position, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(myCardsListAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Set LiveData-observation
        showMyCardsViewModel.getAllCards().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                myCardsListAdapter.setCards(cards);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowMyCardsActivity.this, AddOrEditCardActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_NEW_CARD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode) {
            case REQUEST_CODE_SHOW_SINGLE_CARD_ACTIVITY:
                if(resultCode == ShowSingleCardActivity.RESULT_CODE_DELETE_CARD)
                    showMyCardsViewModel.deleteCardById(currentCardId);
                if(resultCode == ShowSingleCardActivity.RESULT_CODE_EDIT_CARD) {
                    Intent intent = new Intent(ShowMyCardsActivity.this, AddOrEditCardActivity.class);
                    intent.putExtra(EXTRA_IS_EDIT, true);
                    intent.putExtra(EXTRA_TITLE, currentCard.title);
                    intent.putExtra(EXTRA_TYPE, currentCard.type);
                    intent.putExtra(EXTRA_DESCRIPTION, currentCard.description);
                    startActivityForResult(intent, REQUEST_CODE_EDIT_CARD);
                }
                break;
            case REQUEST_CODE_ADD_NEW_CARD:
                if(resultCode == Activity.RESULT_OK) {
                    String title = data.getStringExtra(EXTRA_TITLE);
                    String type = data.getStringExtra(EXTRA_TYPE);
                    String description = data.getStringExtra(EXTRA_DESCRIPTION);
                    Card newCard = new Card(title, type, description, 0, 0);
                    showMyCardsViewModel.insertCard(newCard);
                }
                break;
            case REQUEST_CODE_EDIT_CARD:
                String etitle = data.getStringExtra(EXTRA_TITLE);
                String etype = data.getStringExtra(EXTRA_TYPE);
                String edescription = data.getStringExtra(EXTRA_DESCRIPTION);
                Card enewCard = new Card(etitle, etype, edescription, 0, 0);
                enewCard.id = currentCardId;
                showMyCardsViewModel.updateCard(enewCard);
                break;

        }
    }
}
