package com.chengxing.liyihang;

import android.os.Handler;
import android.os.Message;

import com.prenetwork.liyihang.lib_pre_network.PNBaseActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public abstract class PNSyncTaskUtils implements Runnable, Handler.Callback {

    public static final int action_pre_sync=1778;
    public static final int action_later_sync=1779;
    private static final ExecutorService EXECUTOR=Executors.newSingleThreadExecutor();// 单个的线程池
    private Semaphore semaphore;
    private Handler handler;
    private final Object object=new Object();

    public PNSyncTaskUtils() {
        handler=new Handler(this);
        semaphore = new Semaphore(0);
    }

    public void start(){
        EXECUTOR.submit(this);
    }

    //释放信号量
    public void release() {
        synchronized (object) {
            this.semaphore.release();
        }
    }

    @Override
    public void run() {
        handler.sendMessage(PNBaseActivity.getMsgObj(action_pre_sync, this));
        try {
            semaphore.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.sendMessage(PNBaseActivity.getMsgObj(action_later_sync, this));
    }

    public abstract void pre();
    public abstract void end();

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what==action_pre_sync)
            pre();
        if (msg.what==action_later_sync)
            end();
        return false;
    }
}
