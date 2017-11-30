package com.example.floas.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * @Copyright © 2015 sanbo Inc. All rights reserved.
 * @Description: Toast同一管理类
 * @Version: 1.0
 * @Create: 2015年6月28日 下午1:41:16
 * @Author: sanbo
 */
public class SP {

    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "abc";

    public static void setParam(String key, Object obj) {
        setParam(Content.CONTEXT, key, obj);
    }

    public static Object getParam(String key, Object defaultObject) {
        return getParam(Content.CONTEXT, key, defaultObject);
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context, String key, Object object) {

        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String)object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer)object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean)object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float)object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long)object);
        }
        if (Build.VERSION.SDK_INT > 8) {
            editor.apply();
        } else {
            editor.commit();
        }

    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context, String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String)defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer)defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean)defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float)defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long)defaultObject);
        }
        return defaultObject;
    }
}
