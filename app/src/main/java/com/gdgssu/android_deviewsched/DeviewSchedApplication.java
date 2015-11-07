package com.gdgssu.android_deviewsched;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.facebook.FacebookSdk;
import com.gdgssu.android_deviewsched.helper.FavoritePreferenceHelper;
import com.gdgssu.android_deviewsched.helper.LoginPreferenceHelper;

import com.gdgssu.android_deviewsched.model.sessioninfo.AllScheItems;
import com.gdgssu.android_deviewsched.model.sessioninfo.Day;
import com.navercorp.volleyextensions.volleyer.factory.DefaultRequestQueueFactory;

import java.util.ArrayList;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class DeviewSchedApplication extends Application {

    private static final String TAG = makeLogTag("DeviewSchedApplication");

    private Context GLOBAL_CONTEXT = null;
    public static Boolean LOGIN_STATE = false;
    public static boolean FAVOR_SESSION_STATE = false;
    public static final String HOST_URL = "http://deview.unstabler.pl/";

    public static RequestQueue deviewRequestQueue;

    public static AllScheItems allscheItems = new AllScheItems();

    @Override
    public void onCreate() {
        super.onCreate();

        GLOBAL_CONTEXT = getApplicationContext();

        FacebookSdk.sdkInitialize(GLOBAL_CONTEXT);
        setLoginState();
        setFavorSessionState();
        initRequestQueue();

    }

    public void setLoginState() {
        LoginPreferenceHelper prefHelper = new LoginPreferenceHelper(getBaseContext());
        DeviewSchedApplication.LOGIN_STATE = prefHelper.getPrefLoginValue(LoginPreferenceHelper.PREF_LOGIN_STATE, false);
    }

    public void setFavorSessionState() {
        /**
         * 로그인할때 false로 돌려야함
         */
        FavoritePreferenceHelper prefHelper = new FavoritePreferenceHelper(getBaseContext());
        DeviewSchedApplication.FAVOR_SESSION_STATE = prefHelper.getFavorSessionState(FavoritePreferenceHelper.PREF_FAVOR_STATE);
    }

    public void initRequestQueue() {
        deviewRequestQueue = DefaultRequestQueueFactory.create(GLOBAL_CONTEXT);
        deviewRequestQueue.start();
    }
}
