package com.gdgssu.android_deviewsched.helper;

import android.content.Intent;
import android.net.Uri;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class SpeakerURIHandler {

    private static final String TAG = makeLogTag("SpeakerURIHandler");

    public static Intent getIntent(String uri) {
        if (uri.startsWith("mailto")) {
            return new Intent(Intent.ACTION_SENDTO, Uri.parse(uri));
        } else if (uri.startsWith("http")) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        } else {
            try {
                throw new Exception("Cannot find speaker uri action");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
