package com.prenetwork.liyihang.lib_pre_network;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyihang on 18-1-17.
 */

public class PNRouter {

    private String TAG="PNRouter";
    public static final String ROUTER_AUGMENTS ="parm";
    private ArrayList<PhantomReference<Activity>> activityList;
    private ReferenceQueue<Activity> mReferenceQueue;
    private ArrayList<String> url;
    private ArrayList<String> parm;

    private String routerTag;

    public PNRouter(String routerTag) {
        this.routerTag = routerTag;
        mReferenceQueue=new ReferenceQueue<Activity>();
        activityList=new ArrayList<PhantomReference<Activity>>();
        url=new ArrayList<String>();
        parm=new ArrayList<String>();
    }

    public String getRouterTag() {
        return routerTag;
    }

    public PNRouter jump(Activity activity, String url, String parm){
        return jump(activity,url,parm,-1);
    }

    public PNRouter jump(Activity activity, String url, String parm, int animId){
        if (url==null)
            return this;
        Log.i(TAG,"jump page url=="+url);
        Log.i(TAG,"jump page parm=="+parm);
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setPackage(activity.getPackageName());
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("parm", parm);
        PackageManager packageManager=activity.getPackageManager();
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
        if (! resolveInfos.isEmpty())
        {
            activity.startActivity(intent);
            this.url.add(url);
            this.parm.add(parm);
        }else {
            Log.i(TAG,"no page");
        }
        return this;
    }

    public PNRouter back(Activity activity){
        if (this.url.size()<=1) {
            this.url.clear();
            this.parm.clear();
        }else {
            url.remove(url.size()-1);
            parm.remove(parm.size()-1);
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url.get(url.size()-1)));
            intent.setPackage(activity.getPackageName());
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("parm", parm.get(parm.size()-1));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(intent);
        }
        activity.finish();
        removeActivity(activity);
        return this;
    }

    public PNRouter jumpTopUrl(Activity activity){
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url.get(url.size()-1)));
        intent.setPackage(activity.getPackageName());
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("parm", parm.get(parm.size()-1));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
        return this;
    }


    public boolean isUrlExist(){
        boolean isExist=false;
        if (url.size()>0) {
            isExist=true;
        }
        return isExist;
    }


    public PNRouter addActivity(Activity activity){
        activityList.add(new PhantomReference<Activity>(activity, mReferenceQueue));
        return this;
    }

    public PNRouter removeAllActivity(){
        for (PhantomReference<Activity> item : activityList) {
            if (item.get()!=null) {
                item.get().finish();
                item.clear();
            }
        }
        activityList.clear();
        return this;
    }

    public void removeActivity(Activity activity){
        try {
            int index= -1;
            for (int i=0; i<activityList.size(); i++){
                if (activityList.get(i).get()!=null && activityList.get(i).get()==activity){
                    index = i;
                }
            }
            if (index!=-1){
                activityList.get(index).clear();
                activityList.remove(index);
                Log.i(TAG, "remove activity=="+index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PNRouter removeAllUrlAndParm(){
        url.clear();
        parm.clear();
        return this;
    }

}
