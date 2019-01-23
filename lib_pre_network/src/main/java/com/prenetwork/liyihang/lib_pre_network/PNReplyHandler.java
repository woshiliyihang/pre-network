package com.prenetwork.liyihang.lib_pre_network;

import android.os.Message;


public class PNReplyHandler extends android.os.Handler {

    private PNReplyMessageInterface replyMessageInterface;
    private boolean isRunsUIThread=false;

    public void setRunsUIThread(boolean runsUIThread) {
        isRunsUIThread = runsUIThread;
    }

    public void setReplyMessageInterface(PNReplyMessageInterface replyMessageInterface) {
        this.replyMessageInterface = replyMessageInterface;
    }

    @Override
    public void handleMessage(final Message msg) {
        super.handleMessage(msg);
        if (isRunsUIThread)
            PNUtils.runOnUI(new Runnable() {
                @Override
                public void run() {
                    replyMessageInterface.replyMessage(msg);
                }
            });
        else
            replyMessageInterface.replyMessage(msg);
    }
}
