package com.chengxing.liyihang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import static android.content.Context.LOCATION_SERVICE;

public class PNLocationUtils implements LocationListener {

    public static final String tag= PNLocationUtils.class.getSimpleName();
    private Context context;
    public static final int LOCATION_RESULT=1978;
    private LocationManager manager;
    private Handler.Callback callback;

    public PNLocationUtils(Context context) {
        this.context = context;
        callback= (Handler.Callback) context;

        manager=(LocationManager)context.getSystemService(LOCATION_SERVICE);
        boolean providerEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.i(tag,"location provider enabled:"+providerEnabled);
    }

    @SuppressLint("MissingPermission")
    public void getLocation() {
        try {
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(tag,"getLocation error:"+e.getMessage());
        }
    }

    @SuppressLint("MissingPermission")
    public void release(){
        try {
            manager.removeUpdates(this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(tag,"release error:"+e.getMessage());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Message message=new Message();
        message.what=LOCATION_RESULT;
        message.obj=location;
        callback.handleMessage(message);
        Log.i(tag,"onLocationChanged:"+location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
