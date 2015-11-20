package com.gdgssu.android_deviewsched.ui.location;

import android.os.Bundle;

import com.gdgssu.android_deviewsched.R;
import com.nhn.android.maps.NMapActivity;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class LocationActivity extends NMapActivity {

    private static final String TAG = makeLogTag("LocationActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

    }

    @Override
    protected void onStop() {

        super.onStop();
    }
}
