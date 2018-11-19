package com.chengxing.liyihang;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PNDownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            PNVersionCheckUtils.installApk(context);
        } else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
            Intent viewDownloadIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
            viewDownloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(viewDownloadIntent);
        }

    }
}


//<receiver android:name=".PNDownloadReceiver">
//<intent-filter>
//<action android:name="android.intent.action.DOWNLOAD_COMPLETE" />
//<action android:name="android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED" />
//</intent-filter>
//</receiver>
//
//<provider
//            android:name="android.support.v4.content.FileProvider"
//                    android:authorities="com.chengxing.comchenxingnetapp.fileprovider"
//                    android:exported="false"
//                    android:grantUriPermissions="true">
//<meta-data
//        android:name="android.support.FILE_PROVIDER_PATHS"
//        android:resource="@xml/file_paths" />
//</provider>