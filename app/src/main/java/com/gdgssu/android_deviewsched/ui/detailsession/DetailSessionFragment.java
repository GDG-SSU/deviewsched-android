package com.gdgssu.android_deviewsched.ui.detailsession;

import android.content.ActivityNotFoundException;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.helper.SpeakerURIHandler;
import com.gdgssu.android_deviewsched.model.sessioninfo.Session;
import com.gdgssu.android_deviewsched.model.sessioninfo.Speaker;
import com.gdgssu.android_deviewsched.ui.sche.ScheActivity;
import com.gdgssu.android_deviewsched.util.GlideCircleTransform;

import java.util.ArrayList;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class DetailSessionFragment extends Fragment {

    private static final String TAG = makeLogTag("DetailSessionFragment");

    public static final String KEY_SESSION = "SESSION_DATA";

    private Session mSessionInfo;
    private ArrayList<Speaker> mSpeakers;

    private TextView mSessionTitle;
    private TextView mSessionDesc;
    private LinearLayout mSpeakerLayout;

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
            mSessionInfo = (Session) getArguments().getSerializable(KEY_SESSION);
            mSpeakers = mSessionInfo.speakers;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detailsession, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }


    public void setData() {
        mSessionTitle.setText(mSessionInfo.title);
        mSessionDesc.setText(Html.fromHtml(mSessionInfo.description));

        for (int i = 0; i < mSpeakers.size(); i++) {
            setSpeakerInfo(i);
        }
    }

    private void setSpeakerInfo(final int index) {
        RelativeLayout speakerInfoLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_speaker_info, null, false);
        ImageView speakerPicture = (ImageView) speakerInfoLayout.findViewById(R.id.detailsession_image_speakerphoto);
        TextView speakerName = (TextView) speakerInfoLayout.findViewById(R.id.detailsession_text_speakername);
        TextView speakerOrg = (TextView) speakerInfoLayout.findViewById(R.id.detailsession_text_speakercompany);
        ImageView speakerUrl = (ImageView) speakerInfoLayout.findViewById(R.id.detailsession_image_speakerurl);
        TextView speakerIntro = (TextView) speakerInfoLayout.findViewById(R.id.speaker_intro);

        Glide.with(getActivity())
                .load(mSpeakers.get(index).picture)
                .transform(new GlideCircleTransform(getActivity()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.person_image_empty)
                .into(speakerPicture);

        speakerName.setText(mSpeakers.get(index).name);
        speakerOrg.setText(mSpeakers.get(index).organization);

        if (mSpeakers.get(index).url.equals("")) {
            speakerUrl.setVisibility(View.GONE);
        } else {
            speakerUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent urlIntent = SpeakerURIHandler.getIntent(mSpeakers.get(index).url);
                        if (urlIntent != null) {
                            startActivity(urlIntent);
                        }
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getActivity(), getActivity().getString(R.string.error_url_intent), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        speakerIntro.setText(Html.fromHtml(mSpeakers.get(index).introduction));

        mSpeakerLayout.addView(speakerInfoLayout);
    }


    private void initView(View rootView) {

        initToolbar(rootView);

        mSessionTitle = (TextView) rootView.findViewById(R.id.detailsession_text_title);
        mSessionDesc = (TextView) rootView.findViewById(R.id.detailsession_sessioninfo);
        mSpeakerLayout = (LinearLayout) rootView.findViewById(R.id.detailsession_container_speaker);
        ImageView backarrowButton = (ImageView) rootView.findViewById(R.id.detailsession_button_back);
        backarrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        ImageView shareButton = (ImageView) rootView.findViewById(R.id.detailsession_image_share);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSessionDialogFragment dialogFragment = new ShareSessionDialogFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "ShareSessionDialog");
            }
        });

        setData();

        ImageView backdropImg = (ImageView) rootView.findViewById(R.id.detailsession_image_backdrop);
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
