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

import com.example.flash_card.R;
import com.example.flash_card.database.CardDatabase;
import com.example.flash_card.database.dao.CardDao;
import com.example.flash_card.model.Card;
import com.example.flash_card.ui.validator.StandardValidator;
import com.example.flash_card.ui.validator.ValidatorCard;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class TextDialogFragment extends DialogFragment {
    private View view;
    private Card card;
    private StartField startField = new StartField();
    private Context context;
    private CardDao dao;
    private Utils utils = new Utils();
    private RefreshList refreshList;
    private List<ValidatorCard> validatorCardList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_form, container, false);
        context = getActivity().getApplicationContext();

        CardDatabase database = CardDatabase.getInstance(context);
        dao = database.getCardDatabase();


        nameField();
        loadCard();
        btnSave();
        btnCance();
        return view;

    }

    private class StartField {
        TextInputLayout tILCardName;
        Button btnSave, btnCancel;
    }

    private void loadCard() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            card = (Card) this.getArguments().getSerializable("card");
            fillField();
        } else {
            card = new Card();
        }
    }

    private void finishForm() {
        setCard();
        if (card.validateId()) {
            dao.update(card);
        } else {
            dao.save(card);
        }
    }

    private void fillField() {
        startField.tILCardName.getEditText().setText(card.getName());
    }

    private void nameField() {
        startField.tILCardName = view.findViewById(R.id.id_edt_name);
        addStandardValidator(startField.tILCardName);
    }

    private void setCard() {
        String name = startField.tILCardName.getEditText().getText().toString();
        card.setName(name);
    }

    private void btnSave() {
        startField.btnSave = view.findViewById(R.id.id_btn_save);
        startField.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isValidForm = validField();
                if (isValidForm) {
                    finishForm();
                    refreshList.refresh();
                    getDialog().dismiss();
                }

            }
        });
    }

    private void btnCance() {
        startField.btnCancel = view.findViewById(R.id.id_btn_cancel);
        startField.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            refreshList = (RefreshList) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString().concat("Deve ser implementado AtualizaListener"));
        }
    }

    public interface RefreshList {
        void refresh();
    }

    private void addStandardValidator(TextInputLayout textInputLayout) {
       utils.addStandardValidator(textInputLayout,validatorCardList);
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
