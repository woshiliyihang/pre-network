package com.chengxing.cxsdk;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.Log;

import com.prenetwork.liyihang.lib_pre_network.PNUtils;

import java.util.Locale;

public class CXLangManager {

    public static final String tag=CXLangManager.class.getSimpleName();

    private static CXLangManager manager=null;

    public static CXLangManager getInstance() {
        synchronized (CXLangManager.class) {
            if (manager==null) {
                manager=new CXLangManager();
            }
        }
        return manager;
    }

    private CXLangManager(){
    }

    public static final String langKey="config_lang";

    private Context context;

    public CXLangManager init(Context context){
        this.context=context;
        String configLang = getConfigLang();
        msg("init getConfigLang lang="+configLang);
        getDefaultLang(context);
        return this;
    }

    private void msg(String s) {
        PNUtils.msg(s);
    }

    public CXLangManager changeConfigLang(String lname){
        msg("changeConfigLang==="+lname);
        if (lname==null)
        {
            PNUtils.getSharedPreferences(context).edit().remove(langKey).apply();
            return this;
        }
        PNUtils.getSharedPreferences(context).edit().putString(langKey,lname).apply();
        return this;
    }

    public String getDefaultLang(Context context){
        String city="";
        if (Build.VERSION.SDK_INT>=24) {
            LocaleList cityList = context.getResources().getConfiguration().getLocales();
            for (int i=0; i<cityList.size(); i++)
            {
                if (cityList.get(i)!=null && !"".equals(cityList.get(i)))
                {
                    city=cityList.get(i).getCountry();
                    break;
                }
            }
        }else {
            city=context.getResources().getConfiguration().locale.getCountry();
        }
        msg("getDefaultLang system lang="+city);
        return city;
    }

    public CXLangManager useLang(Context context){
        String name=getConfigLang();
        if (name!=null) {
            changeLang(context,name);
        }
        return this;
    }

    public String getConfigLang(){
        String lang = PNUtils.getSharedPreferences(context).getString(langKey, null);
        return lang;
    }

    public String getLang(){
        return getConfigLang()==null? "zh" : getConfigLang();
    }


    public CXLangManager changeLang(Context context, String name){
        Locale locale=new Locale(name);
        Locale.setDefault(locale);
        Configuration configuration=new Configuration();
        Resources resources = context.getResources();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        msg("changeLang lang===="+name);
        return this;
    }



}
