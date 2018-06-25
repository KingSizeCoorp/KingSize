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

    private ShowCardInDeckViewModel mViewModel;
    private long currentDeck;
    private long currentCard;
    private Card cardToDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card_in_deck);

        Intent intent = getIntent();
        currentDeck = intent.getLongExtra(ShowSingleDeckActivity.STRING_EXTRA_CURRENT_DECK, -1);
        currentCard = intent.getLongExtra(ShowSingleDeckActivity.STRING_EXTRA_CURRENT_CARD, -1);
        String[] cardDetails = intent.getStringArrayExtra(ShowSingleDeckActivity.STRING_ARRAY_EXTRA_CARD_DETAILS);

        ShowCardInDeckViewModelFactory factory = InjectorUtils
                .providShowCardInDeckViewModelFactory(this.getApplicationContext());
        mViewModel = ViewModelProviders.of(this, factory).get(ShowCardInDeckViewModel.class);


        ((TextView)findViewById(R.id.scid_card_title)).setText(cardDetails[1]);
        ((TextView)findViewById(R.id.scid_card_type)).setText(cardDetails[2]);
        ((TextView)findViewById(R.id.scid_card_source)).setText(cardDetails[3]);
        ((TextView)findViewById(R.id.scid_card_description)).setText(cardDetails[4]);

    }

    public void changeCard(View view){
        Intent changeCardIntent = new Intent(ShowCardInDeckActivity.this, ChangeCardActivity.class);
        changeCardIntent.putExtra(ShowSingleDeckActivity.STRING_EXTRA_CURRENT_DECK, currentDeck);
        changeCardIntent.putExtra(ShowSingleDeckActivity.STRING_EXTRA_CURRENT_CARD, currentCard);
        startActivity(changeCardIntent);
    }
}
