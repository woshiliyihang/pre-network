package com.prenetwork.liyihang.lib_pre_network;

import android.os.Handler;
import android.os.Looper;

public abstract class PNBaseObserver extends PNObserver {
    @Override
    public void call(PNRequestObservable observable) {
        final String result = observable.getResult();
        final String error = PNGetPostUtil.isError(result);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                pre();
                if (error!=null)
                {
                    error(error);
                }else {
                    result(result);
                }
                end();
            }
        });
    }

    public abstract void pre();
    public abstract void result(String res);
    public abstract void error(String err);
    public abstract void end();
}
