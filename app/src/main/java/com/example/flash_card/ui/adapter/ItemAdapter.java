package com.example.flash_card.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flash_card.R;
import com.example.flash_card.model.ItemCard;
import com.example.flash_card.ui.listener.CardListener;
import com.example.flash_card.ui.listener.ItemListener;
import com.example.flash_card.ui.viewHolder.ItemViewHolder;

import java.util.List;
import java.util.zip.Inflater;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private final List<ItemCard> itemCardList;
    private final ItemListener listener;


    public ItemAdapter(List<ItemCard> itemCardList,ItemListener listener){
        this.itemCardList = itemCardList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_item_row,parent,false);

        return new ItemViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemCard itemCard = itemCardList.get(position);
        holder.bindData(itemCard,listener);
    }

    @Override
    public int getItemCount() {
        return itemCardList.size();
    }

    public void loadItem(List<ItemCard> itemCardList){
        this.itemCardList.clear();
        this.itemCardList.addAll(itemCardList);
        notifyDataSetChanged();

    }
}
