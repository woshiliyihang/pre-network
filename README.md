# pre-network 网络预加框架

网络预加载框架，监听式网络前置加载框架-Network preload, network preload the framework.- pre-network

### 框架说明

pre-network：是基于观察者模式的网络预先前置加载框架，可以对大程度优化网络加载速度；每一个网络请求相当于被观察者，只要订阅了的观察者能够拿到被观察者的实例。

### 基本使用方法

添加依赖：
```
compile 'com.liyihang:pre-network:1.0.1'
```

每个被观察者网络都必须有一个string 唯一id标识他，观察者订阅也是通过唯一的id来找到被观察者然后订阅。
这里例如一个被观察者请求的i的是web_data；

首先创建被观察者网络请求；
代码如下：

```
PreNetworkHelper.getInstance()
                    .removeRequestObservable("web_data") // 首先清楚之前的相同id的被观察者请求移除，如果不移除相同id请求没有办法添加进入。
                    .addRequestObservable(new PNQuickRequest("web_data", "http://baidu.com/"));// 添加被观察者，添加后立即执行网络请求。
```


PNQuickRequest是被观察者的一个简单封装；可以看看代码：

```
package com.prenetwork.liyihang.lib_pre_network;

import java.util.Map;

/**
 * Created by liyihang on 18-1-16.
 */

public class PNQuickRequest extends PNRequestObservable {

    private String id;
    private String url;
    private String parms;
    private Map<String, String> header;

    public PNQuickRequest(String id, String url) {
        this.id = id;
        this.url = url;
        parms=null;
        header=null;
    }

    public PNQuickRequest(String id, String url, String parms) {
        this.id = id;
        this.url = url;
        this.parms = parms;
        header=null;
    }

    public PNQuickRequest(String id, String url, String parms, Map<String, String> header) {
        this.id = id;
        this.url = url;
        this.parms = parms;
        this.header = header;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Map<String, String> getRequestHeader() {
        return header;
    }

    @Override
    public String getRequestParms() {
        return parms;
    }

    @Override
    public String getRequestUrl() {
        return url;
    }

}

```

其中id是唯一id、url网络请求地址、parm网络请求参数、header网络请求请求头字段。

如何订阅请求代码如下：

```
PreNetworkHelper.getInstance().addObserver(new PNObserver() {
            @Override
            public void call(PNRequestObservable observable) {
                final String result = observable.getResult();// 获取网络请求内容 ， 这里发生在非ui线程中。observable就是被观察者实例
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //... do samething
                    }
                });
            }

            @Override
            public String getId() {
                return "web_data";// 唯一id
            }
        }).removeRequestObservable("web_data");// 请求用完了可以移除 ，如果不移除将会一直保存在内存中，注意： 这个步骤要到addObserver 
```


### 深入使用

基本上公司开发app都会自己封装自己的网络请求框架，pre-network 使用网络请求是基础的HttpURLConnection：

```
        executor.submit(new Runnable() {
            @Override
            public void run() {
                if ("GET".equals(getRequestMethod()))
                    result = PNGetPostUtil.sendGet(getRequestUrl(), getRequestParms(), getRequestHeader());

                if ("POST".equals(getRequestMethod()))
                    result = PNGetPostUtil.sendPost(getRequestUrl(), getRequestParms(), getRequestHeader());

                dataChange();
            }
        });
```

那么是用自己公司网络请求，首先要创建一个实现PNRequestObservable类的请求体，这里一继承okhttp为例子：

```
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
        builder.url("https://www.baidu.com/");
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

```

调用方法例如：

```
        PreNetworkHelper.getInstance()
                .removeAllObservable()// 删除所有请求
                .addRequestObservable(new MyRequestObservable())// 添加请求和执行
                .addObserver(new PNObserver() { //添加回调
                    @Override
                    public void call(PNRequestObservable observable) {
                        MyRequestObservable result= (MyRequestObservable) observable;
                        PNUtils.msg("end:"+result.getResult());
                    }

                    @Override
                    public String getId() {
                        return "request_id";
                    }
                })
                .removeAllObservable();
```

作者：一航
