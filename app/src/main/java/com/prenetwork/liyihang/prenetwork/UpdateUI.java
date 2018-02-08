package com.prenetwork.liyihang.prenetwork;

import com.prenetwork.liyihang.lib_pre_network.PNHandler;
import com.prenetwork.liyihang.lib_pre_network.PNHandlerObserver;
import com.prenetwork.liyihang.lib_pre_network.PNRequestObservable;
import com.prenetwork.liyihang.lib_pre_network.PNUtils;

/**
 * Created by liyihang on 18-1-22.
 */

public class UpdateUI extends PNHandlerObserver {
    public UpdateUI(PNHandler handler) {
        super(handler);
    }

    @Override
    public String getId() {
        return "request_id";
    }

    @Override
    public void preHandler(PNRequestObservable observable) {
        // TODO: 18-1-22 handler data
    }

    @Override
    public void lastHandlerInUIThread(PNRequestObservable observable) {
        //处理 ui动作
        MyRequestObservable result= (MyRequestObservable) observable;
        PNUtils.msg("end:"+result.getResult());
        handler.sendEmptyMessage(10);
    }
}
