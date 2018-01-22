package com.prenetwork.liyihang.prenetwork;

import com.prenetwork.liyihang.lib_pre_network.PNRequestObservable;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;

/**
 * Created by liyihang on 18-1-17.
 */

public class MyRequestObservable extends PNRequestObservable {

    private static final OkHttpClient HTTP_CLIENT=new OkHttpClient();

    @Override
    public String getId() {
        return "request_id";// 唯一id
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
        return null;
    }

    @Override
    public void handlerRequest() {
//        super.handlerRequest(); 将原来网络请求处理方法关闭
        Request.Builder builder=new Request.Builder();
        builder.url("http://blog.csdn.net/mhhyoucom");
        HTTP_CLIENT.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //网络完毕后必须调用
                requestPost(null);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String data = response.body().string();

                // do samething......

                //网络完毕后必须调用
                requestPost(data);
            }
        });
    }
}
