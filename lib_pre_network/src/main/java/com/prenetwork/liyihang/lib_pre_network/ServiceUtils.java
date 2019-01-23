package com.prenetwork.liyihang.lib_pre_network;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import static android.content.Context.BIND_AUTO_CREATE;

public class ServiceUtils implements ServiceConnection {


    Runnable start, end;
    Activity context;
    String className;
    private CommonProvider sp;

    public void setStart(Runnable start) {
        this.start = start;
    }

    public void setEnd(Runnable end) {
        this.end = end;
    }

    public CommonProvider getSp() {
        return sp;
    }

    public ServiceUtils(Activity context, String name) {
        this.context = context;
        className=name;
        initService();
    }

    private void initService() {
        Intent intent=new Intent();
        intent.setComponent(new ComponentName(context.getPackageName(),className));
        context.bindService(intent,this, BIND_AUTO_CREATE);
    }

    public void releaseBind(){
        context.unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        sp = CommonProxy.asInterface(service);
        start.run();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        end.run();
        sp=null;
    }
}
