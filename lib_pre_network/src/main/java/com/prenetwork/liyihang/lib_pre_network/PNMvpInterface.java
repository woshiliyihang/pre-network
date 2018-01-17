package com.prenetwork.liyihang.lib_pre_network;

import android.os.Message;
import android.os.Messenger;

/**
 * 作者： 李一航
 * 时间： 17-8-30.
 */

public interface PNMvpInterface {

    void setMsg(Messenger msg);

    Messenger getMsg();

    void update(Message message);

}
