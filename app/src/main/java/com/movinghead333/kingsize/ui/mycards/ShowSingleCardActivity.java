package com.movinghead333.kingsize.ui.mycards;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.ui.mycards.showmycards.ShowMyCardsActivity;

public class ShowSingleCardActivity extends AppCompatActivity {

    public static final int RESULT_CODE_EDIT_CARD = 1;
    public static final int RESULT_CODE_DELETE_CARD = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_card);

        Intent startIntent = getIntent();

        TextView title = (TextView)findViewById(R.id.ssc_dynamic_card_title);
        title.setText(startIntent.getStringExtra(ShowMyCardsActivity.EXTRA_TITLE));
        TextView type = (TextView)findViewById(R.id.ssc_dynamic_card_type);
        type.setText(startIntent.getStringExtra(ShowMyCardsActivity.EXTRA_TYPE));
        TextView description = (TextView)findViewById(R.id.ssc_dynamic_card_description);
        description.setText(startIntent.getStringExtra(ShowMyCardsActivity.EXTRA_DESCRIPTION));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_card, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
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
                    } });
                adb.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
