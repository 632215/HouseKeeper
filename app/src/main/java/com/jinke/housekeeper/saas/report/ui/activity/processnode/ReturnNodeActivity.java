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
import com.jinke.housekeeper.saas.report.bean.ProcessDetailBean;
import com.jinke.housekeeper.saas.report.bean.ProcessNodeBean;
import com.jinke.housekeeper.saas.report.presenter.DelayProgressActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.ImagePagerActivity;
import com.jinke.housekeeper.saas.report.ui.adapter.GridAdatper;
import com.jinke.housekeeper.saas.report.ui.widget.MyGridView;
import com.jinke.housekeeper.saas.report.ui.widget.NavigationView;
import com.jinke.housekeeper.saas.report.util.RadioUtil;
import com.jinke.housekeeper.saas.report.view.DelayProgressActivityView;
import com.jinke.housekeeper.ui.widget.CustomPopWindows;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by pc on 2017/3/7.
 */

public class ReturnNodeActivity extends BaseActivity<DelayProgressActivityView, DelayProgressActivityPresenter> implements
        NavigationView.OnNacigationTitleCallback,
        View.OnClickListener,
        DelayProgressActivityView, RadioUtil.RadioPlayListener, GridAdatper.PicClickListener {
    @Bind(R.id.rootView)
    LinearLayout rootView;
    @Bind(R.id.titleBar)
    NavigationView titleBar;
    //图片放大
    @Bind(R.id.displayView)
    LinearLayout displayView;
    @Bind(R.id.enlarge)
    PhotoView enlarge;
    //处理人
    @Bind(R.id.dealpeople)
    TextView deal_people;
    //拨打电话
    @Bind(R.id.callPhone)
    TextView callPhone;
    //处理时间
    @Bind(R.id.tv_checkdetails_Signed)
    TextView tv_check_details_Signed;
    //截止时间
    @Bind(R.id.tv_checkdetails_end)
    TextView tv_check_details_end;
    //问题描述
    @Bind(R.id.tv_checkdetails_problem)
    TextView tv_check_details_problem;
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
    @Bind(R.id.tv_work_order)
    TextView tv_work_order;
    private List<String> pictureList = new ArrayList<>();
    private CustomPopWindows callPhoneWindows;//拨打电话的弹出窗口
    private TextView titleContent;//标题
    private TextView cancleDelete;
    private TextView sureDelete;
    private ProcessNodeBean.ObjBean nodeInfosBean;
    ProcessDetailBean.ObjBean objBeanDetail;//节点详情


    @Override
    protected int getContentViewId() {
        return R.layout.activity_return_dode;
    }

    @Override
    protected void initView() {
        titleBar.setTitle("回访详情");
        titleBar.setOnNavigationCallback(this);
        nodeInfosBean = (ProcessNodeBean.ObjBean) getIntent().getSerializableExtra("nodeInfosBean");
        objBeanDetail = (ProcessDetailBean.ObjBean) getIntent().getSerializableExtra("objBeanDetail");
        showBackwardView(R.string.empty, true);
        enlarge.setOnClickListener(this);
        callPhone.setOnClickListener(this);
        voiceLayout.setOnClickListener(this);
        initAdapter();
    }

    private void initAdapter() {
        deal_people.setText(nodeInfosBean.getUserName());
        tv_check_details_Signed.setText(nodeInfosBean.getTime());
        tv_check_details_problem.setVisibility(View.VISIBLE);
        tv_check_details_end.setText(nodeInfosBean.getIfManyi());
        tv_check_details_problem.setText(nodeInfosBean.getRemark());
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
                    if (nodeInfosBean == null) {
                        break;
                    }
                    callPhoneWindows = new CustomPopWindows(this);
                    callPhoneWindows.setContentView(View.inflate(this, R.layout.deletepicwindows, null));
                    View deletePicView = callPhoneWindows.getContentView();
                    titleContent = (TextView) deletePicView.findViewById(R.id.titleContent);
                    cancleDelete = (TextView) deletePicView.findViewById(R.id.cancleDelete);
                    sureDelete = (TextView) deletePicView.findViewById(R.id.sureDelete);
                    if (nodeInfosBean.getUserName() != null || nodeInfosBean.getPhone().toString().trim() != "") {
                        titleContent.setText("是否拨打" + nodeInfosBean.getUserName() + "的电话？");
                        callPhoneWindows.showAtLocation(rootView, Gravity.CENTER, 0, 0);
                        cancleDelete.setOnClickListener(this);
                        sureDelete.setOnClickListener(this);
                    } else {
                        ToastUtils.showShort("未找到联系电话!");
                    }
                }
                break;
            case R.id.cancleDelete:
                callPhoneWindows.dismiss();
                break;
            case R.id.sureDelete:
                if (nodeInfosBean != null && nodeInfosBean.getPhone() != null) {
                    String phoneNum = "tel:" + nodeInfosBean.getPhone().toString().trim();
                    Intent callPhoneIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNum));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(callPhoneIntent);
                } else {
                    ToastUtils.showShort("联系人暂未留取电话号码");
                    return;
                }
                callPhoneWindows.dismiss();
                break;
        }
    }


    @Override
    public void getProcessDetailOnNext(NodeDelayBean info) {

    }

    @Override
    public void getProcessDetailOnError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(MyApplicaption.getInstance(), code);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RadioUtil.closeRadio(this, voiceAnim);
        StatService.onPause(ReturnNodeActivity.this);
        StatService.trackEndPage(ReturnNodeActivity.this, "事件处理进度查询");
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
        StatService.onResume(ReturnNodeActivity.this);
        StatService.trackBeginPage(ReturnNodeActivity.this, "事件处理进度查询");
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
