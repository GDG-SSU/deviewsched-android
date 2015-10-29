package com.gdgssu.android_deviewsched;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.gdgssu.android_deviewsched.helper.FavoritePreferenceHelper;
import com.gdgssu.android_deviewsched.helper.LoginPreferenceHelper;

import com.gdgssu.android_deviewsched.model.sessioninfo.Day;

import java.util.ArrayList;

public class DeviewSchedApplication extends Application{

    public static Boolean LOGIN_STATE = false;
    public static boolean FAVOR_SESSION_STATE = false;
    public static final String HOST_URL = "http://deview.unstabler.pl/";

    private static final String TAG = "DeviewSchedApplication";

    public static RequestQueue deviewRequestQueue;

    public static ArrayList<Day> allscheItems = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

//        FacebookSdk.sdkInitialize(getApplicationContext());
//        setLoginState();
//        setFavorSessionState();
    }

    public void setLoginState() {
        LoginPreferenceHelper prefHelper = new LoginPreferenceHelper(getBaseContext());
        LOGIN_STATE = prefHelper.getPrefLoginValue(LoginPreferenceHelper.PREF_LOGIN_STATE, false);
    }

    public void setFavorSessionState(){
        /**
         * 로그인할떄 false로 돌려야함
         */
        FavoritePreferenceHelper prefHelper = new FavoritePreferenceHelper(getBaseContext());
        FAVOR_SESSION_STATE = prefHelper.getFavorSessionState(FavoritePreferenceHelper.PREF_FAVOR_STATE);
    }
}
