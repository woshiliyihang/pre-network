package com.prenetwork.liyihang.lib_pre_network;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;

/**
 * Created by liyihang on 18-1-16.
 */

public abstract class PNRequestObservable extends Observable implements PNRequestInterface {

    private String result;
    private boolean isEnd=false;
    protected static ExecutorService executor= PNUtils.EXECUTOR;

    public void requestEnd() {
        isEnd = true;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRequestMethod() {
        return "GET";
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public boolean isEnd() {
        return isEnd;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        if (isEnd)
        {
            dataChange();
        }
    }

    @Override
    public void handlerRequest() {
        isEnd=false;
        executor.submit(new Runnable() {
            @Override
            public void run() {
                if ("GET".equals(getRequestMethod()))
                    setResult(PNGetPostUtil.sendGet(getRequestUrl(), getRequestParms(), getRequestHeader()));

                if ("POST".equals(getRequestMethod()))
                    setResult(PNGetPostUtil.sendPost(getRequestUrl(), getRequestParms(), getRequestHeader()));

                requestEnd();
                dataChange();
            }
        });
    }

    protected void requestPost(String data){
        setResult(data);
        requestEnd();
        dataChange();
    }

    protected void dataChange(){
        setChanged();
        notifyObservers();
    }
}
