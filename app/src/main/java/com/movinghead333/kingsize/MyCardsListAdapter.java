package com.movinghead333.kingsize;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MyCardsListAdapter extends RecyclerView.Adapter<MyCardsListAdapter.ViewHolder>{

    private CustomListItemClickListener listener;
    private List<Card> cards;

    public MyCardsListAdapter(CustomListItemClickListener listener){
        this.listener = listener;
        Card[] exampleCards = new Card[20];
        for(int i = 0; i < exampleCards.length; i++){
            exampleCards[i] = new Card("Karte "+(i+1));
        }
        cards = Arrays.asList(exampleCards);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;

        public ViewHolder(TextView textView){
            super(textView);
            this.textView = textView;
        }
    }

    @Override
    public MyCardsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType){
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.show_my_cards_list_item, parent, false
        );

        final ViewHolder viewHolder = new ViewHolder(textView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getLayoutPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){
        if(cards != null){
            Card currentCard = cards.get(position);
            viewHolder.textView.setText(currentCard.title);
        }else{
            viewHolder.textView.setText("No cards");
        }
    }

    @Override
    public int getItemCount(){
        if(cards == null){
            return 0;
        }else{
            return cards.size();
        }
    }
}
