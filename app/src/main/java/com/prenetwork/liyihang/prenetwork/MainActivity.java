package com.prenetwork.liyihang.prenetwork;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.prenetwork.liyihang.lib_pre_network.PNBaseActivity;
import com.prenetwork.liyihang.lib_pre_network.PNClickBindCompat;
import com.prenetwork.liyihang.lib_pre_network.PNHandler;
import com.prenetwork.liyihang.lib_pre_network.PNObserver;
import com.prenetwork.liyihang.lib_pre_network.PNQuickRequest;
import com.prenetwork.liyihang.lib_pre_network.PNRequestObservable;
import com.prenetwork.liyihang.lib_pre_network.PNRouterManager;
import com.prenetwork.liyihang.lib_pre_network.PNUtils;
import com.prenetwork.liyihang.lib_pre_network.PNViewFind;
import com.prenetwork.liyihang.lib_pre_network.PreNetworkHelper;

public class MainActivity extends PNBaseActivity implements View.OnClickListener, Handler.Callback {


    public static final String tag="MainActivity";

    private PNHandler handler;

    @PNClickBindCompat
    @PNViewFind
    public Button mclick;

    @PNClickBindCompat
    @PNViewFind
    public Button version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler=new PNHandler(this);

        PNViewFind.Bind.init(this);
        PNClickBindCompat.Bind.init(this);

        PreNetworkHelper.getInstance()
                .removeAllObservable()
                .addRequestObservable(new MyRequestObservable())
                .addObserver(new UpdateUI(handler))
                .removeAllObservable();

    }

    @Override
    protected void onDestroy() {
        handler.cancel();
        super.onDestroy();
    }

    @Override
    public Object getClassName() {
        return new MainBasePresenter();
    }

    @Override
    public void update(Message message) {

    }


    @Override
    public void onClick(View view) {
        PNUtils.msg("id:"+view.getId());
        if (view.getId()==R.id.mclick)
            sendStateSelf(getMsgObj(10, null));

        if (view.getId()==R.id.version)
        {
            Toast.makeText(this, "version : "+PNUtils.getVersion(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void replyMessage(Message msg) {
        if (msg.what==10)
        {
            PreNetworkHelper.getInstance()
                    .removeRequestObservable("web_data")
                    .addRequestObservable(new PNQuickRequest("web_data", "http://blog.csdn.net/mhhyoucom"));
            PNRouterManager.getInstance().createRouter(this.toString()).getTopRouter().jump(this, "app://sijienet.com/other", null);
        }
    }

    @Override
    public boolean handleMessage(Message message) {
        Toast.makeText(this, "init end", Toast.LENGTH_SHORT).show();
        return false;
    }
}
