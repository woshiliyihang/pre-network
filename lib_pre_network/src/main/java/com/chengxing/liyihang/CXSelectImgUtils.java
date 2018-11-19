package com.chengxing.liyihang;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;

import com.prenetwork.liyihang.lib_pre_network.PNUtils;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

public class CXSelectImgUtils {

    private CXBottomAnimDialog cxBottomAnimDialog;
    private Activity activity;

    private String imgUrl;

    private boolean isPhotoZoom=true;

    //是否开启剪裁
    public void setPhotoZoom(boolean photoZoom) {
        isPhotoZoom = photoZoom;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private CXLoadingUtils loadingUtils;

    public CXLoadingUtils getLoadingUtils() {
        return loadingUtils;
    }

    public void setLoadingUtils(CXLoadingUtils loadingUtils) {
        this.loadingUtils = loadingUtils;
    }

    public CXSelectImgUtils(Activity activity) {
        this.activity = activity;
        final String[] arr=new String[]{"拍照", "图库"};
        cxBottomAnimDialog = new CXBottomAnimDialog(activity, arr);
        cxBottomAnimDialog.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s= (String) view.getTag();
                if (TextUtils.equals(s,arr[0]))
                {
                    cameraSelect();
                }
                if (TextUtils.equals(s,arr[1]))
                {
                    photoSelect();
                }
            }
        });
    }

    //调用系统相册-选择图片
    private static final int IMAGE = 174;
    public static final int REQUEST_VIDEO_CODE=98;

    public void photo(){
        cxBottomAnimDialog.show();
    }

    public void photoSelect() {
        //调用相册
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, IMAGE);
    }

    public void videoSelect() {
        //调用视频
        videoPath=null;
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, REQUEST_VIDEO_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode==CAMERA_REQUEST_CODE && resultCode== RESULT_OK) {
            if (isPhotoZoom) {
                startPhotoZoom(mCameraFile);
            }else {
                setImgPhoto(mCameraFile.getAbsolutePath());
            }
            return;
        }

        if (requestCode==RESULT_REQUEST_CODE && resultCode== RESULT_OK)
        {
            setImgUrl(mCropFile.getAbsolutePath());
            PNUtils.runOnUI(new Runnable() {
                @Override
                public void run() {
                    uploadTokenAndUploadHeaderIcon();
                }
            });
            return;
        }

        //获取图片路径
        if (requestCode == IMAGE && resultCode == RESULT_OK && data != null) {
            setImgUrl(null);

            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = activity.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            imgUrl = c.getString(columnIndex);
            c.close();

            if (imgUrl!=null) {
                loadingUtils.openLoading();
                PNUtils.runThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isPhotoZoom) {
                            startPhotoZoom(new File(imgUrl));
                        }else {
                            setImgPhoto(imgUrl);
                        }
                    }
                });
            }
        }

        //选择视频
        if (requestCode ==REQUEST_VIDEO_CODE  && resultCode == RESULT_OK && null != data)
        {
            Uri selectedVideo = data.getData();
            String[] filePathColumn = {MediaStore.Video.Media.DATA};

            Cursor cursor = activity.getContentResolver().query(selectedVideo,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String VIDEOPATH = cursor.getString(columnIndex);
            cursor.close();
            videoPath=VIDEOPATH;
            PNUtils.msg("video path:"+videoPath);
            File file=new File(videoPath);
            if (file.exists())
            {
                long length = file.length();
                long m=length/1000/1000;
                int max=200;
                if (m>max)
                {
                    PNUtils.toast(String.format("超过最大上传值", String.valueOf(max)));
                }else {
                    uploadTokenAndUploadHeaderIcon2();
                }
            }else {
                PNUtils.toast("文件不存在");
            }
        }
    }

    public void setImgPhoto(String url){
        if (TextUtils.isEmpty(url))
            return;
        loadingUtils.openLoading();
        PNUtils.samplingRateCompress(url, mGalleryFile, 720);
        setImgUrl(mGalleryFile.getAbsolutePath());
        PNUtils.runOnUI(new Runnable() {
            @Override
            public void run() {
                uploadTokenAndUploadHeaderIcon();
            }
        });
    }

    private Runnable getVideoPathRun;

    public void setOnGetVideoPathListener(Runnable getVideoPath) {
        this.getVideoPathRun = getVideoPath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    private String videoPath;

    public static class CXTokenBean extends PNBean {

        public String uploadToken;
        public String bindDomain;

    }

    public interface OnUpdateImgUrlListener{
        void onResult(String url);
    }

    public OnUpdateImgUrlListener updateImgUrlListener;

    public void setUpdateImgUrlListener(OnUpdateImgUrlListener updateImgUrlListener) {
        this.updateImgUrlListener = updateImgUrlListener;
    }

    public CXTokenBean tokenBean;
    public CXTokenBean tokenBean2;

    public void uploadTokenAndUploadHeaderIcon2(){
        if (tokenBean2!=null)
        {
            uploadFile2(activity, tokenBean2);
            return;
        }
        loadingUtils.openLoading();
        //获取token
        uploadFile2(activity, tokenBean2);
        //loadingUtils.closeLoading();
    }

    public void uploadTokenAndUploadHeaderIcon() {
        if (tokenBean!=null)
        {
            uploadFile(activity,tokenBean);
            return;
        }
        loadingUtils.openLoading();
        //获取token
        uploadFile(activity ,tokenBean);
        //loadingUtils.closeLoading();
    }

    private String videoPath2;
    private String videoImg;
    private String videoLength;

    public String getVideoLength() {
        return videoLength;
    }

    public String getVideoImg() {
        return videoImg;
    }

    public String getVideoPath2() {
        return videoPath2;
    }

    protected void uploadFile2(final Context context, final CXTokenBean tokenBean) {
        File file=new File(videoPath);
        loadingUtils.openLoading();
        //上传文件
        if (getVideoPathRun !=null)
            getVideoPathRun.run();
        //loadingUtils.closeLoading();
    }

    protected void uploadFile(final Context context, final CXTokenBean tokenBean) {
        File file=new File(getImgUrl());
        loadingUtils.openLoading();
        if (updateImgUrlListener!=null)
            updateImgUrlListener.onResult("img url");
        //loadingUtils.closeLoading();
    }


    //选择照片
    String packageName="com.chengxing.comchenxingnetapp";
    File mCameraFile = new File(Environment.getExternalStorageDirectory(), "IMAGE_FILE_NAME.jpg");//照相机的File对象
    File mCropFile = new File(Environment.getExternalStorageDirectory(), "PHOTO_FILE_NAME.jpg");//裁剪后的File对象
    File mGalleryFile = new File(Environment.getExternalStorageDirectory(), "IMAGE_GALLERY_NAME.jpg");//相册的File对象


    private static final int IMAGE_REQUEST_CODE = 100;
    private static final int SELECT_PIC_NOUGAT = 101;
    private static final int RESULT_REQUEST_CODE = 102;
    private static final int CAMERA_REQUEST_CODE = 104;


    public void cameraSelect(){


        if (mCameraFile.exists())
            mCameraFile.delete();

        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0及以上
            Uri uriForFile = FileProvider.getUriForFile(activity, packageName+".FileProvider", mCameraFile);
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            intentFromCapture.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
            intentFromCapture.addFlags(FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCameraFile));
        }
        activity.startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
    }


    /**
     * 裁剪图片方法实现
     *
     */
    public void startPhotoZoom(File mCameraFile) {
        Uri inputUri;

        if (mCropFile.exists())
            mCropFile.delete();

        PNUtils.samplingRateCompress(mCameraFile.getAbsolutePath(), mGalleryFile, 720);

        Intent intent = new Intent("com.android.camera.action.CROP");

        // 设置裁剪
        intent.putExtra("crop", true);
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 147);
        intent.putExtra("aspectY", 144);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("scale",true);
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", true);//去除默认的人脸识别，否则和剪裁匡重叠
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 图片格式

        //sdk>=24
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Uri outPutUri = FileProvider.getUriForFile(activity, packageName+".FileProvider", mCropFile);
            inputUri = FileProvider.getUriForFile(activity, packageName+".FileProvider", mGalleryFile);//通过FileProvider创建一个content类型的Uri
            intent.setDataAndType(inputUri, "image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
            intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(FLAG_GRANT_WRITE_URI_PERMISSION);

            //重要的一步，使用grantUriPermission来给对应的包提升读写指定uri的临时权限。否则即使调用成功，也会保存裁剪照片失败。
            List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                activity.grantUriPermission(packageName, outPutUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

        } else {
            Uri outPutUri = Uri.fromFile(mCropFile);
//            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
////                String url = GetImagePath.getPath(this, inputUri);//这个方法是处理4.4以上图片返回的Uri对象不同的处理方法
//                intent.setDataAndType(inputUri, "image/*");
//            } else {
                inputUri = Uri.fromFile(mGalleryFile);
                intent.setDataAndType(inputUri, "image/*");
//            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
        }


        activity.startActivityForResult(intent, RESULT_REQUEST_CODE);//这里就将裁剪后的图片的Uri返回了
    }





}
