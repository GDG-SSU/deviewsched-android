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
import com.gdgssu.android_deviewsched.helper.FavoritePreferenceHelper;
import com.gdgssu.android_deviewsched.ui.BaseFragment;
import com.gdgssu.android_deviewsched.ui.selectsession.SelectSessionActivity;

import java.util.ArrayList;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class ScheFragment extends BaseFragment {

    private static final String TAG = makeLogTag("ScheFragment");
    public static final String KEY_TITLE = "title";
    public static final String KEY_MYSESSION = "mysession";

    public static final int SELECT_SESSION = 100;

    private ViewPager mPager;
    private Spinner mToolbarSpinner;
    private RelativeLayout mEmptyLayout;

    private CharSequence mTitle;
    private boolean mIsMySession = false;
    private ArrayList<Integer> mStoredSessionIDs;

    private SchePagerFragmentAdapter mAdapter;

    public static ScheFragment newInstance(CharSequence title, boolean mIsMySession) {
        ScheFragment fragment = new ScheFragment();
        Bundle args = new Bundle();
        args.putCharSequence(KEY_TITLE, title);
        args.putBoolean(KEY_MYSESSION, mIsMySession);
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
            this.mTitle = getArguments().getString(KEY_TITLE);
            this.mIsMySession = getArguments().getBoolean(KEY_MYSESSION);
            if (!this.mIsMySession) {
                mIsMySession = false;
            } else {
                mIsMySession = true;
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

        return rootView;
    }

    private void setMySessionView(View rootView) {
        mEmptyLayout = (RelativeLayout) rootView.findViewById(R.id.fragment_sche_empty_container);
        if (mIsMySession) {
            if (!DeviewSchedApplication.sFavorSessionState) {
                mEmptyLayout.setVisibility(View.VISIBLE);
                mToolbarSpinner.setVisibility(View.GONE);
                if (!DeviewSchedApplication.sLoginstate)
                    ((ScheActivity) getActivity()).showLoginDialog();
            } else {
                //Todo : 관심 세션 목록을 Preference로부터 받아와야함.
                FavoritePreferenceHelper prefHelper = new FavoritePreferenceHelper(getActivity());
                mStoredSessionIDs = prefHelper.getFavorSessionValue(FavoritePreferenceHelper.PREF_FAVOR_VALUE);
                initFragmentPager(rootView);
                return;
            }
        }
        initFragmentPager(rootView);
    }

    private void initFragmentPager(View rootView) {
        mPager = (ViewPager) rootView.findViewById(R.id.sche_pager_contents);
        //FavoriteFlag와 관심세션 ID 리스트를 전달하는 생성자 오버로딩하기.
        if (!mIsMySession) {
            mAdapter = new SchePagerFragmentAdapter(getChildFragmentManager(), DeviewSchedApplication.sAllscheItems.days.get(0));
        } else {
            mAdapter = new SchePagerFragmentAdapter(getChildFragmentManager(), DeviewSchedApplication.sAllscheItems.days.get(0), DeviewSchedApplication.sFavorSessionState, mStoredSessionIDs);
        }
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
        Toolbar mToolbar = (Toolbar) rootView.findViewById(R.id.sche_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.mTitle);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        initToolbarSpinner(rootView);
    }

    private void initToolbarSpinner(View rootView) {
        mToolbarSpinner = (Spinner) rootView.findViewById(R.id.sche_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.days, R.layout.item_toolbar_spinner);
        adapter.setDropDownViewResource(R.layout.item_toolbar_spinner_dropdown);
        mToolbarSpinner.setAdapter(adapter);
        mToolbarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mAdapter != null) {
                    if (position == 0) {
                        mAdapter.setDayItem(DeviewSchedApplication.sAllscheItems.days.get(0));
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mAdapter.setDayItem(DeviewSchedApplication.sAllscheItems.days.get(1));
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
        if (mIsMySession) {
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

    @Override
    public void onResume() {
        super.onResume();

        FavoritePreferenceHelper prefHelper = new FavoritePreferenceHelper(getActivity());
        boolean favorSessionState = prefHelper.getFavorSessionState(FavoritePreferenceHelper.PREF_FAVOR_STATE);

        if (favorSessionState) {
            mEmptyLayout.setVisibility(View.GONE);
            mToolbarSpinner.setVisibility(View.VISIBLE);
        } else {
            mEmptyLayout.setVisibility(View.VISIBLE);
            mToolbarSpinner.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ScheFragment.SELECT_SESSION) {
            //SelectSessionActivity에 갔다가 돌아왔을때 실행되는 로직
            FavoritePreferenceHelper prefHelper = new FavoritePreferenceHelper(getActivity());
            if (prefHelper.getFavorSessionState(FavoritePreferenceHelper.PREF_FAVOR_STATE)) {
                //저장된 세션 갯수가 0개이면 이 로직이 실행되지 않음.
                ArrayList<Integer> storedSessionIDs = prefHelper.getFavorSessionValue(FavoritePreferenceHelper.PREF_FAVOR_VALUE);
                //Todo 저장된 ID값들을 가져와서 그 ID에 맞는 세션 리스트들을 출력해줘야함.
                //mAdapter에 들어갈 데이터들을 새롭게 짜맞춰줘야함.
                mAdapter = new SchePagerFragmentAdapter(getChildFragmentManager(), DeviewSchedApplication.sAllscheItems.days.get(0), DeviewSchedApplication.sFavorSessionState, storedSessionIDs);
                mAdapter.notifyDataSetChanged();
                mPager.setAdapter(mAdapter);
            }
        }
    }
}
