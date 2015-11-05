package com.gdgssu.android_deviewsched.ui.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.util.LogUtils;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class PreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = makeLogTag("PreferenceFragment");

    public static final String KEY_PREF_NOTI = "pref_noti";
    public static final String KEY_PREF_NOTI_CYCLETIME = "pref_noti_cycletime";

    private SharedPreferences sharedPref;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preference);

        //Get stored Preferencedata from SharedPreference
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case KEY_PREF_NOTI:
                LogUtils.LOGI(TAG, "Change KEY_PREF_NOTI");

                break;

            case KEY_PREF_NOTI_CYCLETIME:
                LogUtils.LOGI(TAG, "Change KEY_PREF_NOTI_CYCLETIME");

                break;

            default:
                try {
                    throw new Exception("Cannot found preference key");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }
}
