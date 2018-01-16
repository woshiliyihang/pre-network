package com.prenetwork.liyihang.prenetwork;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by liyihang on 18-1-16.
 */

public abstract class PNObserver implements PNIDInterface, Observer {

    public abstract void call(PNRequestObservable observable);

    @Override
    public void update(Observable observable, Object o) {
        PNRequestObservable observable1= (PNRequestObservable) observable;
        call(observable1);
    }
}
