package com.chengxing.cxsdk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CXViewPagerSlide extends ViewPager {

    private boolean isSlide = true;

    public void setSlide(boolean slide) {
        isSlide = slide;
    }

    public CXViewPagerSlide(@NonNull Context context) {
        super(context);
    }

    public CXViewPagerSlide(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isSlide){
            return super.onInterceptTouchEvent(ev);
        }else{
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isSlide){
            return super.onTouchEvent(ev);
        }else {
            return true;// 可行,消费,拦截事件
        }
    }


}
