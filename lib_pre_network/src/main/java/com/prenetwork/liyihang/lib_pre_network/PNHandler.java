package com.prenetwork.liyihang.lib_pre_network;

import android.os.Handler;
import android.os.Message;


/**
 * 
 * 
 * handler 控制类
 * @author user
 *
 */
public class PNHandler extends Handler {

	
	boolean isStop = true;
	
	public PNHandler() {
		super();
		init();
	}

	public PNHandler(Callback callback){
		super(callback);
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
		// TODO Auto-generated method stub
		if (isStop) {
			return;
		}
		super.dispatchMessage(msg);
	}

	@Override
	public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
		// TODO Auto-generated method stub
		if (isStop) {
			return false;
		}
		return super.sendMessageAtTime(msg, uptimeMillis);
	}

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
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
