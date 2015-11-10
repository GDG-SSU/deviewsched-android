package com.gdgssu.android_deviewsched.helper;

import android.net.Uri;

public interface ProfileChangedListener {
    void updateUserProfile(Uri imageUri, String name);
}
