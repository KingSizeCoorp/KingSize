package com.movinghead333.kingsize.ui.mydecks.showsingledeckactivity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.utilities.InjectorUtils;

public class ShowSingleDeckActivity extends AppCompatActivity {

    private ShowSingleDeckViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_deck);

        // ViewModel initialisation
        ShowSingleDeckViewModelFactory factory =
                InjectorUtils.provideShowSingleDeckViewModelFactory(this.getApplicationContext());
        mViewModel = ViewModelProviders.of(this, factory).get(ShowSingleDeckViewModel.class);

        // setup recyclerView
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.show_single_Deck_recycler_view);





    }
}
