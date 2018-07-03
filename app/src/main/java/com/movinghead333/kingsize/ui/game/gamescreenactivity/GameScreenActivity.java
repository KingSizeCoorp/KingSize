package com.movinghead333.kingsize.ui.game.gamescreenactivity;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.datawrappers.CardWithSymbol;
import com.movinghead333.kingsize.data.datawrappers.PlayerWithAttribute;
import com.movinghead333.kingsize.ui.game.choosedeckactivity.ChooseDeckActivity;
import com.movinghead333.kingsize.ui.game.setupplayersactivity.SetupPlayersActivity;
import com.movinghead333.kingsize.ui.game.showstatuseffects.ShowStatusEffectsActivity;
import com.movinghead333.kingsize.ui.game.showtokensactivity.ShowTokensActivity;
import com.movinghead333.kingsize.utilities.InjectorUtils;

import java.util.ArrayList;
import java.util.List;

public class GameScreenActivity extends AppCompatActivity {
    // extra constants
    public static final String EXTRA_STATUS_EFFECTS = "EXTRA_STATUS_EFFECTS";
    public static final String EXTRA_TOKENS = "EXTRA_TOKENS";
    public static final int RC_USE_TOKEN = 0;

    private GameScreenViewModel mViewModel;
    TextView currentPlayerTextView;
    TextView nextPlayerTextView;
    TextView cardSymbolTextView;
    TextView cardTypeTextView;
    TextView cardNameTextView;
    Button nextCardButton;
    //Todo currentCardTextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        currentPlayerTextView = (TextView)findViewById(R.id.gsa_current_player);
        nextPlayerTextView = (TextView)findViewById(R.id.gsa_next_player);
        cardSymbolTextView = (TextView)findViewById(R.id.gsa_card_symbol);
        cardTypeTextView = (TextView)findViewById(R.id.gsa_card_type);
        cardNameTextView = (TextView)findViewById(R.id.gsa_card_name);
        nextCardButton = (Button)findViewById(R.id.gsa_button_next_card);

        // get caller-intent
        Intent intent = getIntent();
        long selectedDeckId = intent.getLongExtra(ChooseDeckActivity.EXTRA_LONG_DECK_ID, -1);
        ArrayList<String> listOfPlayers = intent.getStringArrayListExtra(SetupPlayersActivity.EXTRA_PLAYERS);
        String[] players = listOfPlayers.toArray(new String[listOfPlayers.size()]);

        // create ViewModel
        GameScreenViewModelFactory factory =
                InjectorUtils.provideGameScreenViewModelFactory(this.getApplicationContext(),
                        getApplication());
        mViewModel = ViewModelProviders.of(this, factory).get(GameScreenViewModel.class);
        mViewModel.setDeckId(selectedDeckId);
        mViewModel.setPlayers(players);
        mViewModel.getCardsByDeckId().observe(this, new Observer<List<CardWithSymbol>>() {
            @Override
            public void onChanged(@Nullable List<CardWithSymbol> cardWithSymbols) {
                mViewModel.setCards(cardWithSymbols);

                // start the game (game init)
                mViewModel.startGame();
                updateUI();
            }
        });
    }

    private void updateUI(){
        currentPlayerTextView.setText(mViewModel.getCurrentPlayerName()+" "+mViewModel.getRemainingCards());
        nextPlayerTextView.setText((mViewModel.getNextPlayerName()));
        cardSymbolTextView.setText(mViewModel.getCurrentCardSymbol());
        cardTypeTextView.setText(mViewModel.getCurrentCardType());
        cardNameTextView.setText(mViewModel.getCurrentlyDrawnCardName());
        if(mViewModel.getRemainingCards() == 0){
            nextCardButton.setText("Zurück zur Spielerauswahl");
        }
    }

    private void onGameFinished(){
        // todo add dialog between callback to SetupPLayersActivity and GameScreenActivity finish()-call
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_USE_TOKEN){
            if(resultCode == ShowTokensActivity.RESULT_CODE_USE_TOKEN){
                int removeIndex = data.getIntExtra(ShowTokensActivity.EXTRA_INT_REMOVE_INDEX, -1);
                mViewModel.removeTokenByIndex(removeIndex);
            }
        }
    }

    // invoked when when button "next_card" is pressed
    public void nextCard(View view){
        if(mViewModel.getRemainingCards() > 0){
            mViewModel.moveToNextPlayer();
            updateUI();
        }else{
            onGameFinished();
        }
    }

    public void showTokens(View view){
        Intent intent = new Intent(GameScreenActivity.this, ShowTokensActivity.class);

        // put ui-data as extra
        Bundle bundle = new Bundle();
        ArrayList<PlayerWithAttribute> arrayList = mViewModel.getPlayerTokens();
        bundle.putParcelableArrayList(EXTRA_TOKENS, arrayList);
        intent.putExtras(bundle);

        startActivityForResult(intent, RC_USE_TOKEN);
    }

    public void showStatusEffects(View view){
        Intent intent = new Intent(GameScreenActivity.this, ShowStatusEffectsActivity.class);

        // put content into intent
        Bundle bundle = new Bundle();
        ArrayList<PlayerWithAttribute> arrayList = mViewModel.getPlayerStatusEffects();
        bundle.putParcelableArrayList(EXTRA_STATUS_EFFECTS, arrayList);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
