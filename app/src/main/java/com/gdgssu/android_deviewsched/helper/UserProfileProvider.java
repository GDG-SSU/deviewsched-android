package com.gdgssu.android_deviewsched.helper;

import com.facebook.Profile;
import com.gdgssu.android_deviewsched.model.User;

public class UserProfileProvider {
    public static User getUserProfile() {
        User user = new User();
        Profile profile = Profile.getCurrentProfile();
        user.name = profile.getName();
        user.picture = profile.getProfilePictureUri(150, 150).toString();

        return user;
    }
}
