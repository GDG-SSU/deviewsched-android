package com.gdgssu.android_deviewsched.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.gdgssu.android_deviewsched.DeviewSchedApplication;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.helper.UserProfileProvider;
import com.gdgssu.android_deviewsched.model.User;
import com.gdgssu.android_deviewsched.model.sessioninfo.AllScheItems;
import com.gdgssu.android_deviewsched.ui.MainActivity;
import com.gdgssu.android_deviewsched.util.JsonFromFile;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static com.gdgssu.android_deviewsched.util.LogUtils.*;
import static com.navercorp.volleyextensions.volleyer.Volleyer.volleyer;

public class SplashActivity extends AppCompatActivity implements Runnable {

    private static final String TAG = makeLogTag("SplashActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashlogin);

        Handler handler = new Handler();
        handler.postDelayed(this, 1500);

    }

//    private void getAllScheData() {
//
//        volleyer(DeviewSchedApplication.deviewRequestQueue)
//                .get(DeviewSchedApplication.HOST_URL + "2015/list")
//                .withTargetClass(AllScheItems.class)
//                .withListener(new Response.Listener<AllScheItems>() {
//                    @Override
//                    public void onResponse(AllScheItems items) {
//                        AllScheItems.result = items;
//                    }
//                })
//                .withErrorListener(new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d(TAG, error.toString());
//                    }
//                })
//                .execute();
//    }

    private void getAllScheDataFromFile() {
        Gson gson = new Gson();
        String jsonString = JsonFromFile.readJsonFromAssets("allsche.json", getBaseContext());
        DeviewSchedApplication.allscheItems = gson.fromJson(jsonString, AllScheItems.class);
    }

    @Override
    public void run() {
        getAllScheDataFromFile();

        User user = new User();
        if (DeviewSchedApplication.LOGIN_STATE) {
            user = UserProfileProvider.getUserProfile();
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("UserInfo", user);
        startActivity(intent);
        finish();
    }
}
