package com.movinghead333.kingsize.ui.mycards.showmycards;

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
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.movinghead333.kingsize.ui.mycards.AddOrEditCardActivity;
import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.database.Card;
import com.movinghead333.kingsize.ui.CustomListItemClickListener;
import com.movinghead333.kingsize.ui.mycards.ShowSingleCardActivity;
import com.movinghead333.kingsize.utilities.InjectorUtils;

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
    private ShowMyCardsActivityViewModel showMyCardsActivityViewModel;

    // last card picked from the recycler-view
    private Card currentCard;

    // id of currentCard
    private long currentCardId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordlayout);

        //showMyCardsActivityViewModel = ViewModelProviders.of(this).get(ShowMyCardsActivityViewModel.class);
        ShowMyCardsViewModelFactory factory = InjectorUtils.provideShowMyCardsViewModelFactory(this.getApplicationContext());
        showMyCardsActivityViewModel = ViewModelProviders.of(this, factory).get(ShowMyCardsActivityViewModel.class);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.smc_recycler_view);

        String[] sourceStrings = new String[]{
              getResources().getString(R.string.source_standard),
              getResources().getString(R.string.source_my_cards),
              getResources().getString(R.string.source_feed)
        };

        myCardsListAdapter = new MyCardsListAdapter(new CustomListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currentCard = showMyCardsActivityViewModel.getAllCards().getValue().get(position);
                currentCardId = currentCard.id;

                // create intent and start ShowSingleCardActivity
                Intent intent = new Intent(ShowMyCardsActivity.this, ShowSingleCardActivity.class);
                intent.putExtra(EXTRA_TITLE, currentCard.title);
                intent.putExtra(EXTRA_TYPE, currentCard.type);
                intent.putExtra(EXTRA_DESCRIPTION, currentCard.description);
                startActivityForResult(intent, REQUEST_CODE_SHOW_SINGLE_CARD_ACTIVITY);


                //Toast.makeText(getApplicationContext(), "Card with index "+position, Toast.LENGTH_SHORT).show();
            }
        }, sourceStrings, getApplication());

        recyclerView.setAdapter(myCardsListAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Set LiveData-observation
        showMyCardsActivityViewModel.getAllCards().observe(this, new Observer<List<Card>>() {
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
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_card_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search_cards);
        SearchView searchView = (SearchView)searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myCardsListAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode) {
            case REQUEST_CODE_SHOW_SINGLE_CARD_ACTIVITY:
                if(resultCode == ShowSingleCardActivity.RESULT_CODE_DELETE_CARD)
                    showMyCardsActivityViewModel.deleteCardById(currentCardId);
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
                    Card newCard = new Card(title, type, description, 0, 0, getResources().getString(R.string.source_my_cards));
                    showMyCardsActivityViewModel.insertCard(newCard);
                }
                break;
            case REQUEST_CODE_EDIT_CARD:
                String etitle = data.getStringExtra(EXTRA_TITLE);
                String etype = data.getStringExtra(EXTRA_TYPE);
                String edescription = data.getStringExtra(EXTRA_DESCRIPTION);
                Card enewCard = new Card(etitle, etype, edescription, 0, 0, getResources().getString(R.string.source_my_cards));
                enewCard.id = currentCardId;
                showMyCardsActivityViewModel.updateCard(enewCard);
                break;
        }
    }
}
