package com.jinke.housekeeper.saas.equipment.ui.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.view.View;

import com.dk.bleNfc.BleManager.BleManager;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.base.BaseActivity;
import com.jinke.housekeeper.saas.equipment.base.BasePresenter;
import com.jinke.housekeeper.saas.equipment.bean.TaskBean;
import com.jinke.housekeeper.saas.equipment.bean.TaskListBean;
import com.jinke.housekeeper.saas.equipment.utils.SharedPreferenceUtil;
import com.jinke.housekeeper.saas.patrol.ui.activity.PatrolLinkActivity;
import com.jinke.housekeeper.saas.report.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.jinke.housekeeper.saas.equipment.bean.TaskBean.TASK_TO_PERFORM;
import static com.jinke.housekeeper.saas.equipment.ui.activity.BluetoothLinkActivity.CHECKING_INFORMATION;
import static com.jinke.housekeeper.saas.equipment.ui.activity.BluetoothLinkActivity.STAND_BOOK;

public class AwaitLinkActivity extends BaseActivity {

    private BroadcastReceiver bleIndoReceiver;
    private String date;
    private String UID;
    private TaskBean.PointListBean listBean;
    private String taskTime;
    private String taskId;
    private MediaPlayer mediaPlayer;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_await_link;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_await_link));
        showBackwardView(R.string.empty);
        if (null != getIntent().getStringExtra("date")) {
            date = getIntent().getStringExtra("date");
            if ("DAILY_PATROL_DETAILS".equals(date)) {
                listBean = (TaskBean.PointListBean) getIntent().getSerializableExtra("listBean");
                taskTime = getIntent().getStringExtra("taskTime");
                taskId = getIntent().getStringExtra("taskId");
            }
        }
        mediaPlayer = MediaPlayer.create(AwaitLinkActivity.this, R.raw.message_come);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case CHECKING_INFORMATION://1不断开连接  2断开连接
                if (null != data.getStringExtra("date")) {
                    if ("1".equals(data.getStringExtra("date"))) {
                        finish();
                    } else if ("2".equals(data.getStringExtra("date"))) {
                    }
                }
                break;
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

    private class BleIndoReceiver extends BroadcastReceiver {

        private boolean isShow = true;

        //接收到广播后自动调用该方法
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case PatrolLinkActivity.BLE_INFO_BROADCAST_ACTION:
                    mediaPlayer.start();
                    if ("DAILY_PATROL".equals(date)) {
                        TaskListBean taskListBean = (TaskListBean) SharedPreferenceUtil.get(AwaitLinkActivity.this, "DailyPatrolActivity", "TaskListBean");
                        UID = intent.getStringExtra(PatrolLinkActivity.BLE_INFO_UID);
                        List<TaskBean> executeTaskList = new ArrayList<>();
                        List<List<TaskBean>> recordExecuteTaskList = new ArrayList<>();
                        List<String> markTaskList = new ArrayList<>();
                        //循环标签
                        for (TaskBean taskBean : taskListBean.getListData()) {
                            if (TASK_TO_PERFORM.equals(taskBean.getTaskStatus())) {
                                String markString = taskBean.getTaskOrder().split("_")[0];
                                if (markTaskList.size() == 0) {
                                    markTaskList.add(markString);
                                }
                                for (String s : markTaskList) {
                                    if (!markString.equals(s)) {
                                        markTaskList.add(markString);
                                        break;
                                    }
                                }
                            }
                        }
                        //根据标签处理成对应组别的任务列表
                        for (String s : markTaskList) {
                            List<TaskBean> beanList = new ArrayList<>();
                            for (TaskBean taskBean : taskListBean.getListData()) {
                                if (TASK_TO_PERFORM.equals(taskBean.getTaskStatus())) {
                                    String markString = taskBean.getTaskOrder().split("_")[0];
                                    if (s.equals(markString)) {
                                        beanList.add(taskBean);
                                    }
                                }
                            }
                            recordExecuteTaskList.add(beanList);
                        }
                        //根据任务列表取到当前可执行任务
                        for (List<TaskBean> beanList : recordExecuteTaskList) {
                            TaskBean recordTaskBean = null;
                            for (TaskBean taskBean : beanList) {
                                if (TASK_TO_PERFORM.equals(taskBean.getTaskStatus())) {
                                    if (null != recordTaskBean) {
                                        String markIndexString = taskBean.getTaskOrder().split("_")[1];
                                        if (Integer.parseInt(recordTaskBean.getTaskOrder().split("_")[1]) >
                                                Integer.parseInt(markIndexString)) {
                                            recordTaskBean = taskBean;
                                        }
                                    } else {
                                        recordTaskBean = taskBean;
                                    }
                                }
                            }
                            executeTaskList.add(recordTaskBean);
                        }

                        boolean showNoPointToutse = true;
                        boolean showDoToutse = false;
                        for (TaskBean taskBean : executeTaskList) {
                            for (TaskBean.PointListBean listBean : taskBean.getPointList()) {
                                if (UID.equals(listBean.getCardNum())) {
                                    showNoPointToutse = false;
                                    if ("".equals(listBean.getStatus())) {
                                        //巡检信息
                                        Intent checkingInformationIntent = new Intent(context, CheckingInformationActivity.class);
                                        checkingInformationIntent.putExtra("date", listBean);
                                        checkingInformationIntent.putExtra("taskTime", taskBean.getStartTime() + context.getString(R.string.daily_patrol_to) + taskBean.getEndTime());
                                        checkingInformationIntent.putExtra("taskId", taskBean.getId());
                                        startActivityForResult(checkingInformationIntent, CHECKING_INFORMATION);
                                        showDoToutse = false;
                                        break;
                                    } else {
                                        showDoToutse = true;
                                    }
                                }
                            }
                            if (!showDoToutse) {
                                break;
                            }
                        }
                        if (showDoToutse) {
                            ToastUtils.showShort(AwaitLinkActivity.this, "该点位任务已经巡检");
                        }
                        if (showNoPointToutse) {
                            ToastUtils.showShort(AwaitLinkActivity.this, "没有该点位的任务");
                        }
                    } else if ("DAILY_PATROL_DETAILS".equals(date)) {
                        TaskListBean taskListBean = (TaskListBean) SharedPreferenceUtil.get(AwaitLinkActivity.this, "DailyPatrolActivity", "TaskListBean");
                        UID = intent.getStringExtra(PatrolLinkActivity.BLE_INFO_UID);
                        for (TaskBean beanList : taskListBean.getListData()) {
                            if (taskId.equals(beanList.getId())) {
                                for (TaskBean.PointListBean pointListBean : beanList.getPointList()) {
                                    if (listBean.getDeviceId().equals(pointListBean.getDeviceId())) {
                                        listBean = pointListBean;
                                    }
                                }
                                break;
                            }
                        }
                        if (null != listBean.getCardNum() && UID.equals(listBean.getCardNum())) {
                            if ("".equals(listBean.getStatus())) {
                                Intent checkingInformationIntent = new Intent(context, CheckingInformationActivity.class);
                                checkingInformationIntent.putExtra("date", listBean);
                                checkingInformationIntent.putExtra("taskTime", taskTime);
                                checkingInformationIntent.putExtra("taskId", taskId);
                                startActivityForResult(checkingInformationIntent, CHECKING_INFORMATION);
                            } else {
                                ToastUtils.showShort(AwaitLinkActivity.this, "该点位任务已经巡检");
                            }
                        } else {
                            ToastUtils.showShort(AwaitLinkActivity.this, "没有该点位的任务");
                        }
                    } else {
                        AwaitLinkActivity.this.setResult(STAND_BOOK);
                        finish();
                    }
                    break;

                case PatrolLinkActivity.BLE_LINK_BROADCAST_ACTION:
                    if (isShow) {
                        if (BleManager.STATE_DISCONNECTED == intent.getIntExtra(PatrolLinkActivity.BLE_LINK_UID, -1)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new AlertDialog.Builder(AwaitLinkActivity.this).setTitle("提示")//设置对话框标题
                                            .setMessage("设备连接断开请重新连接")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                    dialog.dismiss();
                                                }
                                            }).show();
                                }
                            });
                        }
                        isShow = false;
                    }
                    break;
            }
        }
    }
}
