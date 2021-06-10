package com.example.flash_card.ui.viewHolder;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flash_card.R;
import com.example.flash_card.model.ItemCard;
import com.example.flash_card.ui.listener.CardListener;
import com.example.flash_card.ui.listener.ItemListener;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    TextView txtItemFront,txtItemBack;
    ImageView imgDelete;

    public ItemViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        txtItemFront = itemView.findViewById(R.id.id_txt_item_front);
        txtItemBack = itemView.findViewById(R.id.id_txt_item_back);
        imgDelete = itemView.findViewById(R.id.id_img_delete);

    }

    public void bindData(ItemCard itemCard, ItemListener itemListener){
        txtItemFront.setText(itemCard.getFront());
        txtItemBack.setText(itemCard.getBack());


        txtItemFront.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                itemListener.onEditClick(itemCard);
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.onDeleleteClick(itemCard);
            }
        });




    }
}
