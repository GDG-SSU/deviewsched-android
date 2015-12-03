package com.gdgssu.android_deviewsched.helper;

import android.content.Context;
import android.util.Log;

import com.facebook.Profile;
import com.gdgssu.android_deviewsched.model.User;
import com.gdgssu.android_deviewsched.util.DptopxChanger;

public class UserProfileProvider {

    public static User getUserProfile(Context context, int dp) {
        int size = DptopxChanger.dpToPXChange(context, dp);
        User user = new User();
        Profile profile = Profile.getCurrentProfile();
        if (profile!=null){
            user.name = profile.getName();
            user.picture = profile.getProfilePictureUri(size, size).toString();
        }

        return user;
    }
}
