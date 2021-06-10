package com.example.flash_card.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Card.class,
        parentColumns = "id",
        childColumns = "idCard",
        onDelete = CASCADE))
public class ItemCard implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String front;
    private String back;
    private boolean visible;
    private String date;
    private int idCard;

    public ItemCard() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean validateId() {
        return id > 0;
    }


}
