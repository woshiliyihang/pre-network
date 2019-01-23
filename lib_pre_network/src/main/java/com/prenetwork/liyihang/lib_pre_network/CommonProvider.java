package com.prenetwork.liyihang.lib_pre_network;

import android.os.IInterface;
import android.os.RemoteException;

public interface CommonProvider extends IInterface {
    String call(String jsonParm) throws RemoteException;
}
