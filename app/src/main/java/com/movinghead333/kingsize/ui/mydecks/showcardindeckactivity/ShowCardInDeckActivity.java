package com.movinghead333.kingsize.ui.mydecks.showcardindeckactivity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.utilities.InjectorUtils;

public class ShowCardInDeckActivity extends AppCompatActivity {

    ShowCardInDeckViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card_in_deck);

        ShowCardInDeckViewModelFactory factory = InjectorUtils
                .providShowCardInDeckViewModelFactory(this.getApplicationContext());
        mViewModel = ViewModelProviders.of(this, factory).get(ShowCardInDeckViewModel.class);
    }
}
