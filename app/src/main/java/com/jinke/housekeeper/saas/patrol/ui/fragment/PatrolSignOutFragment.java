package com.jinke.housekeeper.saas.patrol.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.base.BaseFragment;
import com.jinke.housekeeper.saas.patrol.base.BasePresenter;
import com.jinke.housekeeper.saas.patrol.config.PatrolConfig;
import com.jinke.housekeeper.saas.patrol.ui.activity.PatrolBeganActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatrolSignOutFragment extends BaseFragment {

    @Bind(R.id.patrolling_personnel)
    TextView patrollingPersonnel;
    @Bind(R.id.patrolling_position)
    TextView patrollingPosition;
    @Bind(R.id.patrol_sign_in_time)
    TextView patrolSignInTime;
    @Bind(R.id.patrol_sign_out_time)
    TextView patrolSignOutTime;
    @Bind(R.id.patrol_frequencies)
    TextView patrolFrequencies;
    @Bind(R.id.patrol_leak_detection)
    TextView patrolLeakDetection;


    private PatrolBeganActivity.FragmentSwitch fragmentSwitch;
    private String systemSignInTime;
    private String systemSignOutTime;
    private int pointSize;
    private int pointLeakSize;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patrol_sign_out;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        patrollingPersonnel.setText(PatrolConfig.getTokenBean().getStaffName());
        patrollingPosition.setText(PatrolConfig.getTokenBean().getPosition());
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.sign_out_sure_button})
    protected void buttonOnClock(View view) {
        switch (view.getId()) {
            case R.id.sign_out_sure_button:
                fragmentSwitch.fragmentSwitch(systemSignInTime, systemSignOutTime, pointSize,pointLeakSize);
                break;
        }
    }

    private void initDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date signInTime = null;
        Date signOutTime = null;
        try {
            signInTime = simpleDateFormat.parse(systemSignInTime);
            signOutTime = simpleDateFormat.parse(systemSignOutTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat showDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        patrolSignInTime.setText(showDateFormat.format(signInTime));
        patrolSignOutTime.setText(showDateFormat.format(signOutTime));
        patrolFrequencies.setText(String.valueOf(pointSize));
        patrolLeakDetection.setText(String.valueOf(pointLeakSize));
    }


    public void setFragmentSwitch(PatrolBeganActivity.FragmentSwitch fragmentSwitch) {
        this.fragmentSwitch = fragmentSwitch;
    }

    public void setPatrolInfo(String patrolSignInTime, String patrolSignOutTime, int patrolFrequencies,int pointLeakSize) {
        this.systemSignInTime = patrolSignInTime;
        this.systemSignOutTime = patrolSignOutTime;
        this.pointSize = patrolFrequencies;
        this.pointLeakSize = pointLeakSize;
        initDate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
