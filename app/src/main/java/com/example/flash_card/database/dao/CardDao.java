package com.example.flash_card.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.flash_card.model.Card;
import com.example.flash_card.model.ItemCard;

import java.util.List;

@Dao
public interface CardDao {
    @Insert
    Long save(Card card);

    @Update
    void update(Card card);

    @Delete
    void delete(Card card);

    @Query("SELECT * FROM Card ORDER BY ID DESC")
    List<Card> listAll();

    @Insert
    Long saveItemCard(ItemCard itemCard);

    @Query("SELECT * FROM ItemCard ORDER BY ID DESC")
    List<ItemCard> listAllItem();

    @Query("DELETE FROM itemCard")
    void removeAll();

    @Delete
    void deleteItem(ItemCard itemCard);

    @Update
    void update(ItemCard itemCard);

    @Query("SELECT * FROM ItemCard WHERE idCard  = :id")
    List<ItemCard> listItemById(int id);

    @Query("SELECT * FROM ItemCard WHERE visible = 1 and idCard = :id")
    List<ItemCard> listItemVisible(int id);
   


}
