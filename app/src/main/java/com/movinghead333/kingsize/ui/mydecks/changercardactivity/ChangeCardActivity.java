package com.movinghead333.kingsize.ui.mydecks.changercardactivity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.utilities.InjectorUtils;

public class ChangeCardActivity extends AppCompatActivity {

    protected ChangeCardViewModel mViewModel;

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_card);

        ChangeCardViewModelFactory factory = InjectorUtils.proviChangeCardViewModelFactory(this.getApplicationContext());
        mViewModel = ViewModelProviders.of(this, factory).get(ChangeCardViewModel.class);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // setup ViewPager
        mViewPager = (ViewPager)findViewById(R.id.change_card_view_pager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new ShowCardListFragment(), "Standard");

        adapter.addFragment(new ShowCardListFragment(), "Eigene");

        adapter.addFragment(new ShowCardListFragment(), "Web");

        viewPager.setAdapter(adapter);
    }
}
