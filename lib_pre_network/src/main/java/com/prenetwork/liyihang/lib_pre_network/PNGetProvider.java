package com.prenetwork.liyihang.lib_pre_network;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

/**
 * Created by liyihang on 18-1-17.
 */

public class PNGetProvider {

    private String mergeUrl;
    private ContentResolver contentResolver;
    private String urlHost;
    protected Context context;
    protected String broadCastPath;

    public PNGetProvider(String urlHone, Context context, String broadCastPath) {
        this.broadCastPath=broadCastPath;
        this.urlHost = urlHone;
        this.context=context;
        mergeUrl="content://"+ urlHost;
        contentResolver=context.getContentResolver();
    }

    protected String getOpenApiResult(ContentValues contentValues){
        Uri insert = contentResolver.insert(Uri.parse(mergeUrl), contentValues);
        if (insert!=null)
        {
            return insert.toString();
        }
        return null;
    }

}
