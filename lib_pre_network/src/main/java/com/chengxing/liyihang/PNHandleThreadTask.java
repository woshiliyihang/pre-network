package com.chengxing.liyihang;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.prenetwork.liyihang.lib_pre_network.PNHandler;

import java.util.concurrent.Semaphore;

public class PNHandleThreadTask {

    private Semaphore semaphore;
    private PNHandler pnHandler;
    private HandlerThread handlerThread;
    private String name;
    private boolean isStop=false;
    private String resultData;
    private Handler.Callback syncFunction;

    public void setSyncFunction(Handler.Callback syncFunction) {
        this.syncFunction = syncFunction;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }

    public PNHandleThreadTask(String name) {
        this.name = name;
        semaphore = new Semaphore(0);
        handlerThread=new HandlerThread(name);
        handlerThread.start();
        pnHandler = new PNHandler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (isStop)
                    return;
                syncFunction.handleMessage(msg);
                try {
                    semaphore.acquire();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void release(){
        semaphore.release();
    }


    public void cancel(){
        if (isStop)
            return;
        isStop=true;
        handlerThread.quit();
    }

}
