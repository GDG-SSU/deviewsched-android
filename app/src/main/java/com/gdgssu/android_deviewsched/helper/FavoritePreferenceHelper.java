package com.gdgssu.android_deviewsched.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.gdgssu.android_deviewsched.DeviewSchedApplication;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavoritePreferenceHelper {

    private final String PREF_NAME = "com.gdgssu.favoritepref";

    public final static String PREF_FAVOR_STATE = "PREF_FAVOR_STATE";
    public final static String PREF_FAVOR_VALUE = "PREF_FAVOR_VALUE";

    private static Context mContext;

    public FavoritePreferenceHelper(Context context) {
        this.mContext = context;
    }

    public void setFavorSessionState(String key, boolean value){
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(key, value);
        editor.apply();

        DeviewSchedApplication.FAVOR_SESSION_STATE = value;
    }

    public boolean getFavorSessionState(String key){
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);

        return pref.getBoolean(key, false);
    }

    public void setFavorSessionValue(String key, ArrayList<Integer> favorSessionList){

        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        Gson gson = new Gson();
        String favorSessionJson = gson.toJson(favorSessionList);

        editor.putString(key, favorSessionJson);
        editor.apply();

        setFavorSessionState(PREF_FAVOR_STATE, true);
    }

    public List<Integer> getFavorSessionValue(String key){

        List<Integer> favorSessionList;
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);

        if (pref.contains(PREF_FAVOR_VALUE)){
            String favorSessionJson = pref.getString(key, null);
            Gson gson = new Gson();
            Integer[] favorSessionArray = gson.fromJson(favorSessionJson, Integer[].class);
            favorSessionList = Arrays.asList(favorSessionArray);
            favorSessionList = new ArrayList<>(favorSessionList);
        }else{
            return null;
        }

        return favorSessionList;
    }
}
