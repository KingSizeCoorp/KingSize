package com.movinghead333.kingsize.ui.mydecks.changercardactivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movinghead333.kingsize.R;

public class ShowCardListFragment extends Fragment{

    public ShowCardListFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_card_list, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.fragment_display_card_recycler_view);


        return view;
    }
}
