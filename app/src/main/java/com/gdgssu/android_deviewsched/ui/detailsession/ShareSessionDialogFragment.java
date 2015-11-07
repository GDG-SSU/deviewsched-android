package com.gdgssu.android_deviewsched.ui.detailsession;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.gdgssu.android_deviewsched.R;

import static com.gdgssu.android_deviewsched.util.LogUtils.LOGI;
import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class ShareSessionDialogFragment extends DialogFragment {

    private static final String TAG = makeLogTag("ShareSessionDialogFragment");

    private static final int SHARE_FACEBOOK = 0;
    private static final int SHARE_TWITTER = 1;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.share_session_dialog_title))
                .setItems(R.array.share_sns_list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case SHARE_FACEBOOK:
                                LOGI(TAG, "share facebook");

                                break;

                            case SHARE_TWITTER:
                                LOGI(TAG, "share twitter");

                                break;
                        }
                    }
                });

        return builder.create();
    }
}
