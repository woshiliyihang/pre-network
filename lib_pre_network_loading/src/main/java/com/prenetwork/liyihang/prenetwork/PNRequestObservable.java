package com.prenetwork.liyihang.prenetwork;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

/**
 * Created by liyihang on 18-1-16.
 */

public abstract class PNRequestObservable extends Observable implements PNRequestInterface {

    protected String result;

    public String getResult() {
        return result;
    }

    @Override
    public int getConnectTimeout() {
        return 5000;
    }

    @Override
    public boolean getUseCachesEnable() {
        return false;
    }

    @Override
    public String getRequestMethod() {
        return "POST";
    }

    @Override
    public void handlerRequest() {
        Map<String, String> requestHeader = getRequestHeader();
        HttpURLConnection httpURLConnection=null;
        try {
            URL url=new URL(getRequestUrl());
            httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(getRequestMethod());
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(getUseCachesEnable());
            httpURLConnection.setConnectTimeout(getConnectTimeout());
            if (requestHeader!=null)
            {
                Set<String> strings = requestHeader.keySet();
                for (String string : strings) {
                    httpURLConnection.addRequestProperty(string, requestHeader.get(string));
                }
            }
            PrintWriter writer=new PrintWriter(httpURLConnection.getOutputStream());
            writer.write(getRequestParms());
            writer.flush();
            if (httpURLConnection.getResponseCode()==200)
            {
                InputStream inputStream = httpURLConnection.getInputStream();
                ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
                int len=-1;
                byte[] buf=new byte[1024];
                while ((len=inputStream.read(buf))!=-1)
                {
                    outputStream.write(buf,0,len);
                    outputStream.flush();
                }
                result=outputStream.toString("utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection!=null)
            {
                httpURLConnection.disconnect();
            }
            notifyObservers();
        }
    }
}
