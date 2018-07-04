package com.movinghead333.kingsize.ui.mycards.showsinglecardactivity;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.movinghead333.kingsize.data.ArrayResource;
import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.database.Card;
import com.movinghead333.kingsize.data.network.HttpJsonParser;
import com.movinghead333.kingsize.ui.mycards.showmycards.ShowMyCardsActivity;
import com.movinghead333.kingsize.utilities.InjectorUtils;

public class ShowSingleCardActivity extends AppCompatActivity {

    public static final int RESULT_CODE_EDIT_CARD = 1;
    public static final int RESULT_CODE_DELETE_CARD = 2;

    private Card currentCard;
    private ShowSingleCardViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_card);

        Intent startIntent = getIntent();
        currentCard = startIntent.getParcelableExtra(ShowMyCardsActivity.EXTRA_PICKED_CARD);

        ShowSingleCardViewModelFactory factory =
                InjectorUtils.provideShowSingleCardViewModelFactory(this.getApplicationContext());
        mViewModel = ViewModelProviders.of(this, factory)
                .get(ShowSingleCardViewModel.class);

        updateUI();
    }

    private void updateUI(){
        // get color based on the card source
        int color = getColor(currentCard.source);

        // setup static non changing TextViews
        ((TextView)findViewById(R.id.ssc_static_card_title)).setBackgroundColor(color);
        ((TextView)findViewById(R.id.ssc_static_card_type)).setBackgroundColor(color);
        ((TextView)findViewById(R.id.ssc_static_card_source)).setBackgroundColor(color);
        ((TextView)findViewById(R.id.ssc_static_card_description)).setBackgroundColor(color);

        // setup dynamic TextViews
        // card-title
        TextView title = (TextView)findViewById(R.id.ssc_dynamic_card_title);
        title.setText(currentCard.title);
        title.setBackgroundColor(color);

        // card-type
        TextView type = (TextView)findViewById(R.id.ssc_dynamic_card_type);
        type.setText(currentCard.type);
        type.setBackgroundColor(color);

        // card-source
        TextView source = (TextView)findViewById(R.id.ssc_dynamic_card_source);
        source.setText(currentCard.source);
        source.setBackgroundColor(color);

        // card-description
        TextView description = (TextView)findViewById(R.id.ssc_dynamic_card_description);
        description.setText(currentCard.description);
        description.setBackgroundColor(color);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_card, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // check if selected card is standard card
        if((currentCard.source).equals(ArrayResource.CARD_SOURCES[1])){
            Toast.makeText(this, "Standardkarte kannn nicht berarbeitet werden.",
                    Toast.LENGTH_LONG).show();
            return true;
        }

        // check which menu item was pressed
        switch(item.getItemId()){
            case R.id.item_edit:
                setResult(RESULT_CODE_EDIT_CARD);
                finish();
                return true;
            case R.id.item_delete:
                AlertDialog.Builder adb = new AlertDialog.Builder(this);

                adb.setTitle(R.string.delete_current_card);

                adb.setIcon(android.R.drawable.ic_dialog_alert);

                adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_CODE_DELETE_CARD);
                        finish();
                    } });
                adb.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // cancel
                    } });
                adb.show();
                return true;
            case R.id.item_upload:
                AlertDialog.Builder confirmUploadDialog = new AlertDialog.Builder(this);

                confirmUploadDialog.setTitle("Aktion bestätigen:");

                confirmUploadDialog.setMessage("Soll die Karte \""+currentCard.title+"\" wirklich hochgeladen werden?");

                confirmUploadDialog.setIcon(android.R.drawable.ic_dialog_alert);

                confirmUploadDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final MutableLiveData<String> result = mViewModel.uploadCard(currentCard);
                        result.observe(ShowSingleCardActivity.this, new Observer<String>() {
                            @Override
                            public void onChanged(@Nullable String strResult) {
                                String toastText;
                                if(strResult.equals("success")){
                                    toastText = "Hochladen war erfolgreich";
                                }else{
                                    toastText = "Hochladen fehlgeschlagen";
                                }
                                Toast.makeText(ShowSingleCardActivity.this, toastText, Toast.LENGTH_LONG).show();
                            }
                        });
                    } });
                confirmUploadDialog.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // cancel
                    } });
                confirmUploadDialog.show();
                //HttpJsonParser.setCard();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int getColor(String source){
        int color = 0;
        if(source.equals(ArrayResource.CARD_SOURCES[0])){
            color = getResources().getColor(R.color.blue);
        }else if(source.equals(ArrayResource.CARD_SOURCES[1])){
            color = getResources().getColor(R.color.cayn);
        }else if(source.equals(ArrayResource.CARD_SOURCES[2])){
            color = getResources().getColor(R.color.purple);
        }
        return color;
    }
}
