package com.chengxing.liyihang;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 获取系统最新图片信息
 */
public class PNGetImgUtils {

    public static final String cameraPath="/DCIM/Camera";
    public static final String screentShotsPath="/DCIM/Screenshots";
    public static final String screentPicturePath="/Pictures/Screenshots";

    public static class ImgBean{
        public long mTime;
        public String imgUrl;

        public ImgBean(long mTime, String imgUrl) {
            this.mTime = mTime;
            this.imgUrl = imgUrl;
        }

        @Override
        public String toString() {
            return "ImgBean{" +
                    "mTime=" + mTime +
                    ", imgUrl='" + imgUrl + '\'' +
                    '}';
        }
    }

    public static List<ImgBean> getLatestPhoto(Context context, int limit) {
        //拍摄照片的地址
        String CAMERA_IMAGE_BUCKET_NAME = Environment.getExternalStorageDirectory().toString() + cameraPath;
        //截屏照片的地址
        String SCREENSHOTS_IMAGE_BUCKET_NAME = getScreenshotsPath();
        //拍摄照片的地址ID
        String CAMERA_IMAGE_BUCKET_ID = getBucketId(CAMERA_IMAGE_BUCKET_NAME);
        //截屏照片的地址ID
        String SCREENSHOTS_IMAGE_BUCKET_ID = getBucketId(SCREENSHOTS_IMAGE_BUCKET_NAME);
        //查询路径和修改时间
        String[] projection = {MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_MODIFIED};
        //
        String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
        //
        String[] selectionArgs = {CAMERA_IMAGE_BUCKET_ID};
        String[] selectionArgsForScreenshots = {SCREENSHOTS_IMAGE_BUCKET_ID};

        //检查camera文件夹，查询并排序
        List<ImgBean> imgBeans=new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                MediaStore.Files.FileColumns.DATE_MODIFIED + " DESC");
        int index=0;
        while (cursor.moveToNext()) {
            if (index>=limit)
                break;
            long mtime=cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED));
            String imgUrl=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            imgBeans.add(new ImgBean(mtime, imgUrl));
            index++;
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        //检查Screenshots文件夹
        //查询并排序
        cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgsForScreenshots,
                MediaStore.Files.FileColumns.DATE_MODIFIED + " DESC");

        index=0;
        while (cursor.moveToNext()) {
            if (index>=limit)
                break;
            long mtime=cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED));
            String imgUrl=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            imgBeans.add(new ImgBean(mtime, imgUrl));
            index++;
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        //排序对比时间
        Collections.sort(imgBeans, new Comparator<ImgBean>() {
            @Override
            public int compare(ImgBean imgBean, ImgBean t1) {
                return (int) (t1.mTime-imgBean.mTime);
            }
        });
        List<ImgBean> imgBeans1=new ArrayList<>();
        index=0;
        for (ImgBean imgBean : imgBeans) {
            if (index>=limit)
                break;
            imgBeans1.add(imgBean);
            index++;
        }
        return imgBeans1;
    }

    /**
     * 获取相册中最新一张图片
     *
     * @param context
     * @return
     */
    public static List<ImgBean> getLatestPhoto(Context context) {
        return getLatestPhoto(context,1);
    }

    private static String getBucketId(String path) {
        return String.valueOf(path.toLowerCase().hashCode());
    }

    /**
     * 获取截图路径
     * @return
     */
    public static String getScreenshotsPath(){
        String path = Environment.getExternalStorageDirectory().toString() + screentShotsPath;
        File file = new File(path);
        if(!file.exists()){
            path = Environment.getExternalStorageDirectory().toString() + screentPicturePath;
        }
        file = null;
        return path;
    }
}
