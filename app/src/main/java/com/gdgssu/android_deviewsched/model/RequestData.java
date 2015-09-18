package com.gdgssu.android_deviewsched.model;

public class RequestData {
    public String token;
    public long timestamp;
    public String nonce;
    public String signature;

    public RequestData(String facebookToken, long timestamp, String nonce, String signature) {
        this.token = facebookToken;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.signature = signature;
    }
}
