package com.prenetwork.liyihang.prenetwork;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liyihang on 18-1-16.
 */

public class PreNetworkHelper {

    public static PreNetworkHelper helper;

    public static PreNetworkHelper getInstance(){
        if (helper==null)
        {
            synchronized (PreNetworkHelper.class)
            {
                if (helper==null)
                {
                    helper=new PreNetworkHelper();
                }
            }
        }
        return helper;
    }

    private Map<String, PNRequestObservable> objs;
    public static ExecutorService executor= Executors.newFixedThreadPool(3);

    public PreNetworkHelper() {
        objs=new HashMap<>();
    }

    public void addRequestObservable(final PNRequestObservable requestObservable){
        PNRequestObservable ro = objs.get(requestObservable.getId());
        if (ro==null)
        {
            objs.put(requestObservable.getId(), requestObservable);
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    requestObservable.handlerRequest();
                }
            });
        }
    }

    public void addObserver(PNObserver observer){
        PNRequestObservable ro = objs.get(observer.getId());
        if (ro!=null)
        {
            ro.addObserver(observer);
        }
    }

    public void removeAllObservable(){
        Set<String> strings = objs.keySet();
        for (String string : strings) {
            objs.remove(string);
        }
    }

    public void removeRequestObservable(PNIDInterface id){
        objs.remove(id.getId());
    }



}
