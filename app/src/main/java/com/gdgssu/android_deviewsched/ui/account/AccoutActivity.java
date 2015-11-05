package com.gdgssu.android_deviewsched.ui.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gdgssu.android_deviewsched.R;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class AccoutActivity extends AppCompatActivity {

    private static final String TAG = makeLogTag("AccoutActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accout);
    }
}
