package com.movinghead333.kingsize.ui.myfeed;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.database.FeedEntry;
import com.movinghead333.kingsize.ui.CustomListItemClickListener;
import com.movinghead333.kingsize.ui.UpAndDownVotesListener;

import java.util.ArrayList;
import java.util.List;

public class ShowFeedListAdapter extends RecyclerView.Adapter<ShowFeedListAdapter.ViewHolder>{

    private CustomListItemClickListener listItemListener;
    private UpAndDownVotesListener votesListener;

    private List<FeedEntry> feedEntriesList;

    public ShowFeedListAdapter(CustomListItemClickListener listItemListener,
                               UpAndDownVotesListener votesListener){
        this.listItemListener = listItemListener;
        this.votesListener = votesListener;

        /*
        // test data also comment observer in activity to test
        feedEntriesList = new ArrayList<FeedEntry>();
        feedEntriesList.add(new FeedEntry(1,"online Karte", "Aktionskarte",
                "1 naise beschraibun",23,3));
        feedEntriesList.add(new FeedEntry(2,"Wer hat das odd weggemockt", "Tokenkarte",
                "2 naise beschraibun",50,42));
        feedEntriesList.add(new FeedEntry(3,"partey hard", "Aktionskarte",
                "3 naise beschraibun",5,2));
        notifyDataSetChanged();
        */
    }

    void updateList(List<FeedEntry> newEntries){
        feedEntriesList = newEntries;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate list-item-layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_feed_card, parent, false);

        // create ViewHolder instance
        final ViewHolder viewHolder = new ViewHolder(view);

        // add listeners
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemListener.onItemClick(v, viewHolder.getLayoutPosition());
            }
        });

        viewHolder.cardUpvotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                votesListener.onUpVote(viewHolder.getLayoutPosition());
            }
        });

        viewHolder.cardDownvotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                votesListener.onDownVote(viewHolder.getLayoutPosition());
            }
        });

        // return created ViewHolder
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if(feedEntriesList != null){
            FeedEntry currentEntry = feedEntriesList.get(position);
            viewHolder.cardName.setText(currentEntry.getCardName());
            viewHolder.cardType.setText(currentEntry.getType());
            viewHolder.cardDescription.setText(currentEntry.getDescription());
            String voteDifference = String.valueOf(currentEntry.getUpvotes()-currentEntry.getDownvotes());
            viewHolder.cardVotes.setText(voteDifference);
        }
    }

    @Override
    public int getItemCount() {
        if(feedEntriesList == null){
            return 0;
        }else{
            return feedEntriesList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView cardName;
        TextView cardType;
        TextView cardDescription;
        TextView cardVotes;
        ImageView cardUpvotes;
        ImageView cardDownvotes;

        public ViewHolder(View itemView){
            super(itemView);

            cardName = (TextView)itemView.findViewById(R.id.li_fc_cardname);
            cardType = (TextView)itemView.findViewById(R.id.li_fc_cardtype);
            cardDescription = (TextView)itemView.findViewById(R.id.li_fc_description);
            cardVotes = (TextView)itemView.findViewById(R.id.li_fc_votes);
            cardUpvotes = (ImageView)itemView.findViewById(R.id.li_dc_upvote);
            cardDownvotes = (ImageView)itemView.findViewById(R.id.li_fc_downvotes);
        }
    }
}
