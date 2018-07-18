package com.chengxing.liyihang;

import android.support.v7.widget.RecyclerView;

public class CXPauseLoadingImgListener extends RecyclerView.OnScrollListener {

    private OnListImgLoaderListener imageLoader;

    public interface OnListImgLoaderListener{
        void resume();
        void pause();
    }

    public CXPauseLoadingImgListener(OnListImgLoaderListener imageLoader) {
        this.imageLoader = imageLoader;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);


        if(newState==RecyclerView.SCROLL_STATE_SETTLING){
            //end
            imageLoader.resume();
        }

        if (newState==RecyclerView.SCROLL_STATE_DRAGGING)
        {
            //move
        }

        if (newState==RecyclerView.SCROLL_STATE_IDLE)
        {
            //idle
            imageLoader.pause();
        }


    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }
}