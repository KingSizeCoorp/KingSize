package com.movinghead333.kingsize.ui.mydecks.changercardactivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.database.Card;
import com.movinghead333.kingsize.utilities.InjectorUtils;

import java.util.ArrayList;
import java.util.List;

public class ChangeCardActivity extends AppCompatActivity {

    static final String EXTRA_CARD_SOURCE = "EXTRA_CARD_SOURCE";

    ChangeCardViewModel mViewModel;

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_card);

        ChangeCardViewModelFactory factory = InjectorUtils.provideChangeCardViewModelFactory(
                this.getApplicationContext(), getApplication());
        mViewModel = ViewModelProviders.of(this, factory).get(ChangeCardViewModel.class);

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
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_CARD_SOURCE, R.string.source_standard);
        standardCardsFragment.setArguments(bundle);
        mSectionsPageAdapter.addFragment(standardCardsFragment, "Standard");

        // custom cards fragment
        ShowCardListFragment customCardsFragment = new ShowCardListFragment();
        bundle = new Bundle();
        bundle.putInt(EXTRA_CARD_SOURCE, R.string.source_my_cards);
        customCardsFragment.setArguments(bundle);
        mSectionsPageAdapter.addFragment(customCardsFragment, "Eigene");

        // feed cards fragment
        ShowCardListFragment feedCardsFragment = new ShowCardListFragment();
        bundle = new Bundle();
        bundle.putInt(EXTRA_CARD_SOURCE, R.string.source_feed);
        customCardsFragment.setArguments(bundle);
        mSectionsPageAdapter.addFragment(feedCardsFragment, "Feed");

        viewPager.setAdapter(mSectionsPageAdapter);
    }
}
