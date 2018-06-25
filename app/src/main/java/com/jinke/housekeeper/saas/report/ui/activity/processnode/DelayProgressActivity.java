package com.jinke.housekeeper.saas.report.ui.activity.processnode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bm.library.PhotoView;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.saas.report.bean.NodeDelayBean;
import com.jinke.housekeeper.saas.report.bean.ProcessNodeBean;
import com.jinke.housekeeper.saas.report.presenter.DelayProgressActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.ImagePagerActivity;
import com.jinke.housekeeper.saas.report.ui.adapter.GridAdatper;
import com.jinke.housekeeper.saas.report.ui.widget.MyGridView;
import com.jinke.housekeeper.saas.report.ui.widget.NavigationView;
import com.jinke.housekeeper.saas.report.util.RadioUtil;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.view.DelayProgressActivityView;
import com.jinke.housekeeper.ui.widget.CustomPopWindows;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * Created by pc on 2017/3/7.
 */

public class DelayProgressActivity extends BaseActivity<DelayProgressActivityView, DelayProgressActivityPresenter> implements
        NavigationView.OnNacigationTitleCallback,
        View.OnClickListener,
        DelayProgressActivityView, RadioUtil.RadioPlayListener, GridAdatper.PicClickListener {
    @Bind(R.id.rootView)
    LinearLayout rootView;
    @Bind(R.id.titleBar)
    NavigationView titleBar;
    @Bind(R.id.displayView)//图片放大
            LinearLayout displayView;
    @Bind(R.id.enlarge)
    PhotoView enlarge;
    //处理人
    @Bind(R.id.dealpeople)
    TextView dealpeople;
    //拨打电话
    @Bind(R.id.callPhone)
    TextView callPhone;
    //处理时间
    @Bind(R.id.tv_checkdetails_Signed)
    TextView tv_checkdetails_Signed;
    //截止时间
    @Bind(R.id.tv_checkdetails_end)
    TextView tv_checkdetails_end;
    //问题描述
    @Bind(R.id.tv_checkdetails_problem)
    TextView tv_checkdetails_problem;
    //语音描述
    @Bind(R.id.voiceLayout)
    RelativeLayout voiceLayout;
    @Bind(R.id.activity_delay_progress_mygridview)
    MyGridView myGridView;
    private boolean flag = false;//表示音频是否打开
    @Bind(R.id.voiceAnim)
    View voiceAnim;
    //语音长度
    @Bind(R.id.voiceTime)
    TextView voiceTime;
    @Bind(R.id.endLayout)
    RelativeLayout endLayout;
    private GridAdatper pictureAdatper;
    private List<String> pictureList = new ArrayList<>();
    private CustomPopWindows callPhoneWindows;//拨打电话的弹出窗口
    private TextView titleContent;//标题
    private TextView cancleDelete;
    private TextView sureDelete;
    private NodeDelayBean.ObjBean nodeDetail;
    private ProcessNodeBean.ObjBean nodeInfosBean;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_delay_progress;
    }

    @Override
    protected void initView() {
        titleBar.setOnNavigationCallback(this);
        nodeInfosBean = (ProcessNodeBean.ObjBean) getIntent().getSerializableExtra("nodeInfosBean");
        getProcessDetail();
        showBackwardView(R.string.empty, true);
        enlarge.setOnClickListener(this);
        callPhone.setOnClickListener(this);
        voiceLayout.setOnClickListener(this);
        initAdapter();
    }

    private void initAdapter() {
        pictureAdatper = new GridAdatper(this, pictureList, this);
        myGridView.setAdapter(pictureAdatper);
    }

    @Override
    public DelayProgressActivityPresenter initPresenter() {
        return new DelayProgressActivityPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enlarge:
                displayView.setVisibility(View.GONE);
                break;
            case R.id.callPhone:  //电话被点击
                RadioUtil.closeRadio(this, voiceAnim);
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ToastUtils.showShort("请打开电话权限！");
                } else {
                    if (nodeDetail == null) {
                        break;
                    }
                    callPhoneWindows = new CustomPopWindows(this);
                    callPhoneWindows.setContentView(View.inflate(this, R.layout.deletepicwindows, null));
                    View deletePicView = callPhoneWindows.getContentView();
                    titleContent = (TextView) deletePicView.findViewById(R.id.titleContent);
                    cancleDelete = (TextView) deletePicView.findViewById(R.id.cancleDelete);
                    sureDelete = (TextView) deletePicView.findViewById(R.id.sureDelete);
                    if (nodeDetail.getNickName() != null || nodeDetail.getNickName().toString().trim() != "") {
                        titleContent.setText("是否拨打" + nodeDetail.getNickName() + "的电话？");
                        callPhoneWindows.showAtLocation(rootView, Gravity.CENTER, 0, 0);
                        cancleDelete.setOnClickListener(this);
                        sureDelete.setOnClickListener(this);
                    }else {
                        ToastUtils.showShort("未找到联系电话!");
                    }
                }
                break;
            case R.id.cancleDelete:
                callPhoneWindows.dismiss();
                break;
            case R.id.sureDelete:
                if (nodeDetail == null) {
                    return;
                }
                String phoneNum = "tel:" + nodeDetail.getUserPhone().toString().trim();
                Intent callPhoneIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNum));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callPhoneIntent);
                callPhoneWindows.dismiss();
                break;
            case R.id.voiceLayout:
                playRadio();
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
                RadioUtil.openRadio(this, voiceAnim, nodeDetail.getAudioAddr(), this);
            }
        } else {
            flag = false;
            RadioUtil.closeRadio(this, voiceAnim);
        }
    }

    private void getProcessDetail() {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", SharedPreferencesUtils.init(this).getString("quality_sessionId"));
        map.put("processId", nodeInfosBean.getProcessId());
        map.put("taskId", nodeInfosBean.getTaskId());
        map.put("id", nodeInfosBean.getId());
        map.put("type", nodeInfosBean.getType());
        presenter.getProcessDetail(map);
    }

    @Override
    public void getProcessDetailOnNext(NodeDelayBean info) {
        nodeDetail = info.getObj();
        titleBar.setTitle(nodeDetail.getNickName() + nodeInfosBean.getName());
        dealpeople.setText(nodeDetail.getNickName().equals("") ? "暂无" : nodeDetail.getNickName());
        tv_checkdetails_Signed.setText(nodeInfosBean.getTime().equals("") ? "暂无" : nodeInfosBean.getTime());
        if (nodeDetail.getDescrbe().equals("")) {
            tv_checkdetails_problem.setVisibility(View.GONE);
        } else {
            tv_checkdetails_problem.setVisibility(View.VISIBLE);
            tv_checkdetails_problem.setText(nodeDetail.getDescrbe().toString().trim());
        }

        tv_checkdetails_end.setText(nodeDetail.getCompleteTime().equals("") ? "暂无" : nodeDetail.getCompleteTime());
        endLayout.setVisibility(nodeDetail.getCompleteTime().equals("") ? View.GONE : View.VISIBLE);

        if (nodeDetail.getAudioAddr().equals("")) {
            voiceLayout.setVisibility(View.GONE);
        } else {
            voiceLayout.setVisibility(View.VISIBLE);
            voiceTime.setText(nodeDetail.getAudioLen() + "″");
        }

        if (!nodeDetail.getDescrbe().equals("延迟申请")) {
            pictureList.clear();
            if (nodeDetail.getImgUrl() != "") {
                if (nodeDetail.getImgUrl().contains("|")) {
                    String picName[] = nodeDetail.getImgUrl().split("\\|");
                    for (String string : picName) {
                        pictureList.add(string);
                    }
                } else {
                    pictureList.add(nodeDetail.getImgUrl());
                }
                myGridView.setVisibility(View.VISIBLE);
            }
            pictureAdatper.setData(pictureList);
        }
    }

    @Override
    public void getProcessDetailOnError(String code, String msg) {
        com.blankj.utilcode.util.ToastUtils.showShort(msg);
        CommonlyUtils.errorState(MyApplicaption.getInstance(), code);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RadioUtil.closeRadio(this, voiceAnim);
        StatService.onPause(DelayProgressActivity.this);
        StatService.trackEndPage(DelayProgressActivity.this, "事件处理进度查询");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RadioUtil.closeRadio(this, voiceAnim);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RadioUtil.closeRadio(this, voiceAnim);
        if (voiceAnim == null) {
            voiceAnim = findViewById(R.id.voiceAnim);
        }
        voiceAnim.setBackgroundResource(R.drawable.v_anim3);
        StatService.onResume(DelayProgressActivity.this);
        StatService.trackBeginPage(DelayProgressActivity.this, "事件处理进度查询");
    }


    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onRadioPlayOver(boolean isPlay) {
        flag = isPlay;
    }

    @Override
    public void enlargeImage(int picAddress) {
        ImagePagerActivity.startActivity(this, pictureList, picAddress);
    }
}
