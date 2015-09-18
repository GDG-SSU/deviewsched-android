package com.gdgssu.android_deviewsched.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class LoginPreferenceHelper {

    private final String PREF_NAME = "com.gdgssu.loginpref";

    public final static String PREF_LOGIN_STATE = "PREF_LOGIN_STATE";
    public final static String PREF_ACCESS_TOKEN = "PREF_ACCESS_TOKEN";

    private static Context mContext;

    public LoginPreferenceHelper(Context context) {
        this.mContext = context;
    }

    public void setPrefLoginValue(String key, boolean value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getPrefLoginValue(String key, boolean defaultValue) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);

        return pref.getBoolean(key, defaultValue);

    }

    public void setAccessTokenValue(String key, String accessTokenString) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(key, accessTokenString);
        editor.apply();
    }

    public String getAccessTokenValue(String key, String defaultValue) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);

        try {
            return pref.getString(key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
