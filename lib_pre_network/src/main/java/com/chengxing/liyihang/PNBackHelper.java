package com.chengxing.liyihang;

import android.content.Context;

import com.prenetwork.liyihang.lib_pre_network.PNUtils;

public class PNBackHelper {

    boolean firstBack=false;
    Context context;

    public PNBackHelper(Context context) {
        this.context = context;
    }

    public void back(Runnable runnable){
        if (firstBack) {
            firstBack=false;
            runnable.run();
        }else {
            firstBack=true;
            PNUtils.toast("再按一次退出");
            PNUtils.runOnUI(2700, new Runnable() {
                @Override
                public void run() {
                    firstBack=false;
                }
            });
        }
    }


}
