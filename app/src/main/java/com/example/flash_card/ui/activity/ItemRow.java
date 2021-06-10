package com.example.flash_card.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.flash_card.R;
import com.example.flash_card.database.CardDatabase;
import com.example.flash_card.database.dao.CardDao;
import com.example.flash_card.model.ItemCard;
import com.example.flash_card.ui.adapter.ItemAdapter;
import com.example.flash_card.ui.listener.ItemListener;

import java.util.ArrayList;
import java.util.List;

public class ItemRow extends AppCompatActivity implements FormItemFragment.ItemRefresh {
    private CardDao dao;
    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;
    private ItemListener itemListener;
    private List<ItemCard> itemCardList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste);

        recyclerView = findViewById(R.id.id_recycler_view_item);
        CardDatabase cardDatabase = CardDatabase.getInstance(this);
        dao = cardDatabase.getCardDatabase();
        getListener();

    }

    private void getList() {
        Intent intent = getIntent();
        int id = intent.getExtras().getInt("cardId");
        itemAdapter = new ItemAdapter(itemCardList, itemListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.loadItem(dao.listItemById(id));
    }
    private void getListener() {
        itemListener = new ItemListener() {
            @Override
            public void onDeleleteClick(ItemCard itemCard) {
                dao.deleteItem(itemCard);
                getList();
            }

            @Override
            public void onEditClick(ItemCard itemCard) {
                openCardEditMode(itemCard);
                // dao.update(itemCard);
            }
        };
    }

    private void openCardEditMode(ItemCard itemCard) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("itemCard", itemCard);
        FormItemFragment tdf = new FormItemFragment();
        tdf.setArguments(bundle);
        tdf.show(getSupportFragmentManager(), "fsdf");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList();
        getSupportActionBar().hide();
    }


    @Override
    public void itemRefresh() {
        onResume();
    }
}