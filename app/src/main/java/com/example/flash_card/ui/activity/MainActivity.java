package com.example.flash_card.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.flash_card.R;
import com.example.flash_card.database.CardDatabase;
import com.example.flash_card.database.dao.CardDao;
import com.example.flash_card.model.Card;
import com.example.flash_card.model.ItemCard;
import com.example.flash_card.ui.adapter.CardAdapter;
import com.example.flash_card.ui.listener.CardListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.View;

import android.view.MenuItem;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TextDialogFragment.RefreshList, FormItemFragment.ItemRefresh {
    private CardAdapter adapter;
    private List<Card> cardList = new ArrayList<>();
    private List<ItemCard> itemCardList = new ArrayList<>();
    private CardListener listener;
    private Card mCard;
    private CardDao dao;
    private RecyclerView recyclerView;
    FloatingActionButton fab;
    Bundle bundle = new Bundle();
    Date currDate = Calendar.getInstance().getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    String dt = sdf.format(currDate);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.id_recycler_view);
        CardDatabase database = CardDatabase.getInstance(this);
        dao = database.getCardDatabase();





        fab = findViewById(R.id.fab);

        fab();
        getListener();
    }


    private void fab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFormFragment();
            }
        });

        registerForContextMenu(fab);
    }

    private void getListener() {
        listener = new CardListener() {
            @Override
            public void onPassCard(Card card) {
                registerForContextMenu(recyclerView);
                mCard = card;
            }

            @Override
            public void showItemCard(int id) {
                getDateRemember();
                if (itemCardList.size() == 0) {

                    Toast.makeText(MainActivity.this, "Sua lista está vazia", Toast.LENGTH_SHORT).show();
                } else {
                    bundle.putInt("cardId", id);
                    Intent i = new Intent(MainActivity.this,Show.class);
                    i.putExtras(bundle);
                    startActivity(i);
                }


            }


        };
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(R.string.opcao);
        getMenuInflater().inflate(R.menu.menu_options, menu);


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_1:
                dao.delete(mCard);
                onResume();
                return true;
            case R.id.option_2:
                openCardEditMode(mCard);
                return true;
            case R.id.option_3:
                bundle.putInt("itemCardId", mCard.getId());
                FormItemFragment formItemFragment = new FormItemFragment();
                formItemFragment.setArguments(bundle);
                formItemFragment.show(getSupportFragmentManager(), "itemCardId");
                return true;
            case R.id.option_4:
                Intent intent = new Intent(MainActivity.this, ItemRow.class);
                intent.putExtra("cardId", mCard.getId());
                startActivity(intent);
                fab.hide();
                return true;
            default:
                return super.onContextItemSelected(item);

        }
    }


    private void getFormFragment() {
        TextDialogFragment textDialogFragment = new TextDialogFragment();
        textDialogFragment.show(getSupportFragmentManager(), "dialog");
    }

    private void getList() {
        adapter = new CardAdapter(cardList, listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.loadCard(dao.listAll());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList();
        fab.show();
        getSupportActionBar().show();
    }

    @Override
    public void refresh() {
        onResume();
    }

    public void openCardEditMode(Card card) {
        bundle.putSerializable("card", card);
        TextDialogFragment tdf = new TextDialogFragment();
        tdf.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().commit();
        tdf.show(getSupportFragmentManager(), "card");
    }


    @Override
    public void itemRefresh() {
        getDateRemember();

    }

    private void getDateRemember() {
        itemCardList.addAll(dao.listAllItem());
        for (ItemCard item : dao.listAllItem()) {
            Date a = null;
            Date b = null;
            try {
                a = sdf.parse(item.getDate());
                b = sdf.parse(dt);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (item.getDate().equalsIgnoreCase(dt) || b.after(a)) {
                item.setVisible(true);
                dao.update(item);
            }
        }
    }
}