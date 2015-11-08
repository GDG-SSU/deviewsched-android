package com.gdgssu.android_deviewsched.ui.selectsession;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
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

import com.gdgssu.android_deviewsched.DeviewSchedApplication;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.helper.FavoritePreferenceHelper;
import com.gdgssu.android_deviewsched.model.FavoriteSession;
import com.gdgssu.android_deviewsched.ui.sche.ScheFragment;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class SelectSessionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //for tablet
    private static final String TAG = makeLogTag("SelectSessionActivity");

    private SelectSessionListAdapter mAdapter;
    private FavoriteSession selectedSessionList = new FavoriteSession();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_session);

        initView();

        setResult(ScheFragment.SELECT_SESSION);

    }

    private void initView() {
        initToolbar();
        initListView();
        FloatingActionButton doneButton = (FloatingActionButton) findViewById(R.id.select_session_done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedSessionList.getFavorListSize() == 0) {
                    Toast.makeText(getBaseContext(), "선택한 세션이 없습니다", Toast.LENGTH_SHORT).show();
                    FavoritePreferenceHelper prefHelper = new FavoritePreferenceHelper(getBaseContext());
                    prefHelper.setFavorSessionState(FavoritePreferenceHelper.PREF_FAVOR_STATE, false);

                    return;
                }

                saveFavorSessionIDs();
            }
        });
    }

    public void saveFavorSessionIDs() {
        FavoritePreferenceHelper prefHelper = new FavoritePreferenceHelper(getBaseContext());
        prefHelper.setFavorSessionValue(FavoritePreferenceHelper.PREF_FAVOR_VALUE, selectedSessionList.getFavorList());
        Toast.makeText(getBaseContext(), "세션 리스트가 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    private void initListView() {
        ListView listview = (ListView) findViewById(R.id.select_session_list);
        listview.setOnItemClickListener(this);
        mAdapter = new SelectSessionListAdapter(DeviewSchedApplication.allscheItems.days.get(0), getBaseContext());
        listview.setAdapter(mAdapter);
    }

    private void initToolbar() {

        Toolbar mToolbar = (Toolbar) findViewById(R.id.select_session_toolbar);
        setSupportActionBar(mToolbar);

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.days, R.layout.toolbar_spinner_item);
        adapter.setDropDownViewResource(R.layout.toolbar_spinner_item_dropdown);
        toolbarSpinner.setAdapter(adapter);
        toolbarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mAdapter.setDayItem(DeviewSchedApplication.allscheItems.days.get(0));
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter.setDayItem(DeviewSchedApplication.allscheItems.days.get(1));
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
            view.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
            SelectSessionListAdapter.sessionItems.get(position).isSelected = true;
            selectedSessionList.selectSession(SelectSessionListAdapter.sessionItems.get(position).session_id);
        } else {
            view.setBackgroundColor(ContextCompat.getColor(getBaseContext(), android.R.color.white));
            SelectSessionListAdapter.sessionItems.get(position).isSelected = false;
            selectedSessionList.selectSession(SelectSessionListAdapter.sessionItems.get(position).session_id);
        }
    }
}
