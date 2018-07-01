package com.movinghead333.kingsize.ui.game.choosedeckactivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.database.CardDeck;
import com.movinghead333.kingsize.ui.CustomListItemClickListener;

import java.util.List;

/**
 * Listadapter for displaying all the decks the user has created
 */
class ChooseDeckListAdapter extends RecyclerView.Adapter<ChooseDeckListAdapter.ViewHolder>{

    private List<CardDeck> decks;
    private CustomListItemClickListener listener;

    ChooseDeckListAdapter(CustomListItemClickListener listener){
        this.listener = listener;
    }

    void setDecks(List<CardDeck> decks){
        this.decks = decks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_deckname,
                parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, viewHolder.getLayoutPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if(decks != null){
            CardDeck currentDeck = decks.get(position);
            String deckName = currentDeck.deckName;
            viewHolder.deckNameTextView.setText(deckName);
        }
    }

    @Override
    public int getItemCount() {
        if(decks == null){
            return 0;
        }else{
            return decks.size();
        }
    }

    // ViewHolder class for adapter using the same listitem resource as SetupPlayersActivity's adapter
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView deckNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            this.deckNameTextView = itemView.findViewById(R.id.list_item_deckname_text_view);
        }
    }
}
