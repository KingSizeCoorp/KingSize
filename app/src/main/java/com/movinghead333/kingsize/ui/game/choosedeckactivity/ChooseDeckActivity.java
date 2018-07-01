package com.movinghead333.kingsize.ui.game.choosedeckactivity;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.database.CardDeck;
import com.movinghead333.kingsize.ui.CustomListItemClickListener;
import com.movinghead333.kingsize.ui.game.gamescreenactivity.GameScreenActivity;
import com.movinghead333.kingsize.ui.game.setupplayersactivity.SetupPlayersActivity;
import com.movinghead333.kingsize.utilities.InjectorUtils;

import java.util.ArrayList;
import java.util.List;

public class ChooseDeckActivity extends AppCompatActivity {

    public static final String EXTRA_LONG_DECK_ID = "EXTRA_LONG_DECK_ID";

    private ArrayList<String> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_deck);

        // get player-list from SetupPlayersActivity
        Intent intent = getIntent();
        players = intent.getStringArrayListExtra(SetupPlayersActivity.EXTRA_PLAYERS);

        // create ViewModel
        ChooseDeckViewModelFactory factory =
                InjectorUtils.provideChooseDeckViewModelFactory(this.getApplicationContext());
        final ChooseDeckViewModel mViewModel = ViewModelProviders.of(this, factory).get(ChooseDeckViewModel.class);

        // RecyclerView
        RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_choose_deck);

        // setup list-adapter
        final ChooseDeckListAdapter adapter = new ChooseDeckListAdapter(new CustomListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CardDeck selectedCardDeck = mViewModel.getCardDecks().getValue().get(position);
                createDialog(selectedCardDeck);
            }
        });

        // set adapter of RecyclerView
        mRecyclerView.setAdapter(adapter);

        // set LayoutManager of RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mViewModel.getCardDecks().observe(this, new Observer<List<CardDeck>>() {
            @Override
            public void onChanged(@Nullable List<CardDeck> cardDecks) {
                adapter.setDecks(cardDecks);
            }
        });
    }

    private void createDialog(final CardDeck deck){
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aktion bestätigen:");
        builder.setMessage("Spiel mit Deck \""+deck.deckName+"\" starten?");

        // set cancelable
        builder.setCancelable(true);

        // add a button
        builder.setPositiveButton("Spiel starten", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                Intent startGameIntent = new Intent(ChooseDeckActivity.this, GameScreenActivity.class);
                startGameIntent.putExtra(SetupPlayersActivity.EXTRA_PLAYERS, players);
                startGameIntent.putExtra(EXTRA_LONG_DECK_ID, deck.id);
                startActivity(startGameIntent);
            }
        });

        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();

        dialog.show();
    }
}
