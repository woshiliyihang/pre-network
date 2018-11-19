package com.chengxing.liyihang;

import android.view.MotionEvent;
import android.view.View;

import com.prenetwork.liyihang.lib_pre_network.PNUtils;

public class PNTouchListenerUtils implements View.OnTouchListener {

    float startX;
    float startY;
    float endX;
    float endY;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction()==MotionEvent.ACTION_DOWN)
        {
            startX=motionEvent.getRawX();
            startY=motionEvent.getRawY();
        }
        if (motionEvent.getAction()==MotionEvent.ACTION_UP)
        {
            endX=motionEvent.getRawX();
            endY=motionEvent.getRawY();
            handleMove(view, motionEvent);
        }
        return false;
    }

    private void handleMove(View view, MotionEvent motionEvent) {
        PNUtils.msg(startX+"="+startY+"="+endX+"="+endY);
    }
}
