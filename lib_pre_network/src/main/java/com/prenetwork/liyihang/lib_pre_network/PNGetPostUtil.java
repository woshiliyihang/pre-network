package com.prenetwork.liyihang.lib_pre_network;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * Created by liyihang on 18-1-16.
 */

public class PNGetPostUtil {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url    发送请求的URL
     * @param params 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String sendGet(String url, String params, Map<String , String> header) {
        return send(url, params, header, "GET");
    }

    /**
     * 向指定URL发送POST方法的请求
     *
     * @param url    发送请求的URL
     * @param params 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String sendPost(String url, String params, Map<String , String> header) {
        return send(url, params, header, "POST");
    }

    private static void handleConn(HttpURLConnection conn) {
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
        conn.setRequestProperty("Accept-Language",
                "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        conn.setRequestProperty("user-agent",
                "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36");
    }

    public static String send(String url, String params, Map<String , String> header, String method) {
        String result=null;
        HttpURLConnection conn=null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = (HttpURLConnection) realUrl.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(20000);
            conn.setRequestMethod(method);
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 设置通用的请求属性
//            handleConn(conn);

            if (header!=null)
            {
                Set<String> strings = header.keySet();
                for (String string : strings) {
                    conn.setRequestProperty(string, header.get(string));
                }
            }

            if (params!=null)
            {
                // 获取URLConnection对象对应的输出流
                PrintWriter out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(params);
                // flush输出流的缓冲
                out.flush();
                out.close();
            }

            // 定义BufferedReader输入流来读取URL的响应
            if (conn.getResponseCode()==200
                    || conn.getResponseCode() == 302
                    || conn.getResponseCode() == 301){
                result = getBytesByInputStream(conn.getInputStream());
            }
        } catch (Exception e) {
            Log.i("url_connection","发送GET请求出现异常！" + e.getMessage());
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            if (conn!=null)
            {
                conn.disconnect();
            }
        }
        return result;
    }


    //从InputStream中读取数据，转换成byte数组，最后关闭InputStream
    public static String getBytesByInputStream(InputStream is) throws Exception {
        String bytes;
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        int len=-1;
        byte[] arr=new byte[1024];
        while ( (len=is.read(arr))!=-1 )
        {
            outputStream.write(arr, 0, len);
        }
        bytes=outputStream.toString();
        outputStream.flush();
        outputStream.close();
        is.close();
        return bytes;
    }


    //读取响应头
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

    //读取请求头
    public static String getReqeustHeader(HttpURLConnection conn) {
        //https://github.com/square/okhttp/blob/master/okhttp-urlconnection/src/main/java/okhttp3/internal/huc/HttpURLConnectionImpl.java#L236
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
