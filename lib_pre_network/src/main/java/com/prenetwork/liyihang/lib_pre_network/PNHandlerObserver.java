package com.prenetwork.liyihang.lib_pre_network;

/**
 * Created by liyihang on 18-1-22.
 */

public abstract class PNHandlerObserver extends PNObserver {

    protected PNHandler handler;

    public PNHandlerObserver(PNHandler handler) {
        this.handler = handler;
    }

    public abstract void preHandler(PNRequestObservable observable);
    public abstract void lastHandlerInUIThread(PNRequestObservable observable);

    @Override
    public void call(final PNRequestObservable observable) {
        preHandler(observable);
        handler.mPost(new Runnable() {
            @Override
            public void run() {
                lastHandlerInUIThread(observable);
            }
        });
    }
}
