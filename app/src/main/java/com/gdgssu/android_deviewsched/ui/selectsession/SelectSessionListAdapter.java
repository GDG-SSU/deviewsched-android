package com.gdgssu.android_deviewsched.ui.selectsession;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gdgssu.android_deviewsched.DeviewSchedApplication;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.model.Day;
import com.gdgssu.android_deviewsched.model.Session;
import com.gdgssu.android_deviewsched.util.GlideCircleTransform;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SelectSessionListAdapter extends BaseAdapter {

    private final String[] SESSION_TIME =
            {
                    "10:00~10:50", "11:00~11:50", "12:00~12:50", "14:10 ~ 15:00", "15:10 ~ 16:00", "16:10 ~ 17:00"
            };

    private static final String TAG = SelectSessionListAdapter.class.getSimpleName();

    public static ArrayList<Session> sessionItems = new ArrayList<>();

    private LayoutInflater mInflater;
    private Context mContext;

    public SelectSessionListAdapter(Day day, Context context) {
        makeSelectSessionList(day);
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
    }

    public void makeSelectSessionList(Day day) {

        try {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 4; j++) {
                    sessionItems.add(day.tracks.get(j).sessions.get(i));
                }
            }
        } catch (IndexOutOfBoundsException e) {
            Log.d(TAG, e.toString());
        }
    }

    @Override
    public int getCount() {
        return sessionItems.size();
    }

    @Override
    public Object getItem(int position) {
        return sessionItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SelectSessionHolder selectHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_select_session, parent, false);

            selectHolder = new SelectSessionHolder();

            selectHolder.sessionTime = (TextView) convertView.findViewById(R.id.item_select_session_time);
            selectHolder.sessionTrack = (TextView) convertView.findViewById(R.id.item_select_session_track);
            selectHolder.speakerImg = (ImageView) convertView.findViewById(R.id.item_select_session_speaker_img);
            selectHolder.speakerImgSecond = (ImageView) convertView.findViewById(R.id.item_select_session_speaker_img_second);
            selectHolder.speakerName = (TextView) convertView.findViewById(R.id.item_select_session_speaker_name);
            selectHolder.sessionName = (TextView) convertView.findViewById(R.id.item_select_session_session_title);

            convertView.setTag(selectHolder);

        } else {
            selectHolder = (SelectSessionHolder) convertView.getTag();
        }

        Session sessionItem = sessionItems.get(position);

        if (sessionItems.get(position).isSelected){
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        }else{
            convertView.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
        }

        selectHolder.sessionTime.setText(String.format("%s~%s", transferTimestamp(sessionItem.starts_at), transferTimestamp(sessionItem.ends_at)));
        selectHolder.sessionTrack.setText(String.format("Track %s", sessionItem.track));

        if (sessionItem.speakers.size() > 1) {
            setTwoSpeakerInfo(selectHolder, sessionItem);
        } else {
            setOneSpeakerInfo(selectHolder, sessionItem);
        }

        selectHolder.sessionName.setText(sessionItem.title);

        return convertView;
    }

    private String transferTimestamp(long timestamp) {
        Date timeStampDate = new Date(timestamp);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.KOREA);

        return simpleDateFormat.format(timeStampDate);
    }

    public void setDayItem(Day day) {
        sessionItems.clear();
        makeSelectSessionList(day);
    }

    public void setOneSpeakerInfo(SelectSessionHolder sessionHolder, Session sessionItem) {
        sessionHolder.speakerImgSecond.setVisibility(View.GONE);

        Glide.with(mContext)
                .load(sessionItem.speakers.get(0).picture)
                .transform(new GlideCircleTransform(DeviewSchedApplication.GLOBAL_CONTEXT))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.person_image_empty)
                .into(sessionHolder.speakerImg);

        sessionHolder.speakerName.setText(sessionItem.speakers.get(0).name);
    }

    private void setTwoSpeakerInfo(SelectSessionHolder sessionHolder, Session sessionItem) {
        sessionHolder.speakerImgSecond.setVisibility(View.VISIBLE);

        Glide.with(mContext)
                .load(sessionItem.speakers.get(0).picture)
                .transform(new GlideCircleTransform(DeviewSchedApplication.GLOBAL_CONTEXT))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.person_image_empty)
                .into(sessionHolder.speakerImg);

        Glide.with(mContext)
                .load(sessionItem.speakers.get(1).picture)
                .transform(new GlideCircleTransform(DeviewSchedApplication.GLOBAL_CONTEXT))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.person_image_empty)
                .into(sessionHolder.speakerImgSecond);

        sessionHolder.speakerName.setText(sessionItem.speakers.get(0).name + "/" + sessionItem.speakers.get(1).name);
    }

    public static class SelectSessionHolder {

        public TextView sessionTime;
        public TextView sessionTrack;
        public ImageView speakerImg;
        public ImageView speakerImgSecond;
        public TextView speakerName;
        public TextView sessionName;

    }
}
