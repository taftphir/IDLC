package com.alugara.idlc.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
    public static final String KEY_CHAT = "CHAT_STATUS";
    public static final String KEY_EMAIL = "EMAIL";
    public static final String KEY_ID = "ID";
    public static final String KEY_LOGIN = "LOGIN";
    public static final String KEY_PAKET = "PAKET";
    public static final String KEY_REG_EMAIL = "REG_EMAIL";

    private static SharedPreferences getSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setKeyLogin(Context context, boolean values){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(KEY_LOGIN, values);
        editor.apply();
    }

    public static boolean getKeyLogin(Context context){
        return getSharedPreferences(context).getBoolean(KEY_LOGIN, false);
    }

    public static void setKeyPaket(Context context, String values){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_PAKET, values);
        editor.apply();
    }

    public static String getKeyPaket(Context context){
        return getSharedPreferences(context).getString(KEY_PAKET, "");
    }

    public static void setKeyEmail(Context context, String values){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_EMAIL, values);
        editor.apply();
    }

    public static String getKeyEmail(Context context){
        return getSharedPreferences(context).getString(KEY_EMAIL, "");
    }

    public static void setKeyId(Context context, String values){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_ID, values);
        editor.apply();
    }

    public static String getKeyId(Context context){
        return getSharedPreferences(context).getString(KEY_ID, "");
    }

    public static void setKeyRegEmail(Context context, String values){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_REG_EMAIL, values);
        editor.apply();
    }

    public static String getKeyRegEmail(Context context){
        return getSharedPreferences(context).getString(KEY_REG_EMAIL, "");
    }

    public static void setKeyChat(Context context, boolean values){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(KEY_CHAT, values);
        editor.apply();
    }

    public static boolean getKeyChat(Context context){
        return getSharedPreferences(context).getBoolean(KEY_CHAT, false);
    }
}
