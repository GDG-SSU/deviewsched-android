package com.gdgssu.android_deviewsched.ui.detailsession;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.model.sessioninfo.Session;
import com.gdgssu.android_deviewsched.model.sessioninfo.Speaker;
import com.gdgssu.android_deviewsched.util.GlideCircleTransform;

import java.util.ArrayList;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;
import static com.navercorp.volleyextensions.volleyer.Volleyer.volleyer;

public class DetailSessionActivity extends AppCompatActivity {

    private static final String TAG = makeLogTag("DetailSessionActivity");

    private Session sessionInfo;
    private ArrayList<Speaker> speakers;

    private TextView sessionTitle;
    private TextView sessionDesc;
    private LinearLayout speakerBasket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsession);

        Intent intent = getIntent();
        sessionInfo = (Session) intent.getSerializableExtra("SessionInfo");

        speakers = sessionInfo.speakers;

        initView();
    }

    public void setData() {
        sessionTitle.setText(sessionInfo.title);
        sessionDesc.setText(Html.fromHtml(sessionInfo.description));

        for (int i = 0; i < speakers.size(); i++) {
            setSpeakerInfo(i);
        }
    }

    private void setSpeakerInfo(final int index) {
        RelativeLayout speakerInfoLayout = (RelativeLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.layout_speaker_info, null, false);
        ImageView speakerPicture = (ImageView) speakerInfoLayout.findViewById(R.id.speaker_img);
        TextView speakerName = (TextView) speakerInfoLayout.findViewById(R.id.speaker_name);
        TextView speakerOrg = (TextView) speakerInfoLayout.findViewById(R.id.speaker_company);
        ImageView speakerUrl = (ImageView) speakerInfoLayout.findViewById(R.id.speaker_url);
        TextView speakerIntro = (TextView) speakerInfoLayout.findViewById(R.id.speaker_intro);

        Glide.with(getBaseContext())
                .load(speakers.get(index).picture)
                .transform(new GlideCircleTransform(getBaseContext()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.person_image_empty)
                .into(speakerPicture);

        speakerName.setText(speakers.get(index).name);
        speakerOrg.setText(speakers.get(index).organization);
        speakerUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(speakers.get(index).url);
                Intent intent = new Intent(Intent.ACTION_SEND, uri);
                startActivity(intent);
            }
        });
        speakerIntro.setText(Html.fromHtml(speakers.get(index).introduction));

        speakerBasket.addView(speakerInfoLayout);
    }


    private void initView() {

        initToolbar();

        sessionTitle = (TextView) findViewById(R.id.detailsession_title);
        sessionDesc = (TextView) findViewById(R.id.detailsession_sessioninfo);
        speakerBasket = (LinearLayout) findViewById(R.id.detailsession_speaker_basket);
        ImageView backarrowButton = (ImageView) findViewById(R.id.detailsession_backbutton);
        backarrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView shareButton = (ImageView) findViewById(R.id.detailsession_share);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo : 세션 공유시 실행할 액션 작성
            }
        });

        setData();

        ImageView backdropImg = (ImageView) findViewById(R.id.detailsession_backdrop);
        Glide.with(getBaseContext())
                .load("http://insanehong.kr/post/deview2013/@img/keynote.jpg")
                .centerCrop()
                .into(backdropImg);

    }

    private void initToolbar() {

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.detailsession_collapsingtoolbar);
        collapsingToolbar.setTitle("");

        Toolbar toolbar = (Toolbar) findViewById(R.id.detailsession_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("세션 안내");
        }
    }
}
