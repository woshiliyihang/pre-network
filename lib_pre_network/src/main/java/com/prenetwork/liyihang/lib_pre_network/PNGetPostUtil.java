package com.prenetwork.liyihang.lib_pre_network;

import android.util.Log;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by liyihang on 18-1-16.
 */

public class PNGetPostUtil {

    public static String sendGet(String url, String params, Map<String , String> header) {
        return send(url, params, header, "GET");
    }


    public static String sendPost(String url, String params, Map<String , String> header) {
        return send(url, params, header, "POST");
    }


    private static void handleConn(HttpURLConnection conn) {
        conn.setRequestProperty("Content-type", "text/html");
        conn.setRequestProperty("Accept-Charset", "utf-8");
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
        conn.setRequestProperty("Accept-Language",
                "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        conn.setRequestProperty("user-agent",
                "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    }

    public static String send(String url, String params, Map<String , String> header, String method) {
        String result=null;
        HttpURLConnection conn=null;
        try {
            URL realUrl = new URL(url);
            conn = (HttpURLConnection) realUrl.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setUseCaches(false);
            conn.setRequestMethod(method);
            conn.setDoOutput(true);
            conn.setDoInput(true);

//            handleConn(conn);

            if (header!=null)
            {
                Set<String> strings = header.keySet();
                for (String string : strings) {
                    conn.setRequestProperty(string, header.get(string));
                }
            }

            conn.connect();

            if (params!=null)
            {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
                out.write(params);
                out.flush();
                out.close();
            }

            int responseCode = conn.getResponseCode();
            Log.i("url_connection","send response code =:" + responseCode);
            result = getBytesByInputStream(conn.getInputStream());
        } catch (Exception e) {
            Log.i("url_connection","send get error:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            if (conn!=null)
            {
                conn.disconnect();
            }
        }
        return result;
    }


    public static String getBytesByInputStream(InputStream is) throws Exception {
        StringBuilder outs= new StringBuilder();
        int len=-1;
        byte[] arr=new byte[1024];
        while ( (len=is.read(arr))!=-1 )
        {
            outs.append(new String(arr, 0, len));
        }
        is.close();
        return outs.toString();
    }


    public static String getResponseHeader(HttpURLConnection conn) {
        Map<String, List<String>> responseHeaderMap = conn.getHeaderFields();
        int size = responseHeaderMap.size();
        StringBuilder sbResponseHeader = new StringBuilder();
        for(int i = 0; i < size; i++){
            String responseHeaderKey = conn.getHeaderFieldKey(i);
            String responseHeaderValue = conn.getHeaderField(i);
            sbResponseHeader.append(responseHeaderKey);
            sbResponseHeader.append(":");
            sbResponseHeader.append(responseHeaderValue);
            sbResponseHeader.append("\n");
        }
        return sbResponseHeader.toString();
    }

    public static String getReqeustHeader(HttpURLConnection conn) {
        Map<String, List<String>> requestHeaderMap = conn.getRequestProperties();
        Iterator<String> requestHeaderIterator = requestHeaderMap.keySet().iterator();
        StringBuilder sbRequestHeader = new StringBuilder();
        while (requestHeaderIterator.hasNext()) {
            String requestHeaderKey = requestHeaderIterator.next();
            String requestHeaderValue = conn.getRequestProperty(requestHeaderKey);
            sbRequestHeader.append(requestHeaderKey);
            sbRequestHeader.append(":");
            sbRequestHeader.append(requestHeaderValue);
            sbRequestHeader.append("\n");
        }
        return sbRequestHeader.toString();
    }

}
