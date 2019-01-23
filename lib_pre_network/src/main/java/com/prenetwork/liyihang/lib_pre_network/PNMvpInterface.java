package com.prenetwork.liyihang.lib_pre_network;

import android.os.Handler;
import android.os.Message;


public interface PNMvpInterface {

    Handler getMsg();
    void setMsgContent(Object observer);
    void update(Message message);

}
