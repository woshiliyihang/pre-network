package com.chengxing.liyihang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.widget.ProgressBar;

public class PNWebView extends android.webkit.WebView {

    public static final int get_header=11;
    public static final int go_back=12;

    public PNWebView(Context context) {
        super(context);
    }

    public PNWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PNWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ProgressBar mPageLoadingProgressBar = null;

    private boolean useMyDialog=false;

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    public void onCreate(){
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        addJavascriptInterface(new AndroidFunction(), "android");
        setWebViewClient(new android.webkit.WebViewClient());
        setWebChromeClient(new android.webkit.WebChromeClient(){
            @Override
            public void onProgressChanged(android.webkit.WebView view, int newProgress) {
                updateTime(newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
        getSettings().setAllowFileAccess(true);
        getSettings().setSupportMultipleWindows(true);
        getSettings().setAppCacheEnabled(true);
        getSettings().setDomStorageEnabled(true);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setGeolocationEnabled(true);
        getSettings().setDatabaseEnabled(true);
        getSettings().setAppCacheMaxSize(Long.MAX_VALUE);
        getSettings().setAppCachePath(getContext().getDir("appcache", 0).getPath());
        getSettings().setDatabasePath(getContext().getDir("databases", 0).getPath());
        getSettings().setGeolocationDatabasePath(getContext().getDir("geolocation", 0).getPath());



    }



    public void initProgressBar(ProgressBar progressBar) {
        mPageLoadingProgressBar = progressBar;// new
        mPageLoadingProgressBar.setMax(100);
    }




    private class AndroidFunction extends Object {


        @JavascriptInterface
        public String handle(final int what){
            return handle(what, null);
        }


        @JavascriptInterface
        public String handle(final int what, final String msg){
            return null;
        }
    }

    public void test(){

        if (Build.VERSION.SDK_INT<=18) {
            post(new Runnable() {
                @Override
                public void run() {
                    loadUrl("javascript:jsFunc()");
                }
            });
        }else {
            evaluateJavascript("javascript:jsFunc()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {

                }
            });
        }



    }

    private void updateTime(int i) {
        if (mPageLoadingProgressBar!=null)
        {
            if (i==100)
            {
                mPageLoadingProgressBar.setVisibility(GONE);
            }else {
                if (mPageLoadingProgressBar.getVisibility()==GONE)
                {
                    mPageLoadingProgressBar.setVisibility(VISIBLE);
                }
                mPageLoadingProgressBar.setProgress(i);
            }
        }
    }

}
