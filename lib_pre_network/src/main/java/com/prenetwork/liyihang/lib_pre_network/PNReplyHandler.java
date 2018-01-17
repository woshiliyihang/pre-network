package com.prenetwork.liyihang.lib_pre_network;

import android.os.Message;

/**
 * 作者： 李一航
 * 时间： 17-8-30.
 */

public class PNReplyHandler extends android.os.Handler {

    private PNReplyMessageInterface replyMessageInterface;

    public void setReplyMessageInterface(PNReplyMessageInterface replyMessageInterface) {
        this.replyMessageInterface = replyMessageInterface;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        replyMessageInterface.replyMessage(msg);
    }
}
