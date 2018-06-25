package com.jinke.housekeeper.saas.equipment.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.base.BaseActivity;
import com.jinke.housekeeper.saas.equipment.base.BasePresenter;
import com.jinke.housekeeper.saas.equipment.bean.TaskBean;
import com.jinke.housekeeper.saas.equipment.bean.TaskListBean;
import com.jinke.housekeeper.saas.equipment.config.Constant;
import com.jinke.housekeeper.saas.equipment.config.EquipmentConfig;
import com.jinke.housekeeper.saas.equipment.http.Api;
import com.jinke.housekeeper.saas.equipment.ui.widget.CustomDialog;
import com.jinke.housekeeper.saas.equipment.utils.SharedPreferenceUtil;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.ReportRegistActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.NetWorksUtils;

import static com.jinke.housekeeper.saas.equipment.bean.TaskBean.TASK_TO_PERFORM;
import static com.jinke.housekeeper.saas.equipment.ui.activity.BluetoothLinkActivity.CHECKING_INFORMATION;


public class CheckingInformationActivity extends BaseActivity {

    @Bind(R.id.checking_information_time)
    TextView checkingInformationTime;
    @Bind(R.id.checking_information_name)
    TextView checkingInformationName;
    @Bind(R.id.checking_information_location)
    TextView checkingInformationLocation;
    @Bind(R.id.checking_finish)
    TextView activityCheckingFinish;
    @Bind(R.id.checking_matter)
    TextView activityCheckingMatter;
    @Bind(R.id.checking_information_info_list)
    WebView checkingInformationInfoList;
    @Bind(R.id.checking_finish_layout)
    LinearLayout checkingFinishLayout;
    @Bind(R.id.checking_continue_layout)
    LinearLayout checkingContinueLayout;
    @Bind(R.id.checking_repetition_info)
    RelativeLayout checkingRepetitionInfo;
    @Bind(R.id.checking_result)
    TextView checkingResult;
    @Bind(R.id.checking_information_info_repetition_hint)
    TextView checkingInformationInfoRepetitionHint;


    private TaskBean.PointListBean pointListBean;
    private String taskTime;//任务时间
    private String taskId;//任务ID
    private String resultState;//结果1，正常,2异常
    private String remark;//备注
    private List<String> remarkList;//备注
    private static final String STATE_NORMAL = "1";
    private static final String STATE_ABNORMAL = "2";//正常报事回调标记为异常同时写入备注信息

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_checking_information;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_checking_information));
        setTitleBarBgColor(R.color.equipment_title_bg);
        setTitleColor(getResources().getColor(R.color.equipment_title_text));
        showBackwardViewIco(R.drawable.equipment_back_ico);
        remarkList = new ArrayList<>();
        resultState = STATE_NORMAL;
        remark = getString(R.string.normal);
        initDate();
    }


    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    boolean isbreak = true;

    @OnClick({R.id.checking_finish, R.id.checking_matter, R.id.checking_continue})
    public void checkingInfoOnCheckedListener(View view) {
        switch (view.getId()) {
            case R.id.checking_finish:
                if (STATE_NORMAL.equals(resultState)) {
                    remark = getString(R.string.normal);
                } else {
                    int index = 1;
                    remark = "";
                    for (String mark : remarkList) {
                        remark = remark + index + "、" + mark + ";\n";
                        index++;
                    }
                }
                TaskListBean taskListBean = (TaskListBean) SharedPreferenceUtil
                        .get(CheckingInformationActivity.this, "DailyPatrolActivity", "TaskListBean");
                boolean isComplete = true;//是否完成整个任务
                //获取当前时间
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = simpleDateFormat.format(new Date());
                for (TaskBean taskBean : taskListBean.getListData()) {
                    if (taskId.equals(taskBean.getId())) {
                        for (int i = 0; i < taskBean.getPointList().size(); i++) {
                            if (pointListBean.getId().equals(taskBean.getPointList().get(i).getId())) {
                                taskBean.getPointList().get(i).setStatus(TaskBean.PointListBean.TASK_NO_UPDATE);
                                taskBean.getPointList().get(i).setInspecTime(date);
                                taskBean.getPointList().get(i).setResult(resultState);
                                taskBean.getPointList().get(i).setRemark(remark);
                            }
                            if (null == taskBean.getPointList().get(i).getStatus()
                                    || "".equals(taskBean.getPointList().get(i).getStatus())) {
                                isComplete = false;
                            }
                        }
                        if (isComplete) {
                            taskBean.setCompleteTime(date);
                        } else {
                            taskBean.setCompleteTime(null);
                        }
                    }
                }
                //处理数据后存入缓存，再取出缓存中数据进行显示操作
                SharedPreferenceUtil.save(CheckingInformationActivity.this, "DailyPatrolActivity", "TaskListBean", taskListBean);
                CustomDialog.Builder builder = new CustomDialog.Builder(this);
                builder.setMessage(getString(R.string.checking_dialog_message));
                builder.setTitle(getString(R.string.checking_dialog_title));
                builder.setPositiveButton(getString(R.string.checking_dialog_back),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.setNegativeButton(getString(R.string.checking_dialog_sure),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                checkingFinishLayout.setVisibility(View.GONE);
                                checkingContinueLayout.setVisibility(View.VISIBLE);
                                checkingRepetitionInfo.setVisibility(View.VISIBLE);
                                checkingResult.setText(remark);
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;

            case R.id.checking_matter:
                //报事报修
                Intent checkingMatterIntent = new Intent(CheckingInformationActivity.this, ReportRegistActivity.class);
                checkingMatterIntent.putExtra("inspType", "124");
                startActivity(checkingMatterIntent);
                break;

            case R.id.checking_continue:

                TaskListBean taskListBean1 = (TaskListBean) SharedPreferenceUtil
                        .get(CheckingInformationActivity.this, "DailyPatrolActivity", "TaskListBean");
                for (TaskBean taskBean : taskListBean1.getListData()) {
                    if (TASK_TO_PERFORM.equals(taskBean.getTaskStatus())) {
                        for (TaskBean.PointListBean listBean : taskBean.getPointList()) {
                            if (null == listBean.getStatus() || "".equals(listBean.getStatus())) {
                                isbreak = false;
                                break;
                            }
                        }
                    }
                }
                CustomDialog.Builder builder1 = new CustomDialog.Builder(this);
                if (isbreak) {
                    builder1.setMessage(getString(R.string.checking_dialog_message_1));
                } else {
                    builder1.setMessage(getString(R.string.checking_dialog_message_2));
                }
                builder1.setTitle(getString(R.string.checking_dialog_title));
                builder1.setNegativeButton(getString(R.string.checking_dialog_sure),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                if (isbreak) {
                                    intent.putExtra("date", "1");
                                } else {
                                    intent.putExtra("date", "2");
                                }
                                setResult(CHECKING_INFORMATION, intent);
                                dialog.dismiss();
                                finish();
                            }
                        });
                builder1.create().show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Constant.CheckingInformationCallBack:
                if (null != data.getStringExtra(Constant.DATE)) {
                    remarkList.add(data.getStringExtra(Constant.DATE));
                    resultState = STATE_ABNORMAL;
                }
                break;
        }
    }

    private void initDate() {
        if (null != getIntent().getSerializableExtra("date") && null != getIntent().getStringExtra("taskTime")) {
            pointListBean = (TaskBean.PointListBean) getIntent().getSerializableExtra("date");
            taskTime = getIntent().getStringExtra("taskTime");
            taskId = getIntent().getStringExtra("taskId");
            checkingInformationTime.setText(taskTime);
            checkingInformationName.setText(pointListBean.getDeviceName());
            checkingInformationLocation.setText(pointListBean.getInstallationOcation());
            if (NetWorksUtils.isConnected(this)) {
                checkingInformationInfoRepetitionHint.setVisibility(View.GONE);
                String loadUrl = Api.BASE_URL + "app/patrolProject?token=" + EquipmentConfig.getTokenBean().getToken() +
                        "&projectId=" + EquipmentConfig.getProjectId() + "&typeId=" + pointListBean.getTypeId();
                checkingInformationInfoList.loadUrl(loadUrl);
            } else {
                checkingInformationInfoRepetitionHint.setVisibility(View.VISIBLE);
            }
        } else {
            ToastUtils.showShort( getString(R.string.checking_date_err));
        }
    }

}
