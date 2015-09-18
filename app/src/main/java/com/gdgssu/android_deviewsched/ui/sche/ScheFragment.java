package com.gdgssu.android_deviewsched.ui.sche;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.gdgssu.android_deviewsched.DeviewSchedApplication;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.model.AllScheItems;
import com.gdgssu.android_deviewsched.ui.DeviewFragment;
import com.gdgssu.android_deviewsched.ui.MainActivity;
import com.gdgssu.android_deviewsched.ui.selectsession.SelectSessionActivity;

public class ScheFragment extends DeviewFragment {

    private static final String KEY_TITLE = "title";

    private ViewPager mPager;
    private CharSequence title;
    private boolean isMySession = false;

    private String TAG = "ScheFragment";

    private SchePagerFragmentAdapter mAdapter;

    public static ScheFragment newInstance(CharSequence title) {
        ScheFragment fragment = new ScheFragment();
        Bundle args = new Bundle();
        args.putCharSequence(KEY_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    public ScheFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.title = getArguments().getString(KEY_TITLE);
            if (this.title == getActivity().getText(R.string.all_schedule)) {
                isMySession = false;
            } else {
                isMySession = true;
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "onStop()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sche, container, false);

        initToolbar(rootView);
        setMySessionView(rootView);
        initFragmentPager(rootView);

        return rootView;
    }

    private void setMySessionView(View rootView) {
        if ((!isMySession) || DeviewSchedApplication.FAVOR_SESSION_STATE) {
            RelativeLayout emptyLayout = (RelativeLayout) rootView.findViewById(R.id.fragment_sche_empty_container);
            emptyLayout.setVisibility(View.GONE);
        }
    }

    private void initFragmentPager(View rootView) {
        mPager = (ViewPager) rootView.findViewById(R.id.fragment_sche_content_pager);
        mAdapter = new SchePagerFragmentAdapter(getChildFragmentManager(), AllScheItems.result.days.get(0));
        mPager.setAdapter(mAdapter);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.fragment_sche_tabs);
        tabLayout.setupWithViewPager(mPager);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initToolbar(View rootView) {
        Toolbar mToolbar = (Toolbar) rootView.findViewById(R.id.fragment_sche_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.title);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showHome();
            }
        });

        initToolbarSpinner(rootView);
    }

    private void initToolbarSpinner(View rootView) {
        Spinner toolbarSpinner = (Spinner) rootView.findViewById(R.id.fragment_sche_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.days, R.layout.toolbar_spinner_item);
        adapter.setDropDownViewResource(R.layout.toolbar_spinner_item_dropdown);
        toolbarSpinner.setAdapter(adapter);
        toolbarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mAdapter != null) {
                    if (position == 0) {
                        mAdapter.setDayItem(AllScheItems.result.days.get(0));
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mAdapter.setDayItem(AllScheItems.result.days.get(1));
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
