package com.movinghead333.kingsize.ui.game.showtokensactivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.datawrappers.PlayerWithAttribute;

import java.util.ArrayList;

public class ShowTokensListAdapter extends RecyclerView.Adapter<ShowTokensListAdapter.ViewHolder>{

    private ArrayList<PlayerWithAttribute> playersWithTokens;

    ShowTokensListAdapter(ArrayList<PlayerWithAttribute> playersWithTokens){
        this.playersWithTokens  = playersWithTokens;
    }

    @NonNull
    @Override
    public ShowTokensListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_token_item, parent, false);

        return new ShowTokensListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowTokensListAdapter.ViewHolder viewHolder, int position) {
        if(playersWithTokens != null){
            PlayerWithAttribute current = playersWithTokens.get(position);
            viewHolder.textViewTokenName.setText(current.getAttribute());
            viewHolder.textViewPlayerName.setText(current.getPlayerName());
        }
    }

    @Override
    public int getItemCount() {
        if(playersWithTokens == null){
            return 0;
        }else{
            return playersWithTokens.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewPlayerName;
        TextView textViewTokenName;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewPlayerName = (TextView)itemView.findViewById(R.id.list_item_token_item_playername);
            textViewTokenName = (TextView)itemView.findViewById(R.id.list_item_token_item_tokentyp);
        }
    }
}
