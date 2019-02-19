package com.chengxing.liyihang;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

public class CXBottomAnimDialog {

    AlertDialog alertDialog;
    Activity activity;
    String[] arr;
    View.OnClickListener onClickListener;

    public CXBottomAnimDialog(final Activity activity, final String[] arr) {
        this.activity = activity;
        this.arr = arr;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, 3);
        builder.setSingleChoiceItems(arr, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                View view=new View(activity);
                view.setTag(arr[which]);
                onClickListener.onClick(view);
            }
        });
        builder.setNegativeButton("cancel", null);
        alertDialog = builder.create();
    }

    public void setClickListener(View.OnClickListener listener){
        this.onClickListener=listener;
    }

    public void show(){
        alertDialog.show();
    }
}
