package com.gdgssu.android_deviewsched.model.sessioninfo;

import java.io.Serializable;
import java.util.ArrayList;

public class Session implements Serializable {

    public int session_id;
    public boolean is_session;
    public String starts_at;
    public String ends_at;
    public String title;
    public String description;
    public String presentation_url;
    public String video_url;
    public ArrayList<Speaker> speakers;
    public boolean isSelected = false;

}
