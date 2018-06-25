package com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.AppHandleInfo;
import com.jinke.housekeeper.saas.patrol.base.BaseActivity;
import com.jinke.housekeeper.saas.report.bean.ProcessDetailBean;
import com.jinke.housekeeper.saas.report.bean.WorkOrderBean;
import com.jinke.housekeeper.saas.report.presenter.WaitingResponsePresent;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentmsg.ReportDealActivity;
import com.jinke.housekeeper.saas.report.ui.adapter.WaitingResponseAdapter;
import com.jinke.housekeeper.saas.report.util.RadioUtil;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.util.StringUtil;
import com.jinke.housekeeper.saas.report.view.IWaitingResponseView;
import com.jinke.housekeeper.ui.activity.RegisterDepartmentActivity;
import com.jinke.housekeeper.ui.widget.CustomPopWindows;
import com.jinke.housekeeper.utils.CommonlyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by root on 18-4-18.
 */

public class WaitingResponseActivity extends BaseActivity<IWaitingResponseView, WaitingResponsePresent> implements IWaitingResponseView, WaitingResponseAdapter.OnClickImageListener {
    @Bind(R.id.gv_waiting_pic)
    GridView gv_waiting_pic;
    @Bind(R.id.tv_waiting_userName)
    TextView tv_waiting_userName;
    @Bind(R.id.tv_waiting_project)
    TextView tv_waiting_project;
    @Bind(R.id.tv_waiting_room_number)
    TextView tv_waiting_room_number;
    @Bind(R.id.tv_waiting_type)
    TextView tv_waiting_type;
    @Bind(R.id.tv_waiting_location)
    TextView tv_waiting_location;
    @Bind(R.id.tv_waiting_time)
    TextView tv_waiting_time;
    @Bind(R.id.tv_waiting_des)
    TextView tv_waiting_des;
    @Bind(R.id.tv_waiting_appointment)
    TextView tv_waiting_appointment;
    @Bind(R.id.tv_waiting_countdown)
    TextView tv_waiting_countdown;
    @Bind(R.id.voiceLayout)
    RelativeLayout voiceLayout;
    @Bind(R.id.voiceTime)
    View voiceTime;
    @Bind(R.id.tv_audio_len)
    TextView tv_audio_len;
    WaitingResponseAdapter adapter;
    WorkOrderBean.ListObjBean bean;
    private boolean isCall = false;

    @Override
    public WaitingResponsePresent initPresenter() {
        return new WaitingResponsePresent(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_waiting_response;
    }

    @Override
    protected void initView() {
        setTitle("待响应");
        showForwardView("巡检处理", true);
        showBackwardView("", true);
        bean = (WorkOrderBean.ListObjBean) getIntent().getSerializableExtra("bean");
        adapter = new WaitingResponseAdapter(this, bean.getImg_address());
        adapter.setOnClickImageListener(this);
        gv_waiting_pic.setAdapter(adapter);
        initData();
    }

    private void initData() {
        tv_waiting_userName.setText(StringUtil.isEmpty(bean.getUserName()) ? "暂无" : bean.getUserName());
        tv_waiting_project.setText(StringUtil.isEmpty(bean.getOrgname()) ? "暂无" : bean.getOrgname());
        tv_waiting_room_number.setText(StringUtil.isEmpty(bean.getRoomNum()) ? "暂无" : bean.getRoomNum());
        tv_waiting_type.setText(StringUtil.isEmpty(bean.getSceneName()) ? "暂无" : bean.getSceneName());
        tv_waiting_location.setText(StringUtil.isEmpty(bean.getAreaName()) ? "暂无" : bean.getAreaName());
        tv_waiting_time.setText(StringUtil.isEmpty(bean.getSuperviseTime()) ? "暂无" : bean.getSuperviseTime());
        tv_waiting_des.setText(StringUtil.isEmpty(bean.getDescribe()) ? "暂无" : bean.getDescribe());
        long time = TimeUtils.string2Millis(bean.getSuperviseTime());
        Log.i("wan", "time>>>" + time);
        long shutDown = 5 * 60 * 1000 - (System.currentTimeMillis() - time);
        tv_waiting_countdown.setVisibility(View.VISIBLE);
        voiceLayout.setVisibility(StringUtil.isEmpty(bean.getAudioaddress()) ? View.GONE : View.VISIBLE);
        tv_waiting_des.setVisibility(StringUtil.isEmpty(bean.getDescribe()) ? View.GONE : View.VISIBLE);
        if (shutDown > 0) {
            presenter.getShowCountTime(tv_waiting_countdown, shutDown);
        } else {
            tv_waiting_countdown.setText("工单响应超时,请及时处理");
        }
        presenter.setTextViewAnimator(tv_waiting_countdown, 0, tv_waiting_countdown.getLayoutParams().height);//展开收缩动画
    }


    @Override
    protected void onForward(View forwardView) {
        super.onForward(forwardView);
        startActivity(new Intent(this, ReportDealActivity.class));
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    private boolean flag = false;

    private String projectId = "";

    @OnClick({R.id.tv_waiting_start_deal, R.id.tv_waiting_appointment, R.id.voiceLayout,
            R.id.tv_waiting_response_phone, R.id.tv_waiting_location, R.id.tv_waiting_type})
    public void onClick(View view) {
        switch (view.getId()) {
            //选择关键点位
            case R.id.tv_waiting_location:
                projectId = CommonlyUtils.listObjBean != null ? CommonlyUtils.listObjBean.getId() : bean.getOrgId();
                if (!StringUtils.isEmpty(projectId)) {
                    Intent key = new Intent(this, KeyPointsActivity.class);
                    key.putExtra("projectId", projectId);
                    startActivity(key);
                }
                break;
            //开始处理
            case R.id.tv_waiting_start_deal:
                if (!isCall) {
                    ToastUtils.showShort("请先联系报事人,再进行处理!");
                    return;
                }

                SortedMap<String, String> map = new TreeMap<>();
                if (tv_waiting_appointment.getText().toString().trim().equals("请选择预约时间")) {
                    ToastUtils.showShort("请选择预约时间!");
                    return;
                }
                map.put("appointmentTime", tv_waiting_appointment.getText().toString().trim());
                map.put("processId", bean.getId() + "");
                map.put("sessionId", SharedPreferencesUtils.init(this).getString("quality_sessionId"));
                map.put("userId", MyApplicaption.getUserId());
                //位置区域
                if (CommonlyUtils.pointsBean != null) {
                    map.put("cruxId", CommonlyUtils.pointsBean.getId());
                }
                //报事分类
                if (CommonlyUtils.listObjBean != null) {
                    map.put("sceneId", CommonlyUtils.listObjBean.getId());
                }
                presenter.getAppointment(map);
                break;
            //选择报事类型
            case R.id.tv_waiting_type:
                Intent intent = new Intent(this, RegisterDepartmentActivity.class);
                intent.putExtra("projectId", bean.getOrgId());
                startActivity(intent);
                break;
            //选择预约时间
            case R.id.tv_waiting_appointment:
                presenter.initLunarPicker(this, tv_waiting_appointment);
                break;
            //拨打电话
            case R.id.tv_waiting_response_phone:
                presenter.initDialog(this, bean.getUserName(), view);
                break;
            case R.id.voiceLayout:
                if (flag == false) {
                    flag = true;
                    RadioUtil.openRadio(this, view, bean.getAudioaddress(), new RadioUtil.RadioPlayListener() {
                        @Override
                        public void onRadioPlayOver(boolean playOver) {
                            flag = playOver;
                        }
                    });
                } else {
                    flag = false;
                    RadioUtil.closeRadio(this, view);
                }
                break;
        }
    }

    public void initAppHandleMap() {
        //事件响应
        SortedMap<String, String> map = new TreeMap<>();
        map.put("reporId", bean.getId());//当前流程主键ID
        map.put("sessionId", SharedPreferencesUtils.init(this).getString("quality_sessionId"));//用户登录后唯一标识
        map.put("userId", MyApplicaption.getUserId());//用户唯一标识
        presenter.getReportAnswer(map);
    }

    @Override
    public void onProcessDetailOnNext(ProcessDetailBean info) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CommonlyUtils.listObjBean != null) {
            tv_waiting_type.setText(CommonlyUtils.listObjBean.getName());
        }
        if (CommonlyUtils.pointsBean != null) {
            tv_waiting_location.setText(CommonlyUtils.pointsBean.getName());
        }
    }

    @Override
    public void onCallBack(CustomPopWindows callPhoneWindows) {
        String phoneNum = "tel:" + bean.getPhone();
        Intent callPhoneIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNum));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callPhoneIntent);
        callPhoneWindows.dismiss();
        isCall = true;

        initAppHandleMap();
    }

    @Override
    public void onAppointment(AppHandleInfo info) {
        //处理成功,跳转巡检处理
        startActivity(new Intent(this, ReportDealActivity.class));
        finish();
    }

    @Override
    public void onItemImage(int item) {
        List<String> tempList = new ArrayList<>();
        tempList.addAll(bean.getImg_address());
        ImagePagerActivity.startActivity(this, tempList, item);
    }

    @Override
    public void showLoading() {
        showDialog();
    }

    @Override
    public void hideLoading() {
        dimissDialog();
    }
}
