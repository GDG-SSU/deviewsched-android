package com.gdgssu.android_deviewsched.ui.account;

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
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gdgssu.android_deviewsched.DeviewSchedApplication;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.helper.LoginPreferenceHelper;
import com.gdgssu.android_deviewsched.util.LogUtils;

import static com.gdgssu.android_deviewsched.util.LogUtils.LOGI;
import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class AccountDialogFragment extends DialogFragment implements FacebookCallback<LoginResult> {

    private static final String TAG = makeLogTag("AccountDialogFragment");

    public static final int ACCOUNT_REQUEST = 100;

    private CallbackManager mCallbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCallbackManager = CallbackManager.Factory.create();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_account, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final LoginButton loginButton = (LoginButton) view.findViewById(R.id.account_button_facebooklogin_2);
        loginButton.setReadPermissions("user_friends");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!DeviewSchedApplication.sLoginstate) {

                } else if (DeviewSchedApplication.sLoginstate) {
                    //Todo : 로그아웃버튼을 누르고 한번 더 Confirm을 누를때 로그아웃 로직을 발동하여야합니다.
//                    LoginPreferenceHelper prefHelper = new LoginPreferenceHelper(getActivity());
//                    prefHelper.setPrefLoginValue(LoginPreferenceHelper.PREF_LOGIN_STATE, false);
//                    DeviewSchedApplication.sLoginstate = false;
//                    getActivity().setResult(ACCOUNT_REQUEST);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        LoginPreferenceHelper prefHelper = new LoginPreferenceHelper(getActivity());
        prefHelper.setPrefLoginValue(LoginPreferenceHelper.PREF_LOGIN_STATE, true);
        DeviewSchedApplication.sLoginstate = true;
        getActivity().setResult(ACCOUNT_REQUEST);
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }
}
