package com.jinke.housekeeper.saas.report.ui.activity.processnode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
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
import com.jinke.housekeeper.saas.report.bean.NodeDetailBean;
import com.jinke.housekeeper.saas.report.bean.ProcessNodeBean;
import com.jinke.housekeeper.saas.report.presenter.DealProblemActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.ImagePagerActivity;
import com.jinke.housekeeper.saas.report.ui.adapter.GridAdatper;
import com.jinke.housekeeper.saas.report.ui.widget.MyGridView;
import com.jinke.housekeeper.saas.report.ui.widget.NavigationView;
import com.jinke.housekeeper.saas.report.util.RadioUtil;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.view.DealProblemActivityView;
import com.jinke.housekeeper.ui.widget.CustomPopWindows;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.squareup.picasso.Picasso;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * Created by pc on 2017/3/7.
 */

public class DealProblemActivity extends BaseActivity<DealProblemActivityView, DealProblemActivityPresenter> implements
        NavigationView.OnNacigationTitleCallback,
        View.OnClickListener
        , DealProblemActivityView, RadioUtil.RadioPlayListener, GridAdatper.PicClickListener {
    @Bind(R.id.rootView)
    LinearLayout rootView;
    @Bind(R.id.titleBar)
    NavigationView titleBar;
    @Bind(R.id.displayView)//图片放大
            LinearLayout displayView;
    @Bind(R.id.enlarge)
    PhotoView enlarge;
    @Bind(R.id.dealpeople)//处理人
            TextView dealpeople;
    @Bind(R.id.callPhone)//拨打电话
            TextView callPhone;
    @Bind(R.id.tv_checkdetails_Signed)//处理时间
            TextView tv_checkdetails_Signed;
    @Bind(R.id.tv_checkdetails_problem)//问题描述
            TextView tv_checkdetails_problem;
    @Bind(R.id.voiceLayout)//语音描述
            RelativeLayout voiceLayout;
    @Bind(R.id.voiceAnim)
    View voiceAnim;
    @Bind(R.id.voiceTime)//语音长度
            TextView voiceTime;
    @Bind(R.id.activity_deal_problem_mygridview)
    MyGridView myGridView;
    private Drawable drawable; //音频动画图片
    private AnimationDrawable animation;//音频动画
    private boolean flag = false;//表示音频是否打开
    private CustomPopWindows callPhoneWindows;//拨打电话的弹出窗口
    private TextView titleContent;//标题
    private TextView cancleDelete;
    private TextView sureDelete;
    private NodeDetailBean.ObjBean nodeDetail;
    private ProcessNodeBean.ObjBean nodeInfosBean;
    private GridAdatper pictureAdatper;
    private List<String> pictureList = new ArrayList<>();//原图列表
    private List<String> pictureLists = new ArrayList<>();//压缩图列表

    @Override
    protected int getContentViewId() {
        return R.layout.activity_deal_problem;
    }

    @Override
    protected void initView() {
        titleBar.setOnNavigationCallback(this);
        nodeInfosBean = (ProcessNodeBean.ObjBean) getIntent().getSerializableExtra("nodeInfosBean");
        showBackwardView(R.string.empty, true);
        getProcessDetail();
        enlarge.setOnClickListener(this);
        callPhone.setOnClickListener(this);
        voiceLayout.setOnClickListener(this);
        initAdapter();
    }

    public void setImageView(String url, PhotoView photoView) {
        Picasso.with(DealProblemActivity.this)
                .load(url)
                .placeholder(R.drawable.simal)
                .error(R.drawable.simal).into(photoView);
    }

    private void initAdapter() {
        pictureAdatper = new GridAdatper(this, pictureList, this);
        myGridView.setAdapter(pictureAdatper);
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
                    if (nodeDetail.getUserName() != null || nodeDetail.getUserName().toString().trim() != "") {
                        titleContent.setText("是否拨打" + nodeDetail.getUserName() + "的电话？");
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
                String phoneNum = "tel:" + nodeDetail.getHandUserPhone().toString().trim();
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
                RadioUtil.openRadio(this, voiceAnim, nodeDetail.getAudioAdds(), this);
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
        map.put("type", nodeInfosBean.getType());
        map.put("puId", nodeInfosBean.getPuId());
        presenter.getProcessDetail(map);
    }

    @Override
    public void getProcessDetailonError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(MyApplicaption.getInstance(), code);
    }

    @Override
    public void getProcessDetailonNext(NodeDetailBean info) {
        nodeDetail = info.getObj();
        titleBar.setTitle(nodeDetail.getUserName() + nodeInfosBean.getName());
        dealpeople.setText(nodeDetail.getUserName().equals("") ? "暂无" : nodeDetail.getUserName());
        tv_checkdetails_Signed.setText(nodeDetail.getTime().equals("") ? "暂无" : nodeDetail.getTime());
        if (nodeDetail.getStaffDescribe().equals("")) {
            tv_checkdetails_problem.setVisibility(View.GONE);
        } else {
            tv_checkdetails_problem.setVisibility(View.VISIBLE);
            tv_checkdetails_problem.setText(nodeDetail.getStaffDescribe().toString().trim());
        }

        if (nodeDetail.getAudioAdds().equals("") || nodeDetail.getAudioAdds() == null) {
            voiceLayout.setVisibility(View.GONE);
        } else {
            voiceLayout.setVisibility(View.VISIBLE);
            voiceTime.setText(nodeDetail.getAudioAddsLen() + "″");
        }
        pictureList.clear();
        pictureLists.clear();
        //加载报事图片

        //        加载完成整改图片
        if (!nodeDetail.getStaffPotoadd().toString().equals("")) {
            if (nodeDetail.getStaffPotoadd().contains("|")) {
                String picName[] = nodeDetail.getStaffPotoadd().split("\\|");
                for (String string : picName) {
                    pictureList.add(string);
                }
            } else {
                pictureList.add(nodeDetail.getStaffPotoadd());
            }
        }

        if (!nodeDetail.getStaffPotoadds().toString().equals("")) {
            if (nodeDetail.getStaffPotoadds().contains("|")) {
                String picName[] = nodeDetail.getStaffPotoadds().split("\\|");
                for (String string : picName) {
                    pictureLists.add(string);
                }
            } else {
                pictureLists.add(nodeDetail.getStaffPotoadds());
            }
        }

        if (pictureLists != null && pictureLists.size() > 0 && pictureList != null && pictureList.size() == 0) {
            pictureList = pictureLists;
        } else if (pictureLists != null && pictureLists.size() == 0 && pictureList != null && pictureList.size() > 0) {
            pictureLists = pictureList;
        }

        if (pictureList != null && pictureList.size() > 0) {
            myGridView.setVisibility(View.VISIBLE);
            pictureAdatper.setData(pictureLists);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        RadioUtil.closeRadio(this, voiceAnim);
        StatService.onPause(this);
        StatService.trackEndPage(this, "问题处理详情");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RadioUtil.closeRadio(this, voiceAnim);
    }

    @Override
    public DealProblemActivityPresenter initPresenter() {
        return new DealProblemActivityPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RadioUtil.closeRadio(this, voiceAnim);
        if (voiceAnim == null) {
            voiceAnim = findViewById(R.id.voiceAnim);
        }
        voiceAnim.setBackgroundResource(R.drawable.v_anim3);
        StatService.onResume(this);
        StatService.trackBeginPage(this, "问题处理详情");

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
        if (pictureList != null && pictureList.size() > 0)
            ImagePagerActivity.startActivity(this, pictureList, picAddress);
    }
}
