# pre-network 网络预加载工具集

#### 项目介绍

pre-network 是一款android网络框架，更加准确的说法是观察者模式预处理器；它主要的特点是预处理网络加载，设计思想是使用观察者模式的订阅式网络框架。
pre-network Network optimization framework


#### 软件架构

基于观察者模式的设计，每个网络请求相当于一个被观察者，每个使用这个网络请求地方只需要添加观察者，就可以获取网络请求数据，每个网络请求必须是唯一的，可以添加多个观察者。


#### 安装教程

1. 引用module
```

implementation project(':lib_pre_network')

```



#### 使用说明

app 使用dome模块

lib_pre_network 为核心库模块
