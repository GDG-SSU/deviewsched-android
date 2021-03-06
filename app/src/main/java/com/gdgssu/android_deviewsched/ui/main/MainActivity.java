package com.gdgssu.android_deviewsched.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.gdgssu.android_deviewsched.DeviewSchedApplication;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.helper.LoginPreferenceHelper;
import com.gdgssu.android_deviewsched.helper.UserProfileProvider;
import com.gdgssu.android_deviewsched.model.User;
import com.gdgssu.android_deviewsched.ui.BaseActivity;

import com.gdgssu.android_deviewsched.ui.login.LoginDialogFragment;
import com.gdgssu.android_deviewsched.ui.location.LocationActivity;
import com.gdgssu.android_deviewsched.ui.sche.ScheActivity;
import com.gdgssu.android_deviewsched.ui.sche.ScheFragment;
import com.gdgssu.android_deviewsched.ui.setting.SettingActivity;
import com.gdgssu.android_deviewsched.util.GlideCircleTransform;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class MainActivity extends BaseActivity {

    private static final String TAG = makeLogTag("MainActivity");

    private MaterialViewPager mViewPager;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ImageView mAvatarImage;
    private TextView mNameText;

    private FragmentManager mFragmentManager;

    private static User mUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent() != null) {
            mUser = (User) getIntent().getSerializableExtra("UserInfo");
        }

        mFragmentManager = getSupportFragmentManager();

        initMaterialViewPager();
        initToolbar();
        initNavigationView();
    }

    private void initToolbar() {
        setTitle("");
        mToolbar = mViewPager.getToolbar();
        setSupportActionBar(mToolbar);
    }

    private void initMaterialViewPager() {
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return RecyclerViewFragment.newInstance();
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 3) {
                    case 0:
                        return getText(R.string.main_pager_one);
                    case 1:
                        return getText(R.string.main_pager_two);
                    case 2:
                        return getText(R.string.main_pager_three);
                }
                return null;
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.colorPrimary,
                                ContextCompat.getDrawable(getBaseContext(), R.drawable.backwall1));
                    case 1:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.colorPrimary,
                                ContextCompat.getDrawable(getBaseContext(), R.drawable.backwall2));
                    case 2:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.colorPrimary,
                                ContextCompat.getDrawable(getBaseContext(), R.drawable.backwall3));
                }
                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }

    private void initNavigationView() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(mNavigationView);

        View headerView = mNavigationView.inflateHeaderView(R.layout.layout_nav_header);
        mAvatarImage = (ImageView) headerView.findViewById(R.id.navheader_image_userphoto);
        mNameText = (TextView) headerView.findViewById(R.id.navheader_text_username);

        setUserInfo();

        if (DeviewSchedApplication.sLoginstate) {
            mNavigationView.getMenu().findItem(R.id.nav_account).setTitle(R.string.account_logout);
        }
    }

    public void setUserInfo() {
        mUser = UserProfileProvider.getUserProfile(getBaseContext(), 60);

        if (DeviewSchedApplication.sLoginstate) {
            Glide.with(getBaseContext())
                    .load(mUser.picture)
                    .transform(new GlideCircleTransform(getBaseContext()))
                    .into(mAvatarImage);

            mNameText.setText(mUser.name);
        }
    }

    private void resetUserInfo() {
        mAvatarImage.setImageResource(R.drawable.person_image_empty);
        mNameText.setText(getText(R.string.please_login));
        mNavigationView.getMenu().findItem(R.id.nav_account).setTitle(R.string.account_login);

        LoginPreferenceHelper prefHelper = new LoginPreferenceHelper(getBaseContext());
        prefHelper.setPrefLoginValue(LoginPreferenceHelper.PREF_LOGIN_STATE, false);
        DeviewSchedApplication.sLoginstate = false;
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        showHome();
                        break;

                    case R.id.nav_all_schedule:
                        showSche(getResources().getText(R.string.all_schedule), false);
                        break;

                    case R.id.nav_my_schedule:
                        showSche(getResources().getText(R.string.my_schedule), true);
                        break;

                    case R.id.nav_find_friends:
                        showFindFriends();
                        break;

                    case R.id.nav_location:
                        showLocation();
                        break;

                    case R.id.nav_account:
                        if (DeviewSchedApplication.sLoginstate) {
                            LoginManager.getInstance().logOut();
                            resetUserInfo();

                            Toast.makeText(getBaseContext(), getText(R.string.logout_msg), Toast.LENGTH_LONG).show();

                            return true;
                        }
                        showAccount();
                        break;

                    case R.id.nav_setting:
                        showSetting();
                        break;
                }
                menuItem.setChecked(true);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawers();
                return true;
            }
        });
    }

    public void showHome() {
        /**
         * Todo 아래의 메소드가 호출되면 MainActivity위로 있는 모든 Fragment가 소멸됨
         */
        mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void showSche(CharSequence title, boolean isMySession) {
        Intent intent = new Intent(getBaseContext(), ScheActivity.class);
        intent.putExtra(ScheFragment.KEY_TITLE, title);
        intent.putExtra(ScheFragment.KEY_MYSESSION, isMySession);
        startActivity(intent);
    }

    private void showFindFriends() {

    }

    private void showLocation() {
        startActivity(new Intent(getBaseContext(), LocationActivity.class));
    }

    private void showAccount() {
        LoginDialogFragment dialogFragment = LoginDialogFragment.newInstance("MainActivity");
        dialogFragment.show(getSupportFragmentManager(), "LoginDialogFragment");
    }

    private void showSetting() {
        startActivity(new Intent(getBaseContext(), SettingActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            showHome();
        }
    }

    @Override
    public void updateUserProfile() {
        if (DeviewSchedApplication.sLoginstate) {
            setUserInfo();
            mNavigationView.getMenu().findItem(R.id.nav_account).setTitle(R.string.account_logout);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
