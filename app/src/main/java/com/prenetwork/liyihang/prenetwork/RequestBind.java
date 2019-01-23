package com.prenetwork.liyihang.prenetwork;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.prenetwork.liyihang.lib_pre_network.PNGetPostUtil;
import com.prenetwork.liyihang.lib_pre_network.PNQuickRequest;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

public class RequestBind extends PNQuickRequest {

    private static AsyncHttpClient client;
    private final Result result;

    public RequestBind(String id, String url, String parms, Map<String, String> header, String method) {
        super(id, url, parms, header, method);
        result=new Result();
    }

    public static AsyncHttpClient getClient() {
        if (client==null)
        {
            synchronized (RequestBind.class)
            {
                client=new AsyncHttpClient();
                client.setTimeout(5000);
            }
        }
        return client;
    }



    @Override
    public void handlerRequest() {
        setEnd(false);
        if (getRequestHeader()!=null)
        {
            Set<String> strings = getRequestHeader().keySet();
            for (String string : strings) {
                getClient().removeHeader(string);
                getClient().addHeader(string, getRequestHeader().get(string));
            }
        }
        RequestParams params=new RequestParams();
        if (getRequestParms()!=null)
        {
            try {
                JSONObject array=new JSONObject(getRequestParms());
                Iterator<String> keys = array.keys();
                while (keys.hasNext())
                {
                    String key=keys.next();
                    String val= array.getString(key);
                    params.put(key, val);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if ("GET".equals(getRequestMethod())) {
            getClient().get(getRequestUrl(), params, result);
        }else {
            getClient().post(getRequestUrl(), params, result);
        }
    }

    private class Result extends TextHttpResponseHandler{

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            setResult(PNGetPostUtil.ERROR_PRE+throwable.getMessage());
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString) {
            setResult(responseString);
        }

        @Override
        public void onFinish() {
            super.onFinish();
            requestEnd();
            dataChange();
        }
    }
}
