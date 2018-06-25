package com.jinke.housekeeper.saas.patrol.ui.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.base.BaseActivity;
import com.jinke.housekeeper.saas.patrol.bean.PointListBean;
import com.jinke.housekeeper.saas.patrol.bean.TimeOutTaskListBean;
import com.jinke.housekeeper.saas.patrol.config.PatrolConfig;
import com.jinke.housekeeper.saas.patrol.precenter.PatrolManagePresenter;
import com.jinke.housekeeper.saas.patrol.ui.adapter.PatrolManageAdapter;
import com.jinke.housekeeper.saas.patrol.ui.adapter.PlanRecordAdapter;
import com.jinke.housekeeper.saas.patrol.ui.widget.CustomListView;
import com.jinke.housekeeper.saas.patrol.ui.widget.LoadingLayout;
import com.jinke.housekeeper.saas.patrol.view.PatrolManageView;
import com.jinke.housekeeper.utils.CommonlyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class PatrolManageActivity extends BaseActivity<PatrolManageView, PatrolManagePresenter>
        implements PatrolManageView, LoadingLayout.OnReloadListener {

    @Bind(R.id.activity_patrol_manage)
    LoadingLayout layout;
    @Bind(R.id.patrol_manage_add)
    ImageView patrolManageAdd;
    @Bind(R.id.patrol_manage_location)
    TextView patrolManageLocation;
    @Bind(R.id.patrol_manage_list)
    CustomListView patrolManageList;
    @Bind(R.id.plan_record_list)
    CustomListView planRecordList;

    private Map<String, String> map = new HashMap<>();
    private PatrolManageAdapter patrolManageAdapter;
    private List<PointListBean> pointList;
    private PlanRecordAdapter planRecordAdapter;
    private List<TimeOutTaskListBean> outTaskBeans;
    private int label_activity;

    @Override
    public PatrolManagePresenter initPresenter() {
        return new PatrolManagePresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_patrol_manage;
    }

    @Override
    protected void initView() {
        initTitle();
        pointList = new ArrayList<>();
        patrolManageAdapter = new PatrolManageAdapter(this, pointList);
        outTaskBeans = new ArrayList<>();
        planRecordAdapter = new PlanRecordAdapter(this, outTaskBeans);
        patrolManageList.setAdapter(patrolManageAdapter);
        planRecordList.setAdapter(planRecordAdapter);
        planRecordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (3 == outTaskBeans.get(position).getState()) {
                    Intent outTaskDetailsIntent = new Intent(PatrolManageActivity.this, OutTaskDetailsActivity.class);
                    outTaskDetailsIntent.putExtra("date", outTaskBeans.get(position));
                    startActivity(outTaskDetailsIntent);
                }
            }
        });
    }

    public void initTitle() {
        setTitle(getString(R.string.activity_patrol_manage));
        hindTitleLine();
        showBackwardView(R.string.empty, true);
        showForwardViewColor(getResources().getColor(R.color.equipment_bg_3));
        layout.setStatus(LoadingLayout.Loading);
        if (!"1".equals(PatrolConfig.getTokenBean().getIfManage())) {
            patrolManageAdd.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDate();
        layout.setOnReloadListener(this);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onForward(View forwardView) {
        super.onForward(forwardView);
        if (!"1".equals(PatrolConfig.getTokenBean().getIfManage())) {
            ToastUtils.showShort("您没有添加点位权限，请联系管理员");
        } else {
            label_activity = 3;
            initBluetooth();
        }
    }

    @OnClick({R.id.patrol_manage_add})
    protected void buttonOnClock(View view) {
        switch (view.getId()) {
            case R.id.patrol_manage_add:
                if (!"1".equals(PatrolConfig.getTokenBean().getIfManage())) {
                    ToastUtils.showShort("您没有添加点位权限，请联系管理员");
                } else {
                    label_activity = 2;
                    initBluetooth();
                }
                break;
        }
    }

    @Override
    public void showLoading() {
        showDialog();
    }

    @Override
    public void showMessage() {
        dimissDialog();
        layout.setStatus(LoadingLayout.Error);
    }

    @Override
    public void onDeletePoint() {
        initDate();
    }

    @Override
    public void onRefreshData(List<PointListBean> list) {
        //刷新adapter 数据
        dimissDialog();
        if (list.size() > 0) {
            pointList = list;
            patrolManageAdapter.setRefreshData(pointList);
            layout.setStatus(LoadingLayout.Success);
            if (!"1".equals(PatrolConfig.getTokenBean().getIfManage())) {
                showForwardView(R.string.replace_point, false);
            } else {
                showForwardView(R.string.replace_point, true);
            }
        } else {
            layout.setStatus(LoadingLayout.Patrol_Empty_List);
            showForwardView(R.string.replace_point, false);
        }
    }

    @Override
    public void getTimeOutTask(List<TimeOutTaskListBean> list) {
        if (null != list) {
            outTaskBeans = list;
        } else {
            outTaskBeans = new ArrayList<>();
        }
        planRecordAdapter.setTimeOutTaskListBeanList(outTaskBeans);
    }

    @Override
    public void onReload(View v) {
        switch (v.getId()) {
            case R.id.patrol_add:
            case R.id.no_data_reload_btn:
                if (!"1".equals(PatrolConfig.getTokenBean().getIfManage())) {
                    ToastUtils.showShort("您没有添加点位权限，请联系管理员");
                } else {
                    label_activity = 2;
                    initBluetooth();
                }
                break;
            case R.id.no_network_reload_btn:
            case R.id.error_reload_btn:
                initDate();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1313) {
            switch (resultCode) {
                // 点击确认按钮
                case RESULT_OK: {
                    BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (!mBtAdapter.isEnabled()) {
                        ToastUtils.showShort("没有打开蓝牙，请打开蓝牙再试");
                    } else {
                        Intent patrolBeganIntent = new Intent(PatrolManageActivity.this, PatrolLinkActivity.class);
                        patrolBeganIntent.putExtra("label_activity", label_activity);
                        startActivity(patrolBeganIntent);
                    }
                }
                break;

                // 点击取消按钮或点击返回键
                case RESULT_CANCELED: {
                    ToastUtils.showShort("没有打开蓝牙，请打开蓝牙再试");
                }
                break;

                default:
                    break;
            }
        }
    }

    private void initBluetooth() {
        if (null != BluetoothAdapter.getDefaultAdapter()) {
            if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                Intent patrolBeganIntent = new Intent(PatrolManageActivity.this, PatrolLinkActivity.class);
                patrolBeganIntent.putExtra("label_activity", label_activity);
//                patrolBeganIntent.putExtra("projectId", projectId);
                startActivity(patrolBeganIntent);
            } else {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, 1313);
            }
        } else {
            ToastUtils.showShort("没有检测到蓝牙模块");
        }

    }

    private void initDate() {
        if (null != CommonlyUtils.getUserInfo(this).getLeftOrgId() && !"".equals(CommonlyUtils.getUserInfo(this).getLeftOrgId())) {
            patrolManageLocation.setText(CommonlyUtils.getUserInfo(this).getLeftOrgName());
            map.put("projectId", CommonlyUtils.getUserInfo(this).getLeftOrgId());
            presenter.getPointList(map);
            presenter.getTimeOutTask(map);
        } else {
            ToastUtils.showShort("获取项目失败");
            finish();
        }
    }
}
