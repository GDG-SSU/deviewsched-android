package com.gdgssu.android_deviewsched.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gdgssu.android_deviewsched.DeviewSchedApplication;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.helper.LoginPreferenceHelper;
import com.gdgssu.android_deviewsched.ui.main.MainActivity;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class AccountDialogFragment extends DialogFragment {

    private static final String TAG = makeLogTag("AccountDialogFragment");

    public static final int ACCOUNT_REQUEST = 100;

    private CallbackManager mCallbackManager;
    private LoginButton loginButton;
    private ProfileTracker mProfileTracker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCallbackManager = CallbackManager.Factory.create();
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                MainActivity.setUserInfo();
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_account, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginButton = (LoginButton) view.findViewById(R.id.account_button_facebooklogin_2);
        loginButton.setReadPermissions("public_profile");
        loginButton.setFragment(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        LoginPreferenceHelper prefHelper = new LoginPreferenceHelper(getActivity());
                        prefHelper.setPrefLoginValue(LoginPreferenceHelper.PREF_LOGIN_STATE, true);
                        DeviewSchedApplication.sLoginstate = true;
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        getDialog().dismiss();
    }
}