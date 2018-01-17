package com.prenetwork.liyihang.prenetwork;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.prenetwork.liyihang.lib_pre_network.PNBaseActivity;
import com.prenetwork.liyihang.lib_pre_network.PNObserver;
import com.prenetwork.liyihang.lib_pre_network.PNQuickRequest;
import com.prenetwork.liyihang.lib_pre_network.PNRequestObservable;
import com.prenetwork.liyihang.lib_pre_network.PNRouterManager;
import com.prenetwork.liyihang.lib_pre_network.PNUtils;
import com.prenetwork.liyihang.lib_pre_network.PreNetworkHelper;

public class MainActivity extends PNBaseActivity implements View.OnClickListener {

    private Button mclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mclick=findViewById(R.id.mclick);
        mclick.setOnClickListener(this);

        PreNetworkHelper.getInstance()
                .removeAllObservable()
                .addRequestObservable(new MyRequestObservable())
                .addObserver(new PNObserver() {
                    @Override
                    public void call(PNRequestObservable observable) {
                        MyRequestObservable result= (MyRequestObservable) observable;
                        PNUtils.msg("end:"+result.getResult());
                    }

                    @Override
                    public String getId() {
                        return "request_id";
                    }
                })
                .removeAllObservable();

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
        Log.i(getClass().getSimpleName(), "click");
        sendStateSelf(getMsgObj(10, null));
    }

    @Override
    public void replyMessage(Message msg) {
        if (msg.what==10)
        {
            PreNetworkHelper.getInstance()
                    .removeRequestObservable("web_data")
                    .addRequestObservable(new PNQuickRequest("web_data", "http://baidu.com/"));
            PNRouterManager.getInstance().createRouter(this.toString()).getTopRouter().jump(this, "app://sijienet.com/other", null);
        }
    }
}
