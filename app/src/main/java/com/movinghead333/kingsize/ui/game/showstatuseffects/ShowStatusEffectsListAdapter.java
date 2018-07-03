package com.movinghead333.kingsize.ui.game.showstatuseffects;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.datawrappers.PlayerWithAttribute;

import java.util.ArrayList;


public class ShowStatusEffectsListAdapter
        extends RecyclerView.Adapter<ShowStatusEffectsListAdapter.ViewHolder>{

    private ArrayList<PlayerWithAttribute> statusEffects;

    ShowStatusEffectsListAdapter(ArrayList<PlayerWithAttribute> statusEffects){
        this.statusEffects  = statusEffects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_status_effect, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if(statusEffects != null){
            PlayerWithAttribute current = statusEffects.get(position);
            viewHolder.textViewStatusEffect.setText(current.getAttribute());
            viewHolder.textViewPlayerName.setText(current.getPlayerName());
        }
    }

    @Override
    public int getItemCount() {
        if(statusEffects == null){
            return 0;
        }else{
            return statusEffects.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewStatusEffect;
        TextView textViewPlayerName;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewStatusEffect = (TextView)itemView.findViewById(R.id.list_item_status_effect_statusname);
            textViewPlayerName  = (TextView)itemView.findViewById(R.id.list_item_status_effect_playername);
        }
    }
}
