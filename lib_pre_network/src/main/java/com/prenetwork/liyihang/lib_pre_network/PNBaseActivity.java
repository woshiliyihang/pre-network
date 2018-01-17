package com.prenetwork.liyihang.lib_pre_network;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Observable;
import java.util.Observer;

/**
 * 作者： 李一航
 * 时间： 17-8-30.
 */

public abstract class PNBaseActivity extends AppCompatActivity implements PNReplyMessageInterface, Observer {

    public static final String tag = PNBaseActivity.class.getSimpleName();

    private Messenger messenger;
    private Messenger replyMsger;
    private PNReplyHandler repHandler;
    private PNMvpInterface o;

    public PNReplyHandler getRepHandler() {
        return repHandler;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repHandler = new PNReplyHandler();
        repHandler.setReplyMessageInterface(this);
        replyMsger = new Messenger(repHandler);

        loadProxy();
    }

    public void loadProxy() {
        try {
            o = (PNMvpInterface) getClassName();
            o.setMsg(replyMsger);
            messenger = o.getMsg();
            Observable observable = (Observable) o;
            observable.addObserver(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public abstract Object getClassName();

    @Override
    protected void onDestroy() {
        replyMsger = null;
        messenger = null;
        super.onDestroy();
    }

    public synchronized void sendState(Message message) {
        if (messenger != null && o!=null) {
            o.update(message);
            try {
                messenger.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void sendStateSelf(Message message) {
        if (replyMsger != null) {
            try {
                replyMsger.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Message getMsgObj(int what, Object object) {
        Message obtain = Message.obtain();
        obtain.obj = object;
        obtain.what = what;
        return obtain;
    }

    public interface OnGetReplyMsgListener {
        Message getReplyMsg();
    }

    @Override
    public void update(Observable observable, Object data) {
        Log.i(tag, "Observable update");
        OnGetReplyMsgListener replyMsgListener = (OnGetReplyMsgListener) observable;
        update(replyMsgListener.getReplyMsg());
        if (replyMsger != null) {
            try {
                replyMsger.send(replyMsgListener.getReplyMsg());
            } catch (Exception e) {
                Log.i(tag, "update===" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public abstract void update(Message message);
}
