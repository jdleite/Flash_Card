package com.example.flash_card.ui.validator;


import com.google.android.material.textfield.TextInputLayout;

public class StandardValidator implements ValidatorCard {

    private static final String CAMPO_OBRIGATORIO = "Campo Obrigatório";
    private final TextInputLayout textInputLayout;

    public StandardValidator(TextInputLayout textInputLayout) {
       this.textInputLayout = textInputLayout;
    }

    private boolean validatorField() {
        String edtCardName = textInputLayout.getEditText().getText().toString();
        if (edtCardName.trim().isEmpty()) {
            textInputLayout.setError(CAMPO_OBRIGATORIO);
            return false;
        }
        return true;
    }

    private void removeError() {
        textInputLayout.setError(null);
    }


    @Override
    public boolean isValid() {

        if (!validatorField()) return false;
        removeError();
        return true;
    }
}
