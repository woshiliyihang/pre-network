package com.prenetwork.liyihang.prenetwork;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.prenetwork.liyihang.lib_pre_network.PNObserver;
import com.prenetwork.liyihang.lib_pre_network.PNRequestObservable;
import com.prenetwork.liyihang.lib_pre_network.PreNetworkHelper;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mclick;
    private Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mclick=findViewById(R.id.mclick);
        mclick.setOnClickListener(this);

        PreNetworkHelper.getInstance().removeAllObservable();
        PreNetworkHelper.getInstance().addRequestObservable(new Request());
        result = new Result();
    }


    private class Request extends PNRequestObservable {

        @Override
        public String getId() {
            return "web_data";
        }

        @Override
        public Map<String, String> getRequestHeader() {
            return null;
        }

        @Override
        public String getRequestParms() {
            return null;
        }

        @Override
        public String getRequestUrl() {
            return "http://sijienet.com";
        }

    }

    private class Result extends PNObserver {

        @Override
        public String getId() {
            return "web_data";
        }

        @Override
        public void call(PNRequestObservable observable) {
            final String result = observable.getResult();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(MainActivity.class.getSimpleName(), result);
                    mclick.setText(Html.fromHtml(result));
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        Log.i(getClass().getSimpleName(), "click");
        PreNetworkHelper.getInstance().addObserver(result);
    }
}
