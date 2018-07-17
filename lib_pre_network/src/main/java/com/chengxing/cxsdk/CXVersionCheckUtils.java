package com.chengxing.cxsdk;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;

public class CXVersionCheckUtils {

    private static File saveFile;
    private Activity activity;
    private static long downloadId = 0;

    public CXVersionCheckUtils(Activity activity) {
        this.activity = activity;
        initFile();
    }

    private void initFile() {
        if (saveFile==null)
            saveFile=new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "filiday.apk");
    }

    public void start(String url, String t, String d){
        if (downloadId!=0) {
            clearCurrentTask(downloadId);
        }
        downloadId=download(url,t,d);
    }

    public long download(String url, String title, String desc) {
        Uri uri = Uri.parse(url);
        DownloadManager.Request req = new DownloadManager.Request(uri);
        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        if (saveFile.exists()) {
            saveFile.delete();
        }
        req.setDestinationUri(Uri.fromFile(saveFile));
        req.setTitle(title);
        req.setDescription(desc);
        req.setMimeType("application/vnd.android.package-archive");
        DownloadManager dm = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        return dm.enqueue(req);
    }

    public void clearCurrentTask(long downloadId) {
        DownloadManager dm = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        try {
            dm.remove(downloadId);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }

    }

    public static void installApk(Context context) {
        downloadId=0;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            String[] command = {"chmod", "777", saveFile.getAbsolutePath()};
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.chengxing.comchenxingnetapp.fileprovider", saveFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(saveFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }


}
