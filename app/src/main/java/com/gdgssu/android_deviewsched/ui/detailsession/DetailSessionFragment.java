package com.gdgssu.android_deviewsched.ui.detailsession;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.helper.SpeakerURIHandler;
import com.gdgssu.android_deviewsched.model.sessioninfo.Session;
import com.gdgssu.android_deviewsched.model.sessioninfo.Speaker;
import com.gdgssu.android_deviewsched.ui.sche.ScheActivity;
import com.gdgssu.android_deviewsched.util.GlideCircleTransform;

import java.util.ArrayList;

public class DetailSessionFragment extends Fragment {

    public static final String KEY_SESSION = "SESSION_DATA";

    private Session sessionInfo;
    private ArrayList<Speaker> speakers;

    private TextView sessionTitle;
    private TextView sessionDesc;
    private LinearLayout speakerBasket;

    public static DetailSessionFragment newInstance(Session sessionData) {
        DetailSessionFragment fragment = new DetailSessionFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_SESSION, sessionData);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailSessionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().getSerializable(KEY_SESSION) != null) {
            sessionInfo = (Session) getArguments().getSerializable(KEY_SESSION);
            speakers = sessionInfo.speakers;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_detailsession, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }


    public void setData() {
        sessionTitle.setText(sessionInfo.title);
        sessionDesc.setText(Html.fromHtml(sessionInfo.description));

        for (int i = 0; i < speakers.size(); i++) {
            setSpeakerInfo(i);
        }
    }

    private void setSpeakerInfo(final int index) {
        RelativeLayout speakerInfoLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_speaker_info, null, false);
        ImageView speakerPicture = (ImageView) speakerInfoLayout.findViewById(R.id.speaker_img);
        TextView speakerName = (TextView) speakerInfoLayout.findViewById(R.id.speaker_name);
        TextView speakerOrg = (TextView) speakerInfoLayout.findViewById(R.id.speaker_company);
        ImageView speakerUrl = (ImageView) speakerInfoLayout.findViewById(R.id.speaker_url);
        TextView speakerIntro = (TextView) speakerInfoLayout.findViewById(R.id.speaker_intro);

        Glide.with(getActivity())
                .load(speakers.get(index).picture)
                .transform(new GlideCircleTransform(getActivity()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.person_image_empty)
                .into(speakerPicture);

        speakerName.setText(speakers.get(index).name);
        speakerOrg.setText(speakers.get(index).organization);

        if (speakers.get(index).url.equals("")) {
            speakerUrl.setVisibility(View.GONE);
        } else {
            speakerUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent urlIntent = SpeakerURIHandler.getIntent(speakers.get(index).url);
                    if (urlIntent != null) {
                        startActivity(urlIntent);
                    }
                }
            });
        }

        speakerIntro.setText(Html.fromHtml(speakers.get(index).introduction));

        speakerBasket.addView(speakerInfoLayout);
    }


    private void initView(View rootView) {

        initToolbar(rootView);

        sessionTitle = (TextView) rootView.findViewById(R.id.detailsession_title);
        sessionDesc = (TextView) rootView.findViewById(R.id.detailsession_sessioninfo);
        speakerBasket = (LinearLayout) rootView.findViewById(R.id.detailsession_speaker_basket);
        ImageView backarrowButton = (ImageView) rootView.findViewById(R.id.detailsession_backbutton);
        backarrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScheActivity) getActivity()).onBackPressed();
            }
        });

        ImageView shareButton = (ImageView) rootView.findViewById(R.id.detailsession_share);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSessionDialogFragment dialogFragment = new ShareSessionDialogFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "ShareSessionDialog");
            }
        });

        setData();

        ImageView backdropImg = (ImageView) rootView.findViewById(R.id.detailsession_backdrop);
        Glide.with(getActivity())
                .load("http://insanehong.kr/post/deview2013/@img/keynote.jpg")
                .centerCrop()
                .into(backdropImg);
    }

    private void initToolbar(View rootView) {
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.detailsession_toolbar);
        ((ScheActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((ScheActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.detail_session_activity_title));
        }
    }
}
