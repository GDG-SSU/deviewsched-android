package com.gdgssu.android_deviewsched.ui.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.util.LogUtils;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class PreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = makeLogTag("PreferenceFragment");

    private static final String KEY_PREF_NOTI = "pref_session_noti";
    private static final String KEY_PREF_OSSLICENSES = "pref_opensourcelicense";

    private SharedPreferences sharedPref = null;

    private OnSettingFragmentChangeListener mListener = null;

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

            default:
                try {
                    throw new Exception("Cannot found preference key");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {

        switch (preference.getKey()) {
            case KEY_PREF_OSSLICENSES:
                //Todo : SettingActivity에서 PreferenceFragment에서 OSSlicensesFragment로 전환하는 로직 작성
                mListener.onFragmentChange();
                break;

            default:
                try {
                    throw new Exception("Cannot found preference key");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }

        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnSettingFragmentChangeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
