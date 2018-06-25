package com.jinke.housekeeper.saas.patrol.ui.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dk.bleNfc.BleManager.BleManager;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.base.BaseActivity;
import com.jinke.housekeeper.saas.patrol.precenter.AddPatrolPresenter;
import com.jinke.housekeeper.saas.patrol.view.AddPatrolView;
import com.jinke.housekeeper.utils.CommonlyUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.widget.ProgressDialog;

public class PatrolAddActivity extends BaseActivity<AddPatrolView, AddPatrolPresenter> implements AddPatrolView {
    Map<String, String> map = new HashMap<>();
    @Bind(R.id.equipment_hint_1)
    TextView equipmentHint1;
    @Bind(R.id.equipment_hint_2)
    TextView equipmentHint2;
    @Bind(R.id.patrol_point_name)
    EditText patrolPointName;
    @Bind(R.id.patrol_point_remark)
    EditText patrolPointRemark;
    @Bind(R.id.patrol_point_project_name)
    TextView patrolPointProjectName;

    //    private String projectId;
    private String UID;
    private BroadcastReceiver bleIndoReceiver;

    @Override
    public AddPatrolPresenter initPresenter() {
        return new AddPatrolPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_patrol_add;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_patrol_add));
        showBackwardView(R.string.empty, true);
        patrolPointProjectName.setText(CommonlyUtils.getUserInfo(this).getLeftOrgName());
        initDate();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver();
    }

    @OnClick({R.id.patrol_add_save, R.id.patrol_point_input_delete})
    protected void buttonOnClock(View view) {
        switch (view.getId()) {
            case R.id.patrol_add_save:
                String pointName = patrolPointName.getText().toString().trim();
                String pointRemark = patrolPointRemark.getText().toString().trim();
                if (null != UID && null != pointRemark && null != pointRemark
                        && !"".equals(pointName) && !"".equals(UID)) {
                    map = new HashMap<>();
                    map.put("id", UID);
                    map.put("projectId", CommonlyUtils.getUserInfo(PatrolAddActivity.this).getLeftOrgId());
                    map.put("pointName", pointName);
                    map.put("remark", pointRemark);
                    presenter.addPoint(map);
                } else {
                    ToastUtils.showShort("请填写完整的信息");
                }
                break;

            case R.id.patrol_point_input_delete:
                patrolPointName.setText("");
                break;
        }
    }

    @Override
    public void addPatrolSuccess() {
        dimissDialog();
        new AlertDialog.Builder(PatrolAddActivity.this).setTitle("提示")//设置对话框标题
                .setMessage("添加成功")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        setResult(PatrolLinkActivity.PatrolAddActivity, new Intent());
                        dialog.dismiss();
                        finish();
                    }
                }).show();//在按键响应事件中显示此对话框
    }

    @Override
    public void showLoading() {
        showDialog();
    }

    @Override
    public void onError(final String msg) {
        dimissDialog();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(PatrolAddActivity.this).setTitle("提示")//设置对话框标题
                        .setMessage(msg)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                dialog.dismiss();
                            }
                        }).show();//在按键响应事件中显示此对话框
            }
        });
    }

    private void initDate() {
        if (null == CommonlyUtils.getUserInfo(PatrolAddActivity.this).getLeftOrgId() && "".equals(CommonlyUtils.getUserInfo(PatrolAddActivity.this).getLeftOrgId())) {
            ToastUtils.showShort( "未获取到项目相关信息");
            finish();
        }
    }

    private void registerReceiver() {
        bleIndoReceiver = new BleIndoReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PatrolLinkActivity.BLE_INFO_BROADCAST_ACTION);
        intentFilter.addAction(PatrolLinkActivity.BLE_LINK_BROADCAST_ACTION);
        registerReceiver(bleIndoReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        unregisterReceiver(bleIndoReceiver);
    }

    private Boolean isShowPatrolAddDialog = true;

    private class BleIndoReceiver extends BroadcastReceiver {
        //接收到广播后自动调用该方法
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case PatrolLinkActivity.BLE_INFO_BROADCAST_ACTION:
                    UID = intent.getStringExtra(PatrolLinkActivity.BLE_INFO_UID);
                    equipmentHint2.setText(getString(R.string.equipment_hint_6) + UID);
                    break;

                case PatrolLinkActivity.BLE_LINK_BROADCAST_ACTION:
                    if (BleManager.STATE_DISCONNECTED == intent.getIntExtra(PatrolLinkActivity.BLE_LINK_UID, -1)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isShowPatrolAddDialog) {
                                    new AlertDialog.Builder(PatrolAddActivity.this).setTitle("提示")//设置对话框标题
                                            .setMessage("设备连接断开请重新连接")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                    dialog.dismiss();
                                                }
                                            }).show();
                                    isShowPatrolAddDialog = !isShowPatrolAddDialog;
                                }
                            }
                        });
                    }
                    break;
            }

        }
    }

    private ProgressDialog dialog;

    public void showDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
        }
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }

    /**
     * 关闭对话框
     */
    public void dimissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }

    }
}
