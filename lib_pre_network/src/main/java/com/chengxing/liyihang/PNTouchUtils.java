package com.chengxing.liyihang;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.MotionEvent;
import android.view.View;

import com.prenetwork.liyihang.lib_pre_network.PNUtils;

public class PNTouchUtils implements View.OnTouchListener{

    private Context context;
    private String name;
    String touch_receiver_pre="touch_receiver_pre";

    public static int max=74;
    int preX;
    int preY;
    int newX;
    int newY;
    long startTime;

    public PNTouchUtils(Context context, String name) {
        this.context = context;
        this.name=name;
        max= PNUtils.dp2px(context, 47);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction()==MotionEvent.ACTION_DOWN)
        {
            preSet(motionEvent);
            PNUtils.msg(name+"down"+ preX +"+"+ preY);
            return true;
        }
        if (motionEvent.getAction()==MotionEvent.ACTION_MOVE)
        {
            if (preX==0 || preY==0)
            {
                preSet(motionEvent);
                PNUtils.msg(name+"move"+ preX +"+"+ preY);
            }
        }
        if (motionEvent.getAction()==MotionEvent.ACTION_UP)
        {
            PNUtils.msg(name+"pre"+preX+"+"+preY);
            newX = (int) motionEvent.getRawX();
            newY = (int) motionEvent.getRawY();
            int dx=(newX - preX);
            int dy=(newY - preY);
            PNUtils.msg(name+"abs"+dx+"+"+dy);
            if (Math.abs(dx)>Math.abs(dy))
            {
                if (dx<0 && Math.abs(dx)>max) {//left
                    if (intercept()) {
                        PNUtils.msg("intercept");
                    }else {
                        PNUtils.msg("left");
                        Intent intent=new Intent();
                        intent.setAction(touch_receiver_pre+context.toString());
                        intent.putExtra("direction","left");
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    }

                }else if (dx>0 && Math.abs(dx)>max) {// right
                    if (intercept()) {
                        PNUtils.msg("intercept");
                    }else {
                        PNUtils.msg("right");
                        Intent intent=new Intent();
                        intent.setAction(touch_receiver_pre+context.toString());
                        intent.putExtra("direction","right");
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    }

                }
            }else if (Math.abs(dy)>Math.abs(dx))
            {
                if (dy>0 && Math.abs(dy)>(max*2))
                {
                    if (intercept()) {
                        PNUtils.msg("intercept");
                    }else {
                        PNUtils.msg("bottom");
                        Intent intent=new Intent();
                        intent.setAction(touch_receiver_pre+context.toString());
                        intent.putExtra("direction","bottom");
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    }

                }
            }
            endReset();
        }
        if (motionEvent.getAction()==MotionEvent.ACTION_CANCEL)
        {
            endReset();
        }
        return true;
    }

    private void endReset() {
        //归零
        preX=0;
        preY=0;
        newX=0;
        newY=0;
        startTime=0;
    }

    private void preSet(MotionEvent motionEvent) {
        preX = (int) motionEvent.getRawX();
        preY = (int) motionEvent.getRawY();
        startTime=System.currentTimeMillis();
    }

    boolean intercept(){
        if (startTime!=0)
        {
            long mTime=System.currentTimeMillis()-startTime;
            if (mTime>440)
            {
                return true;
            }
        }
        return false;
    }



}
