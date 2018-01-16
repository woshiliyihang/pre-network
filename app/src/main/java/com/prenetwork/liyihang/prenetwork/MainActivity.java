package com.prenetwork.liyihang.prenetwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.prenetwork.liyihang.lib_pre_network.PNQuickRequest;
import com.prenetwork.liyihang.lib_pre_network.PreNetworkHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mclick=findViewById(R.id.mclick);
        mclick.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        Log.i(getClass().getSimpleName(), "click");
        PreNetworkHelper.getInstance()
                .removeAllObservable()
                .addRequestObservable(new PNQuickRequest("web_data", "http://sijienet.com"));
        startActivity(new Intent(this, OtherActivity.class));
    }
}
