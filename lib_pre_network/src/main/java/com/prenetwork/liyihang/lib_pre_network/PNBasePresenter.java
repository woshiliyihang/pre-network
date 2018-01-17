package com.prenetwork.liyihang.lib_pre_network;

import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import java.util.Observable;

/**
 * 作者： 李一航
 * 时间： 17-8-30.
 */

public abstract class PNBasePresenter extends Observable implements PNMvpInterface, Handler.Callback, PNReplyMessageInterface, PNBaseActivity.OnGetReplyMsgListener {

    public static final String tag=PNBasePresenter.class.getSimpleName();

    private Messenger messenger;
    private Messenger replyMsg;
    private Message message;

    @Override
    public void setMsg(Messenger msg) {
        messenger=msg;
    }

    public void sendState(Message message){
        synchronized (this)
        {
            this.message = message;
            setChanged();
            notifyObservers();
            Log.i(tag, "sendState"+message.what+"==="+message.obj);
        }
    }

    @Override
    public Message getReplyMsg() {
        return message;
    }

    @Override
    public Messenger getMsg() {
        replyMsg=new Messenger(new Handler(this));
        return replyMsg;
    }

    @Override
    public boolean handleMessage(Message msg) {
        replyMessage(msg);
        return false;
    }

    public static Message getMsgObj(int what, Object object){
        return PNBaseActivity.getMsgObj(what, object);
    }

}
