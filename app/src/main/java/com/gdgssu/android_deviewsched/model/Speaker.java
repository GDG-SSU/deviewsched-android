package com.gdgssu.android_deviewsched.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Speaker implements Serializable {

    public int id;
    public String name;
    public String organization;
    public String picture;
    public String introduction;
    public String email;

}
