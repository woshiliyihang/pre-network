package com.prenetwork.liyihang.lib_pre_network;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by liyihang on 18-1-17.
 */

public abstract class PNBaseProvider extends ContentProvider {

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String outs=getOpenApiResult(values);
        if (outs!=null){
            return Uri.parse(outs);
        }
        return null;
    }

    public abstract String getOpenApiResult(ContentValues contentValues);

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}
