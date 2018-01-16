package com.prenetwork.liyihang.prenetwork;

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


}
