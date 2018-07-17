package com.prenetwork.liyihang.lib_pre_network;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

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


}
