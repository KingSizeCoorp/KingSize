package com.movinghead333.kingsize.ui.mydecks.showsingledeckactivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.datawrappers.CardWithSymbol;
import com.movinghead333.kingsize.ui.CustomListItemClickListener;
import com.movinghead333.kingsize.ui.mydecks.showcardindeckactivity.ShowCardInDeckActivity;
import com.movinghead333.kingsize.ui.mydecks.showdecks.ShowMyDecksActivity;
import com.movinghead333.kingsize.utilities.InjectorUtils;

import java.util.List;

public class ShowSingleDeckActivity extends AppCompatActivity {
    private static final String TAG = "SSDA";

    public static final String STRING_EXTRA_CURRENT_DECK = "STRING_EXTRA_CURRENT_DECK";
    public static final String STRING_EXTRA_CURRENT_CARD = "STRING_EXTRA_CURRENT_CARD";
    public static final String STRING_EXTRA_SYMBOL = "STRING_EXTRA_SYMBOL";

    private ShowSingleDeckViewModel mViewModel;
    private long selectedDeckId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_deck);

        // get id from selected deck
        Intent intent = getIntent();
        selectedDeckId = intent.getLongExtra(ShowMyDecksActivity.EXTRA_CARD_DECK_ID, -1);
        Log.d(TAG, String.valueOf(selectedDeckId));

        // setup the dackname that is displayed at the top of the activity
        String deckName = intent.getStringExtra(ShowMyDecksActivity.EXTRA_STRING_DECK_NAME);
        TextView tv = (TextView)findViewById(R.id.show_single_deck_deck_title);
        tv.setText(deckName);

        // ViewModel initialisation
        ShowSingleDeckViewModelFactory factory =
                InjectorUtils.provideShowSingleDeckViewModelFactory(this.getApplicationContext(), selectedDeckId);
        mViewModel = ViewModelProviders.of(this, factory).get(ShowSingleDeckViewModel.class);

        // setup recyclerView
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.show_single_Deck_recycler_view);

        // create adapter for recyclerView
        final ShowSingleDeckListAdapter adapter = new ShowSingleDeckListAdapter(new CustomListItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                CardWithSymbol selectedCard = mViewModel.getCardsWithSymbol().getValue().get(position);
                Intent intent = new Intent(ShowSingleDeckActivity.this, ShowCardInDeckActivity.class);
                // send necessary extras for the following activities
                intent.putExtra(STRING_EXTRA_CURRENT_DECK, selectedDeckId);
                intent.putExtra(STRING_EXTRA_CURRENT_CARD, selectedCard.cardId);
                intent.putExtra(STRING_EXTRA_SYMBOL, selectedCard.symbol);

                // start new activity
                startActivity(intent);
            }
        });
        // set recyclerView's adapter
        recyclerView.setAdapter(adapter);

        // LinearLayoutManager for recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // observe LiveData-content of recyclerView
        mViewModel.getCardsWithSymbol().observe(this, new Observer<List<CardWithSymbol>>() {
            @Override
            public void onChanged(@Nullable List<CardWithSymbol> cardWithSymbols) {
                adapter.setCardsInDeck(cardWithSymbols);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_show_decks, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.item_delete_deck:
                AlertDialog.Builder adb = new AlertDialog.Builder(this);

                adb.setTitle("Aktion bestätigen:");

                adb.setMessage("Das aktuelle Kartendeck wirklich löschen?");

                adb.setIcon(android.R.drawable.ic_dialog_alert);

                adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mViewModel.deleteDeckById(selectedDeckId);
                        finish();
                    } });
                adb.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    } });
                adb.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
