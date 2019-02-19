package com.chengxing.liyihang;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.prenetwork.liyihang.lib_pre_network.R;

public class CXLoadingUtils {

    AlertDialog progressDialog;
    Context context;

    @SuppressLint("WrongConstant")
    public CXLoadingUtils(Context context) {
        this.context = context;
        AlertDialog.Builder builder=new AlertDialog.Builder(context, R.style.tm_dialog);
        builder.setCancelable(true);
        View inflate = LayoutInflater.from(context).inflate(R.layout.loading_bar_dialog_view, null);
        builder.setView(inflate);
        progressDialog=builder.create();
    }

    public void openLoading(){
        progressDialog.show();
    }

    public void closeLoading(){
        progressDialog.cancel();
    }

}
