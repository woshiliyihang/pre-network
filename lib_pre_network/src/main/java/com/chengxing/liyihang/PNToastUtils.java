package com.chengxing.liyihang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.prenetwork.liyihang.lib_pre_network.PNUtils;

import java.lang.ref.WeakReference;

public class PNToastUtils {

    private static PNToastUtils manager = null;

    public static PNToastUtils getInstance() {
        synchronized (PNToastUtils.class) {
            if (manager == null) {
                manager = new PNToastUtils();
            }
        }
        return manager;
    }

    private PNToastUtils() {
    }

    private WeakReference<Context> contextWeakReference;

    public void init(Context context) {
        if (contextWeakReference == null)
            contextWeakReference = new WeakReference<>(context);
    }

    //方法一
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Toast mToast = null;
    private Object synObject = new Object();

    public void showToastByThread(String msg) {
        showToastByThread(contextWeakReference.get(), msg, Toast.LENGTH_LONG);
    }

    public void showToastByThread(Context context, int msg) {
        showToastByThread(context, context.getText(msg), Toast.LENGTH_SHORT);
    }

    public void showToastByThread(final Context context, final CharSequence msg, final int length) {
        PNUtils.runThread(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        synchronized (synObject) {
                            if (mToast != null) {
                                mToast.setText(msg);
                                mToast.setDuration(length);
                            } else {
                                mToast = Toast.makeText(context, msg, length);
                            }

                            mToast.show();
                        }
                    }
                });
            }
        });
    }

    //方法二:注意此方法不能再子线程中使用
    private long oneTime;
    private long twoTime;
    private String oldMsg;

    public void showToastByTime(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            mToast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (msg.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    mToast.show();
                }
            } else {
                oldMsg = msg;
                mToast.setText(msg);
                mToast.show();
            }
        }

        oneTime = twoTime;
    }

    public void showToastByTime(final String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                showToastByTime(contextWeakReference.get(), msg);
            }
        });
    }

    public void showToastByTime2(final Context context, final String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                showToastByTime(context, msg);
            }
        });
    }

    //方法三
    public TextView mTextView;
    public WindowManager mWindowManager = null;
    @SuppressLint("HandlerLeak")
    private Handler mPriHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            cancelToast();
        }
    };

    public void showToastByWindow(Context context, String msg) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        if (mTextView == null) {
            mTextView = new TextView(context);
        }
        mTextView.setText(msg);
        mTextView.setTextSize(20);
        mTextView.setPadding(0, 0, 0, 30);

        if (mTextView.getParent() == null) {
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.gravity = Gravity.BOTTOM;
            params.alpha = 0.65f;
            params.x = 0;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
            params.format = PixelFormat.TRANSLUCENT;
            params.windowAnimations = 0;
            mWindowManager.addView(mTextView, params);

            mPriHandler.sendEmptyMessageDelayed(101, 1000);
        } else {
            mTextView.setText(msg);

            mPriHandler.removeMessages(101);
            mPriHandler.sendEmptyMessageDelayed(101, 1000);
        }
    }

    public void cancelToast() {
        if (mTextView != null && mTextView.getParent() != null) {
            mWindowManager.removeView(mTextView);
        }
    }


}
