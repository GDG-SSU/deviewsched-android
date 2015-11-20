package com.gdgssu.android_deviewsched.ui.sche;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.gdgssu.android_deviewsched.ui.BaseFragment;
import com.gdgssu.android_deviewsched.ui.selectsession.SelectSessionActivity;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class ScheFragment extends BaseFragment {

    private static final String TAG = makeLogTag("ScheFragment");
    public static final String KEY_TITLE = "title";
    public static final String KEY_MYSESSION = "mysession";

    public static final int SELECT_SESSION = 100;

    private ViewPager mPager;
    private CharSequence title;
    private boolean isMySession = false;

    private SchePagerFragmentAdapter mAdapter;

    public static ScheFragment newInstance(CharSequence title, boolean isMySession) {
        ScheFragment fragment = new ScheFragment();
        Bundle args = new Bundle();
        args.putCharSequence(KEY_TITLE, title);
        args.putBoolean(KEY_MYSESSION, isMySession);
        fragment.setArguments(args);
        return fragment;
    }

    public ScheFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            this.title = getArguments().getString(KEY_TITLE);
            this.isMySession = getArguments().getBoolean(KEY_MYSESSION);
            if (!this.isMySession) {
                isMySession = false;
            } else {
                isMySession = true;
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
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
        RelativeLayout emptyLayout = (RelativeLayout) rootView.findViewById(R.id.fragment_sche_empty_container);
        if (isMySession) {
            emptyLayout.setVisibility(View.VISIBLE);
            if (DeviewSchedApplication.FAVOR_SESSION_STATE) {
                emptyLayout.setVisibility(View.GONE);
            }
        }
    }

    private void initFragmentPager(View rootView) {
        mPager = (ViewPager) rootView.findViewById(R.id.fragment_sche_content_pager);
        mAdapter = new SchePagerFragmentAdapter(getChildFragmentManager(), DeviewSchedApplication.allscheItems.days.get(0));
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
                getActivity().finish();
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
                        mAdapter.setDayItem(DeviewSchedApplication.allscheItems.days.get(0));
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mAdapter.setDayItem(DeviewSchedApplication.allscheItems.days.get(1));
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (isMySession) {
            inflater.inflate(R.menu.menu_sche, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_all_sche_favorite:

                Intent intent = new Intent(getActivity(), SelectSessionActivity.class);
                startActivityForResult(intent, ScheFragment.SELECT_SESSION);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
