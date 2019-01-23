package com.chengxing.liyihang;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PNErrorBinderUtils implements  TextWatcher {

    EditText phone;
    View line;
    TextView errorFont;
    private Drawable background;

    public PNErrorBinderUtils(EditText phone, View line, TextView errorFont) {
        this.phone = phone;
        this.line = line;
        this.errorFont = errorFont;
        init();
    }

    private void init() {
        phone.addTextChangedListener(this);
    }

    public void showError(String s){
        background = line.getBackground();
        errorFont.setText("* "+s);
        line.setBackgroundColor(Color.parseColor("#D0021B"));
        errorFont.setVisibility(View.VISIBLE);
    }

    public void hideError(){
        errorFont.setText(null);
        line.setBackgroundColor(Color.parseColor("#535AF8"));
        errorFont.setVisibility(View.INVISIBLE);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (errorFont.getVisibility()==View.VISIBLE)
        {
            hideError();
        }
    }
}
