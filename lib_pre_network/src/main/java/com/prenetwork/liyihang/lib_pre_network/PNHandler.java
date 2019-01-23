package com.prenetwork.liyihang.lib_pre_network;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


public class PNHandler extends Handler {

	
	boolean isStop = true;
	
	public PNHandler() {
		super();
		init();
	}

	public boolean isStop() {
		return isStop;
	}

	public void setStop(boolean stop) {
		isStop = stop;
	}

	public PNHandler(Callback callback){
		super(callback);
		init();
	}

	public PNHandler(Looper looper){
		super(looper);
		init();
	}

	private void init(){
		isStop = false;
	}
	
	public void mPost(Runnable runnable){
		if (isStop) {
			return;
		}
		post(runnable);
	}
	
	public void mPostAtTime(Runnable runnable, long mTime){
		if (isStop) {
			return;
		}
		postDelayed(runnable, mTime);
	}
	
	@Override
	public void dispatchMessage(Message msg) {
		if (isStop) {
			return;
		}
		super.dispatchMessage(msg);
	}

	@Override
	public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
		if (isStop) {
			return false;
		}
		return super.sendMessageAtTime(msg, uptimeMillis);
	}

	@Override
	public void handleMessage(Message msg) {
		if (isStop) {
			return;
		}
		super.handleMessage(msg);
	}
	
	public void cancel(){
		if (isStop==false) {
			isStop=true;
		}
		removeCallbacksAndMessages(null);
	}
	
}
