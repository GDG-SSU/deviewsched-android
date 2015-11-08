package com.gdgssu.android_deviewsched.ui.sche;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.model.sessioninfo.Session;
import com.gdgssu.android_deviewsched.ui.DeviewFragment;
import com.gdgssu.android_deviewsched.ui.detailsession.DetailSessionFragment;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class ScheActivity extends AppCompatActivity implements DeviewFragment.OnFragmentInteractionListener {

    private static final String TAG = makeLogTag("ScheActivity");
    private static final String KEY_TITLE = "title";

    private CharSequence title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sche);

        if (getIntent() != null) {
            this.title = getIntent().getStringExtra(KEY_TITLE);
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.sche_list_container, ScheFragment.newInstance(this.title))
                .commit();
    }

    public void setDetailSessionFragment(Session sessionData) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.detailsession_container, DetailSessionFragment.newInstance(sessionData))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
