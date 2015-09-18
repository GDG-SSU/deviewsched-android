package com.gdgssu.android_deviewsched.helper;

import android.util.Base64;

import com.gdgssu.android_deviewsched.model.RequestData;
import com.gdgssu.android_deviewsched.util.DeviewSchedConfig;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class AuthorizationHelper {

    public static String getAuthorizationHeader(String url, String method, String facebookToken, String seriaizedParameters) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String nonce = generateRandomString(32);
        long timestamp = System.currentTimeMillis() / 1000;
        String signature_data = String.format("%s:%s:%s:%s:%s", timestamp, method, url, facebookToken, seriaizedParameters);
        String HMAC_SHA1_key = DeviewSchedConfig.FACEBOOK_APP_SECRET + nonce;
        String signature = Base64.encodeToString(HMAC_SHA1(signature_data, HMAC_SHA1_key), Base64.DEFAULT);
        signature = deleteNewline(signature);

        RequestData requestData = new RequestData(facebookToken, timestamp, nonce, signature);
        Gson gson = new Gson();
        String requestJson = gson.toJson(requestData);

        String result = Base64.encodeToString(requestJson.getBytes("UTF-8"), Base64.DEFAULT);
        result = deleteNewline(result);
        result = String.format("X-DeviewSchedAuth %s", result);

        return result;

    }

    private static String deleteNewline(String text) {
        return text.replace("\n", "");
    }

    private static String generateRandomString(int length) {
        StringBuffer buffer = new StringBuffer();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        int charactersLength = characters.length();

        for (int i = 0; i < length; i++) {
            double index = Math.random() * charactersLength;
            buffer.append(characters.charAt((int) index));
        }

        return buffer.toString();
    }

    public static byte[] HMAC_SHA1(String s, String keyString) throws
            UnsupportedEncodingException, NoSuchAlgorithmException,
            InvalidKeyException {

        SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(key);

        byte[] bytes = mac.doFinal(s.getBytes("UTF-8"));

        return bytes;
    }
}
