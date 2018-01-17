package com.prenetwork.liyihang.prenetwork;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import android.widget.TextView;

import com.prenetwork.liyihang.lib_pre_network.PNObserver;
import com.prenetwork.liyihang.lib_pre_network.PNRequestObservable;
import com.prenetwork.liyihang.lib_pre_network.PreNetworkHelper;

/**
 * Created by liyihang on 18-1-16.
 */

public class OtherActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreNetworkHelper.getInstance().addObserver(new PNObserver() {
            @Override
            public void call(PNRequestObservable observable) {
                final String result = observable.getResult();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ScrollView scrollView=new ScrollView(OtherActivity.this);
                        TextView textView=new TextView(OtherActivity.this);
                        textView.setText(result);
                        scrollView.addView(textView);
                        setContentView(scrollView);
                    }
                });
            }

            @Override
            public String getId() {
                return "web_data";
            }
        });
    }
}
