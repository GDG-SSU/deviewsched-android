package com.gdgssu.android_deviewsched.model.sessioninfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Speaker implements Serializable {

    public int speaker_id;
    public String picture;
    public String name;
    public String organization;
    public String url;
    public String introduction;
}
