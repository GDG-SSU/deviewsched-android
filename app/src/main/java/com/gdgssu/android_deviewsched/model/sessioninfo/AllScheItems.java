package com.gdgssu.android_deviewsched.model.sessioninfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)

public class AllScheItems {
    public ArrayList<Day> days = new ArrayList<>();
}
