package com.gdgssu.android_deviewsched.model.sessioninfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Day implements Serializable {

    public ArrayList<Track> tracks;

}
