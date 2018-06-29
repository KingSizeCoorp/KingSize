package com.movinghead333.kingsize.ui.mydecks.changercardactivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.database.Card;
import com.movinghead333.kingsize.data.database.CardInCardDeckRelation;
import com.movinghead333.kingsize.ui.CustomListItemClickListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ShowCardListFragment extends Fragment{
    private static final String TAG = "ChangeCardActivityLog";

    private List<Card> fragmentCards;

    FragmentListAdapter fragmentListAdapter;

    public ShowCardListFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ChangeCardViewModel mViewModel = ViewModelProviders.of(getActivity()).get(ChangeCardViewModel.class);

        // an item in one of three recyclerviews is clicked
        fragmentListAdapter = new FragmentListAdapter(new CustomListItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                Toast.makeText(getContext(), "it worked: ", Toast.LENGTH_SHORT).show();

                // the cardrelations of the currentdeck are being observed so they are loaded in when checked
                mViewModel.cardRelations.observe(getActivity(), new Observer<List<CardInCardDeckRelation>>() {
                    @Override
                    public void onChanged(@Nullable List<CardInCardDeckRelation> cardInCardDeckRelations) {
                        // get card id from the picked card
                        final long pickedCardId = fragmentCards.get(position).id;

                        // check if the picked card is a new card (not in the deck already)
                        if(mViewModel.checkIfNewCardSelected(pickedCardId, cardInCardDeckRelations)){
                            Log.d(TAG, "new card selected");

                            // alertdialog for the double-check if the user really intended the action
                            AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());

                            // todo fix string res
                            adb.setTitle(R.string.delete_current_card);

                            adb.setIcon(android.R.drawable.ic_dialog_alert);

                            adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    mViewModel.replaceCardInDeck(pickedCardId);
                                    getActivity().finish();
                                } });
                            adb.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                } });
                            adb.show();
                        }else{
                            Log.d(TAG, "card already in deck");
                            // alertdialog for the double-check if the user really intended the action
                            AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());

                            // todo fix string res
                            adb.setTitle(R.string.delete_current_card);

                            adb.setIcon(android.R.drawable.ic_dialog_alert);

                            adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    mViewModel.swapCardsInDeck();
                                    getActivity().finish();
                                } });
                            adb.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                } });
                            adb.show();
                        }
                    }
                });
            }
        });

        int source = getArguments().getInt(ChangeCardActivity.EXTRA_CARD_SOURCE);
        mViewModel.getCardSetBySource(source).observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                fragmentCards = cards;
                fragmentListAdapter.setCards(cards);
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_card_list, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.fragment_display_card_recycler_view);


        recyclerView.setAdapter(fragmentListAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }




    /*
        ListAdapter for RecyclerView
     */
    private class FragmentListAdapter extends RecyclerView.Adapter<FragmentListAdapter.ViewHolder>{

        private CustomListItemClickListener listener;
        private List<Card> cards;
        private long cardToBeExchanged = -1;

        void setCardToBeExchanged(long id){
            cardToBeExchanged = id;
        }

        FragmentListAdapter(CustomListItemClickListener listener){
            this.listener = listener;
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView cardName;
            TextView cardType;
            TextView cardSource;
            TextView cardDescription;

            public ViewHolder(View itemView) {
                super(itemView);
                this.cardName = itemView.findViewById(R.id.cwd_card_name);
                this.cardType = itemView.findViewById(R.id.cwd_card_Type);
                this.cardSource = itemView.findViewById(R.id.cwd_card_source);
                this.cardDescription = itemView.findViewById(R.id.cwd_card_description);
            }
        }

        @NonNull
        @Override
        public FragmentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View listItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.list_item_card_with_description, parent, false);

            final ViewHolder viewHolder = new ViewHolder(listItemLayoutView);
            listItemLayoutView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    listener.onItemClick(v, viewHolder.getLayoutPosition());
                }
            });

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            if(cards != null){
                Card currentCard = cards.get(position);
                if(currentCard.id != cardToBeExchanged){
                    viewHolder.cardName.setText(currentCard.title);
                    viewHolder.cardType.setText(currentCard.type);
                    viewHolder.cardSource.setText(currentCard.source);
                    viewHolder.cardDescription.setText(currentCard.description);
                }
            }
        }

        @Override
        public int getItemCount() {
            if(cards == null){
                return 0;
            }else{
                return cards.size();
            }
        }

        public void setCards(List<Card> cards){
            this.cards = cards;
            notifyDataSetChanged();
        }
    }

    public void setFragmentCards(List<Card> cards){
        this.fragmentCards = cards;
        if(fragmentListAdapter != null)
        fragmentListAdapter.setCards(cards);
    }
}
