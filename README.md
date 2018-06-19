# pre-network

#### 项目介绍
pre-network是一款android网络框架，更加准确的说法是观察者模式预处理器；它主要的特点是预处理网络加载，设计思想是使用观察者模式的订阅式网络框架。


#### 软件架构
基于观察者模式的设计，每个网络请求相当于一个被观察者，每个使用这个网络请求地方只需要添加观察者，就可以获取网络请求数据，每个网络请求必须是唯一的，可以添加多个观察者。


#### 安装教程

1. 引用依赖
```
implementation 'com.liyihang:pre-network:1.3.0'
```



#### 使用说明

1. 发起网络请求
dome当中MainActivity点击跳页按钮打开新activity时候首先执行网络请求，然后执行跳转。

```java
        Map<String, String> headers=new HashMap<>();
        headers.put("headerInfo", "test");
		//第一个参数 是标识网络请求的唯一id，第二个参数是url， 第三个参数是请求参数，第四个参数是请求头部，第五个参数是请求方式。
        PreNetworkHelper.getInstance().addRequestObservable(new PNQuickRequest(url_id_only, "https://blog.csdn.net/mhhyoucom/","name=liyihang&age=18", headers, "GET"));
```

addRequestObservable 是简单封装唯一网络请求，可以根据项目请求使用自己的网络框架。这样网路请求将立即执行。


2. 订阅这个网络请求
dome中的OtherActivity就是要订阅这个网络请求。使用下面代码订阅：
```java
        PreNetworkHelper.getInstance().addObserver(new UpdateUI(this));
```

UpdateUI 就是订阅者；

```java
public class UpdateUI extends PNBaseObserver {

    private WeakReference<OtherActivity> activity;

    public UpdateUI(OtherActivity activity) {
        this.activity = new WeakReference<>(activity);
    }

    @Override
    public void pre() {//订阅回调开始
        // 删除网络请求被观察者，如果不删除一直保存在内存中
        PreNetworkHelper.getInstance().removeRequestObservable(MainActivity.url_id_only);
    }

    @Override
    public void result(String res) {//网络请求成功处理函数
        if (activity.get()==null)
            return;
        activity.get().sendStateSelf(PNBaseActivity.getMsgObj(10, res));
    }

    @Override
    public void error(String err) {//网络请求失败处理函数
        Toast.makeText(activity.get(), err, Toast.LENGTH_LONG).show();
    }

    @Override
    public void end() {// 订阅回调结束

    }

    @Override
    public String getId() {
        return MainActivity.url_id_only;//要订阅的网络请求唯一id
    }
}
```

所有回调都在UI线程中。




#### 参与贡献

1. 李一航，Jason li


#### 邮箱反馈

邮箱：mhh.you@hotmail.com