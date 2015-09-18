package com.gdgssu.android_deviewsched.ui.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.gdgssu.android_deviewsched.R;


public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        ListView listview = (ListView)findViewById(R.id.listview);

        initHeaderView();

        //listview.setAdapter(new SettingAdapter(getApplicationContext(),));

    }

    private void initHeaderView() {
        View headerView = getLayoutInflater().inflate(R.layout.item_setting_header, null, false);

    }
}
