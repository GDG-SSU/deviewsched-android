package com.gdgssu.android_deviewsched.ui.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gdgssu.android_deviewsched.DeviewSchedApplication;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.helper.LoginPreferenceHelper;
import com.gdgssu.android_deviewsched.helper.ProfileChangedListener;
import com.gdgssu.android_deviewsched.ui.BaseActivity;

import static com.gdgssu.android_deviewsched.util.LogUtils.LOGI;
import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class LoginDialogFragment extends DialogFragment {

    private static final String TAG = makeLogTag("LoginDialogFragment");
    public static final String BUNDLE_FROM_ACTIVITY = "BUNDLE_FROM_ACTIVITY";

    private CallbackManager mCallbackManager;
    private LoginButton loginButton;
    private ProfileTracker mProfileTracker;
    private ProfileChangedListener mListener;

    public static LoginDialogFragment newInstance(CharSequence fromActivity) {
        LoginDialogFragment fragment = new LoginDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence(BUNDLE_FROM_ACTIVITY, fromActivity);
        fragment.setArguments(bundle);

        return fragment;
    }

    public LoginDialogFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListener = (BaseActivity) getActivity();

        mCallbackManager = CallbackManager.Factory.create();
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                mListener.updateUserProfile();
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

        loginButton = (LoginButton) view.findViewById(R.id.account_button_facebooklogin);
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

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        if (getArguments().getCharSequence(BUNDLE_FROM_ACTIVITY).equals("ScheActivity")) {
            if (!DeviewSchedApplication.sLoginstate) {
                getActivity().finish();
            }
        }
    }
}
