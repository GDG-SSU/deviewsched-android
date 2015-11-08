package com.gdgssu.android_deviewsched.ui.sche;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.model.sessioninfo.Session;
import com.gdgssu.android_deviewsched.model.sessioninfo.Track;
import com.gdgssu.android_deviewsched.util.GlideCircleTransform;

import java.util.ArrayList;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class SchePagerAdapter extends BaseAdapter {

    private static final String TAG = makeLogTag("SchePagerAdapter");

    private LayoutInflater mInflater;
    private ArrayList<Session> sessionItems;
    private Context mContext;

    public SchePagerAdapter(Track track, Context context) {

        this.sessionItems = track.sessions;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
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
        SessionViewHolder sessionHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_session_sche_sessioninfo, parent, false);

            sessionHolder = new SessionViewHolder();

            sessionHolder.sessionTime = (TextView) convertView.findViewById(R.id.item_session_sche_sessioninfo_time);
            sessionHolder.speakerImg = (ImageView) convertView.findViewById(R.id.item_session_sche_sessioninfo_speaker_img);
            sessionHolder.speakerImgSecond = (ImageView) convertView.findViewById(R.id.item_session_sche_sessioninfo_speaker_img_second);
            sessionHolder.speakerName = (TextView) convertView.findViewById(R.id.item_session_sche_sessioninfo_speaker_name);
            sessionHolder.sessionName = (TextView) convertView.findViewById(R.id.item_session_sche_sessioninfo_session_title);

            convertView.setTag(sessionHolder);

        } else {
            sessionHolder = (SessionViewHolder) convertView.getTag();
        }

        Session sessionItem = sessionItems.get(position);
        sessionHolder.sessionTime.setText(String.format("%s~%s", sessionItem.starts_at, sessionItem.ends_at));

        if (sessionItem.speakers.size() > 1) {
            setTwoSpeakerInfo(sessionHolder, sessionItem);
        } else {
            setOneSpeakerInfo(sessionHolder, sessionItem);
        }

        if (!sessionItem.is_session) {
            sessionHolder.speakerName.setVisibility(View.GONE);
        }
        sessionHolder.sessionName.setText(sessionItem.title);

        return convertView;
    }

    public void setOneSpeakerInfo(SessionViewHolder sessionHolder, Session sessionItem) {
        sessionHolder.speakerImgSecond.setVisibility(View.GONE);
        sessionHolder.speakerName.setText(sessionItem.speakers.get(0).name);

        Object img = getSessionSpeakerImg(sessionItem);

        Glide.with(mContext)
                .load(img)
                .transform(new GlideCircleTransform(mContext))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.person_image_empty)
                .into(sessionHolder.speakerImg);
    }

    private Object getSessionSpeakerImg(Session sessionItem) {
        Object img = null;
        if (sessionItem.is_session) {
            img = sessionItem.speakers.get(0).picture;
        } else {
            if (sessionItem.title.equals("참가등록")) {
                img = R.drawable.ic_check_circle_black_24dp;
            } else if (sessionItem.title.equals("Keynote")) {
                img = "http://tv03.search.naver.net/thm?size=120x150&quality=9&q=http://sstatic.naver.net/people/46/201110261031042641.jpg";
            } else if (sessionItem.title.equals("점심시간")) {
                img = R.drawable.ic_restaurant_menu_black_24dp;
            } else if (sessionItem.title.equals("BOF")) {
                img = R.drawable.ic_group_black_24dp;
            }
        }
        return img;
    }

    private void setTwoSpeakerInfo(SessionViewHolder sessionHolder, Session sessionItem) {
        sessionHolder.speakerImgSecond.setVisibility(View.VISIBLE);

        Glide.with(mContext)
                .load(sessionItem.speakers.get(0).picture)
                .transform(new GlideCircleTransform(mContext))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.person_image_empty)
                .into(sessionHolder.speakerImg);

        Glide.with(mContext)
                .load(sessionItem.speakers.get(1).picture)
                .transform(new GlideCircleTransform(mContext))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(sessionHolder.speakerImgSecond);

        sessionHolder.speakerName.setText(String.format("%s/%s", sessionItem.speakers.get(0).name, sessionItem.speakers.get(1).name));
    }

    public static class SessionViewHolder {

        public TextView sessionTime;
        public ImageView speakerImg;
        public ImageView speakerImgSecond;
        public TextView speakerName;
        public TextView sessionName;

    }
}
