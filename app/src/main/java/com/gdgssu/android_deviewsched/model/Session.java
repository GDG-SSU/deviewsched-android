package com.gdgssu.android_deviewsched.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Session implements Serializable {

    public int day;
    public long starts_at;
    public ArrayList<Speaker> speakers;
    public int track;
    public int id;
    public String title;
    public long ends_at;
    public boolean isSelected = false;

}
