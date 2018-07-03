package com.movinghead333.kingsize.ui.game.setupplayersactivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.ui.CustomListItemClickListener;

import java.util.List;

public class SetupPlayerListAdapter extends RecyclerView.Adapter<SetupPlayerListAdapter.ViewHolder> {

    private List<String> players;
    private CustomListItemClickListener listener;

    public SetupPlayerListAdapter(List<String> players){
        this.players = players;
    }

    void setButtonOnClickListener(CustomListItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public SetupPlayerListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_player_name, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);

        view.findViewById(R.id.list_item_player_name_remove_button)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getLayoutPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if(players != null){
            String pName = players.get(position);
            viewHolder.playerNameTextView.setText(pName);
        }
    }

    @Override
    public int getItemCount() {
        if(players == null){
            return 0;
        }else{
            return players.size();
        }
    }

    public void setPlayers(List<String> players){
        this.players = players;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView playerNameTextView;
        Button removeButton;

        public ViewHolder(View itemView) {
            super(itemView);

            playerNameTextView = (TextView)itemView.findViewById(R.id.list_item_player_name_text_view);
            removeButton = (Button)itemView.findViewById(R.id.list_item_player_name_remove_button);
        }
    }
}
