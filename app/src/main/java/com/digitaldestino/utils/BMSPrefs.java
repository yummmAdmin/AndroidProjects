package com.digitaldestino.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class BMSPrefs {
    static String PREF_NAME = "digital_pref";
    private static SharedPreferences sharedPrefs;
//    private static Editor edit;
    private static void getInstance(Context context) {
        if (sharedPrefs == null) {
            sharedPrefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, 0);
        }
    }

    public static String putString(Context context, String key, String value) {
        getInstance(context);
        Editor edit = sharedPrefs.edit();
        edit.putString(key, value);
        edit.commit();
        return key;
    }

    public static String putBoolean(Context context, String key, Boolean value) {
        getInstance(context);
        Editor  edit = sharedPrefs.edit();
        edit.putBoolean(key, value.booleanValue());
        edit.commit();
        return key;
    }

    public static String getString(Context context, String key) {
        getInstance(context);
        return sharedPrefs.getString(key, "");
    }

    public static Boolean getBoolean(Context context, String key) {
        getInstance(context);
        return Boolean.valueOf(sharedPrefs.getBoolean(key, true));
    }

//    public static void clearAllPref(){
//        edit.clear();
//        edit.commit();
//
//    }
}
