package com.movinghead333.kingsize;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class AddOrEditCardActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private Spinner typeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_card);

        titleEditText = findViewById(R.id.aoec_et_card_title);
        descriptionEditText = findViewById(R.id.aoec_et_card_description);
        typeSpinner = findViewById(R.id.aoec_type_spinner);

        List<String> entries = new ArrayList<String>();
        entries.add(getResources().getString(R.string.card_type_simple_action));
        entries.add(getResources().getString(R.string.card_type_status));
        entries.add(getResources().getString(R.string.card_type_token));


        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, entries);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeSpinner.setAdapter(spinnerAdapter);

        Intent startIntent = getIntent();
        boolean isEdit = startIntent.getBooleanExtra(ShowMyCardsActivity.EXTRA_IS_EDIT, false);
        if(isEdit){
            String title = startIntent.getStringExtra(ShowMyCardsActivity.EXTRA_TITLE);
            titleEditText.setText(title);
            String description = startIntent.getStringExtra(ShowMyCardsActivity.EXTRA_DESCRIPTION);
            descriptionEditText.setText(description);

            String type = startIntent.getStringExtra(ShowMyCardsActivity.EXTRA_TYPE);
            int spinnerPosition = spinnerAdapter.getPosition(type);
            typeSpinner.setSelection(spinnerPosition);

            Button saveButton = findViewById(R.id.aoec_btn_save);
            saveButton.setText("Änderungen speichern");
        }
    }

    public void saveEntry(View view){
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String type = typeSpinner.getSelectedItem().toString();

        Intent returnIntent = new Intent();
        returnIntent.putExtra(ShowMyCardsActivity.EXTRA_TITLE, title);
        returnIntent.putExtra(ShowMyCardsActivity.EXTRA_TYPE, type);
        returnIntent.putExtra(ShowMyCardsActivity.EXTRA_DESCRIPTION, description);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
