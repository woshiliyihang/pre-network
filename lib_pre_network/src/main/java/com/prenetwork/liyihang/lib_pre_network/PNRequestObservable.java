package com.prenetwork.liyihang.lib_pre_network;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liyihang on 18-1-16.
 */

public abstract class PNRequestObservable extends Observable implements PNRequestInterface {

    protected String result;
    protected static ExecutorService executor= Executors.newFixedThreadPool(3);

    public String getResult() {
        return result;
    }

    public String getRequestMethod() {
        return "POST";
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        if (result!=null)
        {
            dataChange();
        }
    }

    @Override
    public void handlerRequest() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                if ("GET".equals(getRequestMethod()))
                    result = PNGetPostUtil.sendGet(getRequestUrl(), getRequestParms(), getRequestHeader());

                if ("POST".equals(getRequestMethod()))
                    result = PNGetPostUtil.sendPost(getRequestUrl(), getRequestParms(), getRequestHeader());

                dataChange();
            }
        });
    }

    protected void dataChange(){
        setChanged();
        notifyObservers();
    }
}
