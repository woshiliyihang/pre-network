package com.chengxing.cxsdk;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class CXLoadMoreListener extends RecyclerView.OnScrollListener {

    private int lastVisibleItem;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> testMovieAdapter;
    private Runnable runnable;
    private LinearLayoutManager linearLayoutManager;

    public CXLoadMoreListener(RecyclerView.Adapter<RecyclerView.ViewHolder> testMovieAdapter, LinearLayoutManager linearLayoutManager, Runnable runnable) {
        this.testMovieAdapter = testMovieAdapter;
        this.runnable = runnable;
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == testMovieAdapter.getItemCount()) {
            if (!isSlideToBottom(recyclerView))
                return;
            if (runnable!=null)
            {
                runnable.run();
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
    }

    public boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

}
