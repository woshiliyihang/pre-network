package com.chengxing.cxsdk;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class CXKeyHelper implements View.OnLayoutChangeListener {

    private Activity activity;

    private View activityRootView;
    private int screenHeight = 0;
    private int keyHeight = 0;

    private Runnable openRun;
    private Runnable closeRun;

    public void setOpenRun(Runnable openRun) {
        this.openRun = openRun;
    }

    public void setCloseRun(Runnable closeRun) {
        this.closeRun = closeRun;
    }

    public CXKeyHelper(Activity activity) {
        this.activity = activity;
    }

    public void pre() {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public void init(ViewGroup viewGroup) {
        activityRootView = viewGroup;
        screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
        keyHeight = screenHeight / 4;
    }

    public void onResume() {
        activityRootView.addOnLayoutChangeListener(this);
    }


    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            if (openRun!=null)
                openRun.run();
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            if (closeRun!=null)
                closeRun.run();
        }
    }
}
