package com.movinghead333.kingsize.ui.mydecks.showcardindeckactivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.database.Card;
import com.movinghead333.kingsize.ui.mydecks.changercardactivity.ChangeCardActivity;
import com.movinghead333.kingsize.ui.mydecks.showsingledeckactivity.ShowSingleDeckActivity;
import com.movinghead333.kingsize.utilities.InjectorUtils;


public class ShowCardInDeckActivity extends AppCompatActivity {
    private static final String TAG = "SCIDA";

    public static final String EXTRA_STRING_SYMBOL = "EXTRA_STRING_SYMBOL";
    public static final String EXTRA_STRING_CARD_TITLE = "EXTRA_STRING_CARD_TITLE";

    private ShowCardInDeckViewModel mViewModel;
    private long currentDeck;
    private long currentCard;
    private int currentCardSymbol;
    private String currentCardTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card_in_deck);

        Intent intent = getIntent();
        currentDeck = intent.getLongExtra(ShowSingleDeckActivity.STRING_EXTRA_CURRENT_DECK, -1);
        currentCard = intent.getLongExtra(ShowSingleDeckActivity.STRING_EXTRA_CURRENT_CARD, -1);
        currentCardSymbol = intent.getIntExtra(ShowSingleDeckActivity.STRING_EXTRA_SYMBOL, -1);

        ShowCardInDeckViewModelFactory factory = InjectorUtils
                .provideShowCardInDeckViewModelFactory(this.getApplicationContext());
        mViewModel = ViewModelProviders.of(this, factory).get(ShowCardInDeckViewModel.class);
        mViewModel.getCardFromDeckBySymbolAndDeckId(currentDeck, currentCardSymbol).observe(this,
                new Observer<Card>() {
                    @Override
                    public void onChanged(@Nullable Card card) {
                        if(card != null){
                            currentCardTitle = card.title;
                            ((TextView)findViewById(R.id.scid_card_title)).setText(card.title);
                            ((TextView)findViewById(R.id.scid_card_type)).setText(card.type);
                            ((TextView)findViewById(R.id.scid_card_source)).setText(card.source);
                            ((TextView)findViewById(R.id.scid_card_description)).setText(card.description);
                        }
                    }
                });

        // todo  needs to be live data so it updated after card change
    }

    /**
     * this method gets called when the user clicks the change_card-button in the
     * ShowCardInDeckActivity
     * @param view corresponds to the button, which has been clicked
     */
    public void changeCard(View view){
        // creating intent for starting ChangeCardActivity
        Intent changeCardIntent = new Intent(ShowCardInDeckActivity.this, ChangeCardActivity.class);

        // send the id of the currently selected deck
        changeCardIntent.putExtra(ShowSingleDeckActivity.STRING_EXTRA_CURRENT_DECK, currentDeck);

        // send the id of the currently selected card
        changeCardIntent.putExtra(ShowSingleDeckActivity.STRING_EXTRA_CURRENT_CARD, currentCard);

        // send the symbol of the currently selected card
        changeCardIntent.putExtra(EXTRA_STRING_SYMBOL, currentCardSymbol);

        // send the card title of the currently selected card
        changeCardIntent.putExtra(EXTRA_STRING_CARD_TITLE, currentCardTitle);

        startActivity(changeCardIntent);
    }
}
