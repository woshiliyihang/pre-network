package com.prenetwork.liyihang.lib_pre_network;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

public abstract class CommonService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new CommonStub() {
            @Override
            public String call(String jsonParm) throws RemoteException {
                return CommonService.this.call(jsonParm);
            }
        };
    }

    public abstract String call(String md);
}
