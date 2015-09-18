package com.gdgssu.android_deviewsched.ui.splashlogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gdgssu.android_deviewsched.DeviewSchedApplication;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.helper.AuthorizationHelper;
import com.gdgssu.android_deviewsched.helper.LoginPreferenceHelper;
import com.gdgssu.android_deviewsched.model.AllScheItems;
import com.gdgssu.android_deviewsched.model.UserItem;
import com.gdgssu.android_deviewsched.ui.MainActivity;

import static com.navercorp.volleyextensions.volleyer.Volleyer.volleyer;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SplashLoginActivity extends AppCompatActivity implements FacebookCallback<LoginResult> {

    private static final String TAG = "SplashLoginActivity";

    private CallbackManager callbackManager;
    private LinearLayout frontInfo;
    private LoginButton loginButton;

    private String currentTokenString;

    private LoginPreferenceHelper prefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashlogin);

        initView();

        callbackManager = CallbackManager.Factory.create();

        prefHelper = new LoginPreferenceHelper(DeviewSchedApplication.GLOBAL_CONTEXT);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (DeviewSchedApplication.LOGIN_STATE) {
                    getAllScheData();
                } else {
                    frontInfoLayoutFadeout();
                }
            }
        }, 2000);
    }

    private void initView() {
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.registerCallback(callbackManager, SplashLoginActivity.this);
            }
        });

        frontInfo = (LinearLayout) findViewById(R.id.activity_splashlogin_frontinfo);

    }

    private void getAllScheData() {

        volleyer(DeviewSchedApplication.deviewRequestQueue)
                .get(DeviewSchedApplication.HOST_URL + "2015/list")
                .withTargetClass(AllScheItems.class)
                .withListener(new Response.Listener<AllScheItems>() {
                    @Override
                    public void onResponse(AllScheItems items) {
                        AllScheItems.result = items;
                        try {
                            loginRequest();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (InvalidKeyException e) {
                            e.printStackTrace();
                        }
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

    private void frontInfoLayoutFadeout() {
        final Animation animationFadeout;
        animationFadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
        animationFadeout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                frontInfo.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        frontInfo.startAnimation(animationFadeout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void loginRequest() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

        currentTokenString = AccessToken.getCurrentAccessToken().getToken();
        String beforeTokenString = prefHelper.getAccessTokenValue(LoginPreferenceHelper.PREF_ACCESS_TOKEN, "");
        if (!currentTokenString.equals(beforeTokenString)) {
            prefHelper.setAccessTokenValue(LoginPreferenceHelper.PREF_ACCESS_TOKEN, currentTokenString);
        }

        String authString = AuthorizationHelper.getAuthorizationHeader("/user", "POST", currentTokenString, "");

        volleyer(DeviewSchedApplication.deviewRequestQueue).post(DeviewSchedApplication.HOST_URL + "user")
                .addHeader("Authorization", authString)
                .withTargetClass(UserItem.class)
                .withListener(new Response.Listener<UserItem>() {
                    @Override
                    public void onResponse(UserItem response) {
                        Intent intent = new Intent(SplashLoginActivity.this, MainActivity.class);
                        intent.putExtra("UserInfo", response);

                        finish();
                        startActivity(intent);
                    }
                })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
                .execute();
    }

    /**
     * Facebook서버로부터 로그인 가능을 수행하기위해
     * loginButton.registerCallback의 인터페이스 메소드를 구현하는 부분
     * onSuccess / onCancel / onError 에 대한 구현이 아래에 있음.
     */

    @Override
    public void onSuccess(LoginResult loginResult) {
        loginButton.setVisibility(View.INVISIBLE);
        getAllScheData();

        prefHelper.setPrefLoginValue(LoginPreferenceHelper.PREF_LOGIN_STATE, true);
        prefHelper.setAccessTokenValue(LoginPreferenceHelper.PREF_ACCESS_TOKEN, currentTokenString);

    }

    @Override
    public void onCancel() {

        Log.d("Login", "onCancel");

    }

    @Override
    public void onError(FacebookException e) {

        Log.d("Login", "onError" + e.toString());

    }
}
