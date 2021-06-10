package com.example.flash_card.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.flash_card.database.dao.CardDao;
import com.example.flash_card.model.Card;
import com.example.flash_card.model.ItemCard;

@Database(entities = {Card.class, ItemCard.class},version = 5,exportSchema = false)
public abstract class CardDatabase extends RoomDatabase {
    private static final String DATABASE = "card";

    public abstract CardDao getCardDatabase();

    public static CardDatabase getInstance(Context context) {

        return Room.databaseBuilder(context,CardDatabase.class,DATABASE)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

    }


}
