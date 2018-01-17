package com.prenetwork.liyihang.lib_pre_network;

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

    public PreNetworkHelper() {
        objs=new HashMap<>();
    }

    public PreNetworkHelper addRequestObservable(final PNRequestObservable requestObservable){
        PNRequestObservable ro = objs.get(requestObservable.getId());
        if (ro==null)
        {
            objs.put(requestObservable.getId(), requestObservable);
            requestObservable.handlerRequest();
        }else {
            PNUtils.msg("Observable is exist !!");
        }
        return this;
    }

    public PreNetworkHelper addObserver(PNObserver observer){
        PNRequestObservable ro = objs.get(observer.getId());
        if (ro!=null)
        {
            ro.addObserver(observer);
        }else {
            PNUtils.msg("Observable is null !!");
        }
        return this;
    }

    public PreNetworkHelper removeAllObservable(){
        Set<String> strings = objs.keySet();
        for (String string : strings) {
            objs.remove(string);
        }
        return this;
    }

    public PreNetworkHelper removeRequestObservable(String id){
        objs.remove(id);
        return this;
    }



}
