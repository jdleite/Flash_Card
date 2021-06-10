package com.example.flash_card.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flash_card.R;
import com.example.flash_card.model.Card;
import com.example.flash_card.ui.listener.CardListener;
import com.example.flash_card.ui.viewHolder.CardViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

    private final List<Card> cardList;
    private final CardListener listener;

    public CardAdapter(List<Card> cardList,CardListener listener) {
        this.cardList = cardList;
        this.listener = listener;

    }


    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_card_row,parent,false);
        return new CardViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        Card card = cardList.get(position);
        holder.bindData(card,listener);


    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public void loadCard(List<Card> cardList){
        this.cardList.clear();
        this.cardList.addAll(cardList);
        notifyDataSetChanged();
    }
}
