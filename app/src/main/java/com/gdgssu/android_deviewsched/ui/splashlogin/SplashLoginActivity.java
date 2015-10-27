package com.gdgssu.android_deviewsched.ui.splashlogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gdgssu.android_deviewsched.DeviewSchedApplication;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.model.AllScheItems;
import com.gdgssu.android_deviewsched.ui.MainActivity;

import static com.navercorp.volleyextensions.volleyer.Volleyer.volleyer;

public class SplashLoginActivity extends AppCompatActivity implements Runnable {

    private static final String TAG = "SplashLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashlogin);

        Handler handler = new Handler();
        handler.postDelayed(this, 3000);

    }

    private void getAllScheData() {

        volleyer(DeviewSchedApplication.deviewRequestQueue)
                .get(DeviewSchedApplication.HOST_URL + "2015/list")
                .withTargetClass(AllScheItems.class)
                .withListener(new Response.Listener<AllScheItems>() {
                    @Override
                    public void onResponse(AllScheItems items) {
                        AllScheItems.result = items;
                    }
                })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                })
                .execute();
    }

    @Override
    public void run() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
