package com.gdgssu.android_deviewsched.ui.sche;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gdgssu.android_deviewsched.model.sessioninfo.Day;

import java.util.ArrayList;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class SchePagerFragmentAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = makeLogTag("SchePagerFragmentAdapter");

    private ArrayList<CharSequence> pagerTitles = new ArrayList<CharSequence>();
    private Day dayItem;

    public SchePagerFragmentAdapter(FragmentManager fm, Day dayItem) {
        super(fm);

        this.dayItem = dayItem;
        setPagerTitles();
    }

    private void setPagerTitles() {
        pagerTitles.add("Track1");
        pagerTitles.add("Track2");
        pagerTitles.add("Track3");
        pagerTitles.add("Track4");

    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return SchePagerFragment.newInstance(dayItem.tracks.get(position));
            case 1:
                return SchePagerFragment.newInstance(dayItem.tracks.get(position));
            case 2:
                return SchePagerFragment.newInstance(dayItem.tracks.get(position));
            case 3:
                return SchePagerFragment.newInstance(dayItem.tracks.get(position));
            default:
                throw new RuntimeException("There is not case");
        }
    }

    @Override
    public int getCount() {
        return pagerTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pagerTitles.get(position);
    }

    public void setDayItem(Day dayItem) {
        this.dayItem = dayItem;
    }
}
