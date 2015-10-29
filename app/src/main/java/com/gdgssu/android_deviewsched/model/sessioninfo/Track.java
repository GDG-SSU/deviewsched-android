package com.gdgssu.android_deviewsched.model.sessioninfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gdgssu.android_deviewsched.model.sessioninfo.Session;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Track implements Serializable {

    public ArrayList<Session> sessions;

}