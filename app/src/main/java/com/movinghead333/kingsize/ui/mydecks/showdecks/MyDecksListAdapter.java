package com.movinghead333.kingsize.ui.mydecks.showdecks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.database.CardDeck;
import com.movinghead333.kingsize.ui.CustomListItemClickListener;

import java.util.List;

public class MyDecksListAdapter extends RecyclerView.Adapter<MyDecksListAdapter.ViewHolder>{

    private CustomListItemClickListener listener;
    private List<CardDeck> cardDecks;

    public MyDecksListAdapter(CustomListItemClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView deckName;
        public TextView cardCount;

        public ViewHolder(View itemView){
            super(itemView);
            this.deckName = itemView.findViewById(R.id.card_deck_list_item_deckname);
            this.cardCount = itemView.findViewById(R.id.card_deck_list_item_cardcount);
        }
    }

    @Override
    public MyDecksListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_deck_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){
        if(cardDecks != null) {
            CardDeck currentDeck = cardDecks.get(position);
            viewHolder.deckName.setText(currentDeck.deckName);
            viewHolder.cardCount.setText(String.valueOf(currentDeck.cardCount));
        }

    }

    @Override
    public int getItemCount(){
        if(cardDecks == null){
            return 0;
        }else{
            return cardDecks.size();
        }
    }

    public void setCardDecks(List<CardDeck> cardDecks){
        this.cardDecks = cardDecks;
        notifyDataSetChanged();
    }
}