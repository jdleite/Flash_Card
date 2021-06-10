package com.example.flash_card.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Card implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public Card() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean validateId(){
        return id > 0;
    }
}
