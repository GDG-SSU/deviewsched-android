package com.gdgssu.android_deviewsched;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.gdgssu.android_deviewsched.helper.FavoritePreferenceHelper;
import com.gdgssu.android_deviewsched.helper.LoginPreferenceHelper;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

import com.gdgssu.android_deviewsched.model.sessioninfo.AllScheItems;
import com.gdgssu.android_deviewsched.ui.main.MainActivity;
import com.navercorp.volleyextensions.volleyer.factory.DefaultRequestQueueFactory;

public class DeviewSchedApplication extends Application {

    private static final String TAG = makeLogTag("DeviewSchedApplication");

    private Context mContext = null;
    public static Boolean sLoginstate = false;
    public static boolean sFavorSessionState = false;

    private CallbackManager mCallbackManager;
    public static RequestQueue sDeviewRequestQueue;
    private ProfileTracker mProfileTracker;

    public static AllScheItems sAllscheItems = new AllScheItems();

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        FacebookSdk.sdkInitialize(mContext);
        mCallbackManager = CallbackManager.Factory.create();
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Log.d(TAG, "onCurrentProfileChanged");
//                mActivity.updateUserProfile(currentProfile.getProfilePictureUri(100, 100), currentProfile.getName());
            }
        };

        setLoginState();
        setFavorSessionState();
        initRequestQueue();

    }

    public void setLoginState() {
        LoginPreferenceHelper prefHelper = new LoginPreferenceHelper(getBaseContext());
        DeviewSchedApplication.sLoginstate = prefHelper.getPrefLoginValue(LoginPreferenceHelper.PREF_LOGIN_STATE, false);
    }

    public void setFavorSessionState() {
        /**
         * 로그인할때 false로 돌려야함
         */
        FavoritePreferenceHelper prefHelper = new FavoritePreferenceHelper(getBaseContext());
        DeviewSchedApplication.sFavorSessionState = prefHelper.getFavorSessionState(FavoritePreferenceHelper.PREF_FAVOR_STATE);
    }

    public void initRequestQueue() {
        sDeviewRequestQueue = DefaultRequestQueueFactory.create(mContext);
        sDeviewRequestQueue.start();
    }
}
