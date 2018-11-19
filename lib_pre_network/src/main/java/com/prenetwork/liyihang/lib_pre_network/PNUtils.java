package com.prenetwork.liyihang.lib_pre_network;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static android.content.pm.PackageManager.GET_SIGNATURES;

/**
 * Created by liyihang on 18-1-17.
 */

public class PNUtils {


    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("cx_config", Context.MODE_PRIVATE);
    }

    public static long[] getRemainingTime(long sysTime){
        long m = 1000L;
        long s = 60 * 1000L;
        long h = 60 * 60 * 1000L;
        long d = 24 * 60 * 60 * 1000L;
        long H = (long) (30.4 * 24 * 60 * 60 * 1000L);
        long y = (long) (365.2 * 24 * 60 * 60 * 1000L);
        // sys time----
        long sysY = (long) (sysTime / y);
        long sysH = (long) ((sysTime % y) / H);
        long sysd = (long) ((sysTime % H) / d);
        long sysh = (long) ((sysTime % d) / h);
        long sysS = (long) ((sysTime % h) / s);
        long sysM = (long) ((sysTime % s) / m);
        long[] outs={sysY, sysH, sysd, sysh, sysS, sysM};
        return outs;
    }

    public static boolean savePhotoToSDCard(Bitmap photoBitmap, String path, String photoName) {
        boolean ret = false;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File photoFile = new File(path, photoName + ".jpg");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(photoFile);
            if (photoBitmap != null) {
                if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
                    fileOutputStream.flush();
                    ret = true;
                }
            }
        } catch (FileNotFoundException e) {
            photoFile.delete();
            e.printStackTrace();
        } catch (IOException e) {
            photoFile.delete();
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }


    public static String numAddZero(int nums){
        if (nums>=10) {
            return nums + "";
        }else {
            return "0"+nums;
        }
    }


    public static String changeToPPTime(long targetTime) {
        String outString = null;
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM-dd");
            long sysTime = System.currentTimeMillis() - targetTime;
            long s = 60 * 1000L;
            long h = 60 * 60 * 1000L;
            long d = 24 * 60 * 60 * 1000L;
            long H = (long) (30.4 * 24 * 60 * 60 * 1000L);
            long y = (long) (365.2 * 24 * 60 * 60 * 1000L);
            // sys time----
            long sysY = (long) (sysTime / y);
            long sysH = (long) ((sysTime % y) / H);
            long sysd = (long) ((sysTime % H) / d);
            long sysh = (long) ((sysTime % d) / h);
            long sysS = (long) ((sysTime % h) / s);
            if (sysY >= 1) {
                outString = simpleDateFormat.format(new Date(targetTime));
            } else if (sysY == 0 && sysH >= 1) {
                outString = simpleDateFormat2.format(new Date(targetTime));
            } else if (sysH == 0 && sysd >= 1) {
                outString = sysd + " day";
            } else if (sysd == 0 && sysh >= 1) {
                outString = sysh + " hour";
            } else if (sysh == 0 && sysS >= 1) {
                outString = sysS + "minute";
            } else {
                outString = " now";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outString;
    }


    public static String mergeParm(String... arrStrings){
        String outString=null;
        try {
            if (arrStrings.length>=2) {
                int cout = arrStrings.length/2;
                JSONObject jsonObject=new JSONObject();
                for (int i = 0; i < cout; i++) {
                    int index = i*2;
                    int lastIndex = index+1;
                    jsonObject.put(arrStrings[index], arrStrings[lastIndex]);
                }
                outString=jsonObject.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outString;
    }


    public static String  mergeStringArr(String[] arr, String fg){
        if (arr==null || arr.length==0){
            return "";
        }
        String outs="";
        try {
            outs=arr[0];
            for (int i=1; i<arr.length; i++){
                outs+=fg+""+ arr[i];
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  outs;
    }


    public static DisplayMetrics getScreenSize(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }


    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static String defaultValue(String value, String defaultVal){
        return ( value!=null && !TextUtils.equals(value,"") ) ? value : defaultVal;
    }


    public static class BindTextClear implements View.OnFocusChangeListener, TextWatcher {

        private EditText editText;
        private ImageView imageView;
        private boolean hasFocus;
        private Runnable runnable;

        public void setRunnable(Runnable runnable) {
            this.runnable = runnable;
        }

        void call(){
            if (runnable!=null)
            {
                runnable.run();
            }
        }

        public BindTextClear(EditText editText, ImageView imageView) {
            this.editText = editText;
            this.imageView = imageView;
            bind();
        }

        private void bind() {
            editText.setOnFocusChangeListener(this);
            editText.addTextChangedListener(this);
            imageView.setVisibility(View.INVISIBLE);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setText("");
                }
            });
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            call();
            this.hasFocus = hasFocus;
            if (editText.getText().length()>0){
                imageView.setVisibility(View.VISIBLE);
            }else {
                imageView.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            call();
            if (editText.getText().length()>0){
                imageView.setVisibility(View.VISIBLE);
            }else {
                imageView.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }


    public static String BitmapToString(Bitmap bitmap){
        String outs=null;
        try {
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] arr=byteArrayOutputStream.toByteArray();
            outs = Base64.encodeToString(arr, Base64.DEFAULT);
            byteArrayOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return outs;
    }


    public static File openCamera(Activity context, int actionId){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        File dirFile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/bl_cache");
        if (!dirFile.exists()){
            dirFile.mkdir();
        }
        File outputImage = new File(dirFile , "/"+System.currentTimeMillis()+".jpg");
        Uri imageUri = Uri.fromFile(outputImage);
        Intent intent = new Intent(); //照相
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //指定图片输出地址
        try {
            context.startActivityForResult(intent, actionId); //启动照相
        }catch (Exception ex){ex.printStackTrace();}
        return outputImage;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth) {
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (width > reqWidth) {
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = widthRatio;
        }
        return inSampleSize;
    }


    public static Bitmap decodeSampledBitmapFromResource(String pathName, int reqWidth) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }


    public static boolean isIdCard(String idCard){
        idCard = idCard.toLowerCase();
        String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|x)$)";
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }


    public static String hideNameMiddle(String name){
        try {
            String pre= name.substring(0,1);
            if (name.length()>2){
                name = pre+"*"+name.substring(2,name.length());
            }else {
                name = pre+"*";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }


    public static String hideIdCardMiddle(String idcard){
        try {
            String pre = idcard.substring(0,4);
            String last = idcard.substring(idcard.length()-4, idcard.length());
            String xing="";
            for (int i=0; i< idcard.length()-8; i++){
                xing+="*";
            }
            idcard = pre + xing + last;
        }catch (Exception e){
            e.printStackTrace();
        }
        return idcard;
    }


    public static Spanned getStringByHtml(String html){
        Spanned outs=null;
        try {
            outs = Html.fromHtml(html);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outs;
    }

    public static void saveFile(String json) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(Environment
                    .getExternalStorageDirectory().getAbsolutePath()
                    + "/mlog.txt"));
            fileOutputStream.write(json.getBytes());
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public static void msg( String msg){
        Log.i("pre-network", msg);
    }

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    //隐式调用转显示调用
    public static Intent changeIntent(Context context, Intent intent) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);

        // Is somebody else trying to intercept our IAP call?
        if (resolveInfos == null || resolveInfos.size() != 1) {
            return null;
        }

        ResolveInfo serviceInfo = resolveInfos.get(0);
        String packageName = serviceInfo.activityInfo.packageName;
        String className = serviceInfo.activityInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        Intent iapIntent = new Intent(intent);
        iapIntent.setComponent(component);
        return iapIntent;
    }

    //通过意图获取类名
    public static String getClassNameForIntent(Context context, Intent intent) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);

        // Is somebody else trying to intercept our IAP call?
        if (resolveInfos == null || resolveInfos.size() != 1) {
            return null;
        }

        ResolveInfo serviceInfo = resolveInfos.get(0);
        return serviceInfo.activityInfo.name;
    }

    /**
     * 构建通知渠道
     */
    public static NotificationChannel initNotificationChannel(Context context, String id, String name) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= 26) {
            channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        return channel;
    }

    /**
     * 创建获取通知
     */
    public static NotificationCompat.Builder getNotification(Context context, NotificationChannel channel) {
        NotificationCompat.Builder builder;
        if (channel != null && Build.VERSION.SDK_INT >= 26) {
            builder = new NotificationCompat.Builder(context, channel.getId());
        } else {
            builder = new NotificationCompat.Builder(context, null);
        }
        return builder;
    }

    public static void tintDrawable2(ImageView imageView, int rid, int color) {
        int color1 = imageView.getContext().getResources().getColor(color);
        tintDrawable(imageView, rid, color1);
    }

    public static void tintDrawable(ImageView imageView, int rid, int color) {
        Drawable drawable = tintDrawable(imageView.getContext(), rid, color);
        imageView.setImageDrawable(drawable);
    }

    public static Drawable tintDrawable(Context context, int rid, int color) {
        Drawable drawable = ContextCompat.getDrawable(context, rid);
        return tintDrawable(drawable, color);
    }

    public static Drawable tintDrawable(@NonNull Drawable drawable, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    public static void runOnUI(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    public static void runOnUI(int time, Runnable runnable) {
        new Handler(Looper.getMainLooper()).postDelayed(runnable, time);
    }

    public static void runThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    /**
     * 打开键盘
     */
    public static void isOpenSoftInput(Activity context, EditText text, boolean yes) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager == null)
            return;
        if (yes) {
//            context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            manager.hideSoftInputFromWindow(text.getWindowToken(), 0);
        }
    }

    /**
     * 通过资源id 获取 资源文件名
     */
    public static String getNameByRid(Context context, int rid) {
        String name = null;
        try {
            name = context.getResources().getResourceEntryName(rid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 检查网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }

    /**
     * 判断WIFI网络是否可用
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取当前网络连接的类型信息
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }


    /**
     * 获取当前的网络状态 ：没有网络0：WIFI网络1：3G网络2：2G网络3
     */
    public static int getAPNType(Context context) {
        int netType = 0;
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;// wifi
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager mTelephony = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    && !mTelephony.isNetworkRoaming()) {
                netType = 2;// 3G
            } else {
                netType = 3;// 2G
            }
        }
        return netType;
    }

    public static int isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][3456789]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return -1;
        else
            return mobiles.matches(telRegex) ? 0 : -2;
    }

    /**
     * 用_拼接参数
     */
    public static String getStringParm(Map<String, String> parm) {
        StringBuilder buffer = new StringBuilder();
        Set<String> strings = parm.keySet();
        for (String string : strings) {
            buffer.append("_").append(parm.get(string));
        }
        return buffer.toString();
    }

    public static String getRequestId(String... arr) {
        StringBuilder buffer = new StringBuilder();
        for (String s : arr) {
            buffer.append("_").append(s);
        }
        return buffer.toString();
    }

    /**
     * 5.采样率压缩（设置图片的采样率，降低图片像素）
     */
    public static void samplingRateCompress(String filePath, File file, int maxWidth) {
        Bitmap bt = PNUtils.decodeSampledBitmapFromResource(filePath, maxWidth);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bt.compress(Bitmap.CompressFormat.JPEG, 90, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //人性化时间部分

    public static String changeToPPTime(Context context, long targetTime) {
        return changeToPPTime(context, targetTime, 0);
    }

    public static String changeToPPTime(Context context, long targetTime, int type) {
        String outString = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM-dd", Locale.getDefault());
            if (type == 1) {
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                simpleDateFormat2 = new SimpleDateFormat("MM-dd  HH:mm", Locale.getDefault());
            }
            long sysTime = System.currentTimeMillis() - targetTime;
            long s = 60 * 1000L;
            long h = 60 * 60 * 1000L;
            long d = 24 * 60 * 60 * 1000L;
            long H = (long) (30.4 * 24 * 60 * 60 * 1000L);
            long y = (long) (365.2 * 24 * 60 * 60 * 1000L);
            // sys time----
            long sysY = (long) (sysTime / y);
            long sysH = (long) ((sysTime % y) / H);
            long sysd = (long) ((sysTime % H) / d);
            long sysh = (long) ((sysTime % d) / h);
            long sysS = (long) ((sysTime % h) / s);
            if (sysY >= 1) {
                outString = simpleDateFormat.format(new Date(targetTime));
            } else if (sysY == 0 && sysH >= 1) {
                outString = simpleDateFormat2.format(new Date(targetTime));
            } else if (sysH == 0 && sysd >= 1) {
                if (sysd <= 2) {
                    outString = sysd + "天前";
                } else {
                    outString = simpleDateFormat2.format(new Date(targetTime));
                }
            } else if (sysd == 0 && sysh >= 1) {
                outString = sysh + "小时前";
            } else if (sysh == 0 && sysS >= 1) {
                outString = sysS + "分钟前";
            } else {
                outString = "刚刚";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outString;
    }

    public static String getPPTime(Context context, String mtime) {
        String outs = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        try {
            Date parse = format.parse(mtime);
            outs = changeToPPTime(context, parse.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outs;
    }


    /**
     * 判断终于大陆区域
     */
    public static boolean isCN(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String countryIso = tm.getSimCountryIso();
        boolean isCN = false;//判断是不是大陆
        if (!TextUtils.isEmpty(countryIso)) {
            countryIso = countryIso.toUpperCase(Locale.US);
            if (countryIso.contains("CN")) {
                isCN = true;
            }
        }
        return isCN;

    }

    public static PackageInfo getPackageInfo(Context context, int flag) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), flag);
        } catch (PackageManager.NameNotFoundException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    /**
     * 获取hash吗
     */
    public static String getKeyHash(final Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String s = Base64.encodeToString(md.digest(), Base64.NO_WRAP);
                msg("hash key:" + s);
                return s;
            } catch (NoSuchAlgorithmException e) {
                Log.w("chengxing", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        return null;
    }

    /**
     * 计算gridView高度
     */
    public static void setGridViewHeight(GridView gridview) {
        // 获取gridview的adapter
        ListAdapter listAdapter = gridview.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int numColumns = gridview.getNumColumns(); //5
        int totalHeight = 0;
        // 计算每一列的高度之和
        for (int i = 0; i < listAdapter.getCount(); i += numColumns) {
            // 获取gridview的每一个item
            View listItem = listAdapter.getView(i, null, gridview);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }
        // 获取gridview的布局参数
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        params.height = totalHeight;
        gridview.setLayoutParams(params);
    }


    /**
     * 定位list位置
     */
    public static void scrollToPostion(RecyclerView list, int position, int offset) {
        if (position != -1) {
            list.scrollToPosition(position);
            LinearLayoutManager mLayoutManager =
                    (LinearLayoutManager) list.getLayoutManager();
            if (mLayoutManager != null)
                mLayoutManager.scrollToPositionWithOffset(position, PNUtils.dp2px(list.getContext(), offset));
        }
    }


}
