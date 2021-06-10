package com.example.flash_card.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.example.flash_card.R;
import com.example.flash_card.database.CardDatabase;
import com.example.flash_card.database.dao.CardDao;
import com.example.flash_card.model.ItemCard;
import com.example.flash_card.ui.validator.StandardValidator;
import com.example.flash_card.ui.validator.ValidatorCard;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FormItemFragment extends DialogFragment {
    private View view;
    private Context context;
    private CardDao dao;
    private StartFields startFields = new StartFields();
    private ItemCard itemCard;
    private ItemRefresh itemRefresh;
    private List<ValidatorCard> validatorCardList = new ArrayList<>();
    private Utils utils = new Utils();
    Date currDate = Calendar.getInstance().getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    String dt = sdf.format(currDate);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_form_item_card, container, false);
        context = getActivity().getApplicationContext();

        context = getActivity().getApplicationContext();
        CardDatabase cardDatabase = CardDatabase.getInstance(context);
        dao = cardDatabase.getCardDatabase();


        getMethods();
        return view;

    }

    private void getMethods() {
        frontField();
        backField();
        loadItem();
        btnSave();
        btnCancel();
    }

    private void frontField() {
        startFields.edtFront = view.findViewById(R.id.id_edt_name_front);
        addStandardValidator(startFields.edtFront);
    }

    private void backField() {
        startFields.edtBack = view.findViewById(R.id.id_edt_name_back);
        addStandardValidator(startFields.edtBack);
    }

    private void setFrontItemCard() {
        String front = startFields.edtFront.getEditText().getText().toString();
        itemCard.setFront(front);
        itemCard.setVisible(true);
    }

    private void setBackItemCard() {
        String back = startFields.edtBack.getEditText().getText().toString();
        itemCard.setBack(back);
    }

    private void setItemCard() {
        String front = startFields.edtFront.getEditText().getText().toString();
        String back = startFields.edtFront.getEditText().getText().toString();
        itemCard.setFront(front);
        itemCard.setFront(back);

    }


    private class StartFields {
        TextInputLayout edtFront, edtBack;
        Button btnSave, btnCancel;

    }

    private void btnSave() {
        startFields.btnSave = view.findViewById(R.id.id_btn_save_item);

        startFields.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidForm = validField();
                if (isValidForm) {
                    setFrontItemCard();
                    setBackItemCard();
                    finishForm();
                    itemRefresh.itemRefresh();
                    dismiss();
                }

            }
        });
    }

    private void btnCancel() {
        startFields.btnCancel = view.findViewById(R.id.id_btn_cancel_item);

        startFields.btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void loadItem() {
        Bundle b = this.getArguments();

        if (b.containsKey("itemCard")) {
            itemCard = (ItemCard) getArguments().getSerializable("itemCard");
            fillFields();
        } else {
            itemCard = new ItemCard();
            int id = b.getInt("itemCardId");
            itemCard.setIdCard(id);
            itemCard.setDate(dt);
        }

    }

    private void fillFields() {
        startFields.edtFront.getEditText().setText(itemCard.getFront());
        startFields.edtBack.getEditText().setText(itemCard.getBack());

    }

    private void finishForm() {
        setItemCard();
        if (itemCard.validateId()) {
            dao.update(itemCard);
        } else {
            dao.saveItemCard(itemCard);
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            itemRefresh = (FormItemFragment.ItemRefresh) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString().concat("Deve ser implementado AtualizaListener"));
        }
    }

    public interface ItemRefresh {
        void itemRefresh();

    }

    private void addStandardValidator(TextInputLayout textInputLayout) {
        utils.addStandardValidator(textInputLayout, validatorCardList);
    }


    private boolean validField() {
        for (ValidatorCard validatorCard : validatorCardList) {
            if (!validatorCard.isValid()) {
                return false;
            }

        }
        return true;

    }


}