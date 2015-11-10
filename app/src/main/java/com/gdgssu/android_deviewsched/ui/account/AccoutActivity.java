package com.gdgssu.android_deviewsched.ui.account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gdgssu.android_deviewsched.DeviewSchedApplication;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.helper.LoginPreferenceHelper;
import com.gdgssu.android_deviewsched.helper.UserProfileProvider;
import com.gdgssu.android_deviewsched.model.User;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import static com.gdgssu.android_deviewsched.util.LogUtils.LOGE;
import static com.gdgssu.android_deviewsched.util.LogUtils.LOGI;
import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class AccoutActivity extends AppCompatActivity implements FacebookCallback<LoginResult> {

    private static final String TAG = makeLogTag("AccoutActivity");

    public static final int ACCOUNT_REQUEST = 100;

    private CallbackManager mCallbackManager;
    private OAuthLogin mOAuthLoginModule;
    private static OAuthLoginHandler mOAuthLoginHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accout);

        initNaverLoginInstance();
        mCallbackManager = CallbackManager.Factory.create();

        initView();
    }

    private void initNaverLoginInstance() {

        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(getApplicationContext(),
                null,  //OAUTH_CLIENT_ID
                null,  //OAUTH_CLIENT_SECRET
                null); //OAUTH_CLIENT_NAME

        mOAuthLoginHandler = new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {
                if (success) {
                    String accessToken = mOAuthLoginModule.getAccessToken(getApplicationContext());
                    String refreshToken = mOAuthLoginModule.getRefreshToken(getApplicationContext());
                    long expiresAt = mOAuthLoginModule.getExpiresAt(getApplicationContext());
                    String tokenType = mOAuthLoginModule.getTokenType(getApplicationContext());
                    LOGI(TAG, String.format("AccessToken : %s / RefreshToken : %s / ExpiresAt : %d / TokenType : %s / LoginState : %s", accessToken, refreshToken, expiresAt, tokenType, mOAuthLoginModule.getState(getApplicationContext()).toString()));

                } else {
                    String errorCode = mOAuthLoginModule.getLastErrorCode(getApplicationContext()).getCode();
                    String errorDesc = mOAuthLoginModule.getLastErrorDesc(getApplicationContext());
                    Toast.makeText(getBaseContext(), "errorCode:" + errorCode
                            + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void initView() {
        final LoginButton fbLoginButton = (LoginButton) findViewById(R.id.account_facebooklogin);
        fbLoginButton.setReadPermissions("user_friends");
        fbLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!DeviewSchedApplication.LOGIN_STATE) {
                    fbLoginButton.registerCallback(mCallbackManager, AccoutActivity.this);
                } else if (DeviewSchedApplication.LOGIN_STATE) {
                    LoginPreferenceHelper prefHelper = new LoginPreferenceHelper(getBaseContext());
                    prefHelper.setPrefLoginValue(LoginPreferenceHelper.PREF_LOGIN_STATE, false);
                    DeviewSchedApplication.LOGIN_STATE = false;
                    setResult(ACCOUNT_REQUEST);
                }
            }
        });

        OAuthLoginButton naverLoginButton = (OAuthLoginButton) findViewById(R.id.account_naverlogin);
        naverLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        LoginPreferenceHelper prefHelper = new LoginPreferenceHelper(getBaseContext());
        prefHelper.setPrefLoginValue(LoginPreferenceHelper.PREF_LOGIN_STATE, true);
        DeviewSchedApplication.LOGIN_STATE = true;
        setResult(ACCOUNT_REQUEST);
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
