package com.example.MyMedicationDiary.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

    static final String PREF_USER_NAME= "username";
    static final String PREF_PASS= "pass";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }
    public static void setLastUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor2 = getSharedPreferences(ctx).edit();
        editor2.putString(PREF_USER_NAME, userName);
        editor2.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
    public static void setPass(Context ctx, String pass)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_PASS, pass);
        editor.commit();
    }


    public static String getPass(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_PASS, "");
    }

}
