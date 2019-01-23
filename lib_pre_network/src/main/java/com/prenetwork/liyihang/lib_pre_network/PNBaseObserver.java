package com.prenetwork.liyihang.lib_pre_network;

public abstract class PNBaseObserver extends PNObserver {
    @Override
    public void call(PNRequestObservable observable) {
        final String result = observable.getResult();
        final String error = PNGetPostUtil.isError(result);
        PNUtils.runOnUI(new Runnable() {
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
