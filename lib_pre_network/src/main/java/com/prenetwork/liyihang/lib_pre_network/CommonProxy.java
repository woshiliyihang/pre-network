package com.prenetwork.liyihang.lib_pre_network;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public class CommonProxy implements CommonProvider {

    private IBinder binder;

    public CommonProxy(IBinder binder) {
        this.binder = binder;
    }

    public static CommonProvider asInterface(IBinder iBinder){
        if (iBinder==null)
            return null;
        IInterface iInterface = iBinder.queryLocalInterface(CommonStub.DESCRIPTOR);
        if (iInterface!=null && iInterface instanceof CommonProvider)
            return (CommonProvider)iInterface;
        return new CommonProxy(iBinder);
    }

    @Override
    public String call(String jsonParm) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        String result=null;
        try {
            data.writeInterfaceToken(CommonStub.DESCRIPTOR);
            data.writeString(jsonParm);
            binder.transact(CommonStub.ACTION_1, data, reply, 0);
            reply.readException();
            result=reply.readString();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            data.recycle();
            reply.recycle();
        }
        return result;
    }

    @Override
    public IBinder asBinder() {
        return binder;
    }
}
