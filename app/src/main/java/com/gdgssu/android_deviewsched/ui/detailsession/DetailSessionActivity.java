package com.gdgssu.android_deviewsched.ui.detailsession;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gdgssu.android_deviewsched.DeviewSchedApplication;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.model.DetailSessionInfo;
import com.gdgssu.android_deviewsched.model.Speakers;
import com.gdgssu.android_deviewsched.util.GlideCircleTransform;

import static com.navercorp.volleyextensions.volleyer.Volleyer.volleyer;

public class DetailSessionActivity extends AppCompatActivity {

    private DetailSessionInfo sessionInfo;
    private Speakers speakers;

    private TextView sessionTitle;
    private TextView sessionDesc;
    private LinearLayout speakerBasket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_session);

        Intent intent = getIntent();
        sessionInfo = (DetailSessionInfo) intent.getSerializableExtra("DetailSessionInfo");

        initView();

        volleyer(DeviewSchedApplication.deviewRequestQueue)
                .get(DeviewSchedApplication.HOST_URL + "2015/" + sessionInfo.id + "/speakers")
                .withTargetClass(Speakers.class)
                .withListener(new Response.Listener<Speakers>() {
                    @Override
                    public void onResponse(Speakers item) {
                        speakers = item;

                        setData();
                    }
                })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
                .execute();
    }

    public void setData() {
        sessionTitle.setText(sessionInfo.title);
        sessionDesc.setText(Html.fromHtml(sessionInfo.description));

        for (int i = 0; i < speakers.speakers.size(); i++) {
            setSpeakerInfo(i);
        }
    }

    private void setSpeakerInfo(final int index) {
        RelativeLayout speakerInfoLayout = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_speaker_info, null, false);
        ImageView speakerPicture = (ImageView) speakerInfoLayout.findViewById(R.id.item_detail_session_header_speaker_img);
        TextView speakerName = (TextView) speakerInfoLayout.findViewById(R.id.item_detail_session_header_name);
        TextView speakerOrg = (TextView) speakerInfoLayout.findViewById(R.id.item_detail_session_header_company);
        ImageView speakerUrl = (ImageView) speakerInfoLayout.findViewById(R.id.item_detail_session_header_url);
        TextView speakerIntro = (TextView) speakerInfoLayout.findViewById(R.id.item_detail_session_header_speakerinfo);

        Glide.with(DeviewSchedApplication.GLOBAL_CONTEXT)
                .load(speakers.speakers.get(index).picture)
                .transform(new GlideCircleTransform(DeviewSchedApplication.GLOBAL_CONTEXT))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.person_image_empty)
                .into(speakerPicture);

        speakerName.setText(speakers.speakers.get(index).name);
        speakerOrg.setText(speakers.speakers.get(index).organization);
        speakerUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Uri uri = Uri.parse(speakers.speakers.get(index).email);
//                Intent intent = new Intent(Intent.ACTION_SEND, uri);
//                startActivity(intent);
            }
        });
        speakerIntro.setText(Html.fromHtml(speakers.speakers.get(index).introduction));

        speakerBasket.addView(speakerInfoLayout);
    }


    private void initView() {

        initToolbar();

        sessionTitle = (TextView) findViewById(R.id.activity_detail_session_header_title);
        sessionDesc = (TextView) findViewById(R.id.activity_detail_session_header_sessioninfo);
        speakerBasket = (LinearLayout) findViewById(R.id.activity_detail_session_header_speaker_basket);

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("세션 안내");
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_session, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_detail_session_share:

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
