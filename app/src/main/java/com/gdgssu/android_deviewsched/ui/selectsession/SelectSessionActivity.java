package com.gdgssu.android_deviewsched.ui.selectsession;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
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

import java.util.ArrayList;

import static com.gdgssu.android_deviewsched.util.LogUtils.LOGI;
import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class SelectSessionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = makeLogTag("SelectSessionActivity");

    private SelectSessionListAdapter mAdapter;
    private FavoriteSession mSelectedSessionList = new FavoriteSession();
    private ArrayList<Integer> mStoredFavoriteSessions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_session);

        // 저장된 관심 목록을 ArrayList형태로 ID들을 가져옴.
        mStoredFavoriteSessions = getFavoriteList();
        // ArrayList형태의 ID리스트를 mSelectedSessionList에 넣는다.
        mSelectedSessionList.setFavorList(mStoredFavoriteSessions);
        // 뷰 초기화
        initView();

        setResult(ScheFragment.SELECT_SESSION);

    }

    private ArrayList<Integer> getFavoriteList() {
        //prefHelper를 통해 관심 목록이 있는지 없는지를 확인하고, 있다면 그 관심목록을 가져온다.
        FavoritePreferenceHelper prefHelper = new FavoritePreferenceHelper(getBaseContext());
        if (prefHelper.getFavorSessionState(FavoritePreferenceHelper.PREF_FAVOR_STATE)) {
            return prefHelper.getFavorSessionValue(FavoritePreferenceHelper.PREF_FAVOR_VALUE);
        }
        //없다면 빈 ArrayList 객체를 반환한다.
        return new ArrayList<>();
    }

    private void initView() {
        initToolbar();
        initListView();
        FloatingActionButton doneButton = (FloatingActionButton) findViewById(R.id.select_session_button_done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Done을 이용하여 Activity를 종료할떄에는 아래와같이 하지만, Back버튼이나
                 * 강제종료를 통해서 액티비티가 종료되게되면 SelectSessionActivity가 열릴때 저장된 리스트
                 * 상태로 저장시켜야함.
                 */
                if (mSelectedSessionList.getFavorListSize() == 0) {
                    // 이 상태로 저장되었을때 getFavoriteList()가 빈 ArrayList를 반환하나 확인해보아야함.
                    Toast.makeText(getBaseContext(), "선택한 세션이 없습니다", Toast.LENGTH_SHORT).show();

                    // 세션 리스트를 별도로 저장하는 로직은 없고 관심 세션 등록 상태를 false로 저장하기만함.
                    FavoritePreferenceHelper prefHelper = new FavoritePreferenceHelper(getBaseContext());
                    prefHelper.setFavorSessionState(FavoritePreferenceHelper.PREF_FAVOR_STATE, false);
                } else if (mSelectedSessionList.getFavorListSize() > 0) {
                    Toast.makeText(getBaseContext(), "세션 리스트가 저장되었습니다.", Toast.LENGTH_SHORT).show();

                    // 선택된 세션들의 ID값들을 저장함.
                    saveFavorSessionIDs();
                }

                finish();
            }
        });
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

    private void initListView() {
        ListView listview = (ListView) findViewById(R.id.select_session_list);
        listview.setOnItemClickListener(this);
        // 세션 선택 리스트뷰를 초기화하기위한 어댑터 초기화. 액티비티 초기화시에 가져온 mStoredFavoriteSessions를 전달함.
        mAdapter = new SelectSessionListAdapter(DeviewSchedApplication.sAllscheItems.days.get(0), mStoredFavoriteSessions, getBaseContext());
        listview.setAdapter(mAdapter);
    }

    public void saveFavorSessionIDs() {
        FavoritePreferenceHelper prefHelper = new FavoritePreferenceHelper(getBaseContext());
        prefHelper.setFavorSessionValue(FavoritePreferenceHelper.PREF_FAVOR_VALUE, mSelectedSessionList.getFavorList());
        //Fixme : ID만을 저장하고있음.
    }

    private void initToolbarSpinner() {
        //Todo : Day 1,2가 바뀔 때 저장된 관심세션 ID값을 가지고 다시 Listview의 row 컬러를 지정해야함.
        Spinner toolbarSpinner = (Spinner) findViewById(R.id.select_session_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.days, R.layout.item_toolbar_spinner);
        adapter.setDropDownViewResource(R.layout.item_toolbar_spinner_dropdown);
        toolbarSpinner.setAdapter(adapter);
        toolbarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mAdapter.setDayItem(DeviewSchedApplication.sAllscheItems.days.get(0));
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter.setDayItem(DeviewSchedApplication.sAllscheItems.days.get(1));
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Todo : mSelectedSessionList에 저장된 관심 세션 목록 ID를 저장하는 로직을 개발해야함.
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!mAdapter.mStoredFavoriteSessionIDs.contains(Integer.valueOf(mAdapter.mSessionItems.get(position).session_id))) { // 선택이 되지 않은것을 선택할때.
            view.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
        } else { // 선택이 된것을 선택해제로 변경할때.
            view.setBackgroundColor(ContextCompat.getColor(getBaseContext(), android.R.color.white));
        }

        //mAdapter.mSessionItems.get(position).isSelected = !mAdapter.mSessionItems.get(position).isSelected;
        mSelectedSessionList.selectSession(mAdapter.mSessionItems.get(position).session_id);

        LOGI(TAG, mSelectedSessionList.toString() + "/" + mSelectedSessionList.getFavorListSize());
    }
}
