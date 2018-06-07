package com.prenetwork.liyihang.prenetwork;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.Button;

import com.prenetwork.liyihang.lib_pre_network.PNBaseActivity;
import com.prenetwork.liyihang.lib_pre_network.PNRouterManager;
import com.prenetwork.liyihang.lib_pre_network.PNViewFind;
import com.prenetwork.liyihang.lib_pre_network.PreNetworkHelper;

/**
 * Created by liyihang on 18-1-16.
 */

public class OtherActivity extends PNBaseActivity{

    @PNViewFind
    Button mclick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PNViewFind.Bind.init(this);
        initView();
    }

    @Override
    public Object getClassName() {
        return new MainBasePresenter();
    }

    @Override
    public void update(Message message) {

    }

    private void initView() {
        PreNetworkHelper.getInstance().addObserver(new UpdateUI(this));
    }

    @Override
    public void onBackPressed() {
        PNRouterManager.getInstance().getTopRouter().back(this);
        super.onBackPressed();

    }

    @Override
    public void replyMessage(Message msg) {
        if (msg.what==10)
        {
            String d= (String) msg.obj;
            mclick.setText(Html.fromHtml(d));
        }
    }
}
