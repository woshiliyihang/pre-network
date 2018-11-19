package com.chengxing.liyihang;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.prenetwork.liyihang.lib_pre_network.PNUtils;
import com.prenetwork.liyihang.lib_pre_network.R;

import java.util.List;

public class PNLoadMoreAdapterHelper {

    public static final int TYPE_FOOTER=417;
    public static final int IS_BOTTOM=414;
    public static final int LOAD_MORE=444;
    public static final int NO_MORE=447;
    private int loadMoreStatus=0;

    private Context context;

    private int backColor;
    private FooterHolder footerHolder;

    public void setBackColor(int backColor) {
        this.backColor = backColor;
    }

    public PNLoadMoreAdapterHelper(Context context) {
        this.context = context;
    }


    public interface CXGetHolderListener{
        RecyclerView.ViewHolder getHolder();
    }

    public interface CXGetItemTypelistener{
        int getType();
    }

    public RecyclerView.ViewHolder getHolder(Context context,ViewGroup parent, int viewType, CXGetHolderListener holderListener){
        if (viewType==TYPE_FOOTER) {
            View contactView = LayoutInflater.from(context).inflate(R.layout.cx_load_more_footer, parent, false);
            footerHolder = new FooterHolder(contactView);
            return footerHolder;
        }else {
            return holderListener.getHolder();
        }
    }

    public void noFooterHandle(RecyclerView.ViewHolder holder,Runnable runnable){
        if (holder instanceof FooterHolder)
        {
            // do samething ....
        }
        else {
            runnable.run();
        }
    }

    public int getItemViewType(int position, int count, CXGetItemTypelistener itemTypelistener){
        if (position+1 == count)
        {
            return TYPE_FOOTER;
        }else {
            return itemTypelistener.getType();
        }
    }

    public void bind(RecyclerView.ViewHolder holder2, Runnable runnable){
        if (holder2 instanceof FooterHolder)
        {
            FooterHolder holder= (FooterHolder) holder2;
            if (loadMoreStatus==IS_BOTTOM)
            {
                holder.footer_font.setText(context.getResources().getString(R.string.lm_preper));
                isShowFooter(holder.itemView, true);
                holder.stopProgress();
            }else if (loadMoreStatus==LOAD_MORE)
            {
                holder.footer_font.setText(context.getResources().getString(R.string.lm_loading));
                isShowFooter(holder.itemView, true);
                holder.startProgress();
            }else if (loadMoreStatus==NO_MORE)
            {
//                holder.footer_font.setText(context.getResources().getString(R.string.lm_no_more));
                holder.footer_font.setText(null);
                isShowFooter(holder.itemView, false);
                holder.stopProgress();
            }
            if (backColor!=0) {
                holder.footer_layout.setBackgroundColor(context.getResources().getColor(backColor));
            }
        }else {
            runnable.run();
        }
    }

    private void isShowFooter(View itemView, boolean b) {
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.height=b? PNUtils.dp2px(itemView.getContext(), 37) : 0;
        itemView.setLayoutParams(layoutParams);
    }


    public void changeMoreStatus(int status) {
        loadMoreStatus = status;
    }

    public int getItemCount(List<?> mItemList){
        return mItemList.size()==0 ? 0:mItemList.size()+1;
    }

    public int getItemCount(int count){
        return count==0 ? 0:count+1;
    }

    public static class FooterHolder extends RecyclerView.ViewHolder
    {

        TextView footer_font;
        LinearLayout footer_layout;
        ProgressBar bar;
        View itemView;

        public FooterHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            footer_font=itemView.findViewById(R.id.footer_font);
            footer_layout=itemView.findViewById(R.id.footer_layout);
            bar=itemView.findViewById(R.id.indicator);
            bar.setVisibility(View.GONE);
        }

        public void startProgress(){
            if (bar.getVisibility()==View.GONE){
                bar.setVisibility(View.VISIBLE);
            }
        }

        public void stopProgress(){
            if (bar.getVisibility()==View.VISIBLE){
                bar.setVisibility(View.GONE);
            }
        }

    }
}
