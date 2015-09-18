package com.gdgssu.android_deviewsched.model;

public class FindFriend {

    private String imgURL;
    private String userName;

    public FindFriend(String imgURL, String userName) {
        this.imgURL = imgURL;
        this.userName = userName;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
