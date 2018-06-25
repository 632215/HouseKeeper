package com.jinke.housekeeper.saas.report.ui.activity.processnode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.saas.report.bean.NodeElseBean;
import com.jinke.housekeeper.saas.report.bean.ProcessNodeBean;
import com.jinke.housekeeper.saas.report.bean.WorkBean;
import com.jinke.housekeeper.saas.report.presenter.ProcessDetailActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.widget.NavigationView;
import com.jinke.housekeeper.saas.report.util.RadioUtil;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.view.ProcessDetailActivityView;
import com.jinke.housekeeper.ui.widget.CustomPopWindows;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.jinke.housekeeper.saas.report.util.MediaPlayerManager;
import com.tencent.stat.StatService;

import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * Created by pc on 2017/3/7.
 */

public class ProcessDetailActivity extends BaseActivity<ProcessDetailActivityView, ProcessDetailActivityPresenter> implements
        NavigationView.OnNacigationTitleCallback,
        View.OnClickListener,
        ProcessDetailActivityView, RadioUtil.RadioPlayListener {
    @Bind(R.id.rootView)
    LinearLayout rootView;
    @Bind(R.id.titleBar)
    NavigationView titleBar;
    //处理人
    @Bind(R.id.dealpeople)
    TextView dealpeople;
    @Bind(R.id.callPhone)//拨打电话
            TextView callPhone;
    @Bind(R.id.tv_checkdetails_Signed)//处理时间
            TextView tv_checkdetails_Signed;
    @Bind(R.id.activity_process_detail_rl_dealdetail)
    RelativeLayout rlDealdetail;
    private CustomPopWindows callPhoneWindows;//拨打电话的弹出窗口
    private TextView titleContent;//标题
    private TextView cancleDelete;
    private TextView sureDelete;
    private NodeElseBean.ObjBean nodeDetail;
    private WorkBean.ObjBean workBean;
    private ProcessNodeBean.ObjBean nodeInfosBean;
    @Bind(R.id.voiceLayout)//语音描述
            RelativeLayout voiceLayout;
    private Drawable drawable; //音频动画图片
    private AnimationDrawable animation;//音频动画
    private boolean flag = false;//表示音频是否打开
    @Bind(R.id.voiceAnim)
    View voiceAnim;
    @Bind(R.id.voiceTime)//语音长度
            TextView voiceTime;
    @Bind(R.id.tv_checkdetails_problem)
    TextView tv_checkdetails_problem;
    @Bind(R.id.resultLayout)//处理结果
            RelativeLayout resultLayout;
    @Bind(R.id.result)
    TextView result;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_process_detail;
    }

    @Override
    protected void initView() {
        checkCallPermission();
        voiceLayout.setOnClickListener(this);
        nodeInfosBean = (ProcessNodeBean.ObjBean) getIntent().getSerializableExtra("nodeInfosBean");
        resultLayout.setVisibility(nodeInfosBean.getName().equals("审核监管") ? View.VISIBLE : View.GONE);
        switch (nodeInfosBean.getType()) {
            case "3"://其他
                getProcessDetail();
                break;
            case "4"://派发工单
                getWorkDetail();
                break;
        }
        titleBar.setOnNavigationCallback(this);
        callPhone.setOnClickListener(this);
    }

    @Override
    public void onBackClick() {
        finish();
    }

    private void getProcessDetail() {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", SharedPreferencesUtils.init(this).getString("quality_sessionId"));
        map.put("processId", nodeInfosBean.getProcessId());
        map.put("taskId", nodeInfosBean.getTaskId());
        map.put("puId", nodeInfosBean.getPuId());
        map.put("type", nodeInfosBean.getType());
        presenter.getProcessDetail(map);
    }

    @Override
    public void getProcessDetailonNext(NodeElseBean info) {
        nodeDetail = info.getObj().get(0);
        titleBar.setTitle(nodeDetail.getUserName() + nodeInfosBean.getName());
        dealpeople.setText(nodeDetail.getUserName());
        tv_checkdetails_Signed.setText(nodeDetail.getTime());
        if (nodeDetail.getRemark().equals("")) {
            rlDealdetail.setVisibility(View.GONE);
        } else {
            rlDealdetail.setVisibility(View.VISIBLE);
            tv_checkdetails_problem.setVisibility(View.VISIBLE);
            tv_checkdetails_problem.setText(nodeDetail.getRemark().toString().trim());
        }
        if (nodeDetail.getRecode() != null) {
            result.setText(nodeDetail.getRecode());
        }
        if (nodeDetail.getAudioAddr().equals("")) {
            voiceLayout.setVisibility(View.GONE);
        } else {
            voiceLayout.setVisibility(View.VISIBLE);
            voiceTime.setText(nodeDetail.getAudioLen() + "″");
        }
    }

    @Override
    public void getProcessDetailonError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(MyApplicaption.getInstance(), code);
    }

    private void getWorkDetail() {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", SharedPreferencesUtils.init(this).getString("quality_sessionId"));
        map.put("processId", nodeInfosBean.getProcessId());
        map.put("taskId", nodeInfosBean.getTaskId());
        map.put("id", nodeInfosBean.getId());
        map.put("type", nodeInfosBean.getType());
        map.put("pid", String.valueOf(nodeInfosBean.getPid()));
        map.put("puId", nodeInfosBean.getPuId());
        presenter.getWorkDetail(map);
    }

    @Override
    public void getWorkDetailonNext(WorkBean.ObjBean workBean) {
        this.workBean = workBean;
        titleBar.setTitle(workBean.getStaffIdName() + nodeInfosBean.getName());
        dealpeople.setText(workBean.getStaffIdName().equals("") ? "暂无" : workBean.getStaffIdName());
        tv_checkdetails_Signed.setText(workBean.getCreateTime().equals("") ? "暂无" : workBean.getCreateTime());
        if (workBean.getDescribes().equals("")) {
            tv_checkdetails_problem.setVisibility(View.GONE);
        } else {
            tv_checkdetails_problem.setVisibility(View.VISIBLE);
            tv_checkdetails_problem.setText(workBean.getDescribes().toString().trim());
        }
        if (workBean.getAudioAddr().equals("")) {
            voiceLayout.setVisibility(View.GONE);
        } else {
            voiceLayout.setVisibility(View.VISIBLE);
            voiceTime.setText(workBean.getAudioLen() + "″");
        }
    }

    @Override
    public void getWorkDetailonError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(MyApplicaption.getInstance(), code);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.voiceLayout:
                playRadio();
                break;

            case R.id.callPhone:  //电话被点击
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ToastUtils.showShort("请打开电话权限！");
                } else {
                    if (nodeDetail == null && workBean == null) {
                        break;
                    }
                    callPhoneWindows = new CustomPopWindows(this);
                    callPhoneWindows.setContentView(View.inflate(this, R.layout.deletepicwindows, null));
                    View deletePicView = callPhoneWindows.getContentView();
                    titleContent = (TextView) deletePicView.findViewById(R.id.titleContent);
                    cancleDelete = (TextView) deletePicView.findViewById(R.id.cancleDelete);
                    sureDelete = (TextView) deletePicView.findViewById(R.id.sureDelete);
                    if (nodeInfosBean.getType().equals("3") && nodeDetail != null) {
                        if (nodeDetail.getUserName() != null && nodeDetail.getUserName().toString().trim() != "") {
                            titleContent.setText("是否拨打" + nodeDetail.getUserName() + "的电话？");
                        }
                    } else if (nodeInfosBean.getType().equals("4") && workBean != null) {
                        if (workBean.getStaffIdName() != null && workBean.getStaffIdName().toString().trim() != "") {
                            titleContent.setText("是否拨打" + workBean.getStaffIdName() + "的电话？");
                            callPhoneWindows.showAtLocation(rootView, Gravity.CENTER, 0, 0);
                            cancleDelete.setOnClickListener(this);
                            sureDelete.setOnClickListener(this);
                        }else {
                            ToastUtils.showShort("未找到联系电话!");
                        }
                    }

                }
                break;
            case R.id.cancleDelete:
                callPhoneWindows.dismiss();
                break;
            case R.id.sureDelete:
                if (workBean == null && nodeDetail == null) {
                    return;
                }
                String phoneNum = "";
                switch (nodeInfosBean.getType()) {
                    case "3"://其他
                        phoneNum = "tel:" + nodeDetail.getMoblePhone().toString().trim();
                        break;
                    case "4"://派发工单
                        phoneNum = "tel:" + workBean.getMobilePhone().toString().trim();
                        break;
                }

                Intent callPhoneIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNum));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callPhoneIntent);
                callPhoneWindows.dismiss();
                break;
        }
    }

    private void playRadio() {
        if (flag == false) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ToastUtils.showShort(getResources().getString(R.string.please_open_record_permision));
            } else {
                flag = true;
                if (voiceAnim == null) {
                    voiceAnim = this.findViewById(R.id.voiceAnim);
                }
                voiceAnim.setBackgroundResource(R.drawable.v_anim3);
                if (nodeDetail != null) {
                    RadioUtil.openRadio(this, voiceAnim, nodeDetail.getAudioAddr(), this);
                }
            }
        } else {
            flag = false;
            RadioUtil.closeRadio(this, voiceAnim);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaPlayerManager.release();
        StatService.onPause(ProcessDetailActivity.this);
        StatService.trackEndPage(ProcessDetailActivity.this, "事件进度详情");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaPlayerManager.release();
    }

    @Override
    public ProcessDetailActivityPresenter initPresenter() {
        return new ProcessDetailActivityPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaPlayerManager.release();
        if (voiceAnim == null) {
            voiceAnim = findViewById(R.id.voiceAnim);
        }
        voiceAnim.setBackgroundResource(R.drawable.v_anim3);
        StatService.onResume(ProcessDetailActivity.this);
        StatService.trackBeginPage(ProcessDetailActivity.this, "事件进度详情");
    }

    private void checkCallPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        switch (requestCode) {
            case 2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "请在应用管理中打开“通话”访问权限！", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onRadioPlayOver(boolean flag) {
        this.flag = flag;
    }
}

