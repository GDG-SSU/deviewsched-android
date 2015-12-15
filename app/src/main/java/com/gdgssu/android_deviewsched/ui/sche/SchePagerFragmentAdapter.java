package com.gdgssu.android_deviewsched.ui.sche;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gdgssu.android_deviewsched.model.sessioninfo.Day;

import java.util.ArrayList;

import static com.gdgssu.android_deviewsched.util.LogUtils.LOGI;
import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class SchePagerFragmentAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = makeLogTag("SchePagerFragmentAdapter");

    private ArrayList<CharSequence> mPagerTitles = new ArrayList<CharSequence>();
    private Day mDayItem;
    private ArrayList<Integer> mStoredSessionIDs = null;
    private Boolean mIsFavoriteMode = false;

    public SchePagerFragmentAdapter(FragmentManager fm, Day dayItem) {
        super(fm);

        this.mDayItem = dayItem;
        setPagerTitles();
    }

    public SchePagerFragmentAdapter(FragmentManager fm, Day dayItem, boolean isFavoriteMode, ArrayList<Integer> storedSessionIDs) {
        super(fm);

        this.mDayItem = dayItem;

        this.mStoredSessionIDs = storedSessionIDs;
        this.mIsFavoriteMode = isFavoriteMode;
        setPagerTitles();
    }

    private void setPagerTitles() {
        mPagerTitles.add("Track1");
        mPagerTitles.add("Track2");
        mPagerTitles.add("Track3");
        mPagerTitles.add("Track4");

    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return SchePagerFragment.newInstance(mDayItem.tracks.get(position), mIsFavoriteMode, mStoredSessionIDs);
            case 1:
                return SchePagerFragment.newInstance(mDayItem.tracks.get(position), mIsFavoriteMode, mStoredSessionIDs);
            case 2:
                return SchePagerFragment.newInstance(mDayItem.tracks.get(position), mIsFavoriteMode, mStoredSessionIDs);
            case 3:
                return SchePagerFragment.newInstance(mDayItem.tracks.get(position), mIsFavoriteMode, mStoredSessionIDs);
            default:
                throw new RuntimeException("There is not case");
        }
    }

    @Override
    public int getCount() {
        return mPagerTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPagerTitles.get(position);
    }

    public void setDayItem(Day dayItem) {
        this.mDayItem = dayItem;
    }
}
