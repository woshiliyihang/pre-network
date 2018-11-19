package com.chengxing.liyihang;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public abstract class PNComHeaderFooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private PNLoadMoreAdapterHelper helper;
    View headerView;
    Context context;

    public static final int headerViewType=146;

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
    }

    public View getHeaderView() {
        return headerView;
    }

    public PNComHeaderFooterAdapter(Context context) {
        this.context = context;
        helper=new PNLoadMoreAdapterHelper(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        if (i==headerViewType && getHeaderView()!=null)
            return new HeaderViewHolder(headerView);
        return helper.getHolder(viewGroup.getContext(), viewGroup, i, new PNLoadMoreAdapterHelper.CXGetHolderListener() {
            @Override
            public RecyclerView.ViewHolder getHolder() {
                return createHolder(viewGroup, i);
            }
        });
    }

    public abstract RecyclerView.ViewHolder createHolder(ViewGroup viewGroup, int type);

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if (holder instanceof HeaderViewHolder) {
            //todo same thing
        }else {
            int afterPos = i;
            if (getHeaderView()!=null) {
                afterPos=i-1;
            }
            final int finalAfterPos = afterPos;
            helper.bind(holder, new Runnable() {
                @Override
                public void run() {
                    bindHolder(holder, i, finalAfterPos);
                }
            });
        }
    }

    public abstract void bindHolder(RecyclerView.ViewHolder holder, int realPos, int afterPos);

    @Override
    public int getItemCount() {
        if (getHeaderView()!=null)
            return helper.getItemCount(getCount())+1;
        return helper.getItemCount(getCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0 && getHeaderView()!=null) {
            return headerViewType;
        }else {
            return helper.getItemViewType(position, getItemCount(), new PNLoadMoreAdapterHelper.CXGetItemTypelistener() {
                @Override
                public int getType() {
                    return PNComHeaderFooterAdapter.this.getType();
                }
            });
        }
    }
    
    public abstract int getType();

    public abstract int getCount();

    public void changeMoreStatus(int status) {
        helper.changeMoreStatus(status);
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder
    {
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
