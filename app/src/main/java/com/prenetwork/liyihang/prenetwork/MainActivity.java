package com.prenetwork.liyihang.prenetwork;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.prenetwork.liyihang.lib_pre_network.PNBaseActivity;
import com.prenetwork.liyihang.lib_pre_network.PNClickBindCompat;
import com.prenetwork.liyihang.lib_pre_network.PNQuickRequest;
import com.prenetwork.liyihang.lib_pre_network.PNRouterManager;
import com.prenetwork.liyihang.lib_pre_network.PNViewFind;
import com.prenetwork.liyihang.lib_pre_network.PreNetworkHelper;

public class MainActivity extends PNBaseActivity implements View.OnClickListener {


    public static final String tag = "MainActivity";

    public static final String url_id_only = "web_data";

    @PNClickBindCompat
    @PNViewFind
    public Button mclick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PNViewFind.Bind.init(this);
        PNClickBindCompat.Bind.init(this);

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
        sendStateSelf(getMsgObj(10, null));
    }

    @Override
    public void replyMessage(Message msg) {

        //pre network loading data ....
        // plase use your self network framework ， here is Simple network util
        PreNetworkHelper.getInstance().addRequestObservable(new PNQuickRequest(url_id_only, "https://blog.csdn.net/mhhyoucom/",null, null, "GET"));

        PNRouterManager.getInstance().createRouter(this.toString()).getTopRouter().jump(this, "app://sijienet.com/other", null);
    }

}
