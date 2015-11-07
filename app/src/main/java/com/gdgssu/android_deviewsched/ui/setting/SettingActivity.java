package com.gdgssu.android_deviewsched.ui.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.util.LogUtils;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;


public class SettingActivity extends AppCompatActivity implements OnSettingFragmentChangeListener {

    private static final String TAG = makeLogTag("SettingActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setFragment();
    }

    private void setFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.setting_contents_container, new PreferenceFragment())
                .commit();
    }

    @Override
    public void onFragmentChange() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.setting_contents_container, new OSSlicensesFragment())
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            finish();
        }
    }
}
