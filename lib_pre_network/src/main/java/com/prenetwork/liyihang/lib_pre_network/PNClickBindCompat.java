package com.prenetwork.liyihang.lib_pre_network;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by user on 2017/3/13.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PNClickBindCompat {

    int value() default 0;

    public static class Bind{

        public static void init(Activity activity){
            try {
                Field[] declaredFields = activity.getClass().getDeclaredFields();
                for (Field item : declaredFields){
                    PNClickBindCompat annotation = item.getAnnotation(PNClickBindCompat.class);
                    if (annotation!=null){
                        int rid = activity.getResources().getIdentifier(item.getName(), "id", activity.getPackageName());
                        item.setAccessible(true);
                        activity.findViewById(rid).setOnClickListener(new Bind.OnListener(activity, "onClick"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void init(View view, Object object){
            try {
                Field[] declaredFields = object.getClass().getDeclaredFields();
                for (Field item : declaredFields){
                    PNClickBindCompat annotation = item.getAnnotation(PNClickBindCompat.class);
                    if (annotation!=null){
                        int rid = view.getResources().getIdentifier(item.getName(), "id", view.getContext().getPackageName());
                        item.setAccessible(true);
                        view.findViewById(rid).setOnClickListener(new Bind.OnListener(object, "onClick"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static class OnListener implements View.OnClickListener {

            Object activity;
            String methodName;

            public OnListener(Object activity, String methodName) {
                super();
                this.activity = activity;
                this.methodName = methodName;
            }

            @Override
            public void onClick(View arg0) {
                try {
                    Method method = activity.getClass().getDeclaredMethod(methodName, View.class);
                    method.setAccessible(true);
                    method.invoke(activity, arg0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }


}
