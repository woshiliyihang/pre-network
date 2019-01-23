package com.prenetwork.liyihang.lib_pre_network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.Observable;
import java.util.Observer;


public abstract class PNBasePresenter extends Observable implements PNMvpInterface, PNReplyMessageInterface, PNBaseActivity.OnGetReplyMsgListener {

    public static final String tag= PNBasePresenter.class.getSimpleName();
    private PNReplyHandler replyMsg;
    private Message message;
    private Observer observer;

    public void sendState(Message message){
        synchronized (this) {
            try {
                this.message = message;
                observer.update(this, message);
                Log.i(tag, "sendState"+message.what+"==="+message.obj);
            } catch (Exception e) {
                Log.i(tag, "sendState error:"+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setMsgContent(Object observer) {
        this.observer= (Observer) observer;
    }

    @Override
    public Message getReplyMsg() {
        return message;
    }

    @Override
    public Handler getMsg() {
        replyMsg=new PNReplyHandler();
        replyMsg.setReplyMessageInterface(this);
        replyMsg.setRunsUIThread(true);
        return replyMsg;
    }

    public static Message getMsgObj(int what, Object object){
        return PNUtils.msgObj(what, object);
    }

}
