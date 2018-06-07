package com.prenetwork.liyihang.lib_pre_network;

import android.os.Message;
import android.os.Messenger;



public interface PNMvpInterface {

    void setMsg(Messenger msg);

    Messenger getMsg();

    void update(Message message);

}
