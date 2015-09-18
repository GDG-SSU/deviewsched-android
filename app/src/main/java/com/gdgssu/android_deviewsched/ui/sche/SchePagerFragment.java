package com.gdgssu.android_deviewsched.ui.sche;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gdgssu.android_deviewsched.DeviewSchedApplication;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.model.DetailSessionInfo;
import com.gdgssu.android_deviewsched.model.FavoriteSession;
import com.gdgssu.android_deviewsched.model.Track;
import com.gdgssu.android_deviewsched.ui.detailsession.DetailSessionActivity;
import com.gdgssu.android_deviewsched.ui.selectsession.SelectSessionActivity;

import static com.navercorp.volleyextensions.volleyer.Volleyer.volleyer;

public class SchePagerFragment extends Fragment {

    private static final String TAG = "SchePagerFragment";

    private Track mTrackData;

    private ListView listview;
    private SchePagerAdapter adapter;
    private FavoriteSession sessionList;

    public static SchePagerFragment newInstance(Track track) {
        SchePagerFragment fragment = new SchePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG, track);
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
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sche_pager, container, false);

        initScheListView(rootView);

        return rootView;
    }

    private void initScheListView(View rootView) {
        listview = (ListView) rootView.findViewById(R.id.fragment_sche_pager_list);
        adapter = new SchePagerAdapter(mTrackData, DeviewSchedApplication.GLOBAL_CONTEXT);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                volleyer(DeviewSchedApplication.deviewRequestQueue)
                        .get(DeviewSchedApplication.HOST_URL + "2015/" + mTrackData.sessions.get(position).id)
                        .withTargetClass(DetailSessionInfo.class)
                        .withListener(new Response.Listener<DetailSessionInfo>() {
                            @Override
                            public void onResponse(DetailSessionInfo item) {

                                Intent intent = new Intent(new Intent(getActivity(), DetailSessionActivity.class));
                                intent.putExtra("DetailSessionInfo", item);
                                getActivity().startActivity(intent);

                            }
                        })
                        .withErrorListener(new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, error.toString());
                            }
                        })
                        .execute();

            }
        });
        listview.setAdapter(adapter);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_sche, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_all_sche_favorite:

                Intent intent = new Intent(getActivity(), SelectSessionActivity.class);
                startActivity(intent);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
