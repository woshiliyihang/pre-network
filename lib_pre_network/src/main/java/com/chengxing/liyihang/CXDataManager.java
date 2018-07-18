package com.chengxing.liyihang;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CXDataManager extends SQLiteOpenHelper {

    private static CXDataManager manager=null;

    public static CXDataManager getInstance(Context context) {
        synchronized (CXDataManager.class) {
            if (manager==null) {
                manager=new CXDataManager(context);
            }
        }
        return manager;
    }

    public synchronized static CXDataManager getInstance() {
        return manager;
    }

    private Context context;

    private CXDataManager(Context context) {
        super(context, "cx_database", null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS newsinfo (cid integer primary key autoincrement, keyid VARCHAR(240), jsonstr TEXT, mtime BIGINT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int getDataSize(){
        int nums=0;
        SQLiteDatabase writableDatabase = getWritableDatabase();

        Cursor cursor = writableDatabase.rawQuery("select count(*) as nums from newsinfo", new String[]{});

        while (cursor.moveToNext())
        {
            nums = cursor.getInt(cursor.getColumnIndex("nums"));
        }

        writableDatabase.close();
        return nums;
    }

    public CXDataCacheBean getCache(String keyid){
        CXDataCacheBean bean=null;
        SQLiteDatabase writableDatabase = getWritableDatabase();

        Cursor cursor = writableDatabase.rawQuery("select *,count(*) as nums from  newsinfo where keyid=?", new String[]{keyid});

        while (cursor.moveToNext())
        {
            int nums = cursor.getInt(cursor.getColumnIndex("nums"));
            int newsid = cursor.getInt(cursor.getColumnIndex("cid"));
            String kid = cursor.getString(cursor.getColumnIndex("keyid"));
            String jsonstr = cursor.getString(cursor.getColumnIndex("jsonstr"));
            long mtime=cursor.getLong(cursor.getColumnIndex("mtime"));
            bean=new CXDataCacheBean(newsid,kid, jsonstr, mtime, nums);
        }

        writableDatabase.close();
        return bean;
    }

    public CXDataManager delCache(String keyid){
        SQLiteDatabase writableDatabase = getWritableDatabase();

        writableDatabase.execSQL("delete from newsinfo where keyid=?", new String[]{keyid});

        writableDatabase.close();
        return this;
    }

    public CXDataManager clearCache(){
        SQLiteDatabase writableDatabase = getWritableDatabase();

        writableDatabase.execSQL("delete from newsinfo");

        writableDatabase.close();
        return this;
    }

    public CXDataManager insertCache(String keyid, String jsonstr){
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();

        writableDatabase.execSQL("delete from newsinfo where keyid=?", new String[]{keyid});
        writableDatabase.execSQL("insert into newsinfo(keyid, jsonstr, mtime) values(?,?,?)", new String[]{keyid, jsonstr, String.valueOf(System.currentTimeMillis())});

        writableDatabase.setTransactionSuccessful();
        writableDatabase.endTransaction();
        writableDatabase.close();
        return this;
    }
}
