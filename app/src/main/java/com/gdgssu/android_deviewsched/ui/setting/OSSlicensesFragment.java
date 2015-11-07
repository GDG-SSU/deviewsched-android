package com.gdgssu.android_deviewsched.ui.setting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdgssu.android_deviewsched.R;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class OSSlicensesFragment extends Fragment {

    private static final String TAG = makeLogTag("OSSlicensesFragment");

    public OSSlicensesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_osslicenses, container, false);
    }
}
