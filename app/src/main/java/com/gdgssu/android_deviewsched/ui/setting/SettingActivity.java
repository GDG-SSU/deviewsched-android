package com.gdgssu.android_deviewsched.ui.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gdgssu.android_deviewsched.R;


public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setFragment();
    }

    private void setFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.setting_contents_container, new PreferenceFragment())
                .commit();
    }
}
