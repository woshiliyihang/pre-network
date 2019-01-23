package com.prenetwork.liyihang.lib_pre_network;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PNViewHodlerUtils {

	@SuppressWarnings("unused")
	private int mPosition;
	private SparseArray<View> mViews;
	private View mConvertView;

	public static PNViewHodlerUtils get(Context context, View convertView,
                                        ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new PNViewHodlerUtils(context, parent, layoutId, position);
		} else {
			PNViewHodlerUtils holder = (PNViewHodlerUtils) convertView.getTag();
			holder.mPosition = position;
			return holder;
		}
	}

	public PNViewHodlerUtils(Context context, ViewGroup parent, int layoutId,
							 int position) {
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		mConvertView.setTag(this);
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends View> E getView(Object object, int id){
		try {
			if (object instanceof Activity) {
				return (E)(((Activity)object).findViewById(id));
			} else {
				return (E)(((View)object).findViewById(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ImageView mImageView(Object object, int id){
		ImageView imageView=getView(object, id);
		return imageView;
	}
	
	public static TextView mTextView(Object object, int id){
		TextView textView=getView(object, id);
		return textView;
	}

	public static Button mButton(Object object, int id){
		Button mView=getView(object, id);
		return mView;
	}

	public static RecyclerView mRecyclerView(Object object, int id){
		RecyclerView mView=getView(object, id);
		return mView;
	}
	
	public static RelativeLayout mRelativeLayout(Object object, int id){
		RelativeLayout relativeLayout=getView(object, id);
		return relativeLayout;
	}
	
	public static LinearLayout mLinearLayout(Object object, int id){
		LinearLayout relativeLayout=getView(object, id);
		return relativeLayout;
	}
	
	public static ViewGroup mViewGroup(Object object, int id){
		ViewGroup relativeLayout=getView(object, id);
		return relativeLayout;
	}

	public static FrameLayout mFrameLayout(Object object, int id){
		FrameLayout mView=getView(object, id);
		return mView;
	}

	public static GridLayout mGridLayout(Object object, int id){
		GridLayout mView=getView(object, id);
		return mView;
	}
	
	public ViewGroup getViewGroup(int id){
		return (ViewGroup)getView(id);
	}

	public TextView getTextView(int id) {
		return (TextView) getView(id);
	}

	public ImageView getImageView(int id) {
		return (ImageView) getView(id);
	}
	
	public LinearLayout getLinearLayout(int id){
		return (LinearLayout)getView(id);
	}
	
	public RelativeLayout getRelativeLayout(int id){
		return (RelativeLayout)getView(id);
	}

	public View getmConvertView() {
		return mConvertView;
	}

}
