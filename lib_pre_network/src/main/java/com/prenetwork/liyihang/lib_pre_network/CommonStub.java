package com.prenetwork.liyihang.lib_pre_network;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public abstract class CommonStub extends Binder implements CommonProvider {

    public static final String DESCRIPTOR="com.prenetwork.liyihang.lib_pre_network.CommonProvider";
    public static final int ACTION_1 = IBinder.FIRST_CALL_TRANSACTION;

    public CommonStub() {
        this.attachInterface(this, DESCRIPTOR);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code)
        {
            case INTERFACE_TRANSACTION:
            {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            case ACTION_1:
            {
                data.enforceInterface(DESCRIPTOR);
                String parm = data.readString();
                String jsonData = call(parm);
                reply.writeNoException();
                reply.writeString(jsonData);
                return true;
            }
        }
        return super.onTransact(code, data, reply, flags);
    }
}
