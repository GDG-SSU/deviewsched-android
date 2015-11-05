package com.gdgssu.android_deviewsched.ui.account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gdgssu.android_deviewsched.DeviewSchedApplication;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.helper.FavoritePreferenceHelper;
import com.gdgssu.android_deviewsched.helper.LoginPreferenceHelper;

import static com.gdgssu.android_deviewsched.util.LogUtils.LOGE;
import static com.gdgssu.android_deviewsched.util.LogUtils.LOGI;
import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class AccoutActivity extends AppCompatActivity implements FacebookCallback<LoginResult> {

    private static final String TAG = makeLogTag("AccoutActivity");

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accout);

        initView();

        callbackManager = CallbackManager.Factory.create();
        setLoginState();
        setFavorSessionState();
    }

    private void initView() {
        final LoginButton fbLoginButton = (LoginButton)findViewById(R.id.account_facebooklogin);
        fbLoginButton.setReadPermissions("user_friends");
        fbLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbLoginButton.registerCallback(callbackManager, AccoutActivity.this);
            }
        });
    }

    public void setLoginState() {
        LoginPreferenceHelper prefHelper = new LoginPreferenceHelper(getBaseContext());
        DeviewSchedApplication.LOGIN_STATE = prefHelper.getPrefLoginValue(LoginPreferenceHelper.PREF_LOGIN_STATE, false);
    }

    public void setFavorSessionState() {
        /**
         * 로그인할떄 false로 돌려야함
         */
        FavoritePreferenceHelper prefHelper = new FavoritePreferenceHelper(getBaseContext());
        DeviewSchedApplication.FAVOR_SESSION_STATE = prefHelper.getFavorSessionState(FavoritePreferenceHelper.PREF_FAVOR_STATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        LOGI(TAG, "onSuccess");
    }

    @Override
    public void onCancel() {
        LOGI(TAG, "onCancel");
    }

    @Override
    public void onError(FacebookException error) {
        LOGE(TAG, "onError", error.getCause());
    }
}
