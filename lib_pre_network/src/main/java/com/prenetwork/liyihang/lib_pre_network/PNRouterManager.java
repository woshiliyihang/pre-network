package com.prenetwork.liyihang.lib_pre_network;

import java.util.ArrayList;

/**
 * Created by liyihang on 18-1-17.
 */

public class PNRouterManager {

    private static PNRouterManager instance;
    private ArrayList<PNRouter> routerList;

    private PNRouterManager(){
        routerList=new ArrayList<PNRouter>();
    }

    public static PNRouterManager getInstance(){
        if (instance==null) {
            synchronized(PNRouterManager.class){
                if (instance==null) {
                    instance=new PNRouterManager();
                }
            }
        }
        return instance;
    }

    public PNRouterManager createRouter(String routerTag){
        PNRouter router=getRouterByRouterTag(routerTag);
        if (router==null) {
            PNRouter router2=new PNRouter(routerTag);
            routerList.add(router2);
        }
        return this;
    }

    public PNRouterManager removeRouterByTag(String routerTag){
        for (int i=0; i<routerList.size(); i++){
            if (routerList.get(i).getRouterTag().equals(routerTag)){
                routerList.remove(i);
                break;
            }
        }
        return this;
    }

    public PNRouter getRouterByRouterTag(String routerTag){
        PNRouter router=null;
        for (PNRouter item : routerList) {
            if (item.getRouterTag().equals(routerTag)) {
                router=item;
                break;
            }
        }
        return router;
    }

    public PNRouter getTopRouter(){
        if (routerList.size()==0) {
            return null;
        }
        return routerList.get(routerList.size()-1);
    }

}
