package com.chengxing.liyihang;

import android.os.Handler;

import com.prenetwork.liyihang.lib_pre_network.PNBaseActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class PNSyncTaskUtils implements Runnable {

    public static final int action_pre_sync=1778;
    public static final int action_later_sync=1779;
    private static final ExecutorService EXECUTOR=Executors.newSingleThreadExecutor();// 单个的线程池
    private Semaphore semaphore;
    private Handler.Callback callback;
    private Handler handler;

    public PNSyncTaskUtils(Handler.Callback callback) {
        this.callback = callback;
        handler=new Handler(callback);
        semaphore = new Semaphore(0);
        EXECUTOR.submit(this);
    }

    //释放信号量
    public synchronized void release() {
        this.semaphore.release();
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
}
