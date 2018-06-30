package com.movinghead333.kingsize.ui.game.setupplayersactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.database.CardDeck;
import com.movinghead333.kingsize.ui.CustomListItemClickListener;
import com.movinghead333.kingsize.ui.game.choosedeckactivity.ChooseDeckActivity;
import com.movinghead333.kingsize.ui.mydecks.showdecks.ShowMyDecksActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetupPlayersActivity extends AppCompatActivity {

    public static final String EXTRA_PLAYERS = "EXTRA_PLAYERS";

    private List<String> players = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_players);

        // recyclerView from id
        RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.spa_recyclerview);

        final SetupPlayerListAdapter adapter = new SetupPlayerListAdapter(players);
        adapter.setButtonOnClickListener(new CustomListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                players.remove(position);
                adapter.setPlayers(players);
            }
        });

        mRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);



        // button for changing to the next activity ChooseDeckActivity
        Button startChooseDeckButton = (Button)findViewById(R.id.spa_button_select_deck);
        startChooseDeckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(players.size() > 1){
                    Intent intent = new Intent(SetupPlayersActivity.this, ChooseDeckActivity.class);

                    intent.putStringArrayListExtra(EXTRA_PLAYERS, (ArrayList<String>) players);
                    startActivity(intent);
                }else{
                    Toast.makeText(SetupPlayersActivity.this,
                            "Es werden mindestens zwei Spieler zum starten des Spiels benötigt.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });



        // floating action button for adding a new player
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_player);
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
        builder.setTitle("Neuen Spieler hinzufügen:");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_enter_playername, null);
        builder.setView(customLayout);

        // set cancelable
        builder.setCancelable(true);

        // add a button
        builder.setPositiveButton("Hinzufügen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                EditText editText = customLayout.findViewById(R.id.dialog_enter_playername_edittext);
                String pName = editText.getText().toString();
                if(pName.length() == 0){
                    Toast.makeText(SetupPlayersActivity.this,
                            "Ungültiger Name: das Namensfeld ist leer!", Toast.LENGTH_LONG).show();
                }else{
                    for(int i = 0; i < players.size(); i++){
                        if(players.get(i).equals(pName)){
                            Toast.makeText(SetupPlayersActivity.this,
                                    "Fehler: Name bereits vorhanden", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    players.add(pName);
                }
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
