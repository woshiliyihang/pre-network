package com.prenetwork.liyihang.lib_pre_network;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by liyihang on 18-1-16.
 */

public abstract class PNRequestObservable extends Observable implements PNRequestInterface {

    protected String result;

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
        if ("GET".equals(getRequestMethod()))
            result = PNGetPostUtil.sendGet(getRequestUrl(), getRequestParms(), getRequestHeader());

        if ("POST".equals(getRequestMethod()))
            result = PNGetPostUtil.sendPost(getRequestUrl(), getRequestParms(), getRequestHeader());

        dataChange();
    }

    protected void dataChange(){
        setChanged();
        notifyObservers();
    }
}
