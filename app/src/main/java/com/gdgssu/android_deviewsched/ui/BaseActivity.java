package com.gdgssu.android_deviewsched.ui;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import com.gdgssu.android_deviewsched.helper.ProfileChangedListener;

public class BaseActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener, ProfileChangedListener {
    @Override
    public void updateUserProfile() {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
