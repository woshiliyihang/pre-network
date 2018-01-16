package com.prenetwork.liyihang.prenetwork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mclick=findViewById(R.id.mclick);
        mclick.setOnClickListener(this);

        PreNetworkHelper.getInstance().addRequestObservable(new Request());
    }


    private class Request extends PNRequestObservable{

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

    private class Result extends PNObserver{

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
                    mclick.setText(Html.fromHtml(result));
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        PreNetworkHelper.getInstance().addObserver(new Result());
    }
}
