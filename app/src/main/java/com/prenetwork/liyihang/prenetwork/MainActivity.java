package com.prenetwork.liyihang.prenetwork;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.prenetwork.liyihang.lib_pre_network.PNBaseActivity;
import com.prenetwork.liyihang.lib_pre_network.PNQuickRequest;
import com.prenetwork.liyihang.lib_pre_network.PreNetworkHelper;

public class MainActivity extends PNBaseActivity implements View.OnClickListener {

    private Button mclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mclick=findViewById(R.id.mclick);
        mclick.setOnClickListener(this);

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
                    .addRequestObservable(new PNQuickRequest("web_data", "http://sijienet.com/"));
            startActivity(new Intent(this, OtherActivity.class));
        }
    }
}
