package com.example.flash_card.ui.viewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flash_card.R;
import com.example.flash_card.model.Card;
import com.example.flash_card.ui.listener.CardListener;

public class CardViewHolder extends RecyclerView.ViewHolder {

    private final Context context;
    TextView txtNameCard;
    private CardView cardView;

    public CardViewHolder(@NonNull View itemView, Context context) {
        super(itemView);

        txtNameCard = itemView.findViewById(R.id.id_txt_card_name);
        cardView = itemView.findViewById(R.id.id_cardView);
        this.context = context;
    }

    public void bindData(Card card, CardListener cardListener) {
        txtNameCard.setText(card.getName());

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                cardListener.onPassCard(card);
                return false;
            }
        });

        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                cardListener.showItemCard(card.getId());

            }
        });


    }


}
