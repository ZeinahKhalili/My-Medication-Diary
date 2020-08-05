package com.example.MyMedicationDiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class sharedPref {
    static final String LAST_DATE= "";
    static final String PREF_USER_NAME2= "username";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setLastDate(Context ctx, String DDD)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(LAST_DATE, DDD);
        editor.commit();
    }
    public static void setLastUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME2, userName);
        editor.commit();
    }

    public static String getLastDate(Context ctx)
    {
        return getSharedPreferences(ctx).getString(LAST_DATE, "");
    }
    public static String getLastUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME2, "");
    }

}
