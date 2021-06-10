package com.example.flash_card.ui.activity;

import android.view.View;

import com.example.flash_card.model.ItemCard;
import com.example.flash_card.ui.validator.StandardValidator;
import com.example.flash_card.ui.validator.ValidatorCard;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {


    public void addStandardValidator(TextInputLayout textInputLayout, List<ValidatorCard> validatorCardList) {
        StandardValidator standardValidator = new StandardValidator(textInputLayout);
        validatorCardList.add(standardValidator);

        textInputLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    standardValidator.isValid();
                }
            }
        });
    }


}
