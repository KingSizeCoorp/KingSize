package com.movinghead333.kingsize.ui.mydecks.showdecks;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.movinghead333.kingsize.ArrayResource;
import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.database.CardDeck;
import com.movinghead333.kingsize.data.database.CardInCardDeckRelation;
import com.movinghead333.kingsize.ui.CustomListItemClickListener;
import com.movinghead333.kingsize.utilities.InjectorUtils;

import java.util.ArrayList;
import java.util.List;

public class ShowMyDecksActivity extends AppCompatActivity {

    private MyDecksListAdapter myDecksListAdapter;
    private ShowMyDecksViewModel showMyDecksViewModel;
    private Context acitivityContext;
    private Spinner dialogSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_decks);

        ShowMyDecksViewModelFactory factory =
                InjectorUtils.provideShowMyDecksViewModelFactory(this.getApplicationContext());
        showMyDecksViewModel = ViewModelProviders.of(this, factory).get(ShowMyDecksViewModel.class);

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
                createDialog();
            }
        });
    }

    private void createDialog(){
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_create_new_deck, null);
        builder.setView(customLayout);

        // spinner stuff
        List<String> entries = new ArrayList<String>();
        entries.add("36");
        entries.add("52");


        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, entries);
        //spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dialogSpinner = (Spinner)customLayout.findViewById(R.id.dcnd_spinner_card_count);
        dialogSpinner.setAdapter(spinnerAdapter);
        //cardAmountSpinner.setSelection(0);

        // set cancelable
        builder.setCancelable(true);

        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                EditText editText = customLayout.findViewById(R.id.dcnd_et_deck_name);
                createCardDeckInDatabase(editText.getText().toString(),
                        Integer.parseInt(dialogSpinner.getSelectedItem().toString()));
            }
        });

        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            } });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public void createCardDeckInDatabase(String name, int cardCount){
        CardDeck newDeck = new CardDeck(name, cardCount);
        long newDeckId = showMyDecksViewModel.insertCardDeck(newDeck);

        CardInCardDeckRelation rel = new CardInCardDeckRelation(newDeckId,
                showMyDecksViewModel.getStandardCardByName(),
                ArrayResource.CARDS_IN_36_CARDSDECK[0]);

        //showMyDecksViewModel.insertCardInCardDeckRelation(rel);
    }
}
