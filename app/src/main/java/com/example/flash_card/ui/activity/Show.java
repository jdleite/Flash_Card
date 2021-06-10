package com.example.flash_card.ui.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flash_card.R;
import com.example.flash_card.database.CardDatabase;
import com.example.flash_card.database.dao.CardDao;
import com.example.flash_card.model.ItemCard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Show extends AppCompatActivity {

    private StartFields startFields = new StartFields();
    private List<ItemCard> itemCardList = new ArrayList<>();
    CardDao dao;
    int i = 0;
    private ItemCard item;
    Date currDate = Calendar.getInstance().getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    String dt = sdf.format(currDate);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_show);

        CardDatabase cardDatabase = CardDatabase.getInstance(this);
        dao = cardDatabase.getCardDatabase();

        getListItemById();
        hideItem();
        showBackCard();
        showQuestion();

    }

    private void getListItemById() {
        Intent i = getIntent();
        Bundle b = i.getExtras();
        int id = b.getInt("cardId");
        itemCardList.addAll(dao.listItemVisible(id));
    }

    public void getMethods() {
        btnOneDayField();
        btnThreeDaysField();
        btnOneWeekField();
        btnShowAnswer();
        btnOneDayOption();
        btnThreeDaysOptions();
        btnOneWeekOptions();
        frontField();
        backField();
    }

    public void hideItem() {
        getMethods();
        startFields.txtBack.setVisibility(View.INVISIBLE);
        startFields.btnOneDay.setVisibility(View.INVISIBLE);
        startFields.btnThreeDays.setVisibility(View.INVISIBLE);
        startFields.btnOneWeek.setVisibility(View.INVISIBLE);
    }

    public void showItem() {
        getMethods();
        startFields.txtFront.setVisibility(View.VISIBLE);
        startFields.txtBack.setVisibility(View.VISIBLE);
        startFields.btnOneDay.setVisibility(View.VISIBLE);
        startFields.btnThreeDays.setVisibility(View.VISIBLE);
        startFields.btnOneWeek.setVisibility(View.VISIBLE);
        startFields.btnShowAnswer.setVisibility(View.INVISIBLE);
    }

    private class StartFields {
        TextView txtFront, txtBack;
        Button btnShowAnswer, btnOneDay, btnThreeDays, btnOneWeek;
    }

    private void frontField() {
        startFields.txtFront = findViewById(R.id.txt_front_show);
    }

    private void backField() {
        startFields.txtBack = findViewById(R.id.txt_back_show);
    }

    private void btnOneDayField() {
        startFields.btnOneDay = findViewById(R.id.id_btn_one_day);
    }

    private void btnShowAnswer() {
        startFields.btnShowAnswer = findViewById(R.id.id_btn_show_answer);
    }

    private void btnThreeDaysField() {
        startFields.btnThreeDays = findViewById(R.id.id_btn_three_days);
    }

    private void btnOneWeekField() {
        startFields.btnOneWeek = findViewById(R.id.id_btn_one_week);
    }

    private void showBackCard() {

        startFields.btnShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getItemCardList();

                showItem();

            }
        });
    }

    private void getItemCardList() {


        if (i < itemCardList.size()) {
            item = itemCardList.get(i);
            startFields.txtFront.setText(item.getFront());
            startFields.txtBack.setText(item.getBack());
            i++;
        } else {
            startActivity(new Intent(Show.this, MainActivity.class));
        }


    }

    private void showQuestion() {

        if (itemCardList.size() == 0) {
            Toast.makeText(this, "Nada para memorizar hoje ", Toast.LENGTH_SHORT).show();
            validateFinishList();
        } else {
            item = itemCardList.get(i);
            startFields.txtFront.setText(item.getFront());
            startFields.btnShowAnswer.setVisibility(View.VISIBLE);
        }


    }

    private void btnOneDayOption() {

        startFields.btnOneDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateRemember(1);
                confBtnDay();

                validateFinishList();

            }
        });
    }

    private void btnThreeDaysOptions() {
        startFields.btnThreeDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateRemember(3);
                confBtnDay();
                validateFinishList();
            }
        });

    }

    private void btnOneWeekOptions() {
        startFields.btnOneWeek.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dateRemember(7);
                confBtnDay();
                validateFinishList();
            }
        });
    }

    private void confBtnDay() {
        item.setVisible(false);
        item.setDate(dt);
        dao.update(item);
    }

    private void validateFinishList() {
        if (i >= itemCardList.size()) {
            startActivity(new Intent(Show.this, MainActivity.class));
        } else {
            showQuestion();
            hideItem();

        }
    }

    private void dateRemember(int day) {
        Calendar cld = Calendar.getInstance();
        cld.add(Calendar.DATE, day);
        dt = sdf.format(cld.getTime());
        item.setDate(dt);
        dao.update(item);
    }


}