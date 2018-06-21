package com.movinghead333.kingsize.ui.myfeed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.movinghead333.kingsize.R;
import java.util.List;

import static com.movinghead333.kingsize.R.layout.empoyee_row;


/**
 * Created by Abhi on 03 Sep 2017 008.
 */

public class CardAdapter extends ArrayAdapter<CardDetails> {
    private static final String KEY_ID = "Id: ";
    private static final String KEY_TYPE = "Type: ";
    private static final String KEY_TITLE= "Title: ";
    private static final String KEY_DESCRIPTION = "Description: ";
    private static final String KEY_POSITIVE_VOTES = "Pos. Votes: ";
    private static final String KEY_NEGATIVE_VOTES = "Neg. Votes: ";
    private List<CardDetails> dataSet;

    public CardAdapter(List<CardDetails> dataSet, Context mContext) {
        super(mContext , R.layout.empoyee_row, dataSet);
        this.dataSet = dataSet;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.empoyee_row, null);
        }
        CardDetails carddetails = dataSet.get(position);
        if (carddetails != null) {
            //Text View references
            TextView employeeId = (TextView) v.findViewById(R.id.employeeId);
            TextView name = (TextView) v.findViewById(R.id.name);
            TextView dob = (TextView) v.findViewById(R.id.dob);
            TextView designation = (TextView) v.findViewById(R.id.designation);
            TextView contactNumber = (TextView) v.findViewById(R.id.contact_number);
            TextView email = (TextView) v.findViewById(R.id.email);


            //Updating the text views
            employeeId.setText(KEY_ID + carddetails.getId());
            name.setText(KEY_TYPE + carddetails.getType());
            dob.setText(KEY_TITLE + carddetails.getTitle());
            designation.setText(KEY_DESCRIPTION + carddetails.getDescription());
            contactNumber.setText(KEY_POSITIVE_VOTES + carddetails.getPositive_votes());
            email.setText(KEY_NEGATIVE_VOTES + carddetails.getNegative_votes());
        }

        return v;
    }
}