package com.gdgssu.android_deviewsched;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.facebook.FacebookSdk;
import com.gdgssu.android_deviewsched.helper.FavoritePreferenceHelper;
import com.gdgssu.android_deviewsched.helper.LoginPreferenceHelper;

import com.navercorp.volleyextensions.volleyer.factory.DefaultRequestQueueFactory;

public class DeviewSchedApplication extends Application{

    public static Boolean LOGIN_STATE = false;
    public static boolean FAVOR_SESSION_STATE = false;
    public static final String HOST_URL = "http://deview.unstabler.pl/";

    private static final String TAG = "DeviewSchedApplication";

    public static RequestQueue deviewRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

//        FacebookSdk.sdkInitialize(getApplicationContext());
//        setLoginState();
//        setFavorSessionState();
    }

    public void setLoginState() {
        LoginPreferenceHelper prefHelper = new LoginPreferenceHelper(getApplicationContext());
        LOGIN_STATE = prefHelper.getPrefLoginValue(LoginPreferenceHelper.PREF_LOGIN_STATE, false);
    }

    public void setFavorSessionState(){
        /**
         * 로그인할떄 false로 돌려야함
         */
        FavoritePreferenceHelper prefHelper = new FavoritePreferenceHelper(getApplicationContext());
        FAVOR_SESSION_STATE = prefHelper.getFavorSessionState(FavoritePreferenceHelper.PREF_FAVOR_STATE);
    }
}
