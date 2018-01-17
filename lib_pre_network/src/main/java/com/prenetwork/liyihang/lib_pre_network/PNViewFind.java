package com.prenetwork.liyihang.lib_pre_network;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PNViewFind {

	int value() default 0;

	public static class Bind {

		public static void init(Activity activity) {
			try {
				Field[] declaredFields = activity.getClass().getDeclaredFields();
				for (Field item : declaredFields){
					if (item==null)
						continue;
					PNViewFind annotation = item.getAnnotation(PNViewFind.class);
					if (annotation==null)
						continue;
					item.setAccessible(true);
					int value = annotation.value();
					if (value==0){
						int rid = activity.getResources().getIdentifier(item.getName(),"id",activity.getPackageName());
						item.set(activity, activity.findViewById(rid));
					}else {
						item.set(activity, activity.findViewById(value));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		

		public static void init(View mView, Object mObj) {
			try {
				Field[] declaredFields = mObj.getClass().getDeclaredFields();
				for (Field item : declaredFields){
					if (item==null)
						continue;
					PNViewFind annotation = item.getAnnotation(PNViewFind.class);
					if (annotation==null)
						continue;
					item.setAccessible(true);
					int value = annotation.value();
					if (value==0){
						int rid = mView.getResources().getIdentifier(item.getName(),"id",mView.getContext().getPackageName());
						item.set(mObj, mView.findViewById(rid));
					}else {
						item.set(mObj, mView.findViewById(value));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
