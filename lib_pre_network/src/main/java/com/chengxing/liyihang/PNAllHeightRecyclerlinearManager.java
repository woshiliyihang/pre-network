package com.chengxing.liyihang;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.prenetwork.liyihang.lib_pre_network.PNUtils;

public class PNAllHeightRecyclerlinearManager extends LinearLayoutManager {

    public int height;
    Context context;

    public PNAllHeightRecyclerlinearManager(Context context) {
        super(context);
        this.context=context;
        height= PNUtils.dp2px(context, 260);
    }

    @Override
    public void onMeasure(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int widthSpec, int heightSpec) {
        int count = state.getItemCount();
        if (count > 0) {
            height= PNUtils.dp2px(context, 260);
            int realHeight = 0;
            int realWidth = 0;
            for(int i = 0;i < count; i++){
                View view = recycler.getViewForPosition(0);
                if (view != null) {
                    measureChild(view, widthSpec, heightSpec);
                    int measuredWidth = View.MeasureSpec.getSize(widthSpec);
                    int measuredHeight = view.getMeasuredHeight();
                    realWidth = realWidth > measuredWidth ? realWidth : measuredWidth;
                    realHeight += measuredHeight;
                }
                setMeasuredDimension(realWidth, realHeight);
            }
            height = realHeight;
        } else {
            super.onMeasure(recycler, state, widthSpec, heightSpec);
        }
    }
}
