package com.movinghead333.kingsize.ui.mydecks.showcardindeckactivity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.database.Card;
import com.movinghead333.kingsize.ui.mydecks.changercardactivity.ChangeCardActivity;
import com.movinghead333.kingsize.ui.mydecks.showsingledeckactivity.ShowSingleDeckActivity;
import com.movinghead333.kingsize.utilities.InjectorUtils;

import org.w3c.dom.Text;

public class ShowCardInDeckActivity extends AppCompatActivity {
    private static final String TAG = "SCIDA";

    public static final String EXTRA_STRING_SYMBOL = "EXTRA_STRING_SYMBOL";

    private ShowCardInDeckViewModel mViewModel;
    private long currentDeck;
    private long currentCard;
    private Card cardToDisplay;
    private String[] cardDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card_in_deck);

        Intent intent = getIntent();
        currentDeck = intent.getLongExtra(ShowSingleDeckActivity.STRING_EXTRA_CURRENT_DECK, -1);
        currentCard = intent.getLongExtra(ShowSingleDeckActivity.STRING_EXTRA_CURRENT_CARD, -1);
        Log.d(TAG, String.valueOf(currentCard));
        cardDetails = intent.getStringArrayExtra(ShowSingleDeckActivity.STRING_ARRAY_EXTRA_CARD_DETAILS);

        ShowCardInDeckViewModelFactory factory = InjectorUtils
                .providShowCardInDeckViewModelFactory(this.getApplicationContext());
        mViewModel = ViewModelProviders.of(this, factory).get(ShowCardInDeckViewModel.class);

        // todo  needs to be live data so it updated after card change
        ((TextView)findViewById(R.id.scid_card_title)).setText(cardDetails[1]);
        ((TextView)findViewById(R.id.scid_card_type)).setText(cardDetails[2]);
        ((TextView)findViewById(R.id.scid_card_source)).setText(cardDetails[3]);
        ((TextView)findViewById(R.id.scid_card_description)).setText(cardDetails[4]);

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
        changeCardIntent.putExtra(EXTRA_STRING_SYMBOL, Integer.parseInt(cardDetails[0]));
        startActivity(changeCardIntent);
    }
}
