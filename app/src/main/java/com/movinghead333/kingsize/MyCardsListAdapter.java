package com.movinghead333.kingsize;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyCardsListAdapter extends RecyclerView.Adapter<MyCardsListAdapter.ViewHolder>{

    private CustomListItemClickListener listener;
    private List<Card> cards;

    public MyCardsListAdapter(CustomListItemClickListener listener){
        this.listener = listener;
        /*
        Card[] exampleCards = new Card[20];
        for(int i = 0; i < exampleCards.length; i++){
            exampleCards[i] = new Card("Karte "+(i+1));
        }
        cards = Arrays.asList(exampleCards);
        */
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView cardTitle;
        public TextView cardType;
        public TextView cardSource;

        public ViewHolder(View itemView){
            super(itemView);
            this.cardTitle = itemView.findViewById(R.id.detailed_card_item_title);
            this.cardType = itemView.findViewById(R.id.detailed_card_item_type);
            this.cardSource = itemView.findViewById(R.id.detailed_card_item_source);
        }
    }

    @Override
    public MyCardsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_detailed_card_info, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
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
            viewHolder.cardTitle.setText(currentCard.title);
            viewHolder.cardType.setText(currentCard.type);
            viewHolder.cardSource.setText(currentCard.source);
        }else{
            viewHolder.cardTitle.setText("No cards");
            viewHolder.cardType.setText("...");
            viewHolder.cardSource.setText("...");
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

    public void setCards(List<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }
}
