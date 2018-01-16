package com.prenetwork.liyihang.lib_pre_network;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * Created by liyihang on 18-1-16.
 */

public abstract class PNRequestObservable extends Observable implements PNRequestInterface {

    protected String result;

    public String getResult() {
        return result;
    }

    public int getConnectTimeout() {
        return 5000;
    }

    public boolean getUseCachesEnable() {
        return false;
    }

    public String getRequestMethod() {
        return "POST";
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        if (result!=null)
        {
            setChanged();
            notifyObservers();
        }
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
            if (getRequestParms()!=null)
            {
                PrintWriter writer=new PrintWriter(httpURLConnection.getOutputStream());
                writer.write(getRequestParms());
                writer.flush();
            }
            if (httpURLConnection.getResponseCode()==200
                    || httpURLConnection.getResponseCode()==301
                    || httpURLConnection.getResponseCode()==302)
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
            setChanged();
            notifyObservers();
        }
    }
}
