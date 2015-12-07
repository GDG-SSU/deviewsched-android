package com.gdgssu.android_deviewsched.ui.sche;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.model.FavoriteSession;
import com.gdgssu.android_deviewsched.model.sessioninfo.Track;
import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.gdgssu.android_deviewsched.util.LogUtils.LOGI;
import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class SchePagerFragment extends Fragment {

    private static final String TAG = makeLogTag("SchePagerFragment");
    private static final String BUNDLE_ISFAVORITEMODE = "BUNDLE_ISFAVORITEMODE";
    private static final String BUNDLE_STOREDSESSIONIDARRAY = "BUNDLE_STOREDSESSIONIDARRAY";

    private Track mTrackData;

    private ListView mListview;
    private SchePagerAdapter mAdapter;
    private boolean mIsFavoriteMode;
    private List<Integer> mStoredSessionIDs;

    public static SchePagerFragment newInstance(Track track, @Nullable Boolean isFavoriteMode, @Nullable ArrayList<Integer> storedSessionIDs) {
        SchePagerFragment fragment = new SchePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG, track);
        if (isFavoriteMode) {
            bundle.putBoolean(BUNDLE_ISFAVORITEMODE, isFavoriteMode);
            int[] storedSessionIDArray = Ints.toArray(storedSessionIDs);
            bundle.putIntArray(BUNDLE_STOREDSESSIONIDARRAY, storedSessionIDArray);
        }

        fragment.setArguments(bundle);

        return fragment;
    }

    public SchePagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mTrackData = (Track) getArguments().getSerializable(TAG);
            if (getArguments().getBoolean(BUNDLE_ISFAVORITEMODE)) {
                mIsFavoriteMode = getArguments().getBoolean(BUNDLE_ISFAVORITEMODE);
                mStoredSessionIDs = Ints.asList(getArguments().getIntArray(BUNDLE_STOREDSESSIONIDARRAY));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schepager, container, false);

        initScheListView(rootView);

        return rootView;
    }

    private void initScheListView(View rootView) {
        mListview = (ListView) rootView.findViewById(R.id.schepager_list);
        mAdapter = new SchePagerAdapter(mTrackData, getActivity(), mIsFavoriteMode, mStoredSessionIDs);

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAdapter.mSessionItems.get(position).is_session){
                    ((ScheActivity) getActivity()).setDetailSessionFragment(mAdapter.mSessionItems.get(position));
                }
            }
        });
        mListview.setAdapter(mAdapter);
    }
}
