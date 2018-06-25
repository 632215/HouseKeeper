package com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.HouseMsgBean;
import com.jinke.housekeeper.saas.report.bean.WorkOrderBean;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.ui.adapter.WaitingResponseAdapter;
import com.jinke.housekeeper.saas.report.ui.widget.GrabSingleDialog;
import com.jinke.housekeeper.saas.report.ui.widget.MyGridView;
import com.jinke.housekeeper.saas.report.util.RadioUtil;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.util.StringUtil;
import com.jinke.housekeeper.saas.report.util.ToastUtils;
import com.jinke.housekeeper.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 工单详情
 */

public class WorkOrderDetailsActivity extends BaseActivity implements WaitingResponseAdapter.OnClickImageListener, GrabSingleDialog.onStartTimerListener {
    @Bind(R.id.gv_work_order_pic)
    MyGridView gv_work_order_pic;
    @Bind(R.id.tv_work_order_project)
    TextView tv_work_order_project;
    @Bind(R.id.tv_work_order_room_number)
    TextView tv_work_order_room_number;
    @Bind(R.id.tx_work_order_location)
    TextView tx_work_order_location;
    @Bind(R.id.tv_word_order_type)
    TextView tv_word_order_type;
    @Bind(R.id.tv_work_order_time)
    TextView tv_work_order_time;
    @Bind(R.id.tv_work_order_des)
    TextView tv_work_order_des;
    @Bind(R.id.tv_work_order_userName)
    TextView tv_work_order_userName;
    @Bind(R.id.voiceLayout)
    RelativeLayout voiceLayout;
    @Bind(R.id.voiceTime)
    View voiceTime;
    @Bind(R.id.tv_audio_len)
    TextView tv_audio_len;
    WaitingResponseAdapter adapter;
    WorkOrderBean.ListObjBean bean;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_work_order_details;
    }

    @Override
    protected void initView() {
        bean = (WorkOrderBean.ListObjBean) getIntent().getSerializableExtra("bean");
        adapter = new WaitingResponseAdapter(this, bean.getImg_address());
        gv_work_order_pic.setAdapter(adapter);
        adapter.setOnClickImageListener(this);
        initData();
    }

    private void initData() {
        tv_work_order_project.setText(StringUtil.isEmpty(bean.getOrgname()) ? "暂无" : bean.getOrgname());
        tv_work_order_room_number.setText(StringUtil.isEmpty(bean.getRoomNum()) ? "暂无" : bean.getRoomNum());
        tx_work_order_location.setText(StringUtil.isEmpty(bean.getAreaName()) ? "暂无" : bean.getAreaName());
        tv_word_order_type.setText(StringUtil.isEmpty(bean.getSceneName()) ? "暂无" : bean.getSceneName());
        tv_work_order_time.setText(StringUtil.isEmpty(bean.getSuperviseTime()) ? "暂无" : bean.getSuperviseTime());
        tv_work_order_des.setText(StringUtil.isEmpty(bean.getDescribe()) ? "暂无" : bean.getDescribe());
        tv_work_order_userName.setText(StringUtil.isEmpty(bean.getUserName()) ? "暂无" : bean.getUserName());
        tv_work_order_des.setVisibility(StringUtil.isEmpty(bean.getDescribe()) ? View.GONE : View.VISIBLE);
        voiceLayout.setVisibility(StringUtil.isEmpty(bean.getAudioaddress()) ? View.GONE : View.VISIBLE);
        tv_audio_len.setText(bean.getAudiolen() + "");
    }

    private boolean flag = false;

    @OnClick({R.id.tv_grab_single, R.id.img_work_order_back, R.id.voiceTime})
    public void onClick(View view) {
        switch (view.getId()) {
            //抢单
            case R.id.tv_grab_single:
                getGrabSingle();
                break;
            //返回
            case R.id.img_work_order_back:
                finish();
                break;
            //播放语音
            case R.id.voiceTime:
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

    GrabSingleDialog dialog;
    private boolean isFlag = true;

    public void getGrabSingle() {
        //抢单
        SortedMap<String, String> map = new TreeMap<>();
        map.put("taskId", bean.getTaskId());
        map.put("processId", bean.getId());
        map.put("sessionId", SharedPreferencesUtils.init(this).getString("quality_sessionId"));
        map.put("userId", MyApplicaption.getUserId());
        map.put("thirdParty", "0");
        dialog = new GrabSingleDialog(WorkOrderDetailsActivity.this);
        dialog.show();
        dialog.setOnStartTimerListener(this);
        dialog.setImg_grab_state(R.drawable.icon_order_details_submit);
        dialog.setTx_window_state("抢单中,请等候抢单结果!");
        dialog.setCanceledOnTouchOutside(false);
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<HouseMsgBean>() {
            @Override
            public void onNext(HouseMsgBean info) {
                isFlag = true;
                startWaiting();
            }

            @Override
            public void onError(String Code, String Msg) {
                isFlag = false;
                dialog.setImg_grab_state(R.drawable.icon_grab_single_submit_fial);
                dialog.setTx_window_state("对不起，抢单失败");
                dialog.getStartTime();
            }
        };
        HttpMethods.getInstance().getGrabSingle(new ProgressSubscriber<HttpResult<HouseMsgBean>>(onNextListener, this, false), map, ReportConfig.createSign(map));
    }


    public void startWaiting() {
        //弹出等待框时间五秒/抢单成功
        dialog.setTx_window_state("恭喜您，抢单成功!");
        dialog.setImg_grab_state(R.drawable.icon_grab_single_submit_success);
        dialog.getStartTime();
    }


    @Override
    public void onItemImage(int item) {
        List<String> tempList = new ArrayList<>();
        tempList.addAll(bean.getImg_address());
        ImagePagerActivity.startActivity(this, tempList, item);
    }

    @Override
    public void startTime() {
        dialog.dismiss();
        if (isFlag) {
            Intent intent = new Intent(WorkOrderDetailsActivity.this, WaitingResponseActivity.class);
            intent.putExtra("bean", bean);
            startActivity(intent);
            finish();
        }
    }
}
