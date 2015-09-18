package com.gdgssu.android_deviewsched.ui.selectsession;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.helper.FavoritePreferenceHelper;
import com.gdgssu.android_deviewsched.model.AllScheItems;
import com.gdgssu.android_deviewsched.model.FavoriteSession;

public class SelectSessionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = SelectSessionActivity.class.getSimpleName();

    private SelectSessionListAdapter mAdapter;
    private FavoriteSession selectedSessionList = new FavoriteSession();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_session);

        initView();

    }

    private void initView() {
        initToolbar();
        initListView();
        FloatingActionButton doneButton = (FloatingActionButton) findViewById(R.id.select_session_done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedSessionList.getFavorListSize() == 0) {
                    Toast.makeText(getApplicationContext(), "선택한 세션이 없습니다", Toast.LENGTH_SHORT).show();
                    FavoritePreferenceHelper prefHelper = new FavoritePreferenceHelper(getApplicationContext());
                    prefHelper.setFavorSessionState(FavoritePreferenceHelper.PREF_FAVOR_STATE, false);
                    return;
                }

                saveFavorSessionIDs();
            }
        });
    }

    public void saveFavorSessionIDs() {
        FavoritePreferenceHelper prefHelper = new FavoritePreferenceHelper(getApplicationContext());
        prefHelper.setFavorSessionValue(FavoritePreferenceHelper.PREF_FAVOR_VALUE, selectedSessionList.getFavorList());
        Toast.makeText(getApplicationContext(), "세션 리스트가 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    private void initListView() {
        ListView listview = (ListView) findViewById(R.id.select_session_list);
        listview.setOnItemClickListener(this);
        mAdapter = new SelectSessionListAdapter(AllScheItems.result.days.get(0), getApplicationContext());
        listview.setAdapter(mAdapter);
    }

    private void initToolbar() {

        Toolbar mToolbar = (Toolbar) findViewById(R.id.select_session_toolbar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("세션 선택");

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initToolbarSpinner();
    }

    private void initToolbarSpinner() {
        Spinner toolbarSpinner = (Spinner) findViewById(R.id.select_session_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.days, R.layout.toolbar_spinner_item);
        adapter.setDropDownViewResource(R.layout.toolbar_spinner_item_dropdown);
        toolbarSpinner.setAdapter(adapter);
        toolbarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mAdapter.setDayItem(AllScheItems.result.days.get(0));
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter.setDayItem(AllScheItems.result.days.get(1));
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!SelectSessionListAdapter.sessionItems.get(position).isSelected) {
            view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            SelectSessionListAdapter.sessionItems.get(position).isSelected = true;
            selectedSessionList.selectSession(SelectSessionListAdapter.sessionItems.get(position).id);
        } else {
            view.setBackgroundColor(getResources().getColor(android.R.color.white));
            SelectSessionListAdapter.sessionItems.get(position).isSelected = false;
            selectedSessionList.selectSession(SelectSessionListAdapter.sessionItems.get(position).id);
        }
    }
}
