package com.chengxing.liyihang;


import android.os.Handler;

public class PNCountDownUtils implements Runnable {
    private int time=60;
    private Handler handler;
    private boolean isEnd;

    public PNCountDownUtils(int time, Handler handler, OnCountDownlistner onCountDownlistner) {
        this.time = time;
        this.handler = handler;
        this.onCountDownlistner = onCountDownlistner;
        handler.post(this);
    }

    private OnCountDownlistner onCountDownlistner;

    public interface OnCountDownlistner{
        void update(int time, boolean isEnd);
    }

    @Override
    public void run() {
        if (isEnd){
            stop();
            return;
        }
        time--;
        if (time>0)
        {
            if (onCountDownlistner!=null)
                onCountDownlistner.update(time, isEnd);
            handler.postDelayed(this, 1000);
        }else {
            stop();
        }
    }

    public void stop(){
        isEnd=true;
        handler.removeCallbacks(this);
        if (onCountDownlistner!=null)
            onCountDownlistner.update(time, isEnd);
    }
}
