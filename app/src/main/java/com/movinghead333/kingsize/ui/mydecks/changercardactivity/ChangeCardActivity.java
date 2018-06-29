package com.movinghead333.kingsize.ui.mydecks.changercardactivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.database.Card;
import com.movinghead333.kingsize.ui.mydecks.showcardindeckactivity.ShowCardInDeckActivity;
import com.movinghead333.kingsize.ui.mydecks.showsingledeckactivity.ShowSingleDeckActivity;
import com.movinghead333.kingsize.utilities.InjectorUtils;

import java.util.ArrayList;
import java.util.List;

public class ChangeCardActivity extends AppCompatActivity {
    private static final String TAG = "ChangeCardActvityLog";

    static final String EXTRA_CARD_SOURCE = "EXTRA_CARD_SOURCE";

    ChangeCardViewModel mViewModel;

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    private long currentDeckId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_card);

        Intent intent = getIntent();
        currentDeckId = intent.getLongExtra(ShowSingleDeckActivity.STRING_EXTRA_CURRENT_DECK, -1);


        ChangeCardViewModelFactory factory = InjectorUtils.provideChangeCardViewModelFactory(
                this.getApplicationContext(), getApplication(), currentDeckId);
        mViewModel = ViewModelProviders.of(this, factory).get(ChangeCardViewModel.class);
        mViewModel.setCurrentSymbol(intent.getIntExtra(ShowCardInDeckActivity.EXTRA_STRING_SYMBOL, -1));

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // setup ViewPager
        mViewPager = (ViewPager)findViewById(R.id.change_card_view_pager);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // standard cards fragment
        ShowCardListFragment standardCardsFragment = new ShowCardListFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt(EXTRA_CARD_SOURCE, R.string.source_standard);
        standardCardsFragment.setArguments(bundle1);
        mSectionsPageAdapter.addFragment(standardCardsFragment, "Standard");

        // custom cards fragment
        ShowCardListFragment customCardsFragment = new ShowCardListFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt(EXTRA_CARD_SOURCE, R.string.source_my_cards);
        customCardsFragment.setArguments(bundle2);
        mSectionsPageAdapter.addFragment(customCardsFragment, "Eigene");

        // feed cards fragment
        ShowCardListFragment feedCardsFragment = new ShowCardListFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt(EXTRA_CARD_SOURCE, R.string.source_feed);
        feedCardsFragment.setArguments(bundle3);
        mSectionsPageAdapter.addFragment(feedCardsFragment, "Feed");

        viewPager.setAdapter(mSectionsPageAdapter);
    }
}
