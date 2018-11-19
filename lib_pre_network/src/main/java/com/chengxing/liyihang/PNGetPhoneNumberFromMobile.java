package com.chengxing.liyihang;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PNGetPhoneNumberFromMobile {
    private List<PNPhoneInfoBean> list;

    public  static File dir=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/jason_img_dir/img");

    public List<PNPhoneInfoBean> getPhoneNumberFromMobile(Context context) {
        list = new ArrayList<PNPhoneInfoBean>();
        Uri uri=Uri.parse("content://com.android.contacts/contacts");
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{"display_name", "sort_key", "contact_id",
                        "data1", "data15", "_id"}, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("display_name"));
            String number = cursor.getString(cursor.getColumnIndex("data1"));
            int Id = cursor.getInt(cursor.getColumnIndex("contact_id"));
            String Sortkey = getSortkey(cursor.getString(1));

            PNPhoneInfoBean CXPhoneInfo = new PNPhoneInfoBean();
            byte[] photoBytes = getPhotoBytes(context, String.valueOf(Id));
            String imgFileName="header_img_"+Id+".jpg";
            saveFile(imgFileName, photoBytes);
            CXPhoneInfo.setHeaderImgPath(new File(dir, imgFileName).getAbsolutePath());
            CXPhoneInfo.setId(Id);
            CXPhoneInfo.setName(name);
            CXPhoneInfo.setNumber(number);
            CXPhoneInfo.setSortKey(Sortkey);
            list.add(CXPhoneInfo);
        }
        cursor.close();
        return list;
    }

    public static byte[] getPhotoBytes(Context context, String contactId) {
        byte[] bytes=null;
        Cursor dataCursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                new String[]{"data15"},
                "contact_id" + "=?" + " AND "
                        + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'",
                new String[]{String.valueOf(contactId)}, null);
        if (dataCursor != null) {
            if (dataCursor.getCount() > 0) {
                dataCursor.moveToFirst();
                bytes = dataCursor.getBlob(dataCursor.getColumnIndex(ContactsContract.Contacts.Photo.PHOTO));
            }
            dataCursor.close();
        }
        return bytes;
    }

    public static void saveFile(String name, byte[] arr) {
        if (arr!=null && arr.length>0){}else {
            return;
        }
        FileOutputStream fileOutputStream = null;
        if (!dir.exists())
            dir.mkdirs();
        File fileName = new File(dir, name);
        if (fileName.exists())
            return;
        try {
            fileOutputStream = new FileOutputStream(fileName);
            fileOutputStream.write(arr);
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



    private static String getSortkey(String sortKeyString) {
        String key = sortKeyString.substring(0, 1).toUpperCase();
        if (key.matches("[A-Z]")) {
            return key;
        } else
            return "#";
    }

}
