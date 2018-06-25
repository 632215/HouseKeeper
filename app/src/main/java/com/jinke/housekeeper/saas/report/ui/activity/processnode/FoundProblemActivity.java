package com.jinke.housekeeper.saas.report.ui.activity.processnode;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.jinke.housekeeper.saas.report.bean.WorkOrderBean;
import com.jinke.housekeeper.saas.report.presenter.FoundProblemActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.ImagePagerActivity;
import com.jinke.housekeeper.saas.report.ui.adapter.GridAdatper;
import com.jinke.housekeeper.saas.report.ui.widget.MyGridView;
import com.jinke.housekeeper.saas.report.ui.widget.NavigationView;
import com.jinke.housekeeper.saas.report.util.NetWorksUtils;
import com.jinke.housekeeper.saas.report.util.RadioUtil;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.view.FoundProblemActivityView;
import com.jinke.housekeeper.ui.widget.CustomPopWindows;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.squareup.picasso.Picasso;
import com.tencent.stat.StatService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;

import static com.jinke.housekeeper.saas.report.ui.fragment.GrabSingleFragment.GrabSingleCallBackCode;

/**
 * Created by pc on 2017/3/7.
 */

public class FoundProblemActivity extends BaseActivity<FoundProblemActivityView, FoundProblemActivityPresenter> implements
        NavigationView.OnNacigationTitleCallback,
        View.OnClickListener,
        FoundProblemActivityView, RadioUtil.RadioPlayListener, GridAdatper.PicClickListener {
    @Bind(R.id.titleBar)
    NavigationView titleBar;
    @Bind(R.id.rootView)
    RelativeLayout rootView;
    @Bind(R.id.callPhone)//拨打电话
            TextView callPhone;
    @Bind(R.id.dealpeople)//处理人
            TextView dealpeople;
    @Bind(R.id.tv_checkdetails_project)//所属项目
            TextView tv_checkdetails_project;
    @Bind(R.id.tv_checkdetails_type)//问题归属
            TextView tv_checkdetails_type;
    @Bind(R.id.tv_checkdetails_area)//关键点位
            TextView tv_checkdetails_area;
    @Bind(R.id.tv_checkdetails_Signed)//发现时间
            TextView tv_checkdetails_Signed;
    @Bind(R.id.tv_checkdetails_problem)//问题描述
            TextView tv_checkdetails_problem;
    @Bind(R.id.voiceLayout)//语音描述
            RelativeLayout voiceLayout;
    @Bind(R.id.voiceAnim)
    View voiceAnim;
    @Bind(R.id.voiceTime)//语音长度
            TextView voiceTime;
    @Bind(R.id.grab_single_button)
    TextView grabSingleButton;
    @Bind(R.id.grab_single_bg)
    RelativeLayout grabSingleBg;
    @Bind(R.id.activity_found_problem_mygridview)
    MyGridView myGridView;
    private boolean flag = false;//表示音频是否打开
    private CustomPopWindows callPhoneWindows;//拨打电话的弹出窗口
    private TextView titleContent;//标题
    private TextView cancleDelete;
    private TextView sureDelete;
    private NodeDetailBean.ObjBean nodeDetail;
    private ProcessNodeBean.ObjBean nodeInfosBean;
    private WorkOrderBean.ListObjBean listObjBean;
    private Dialog grabSingleLodedialog;
    private TextView lodedialogTextView;
    private ImageView lodedialogImageView;

    private GridAdatper pictureAdatper;
    private List<String> pictureList = new ArrayList<>();
    private List<String> pictureLists = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_found_problem;
    }

    @Override
    protected void initView() {
        initDate();
        getProcessDetail();
        showBackwardView(R.string.empty, true);
        titleBar.setOnNavigationCallback(this);
        voiceLayout.setOnClickListener(this);
        callPhone.setOnClickListener(this);
        grabSingleButton.setOnClickListener(this);
        initAdapter();
    }

    private void initAdapter() {
        pictureAdatper = new GridAdatper(this, pictureList, this);
        myGridView.setAdapter(pictureAdatper);
    }

    @Override
    public FoundProblemActivityPresenter initPresenter() {
        return new FoundProblemActivityPresenter(this);
    }

    private void initDate() {
        if (null != getIntent().getSerializableExtra("date")) {
            listObjBean = (WorkOrderBean.ListObjBean) getIntent().getSerializableExtra("date");
            nodeInfosBean = new ProcessNodeBean.ObjBean();
            if (listObjBean.getImg_address().size() > 0) {
                nodeInfosBean.setPuId(listObjBean.getImg_address().get(0));
                nodeInfosBean.setAudioAddress(listObjBean.getImg_address().get(0));
            }
            nodeInfosBean.setLen(listObjBean.getAudiolen());
            nodeInfosBean.setProcessId(listObjBean.getId());
            nodeInfosBean.setTaskId(listObjBean.getTaskId());
            nodeInfosBean.setTime("");
            nodeInfosBean.setType("1");
            titleBar.setTitle("抢单");
            grabSingleBg.setVisibility(View.VISIBLE);
            initLodedialog(0);
        }
        if (null != getIntent().getSerializableExtra("nodeInfosBean")) {
            nodeInfosBean = (ProcessNodeBean.ObjBean) getIntent().getSerializableExtra("nodeInfosBean");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.voiceLayout:
                playRadio();
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
                    if (nodeDetail.getHandUserName() != null || nodeDetail.getHandUserName().toString().trim() != "") {
                        titleContent.setText("是否拨打" + nodeDetail.getHandUserName() + "的电话？");
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

            case R.id.grab_single_button:
                RadioUtil.closeRadio(this, voiceAnim);
                grabSingle();
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
                RadioUtil.openRadio(this, voiceAnim, nodeDetail.getAudioAdd(), this);
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
        presenter.getProcessDetail(map);
    }

    @Override
    public void getProcessDetailonNext(NodeDetailBean info) {
        nodeDetail = info.getObj();
        if (null == listObjBean) {
            titleBar.setTitle(nodeDetail.getHandUserName() + nodeInfosBean.getName());
        }
        dealpeople.setText(nodeDetail.getHandUserName().equals("") ? "暂无" : nodeDetail.getHandUserName());
        tv_checkdetails_project.setText(nodeDetail.getProName().equals("") ? "暂无" : nodeDetail.getProName());
        tv_checkdetails_type.setText(nodeDetail.getSceneName().equals("") ? "暂无" : nodeDetail.getSceneName());
        tv_checkdetails_area.setText(nodeDetail.getAreaName().equals("") ? "暂无" : nodeDetail.getAreaName());
        tv_checkdetails_Signed.setText(nodeDetail.getHandTime().equals("") ? "暂无" : getTimeText(nodeDetail.getHandTime()));
        if (nodeDetail.getSuperviseDescribe().equals("")) {
            tv_checkdetails_problem.setVisibility(View.GONE);
        } else {
            tv_checkdetails_problem.setVisibility(View.VISIBLE);
            tv_checkdetails_problem.setText(nodeDetail.getSuperviseDescribe().toString().trim());
        }

        if (nodeDetail.getAudioAdd().equals("") || nodeDetail.getAudioAdd() == null) {
            voiceLayout.setVisibility(View.GONE);
        } else {
            voiceLayout.setVisibility(View.VISIBLE);
            voiceTime.setText(nodeDetail.getAudioAddLen() + "″");
        }
        pictureList.clear();
        pictureLists.clear();
        if (!nodeDetail.getSupervisePotoadd().toString().equals("") && nodeDetail.getSupervisePotoadd().toString().contains("|")) {
            for (String imageUrl : nodeDetail.getSupervisePotoadd().split("\\|")) {
                pictureList.add(imageUrl);
            }
        } else if (!nodeDetail.getSupervisePotoadd().toString().equals("")) {
            pictureList.add(nodeDetail.getSupervisePotoadd().toString());
        }

        if (!nodeDetail.getSupervisePotoadds().toString().equals("") && nodeDetail.getSupervisePotoadds().toString().contains("|")) {
            for (String imageUrl : nodeDetail.getSupervisePotoadd().split("\\|")) {
                pictureLists.add(imageUrl);
            }
        } else if (!nodeDetail.getSupervisePotoadds().toString().equals("")) {
            pictureLists.add(nodeDetail.getSupervisePotoadds().toString());
        }

        if (pictureLists != null && pictureLists.size() > 0 && pictureList != null && pictureList.size() == 0) {
            pictureList = pictureLists;
        } else if (pictureLists != null && pictureLists.size() == 0 && pictureList != null && pictureList.size() > 0) {
            pictureLists = pictureList;
        }
        if (pictureList != null && pictureList.size() > 0) {
            myGridView.setAdapter(pictureAdatper);
            pictureAdatper.setData(pictureList);
            myGridView.setVisibility(View.VISIBLE);
        }
//
    }

    @Override
    public void getProcessDetailonError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(MyApplicaption.getInstance(), code);
    }

    private void grabSingle() {
        if (!grabSingleLodedialog.isShowing()) {
            grabSingleLodedialog.show();
            if (NetWorksUtils.isConnected(FoundProblemActivity.this)) {
                SortedMap<String, String> map = new TreeMap<>();
                map.put("userId", MyApplicaption.getUserId());
                map.put("sessionId", SharedPreferencesUtils.init(this).getString("quality_sessionId"));
                map.put("processId", nodeInfosBean.getProcessId());
                map.put("taskId", nodeInfosBean.getTaskId());
                presenter.grabSingle(map);
            } else {
                grabSingleErr();
            }
        }
    }

    @Override
    public void grabSingleonNext() {
        grabSingleSuccess();
    }

    @Override
    public void grabSingleonError(String code, String msg) {
        grabSingleErr();
        CommonlyUtils.errorState(MyApplicaption.getInstance(), code);
    }

    /**
     * 抢单失败
     */
    private void grabSingleErr() {
        grabSingleLodedialog.dismiss();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initLodedialog(2);
                grabSingleLodedialog.show();
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        grabSingleLodedialog.dismiss();
                        finish();
                    }
                }.start();
            }
        });
    }

    /**
     * 抢单成功
     */
    private void grabSingleSuccess() {
        grabSingleLodedialog.dismiss();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initLodedialog(1);
                grabSingleLodedialog.show();
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        grabSingleLodedialog.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("code", 1);
                        setResult(GrabSingleCallBackCode, intent);
                        finish();
                    }
                }.start();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        RadioUtil.closeRadio(this, voiceAnim);
        StatService.onPause(FoundProblemActivity.this);
        if (null != grabSingleLodedialog && grabSingleLodedialog.isShowing()) {
            grabSingleLodedialog.cancel();
        }
        if (null != listObjBean) {
            StatService.trackEndPage(FoundProblemActivity.this, "抢单");
        } else {
            StatService.trackEndPage(FoundProblemActivity.this, "发现问题");
        }
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
        StatService.onResume(FoundProblemActivity.this);
        if (null != listObjBean) {
            StatService.trackBeginPage(FoundProblemActivity.this, "抢单");
        } else {
            StatService.trackBeginPage(FoundProblemActivity.this, "发现问题");
        }
    }

    /**
     * 格式时间
     *
     * @param systemTime
     */
    private String getTimeText(String systemTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(systemTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat showDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        if (null != date) {
            return showDateFormat.format(date);
        } else {
            return "";
        }
    }

    /**
     * 抢单状态对话框
     *
     * @param state 展示界面状态
     */
    private void initLodedialog(int state) {
        grabSingleLodedialog = new Dialog(this);
        grabSingleLodedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window dialogWindow = grabSingleLodedialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.TOP);
        lp.y = (int) getResources().getDimension(R.dimen.base_dimen_278); // 新位置Y坐标
        dialogWindow.setAttributes(lp);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.grab_single_load, null);
        lodedialogTextView = (TextView) view.findViewById(R.id.grab_single_hint);
        lodedialogImageView = (ImageView) view.findViewById(R.id.grab_single_wate_ico);
        switch (state) {
            case 0:
                grabSingleLodedialog.setContentView(R.layout.grab_single_load);
                break;
            case 1:
                grabSingleLodedialog.setContentView(R.layout.grab_single_load_success);
                break;
            case 2:
                grabSingleLodedialog.setContentView(R.layout.grab_single_load_false);
                break;
        }
        grabSingleLodedialog.setCanceledOnTouchOutside(false);
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
