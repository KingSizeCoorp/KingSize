package com.movinghead333.kingsize.ui.game.showtokensactivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.datawrappers.PlayerWithAttribute;
import com.movinghead333.kingsize.ui.CustomListItemClickListener;

import java.util.ArrayList;

public class ShowTokensListAdapter extends RecyclerView.Adapter<ShowTokensListAdapter.ViewHolder>{

    private ArrayList<PlayerWithAttribute> playersWithTokens;
    private CustomListItemClickListener listener;

    ShowTokensListAdapter(ArrayList<PlayerWithAttribute> playersWithTokens,
                          CustomListItemClickListener listener){
        this.playersWithTokens  = playersWithTokens;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShowTokensListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_token_item, parent, false);

        final ViewHolder viewHolder = new ShowTokensListAdapter.ViewHolder(view);

        view.findViewById(R.id.list_item_token_item_button_remove)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(v, viewHolder.getLayoutPosition());
                    }
                });

        return viewHolder;
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
