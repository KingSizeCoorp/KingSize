package com.movinghead333.kingsize;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowSingleCardActivity extends AppCompatActivity {

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
}
