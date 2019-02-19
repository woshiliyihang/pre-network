package com.prenetwork.liyihang.lib_pre_network;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Observable;
import java.util.Observer;

/**
 *  liyihang
 */

public abstract class PNBaseActivity extends AppCompatActivity implements PNReplyMessageInterface {

    public static final String tag = PNBaseActivity.class.getSimpleName();

    private PNReplyHandler messenger;
    private PNReplyHandler repHandler;
    private PNMvpInterface o;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repHandler = new PNReplyHandler();
        repHandler.setReplyMessageInterface(this);

        loadProxy();
    }

    private void loadProxy() {
        try {
            o = (PNMvpInterface) getClassName();
            messenger = (PNReplyHandler) o.getMsg();
            o.setMsgContent(new ReplyObserver());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract Object getClassName();

    @Override
    protected void onDestroy() {
        messenger = null;
        super.onDestroy();
    }

    private final class Runs implements Runnable{
        Message message;
        public Runs(Message message) {
            this.message = message;
        }
        @Override
        public void run() {
            if (repHandler!=null)
                repHandler.handleMessage(message);
        }
    }

    public synchronized void sendState(Message message) {
        if (messenger != null && o!=null) {
            o.update(message);
            messenger.handleMessage(message);
        }
    }

    public synchronized void sendStateSelf(Message message) {
        try {
            runOnUiThread(new Runs(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Message getMsgObj(int what, Object object) {
        return PNUtils.msgObj(what, object);
    }

    public interface OnGetReplyMsgListener {
        Message getReplyMsg();
    }

    private final class ReplyObserver implements Observer{
        @Override
        public void update(Observable o, Object arg) {
            Log.i(tag, "Observable update");
            OnGetReplyMsgListener replyMsgListener = (OnGetReplyMsgListener) o;
            PNBaseActivity.this.update(replyMsgListener.getReplyMsg());
            runOnUiThread(new Runs(replyMsgListener.getReplyMsg()));
        }
    }


    public abstract void update(Message message);
}
