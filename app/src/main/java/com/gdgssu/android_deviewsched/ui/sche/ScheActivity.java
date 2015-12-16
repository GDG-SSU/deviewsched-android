package com.gdgssu.android_deviewsched.ui.sche;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.model.sessioninfo.Session;
import com.gdgssu.android_deviewsched.ui.BaseActivity;
import com.gdgssu.android_deviewsched.ui.login.LoginDialogFragment;
import com.gdgssu.android_deviewsched.ui.detailsession.DetailSessionFragment;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class ScheActivity extends BaseActivity {

    private static final String TAG = makeLogTag("ScheActivity");
    private static final String KEY_TITLE = "title";
    private static final String KEY_MYSESSION = "mysession";

    private CharSequence mTitle;
    private boolean mIsMySession;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sche);

        if (getIntent() != null) {
            this.mTitle = getIntent().getStringExtra(KEY_TITLE);
            this.mIsMySession = getIntent().getBooleanExtra(KEY_MYSESSION, false);
        }

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .add(R.id.sche_container_list, ScheFragment.newInstance(this.mTitle, this.mIsMySession))
                .commit();
    }

    public void setDetailSessionFragment(Session sessionData) {
        mFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.sche_container_detailsession, DetailSessionFragment.newInstance(sessionData))
                .addToBackStack("detailsession")
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStackImmediate("detailsession", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void showLoginDialog() {
        LoginDialogFragment dialogFragment = LoginDialogFragment.newInstance("ScheActivity");
        dialogFragment.show(mFragmentManager, "LoginDialogFragment");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
