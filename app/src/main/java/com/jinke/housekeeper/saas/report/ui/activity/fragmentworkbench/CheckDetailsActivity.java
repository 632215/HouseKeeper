package com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.saas.report.bean.ImageInfo;
import com.jinke.housekeeper.saas.report.bean.ProcessDetailBean;
import com.jinke.housekeeper.saas.report.bean.WaitRectifiedBean;
import com.jinke.housekeeper.saas.report.presenter.CheckDetailsActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.adapter.GridAdatper;
import com.jinke.housekeeper.saas.report.ui.fragment.fragmentdeal.DelayFragment;
import com.jinke.housekeeper.saas.report.ui.fragment.fragmentdeal.FinishFragment;
import com.jinke.housekeeper.saas.report.ui.fragment.fragmentdeal.ReportFragment;
import com.jinke.housekeeper.saas.report.ui.fragment.fragmentdeal.SeriousFragment;
import com.jinke.housekeeper.saas.report.ui.fragment.fragmentdeal.WorkFragment;
import com.jinke.housekeeper.saas.report.ui.widget.MyGridView;
import com.jinke.housekeeper.saas.report.ui.widget.NavigationView;
import com.jinke.housekeeper.saas.report.util.NetWorksUtils;
import com.jinke.housekeeper.saas.report.util.RadioUtil;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.util.TencentCountUtil;
import com.jinke.housekeeper.saas.report.util.ToastUtils;
import com.jinke.housekeeper.saas.report.view.CheckDetailsActivityView;
import com.jinke.housekeeper.ui.widget.CustomPopWindows;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * 巡检详情
 * Created by root on 7/01/17.
 */

public class CheckDetailsActivity extends BaseActivity<CheckDetailsActivityView, CheckDetailsActivityPresenter>
        implements NavigationView.OnNacigationTitleCallback,
        View.OnClickListener,
        RadioGroup.OnCheckedChangeListener,
        LoadingLayout.OnReloadListener,
        NavigationView.OnNacigationSaveCallback,
        CheckDetailsActivityView, RadioUtil.RadioPlayListener, GridAdatper.PicClickListener {
    @Bind(R.id.rootView)
    RelativeLayout rootView;
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    @Bind(R.id.titleBar)
    NavigationView title;
    @Bind(R.id.tv_checkdetails_project)
    TextView project;
    @Bind(R.id.tv_checkdetails_type)
    TextView type;
    @Bind(R.id.tv_checkdetails_area)
    TextView area;
    @Bind(R.id.tv_checkdetails_time)
    TextView time;
    @Bind(R.id.activity_checkdetails_mygridview)
    MyGridView myGridView;
    @Bind(R.id.tv_checkdetails_problem)
    TextView tv_checkdetails_problem;
    @Bind(R.id.sponsor)
    TextView sponsor;
    @Bind(R.id.callPhone)
    TextView callPhone;
    @Bind(R.id.viewProcessNode)
    RelativeLayout viewProcessNode;//查看流程节点
    @Bind(R.id.voiceLayout)
    RelativeLayout voiceLayout;//语音布局
    @Bind(R.id.voiceAnim)
    View voiceAnim;
    @Bind(R.id.voiceTime)
    TextView voiceTime;
    @Bind(R.id.checkDetails_group)
    RadioGroup checkDetailsGroup;
    @Bind(R.id.finish)
    RadioButton finish;
    @Bind(R.id.checkDetails_yanshi)
    RadioButton checkDetailsYanshi;
    @Bind(R.id.checkDetails_paifa)
    RadioButton checkDetailsPaifa;
    @Bind(R.id.checkDetails_shenqing)
    RadioButton checkDetailsShenqing;
    @Bind(R.id.tv_check_details_room)
    TextView tv_check_details_room;
    private String voiceFileName;
    private boolean flag = false;//表示音频是否打开
    public ImageInfo imageInfo;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    public UserInfo userInfo;

    private ProcessDetailBean.ObjBean objBean;//待整改详情
    public WaitRectifiedBean.ListObjBean listObjBean;

    private CustomPopWindows callPhoneWindows;//拨打电话的弹出窗口
    private TextView titleContent;//标题
    private TextView cancleDelete;
    private TextView sureDelete;

    private FinishFragment finishFragment;
    private DelayFragment delayFragment;
    private WorkFragment workFragment;
    private SeriousFragment seriousFragment;
    private ReportFragment reportFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private GridAdatper gridPictureAdatper;
    private ArrayList<String> pictureList = new ArrayList<>();//原图
    private ArrayList<String> pictureLists = new ArrayList<>();//压缩图片

    @Override
    protected int getContentViewId() {
        return R.layout.activity_checkdetails;
    }

    @Override
    protected void initView() {
        checkCallPermission();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        userInfo = CommonlyUtils.getUserInfo(CheckDetailsActivity.this);
        setOnClickListener();
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
        imageInfo = new ImageInfo();
        initTitle();
        loadLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Success : LoadingLayout.No_Network);
        loadLayout.setOnReloadListener(this);
        initFragment();//初始化fragment信息
        listObjBean = (WaitRectifiedBean.ListObjBean) getIntent().getSerializableExtra("listObjBean");
        transaction.add(R.id.checkDetails_fragment, finishFragment);
        transaction.commit();
        checkDetailsGroup.setOnCheckedChangeListener(this);
        checkDetailsGroup.check(R.id.finish);
        initViewPage();
        getProcessDetail();
    }

    private void setOnClickListener() {
        viewProcessNode.setOnClickListener(this);
        callPhone.setOnClickListener(this);
        voiceLayout.setOnClickListener(this);
    }

    private void initTitle() {
        title.setTitle(getResources().getString(R.string.deal));
        title.setOnNavigationCallback(this);
        title.setSaveVISIBLE(View.VISIBLE);
        title.setSave(getResources().getString(R.string.commit));
        title.setOnNavigationSave(this);
    }

    private void initViewPage() {
        finish.setVisibility(View.GONE);
        checkDetailsPaifa.setVisibility(View.GONE);
        checkDetailsShenqing.setVisibility(View.GONE);
        checkDetailsYanshi.setVisibility(View.GONE);
        switch (listObjBean.getCheckState()) {
            case "1":
                finish.setVisibility(View.VISIBLE);
                break;
            default:
                switch (userInfo.getPower()) {
                    case "1":
                        if (listObjBean.getType().equals("0")) {//已超时或已退回
                            finish.setVisibility(View.VISIBLE);//完成
                        } else if (listObjBean.getType().equals("1")) {//已延迟
                            finish.setVisibility(View.VISIBLE);
                            checkDetailsYanshi.setVisibility(View.VISIBLE);
                        } else if (listObjBean.getType().equals("-1")) {
                            finish.setVisibility(View.VISIBLE);
                            checkDetailsShenqing.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "0"://项目人员
                        if (listObjBean.getType().equals("0")) {//已超时或已退回
                            finish.setVisibility(View.VISIBLE);
                        } else if (listObjBean.getType().equals("1")) {//延时
                            finish.setVisibility(View.VISIBLE);
                            checkDetailsYanshi.setVisibility(View.VISIBLE);
                        } else if (listObjBean.getType().equals("-1")) {
                            finish.setVisibility(View.VISIBLE);
                            checkDetailsShenqing.setVisibility(View.VISIBLE);
//                            checkDetailsJingli.setVisibility(View.VISIBLE);
                        }
                        break;

                    default:
                        if (listObjBean.getType().equals("0")) {//已超时或已退回
                            finish.setVisibility(View.VISIBLE);
                        } else if (listObjBean.getType().equals("1")) {//延时
                            finish.setVisibility(View.VISIBLE);
                            checkDetailsYanshi.setVisibility(View.VISIBLE);
                        } else if (listObjBean.getType().equals("-1")) {//超时
                            finish.setVisibility(View.VISIBLE);
                            checkDetailsShenqing.setVisibility(View.VISIBLE);
                            checkDetailsPaifa.setVisibility(View.VISIBLE);
//                            checkDetailsJingli.setVisibility(View.VISIBLE);
                        }
                        break;
                }
                break;
        }
    }

    private void getProcessDetail() {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("sessionId", SharedPreferencesUtils.init(this).getString("quality_sessionId"));
        map.put("userId", MyApplicaption.getUserId());
        map.put("processId", listObjBean.getId());
        map.put("taskId", listObjBean.getTaskId());
        map.put("type", "1");
        presenter.getProcessDetail(map);
    }

    @Override
    public void getProcessDetailOnNext(ProcessDetailBean info) {
        if (info != null) {
            objBean = info.getObj();
            sponsor.setText(objBean.getHandUserName() == null || objBean.getHandUserName() == "" ? getResources().getString(R.string.no) : objBean.getHandUserName().toString().trim());
            project.setText(objBean.getProName() == null || objBean.getProName() == "" ? getResources().getString(R.string.no) : objBean.getProName().toString().trim());
            type.setText(objBean.getSceneName() == null || objBean.getSceneName() == "" ? getResources().getString(R.string.no) : objBean.getSceneName().toString().trim());
            area.setText(objBean.getAreaName() == null || objBean.getAreaName() == "" ? getResources().getString(R.string.no) : objBean.getAreaName().toString().trim());
            time.setText(objBean.getHandTime() == null || objBean.getHandTime() == "" ? getResources().getString(R.string.no) : objBean.getHandTime().toString().trim());
            tv_check_details_room.setText(listObjBean.getRoomNum());
            gridPictureAdatper = new GridAdatper(this, pictureList, this);
            //
            pictureList.clear();
            for (String imageUrl : objBean.getSupervisePotoadd().split("\\|")) {
                pictureList.add(imageUrl);
            }

            pictureLists.clear();
            for (String imageUrl : objBean.getSupervisePotoadds().split("\\|")) {
                pictureLists.add(imageUrl);
            }
            if (pictureLists != null && pictureLists.size() > 0) {
                myGridView.setAdapter(gridPictureAdatper);
                gridPictureAdatper.setData(pictureLists);
                myGridView.setVisibility(View.VISIBLE);
            }

            if (objBean.getSuperviseDescribe() == null || objBean.getSuperviseDescribe().toString().trim().equals("")) {
                tv_checkdetails_problem.setVisibility(View.GONE);
            } else {
                tv_checkdetails_problem.setVisibility(View.VISIBLE);
                tv_checkdetails_problem.setText(listObjBean.getDescribe());
            }

            if (objBean.getAudioAdd() == null || objBean.getAudioAdd().toString().trim() == "") {
                voiceLayout.setVisibility(View.GONE);
            } else {
                voiceTime.setText(listObjBean.getAudiolen() + "″");
                voiceFileName = objBean.getAudioAdd().toString().trim();
                voiceLayout.setVisibility(View.VISIBLE);
            }

            loadLayout.setStatus(LoadingLayout.Success);
        } else {
            loadLayout.setStatus(LoadingLayout.Empty);
        }
    }

    @Override
    public void getProcessDetailonError(String code, String msg) {
       com.blankj.utilcode.util.ToastUtils.showShort(msg);
        CommonlyUtils.errorState(MyApplicaption.getInstance(), code);
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onReload(View v) {
        CommonlyUtils.setLoadConnected(loadLayout, CheckDetailsActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewProcessNode://查看流程节点
                Intent intent = new Intent(this, RectProcessActivity.class);
                intent.putExtra("listObjBean", listObjBean);
                intent.putExtra("objBeanDetail", objBean);
                startActivity(intent);
                break;
            case R.id.callPhone:  //电话被点击
                RadioUtil.closeRadio(this, voiceAnim);
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ToastUtils.showLongToast(getResources().getString(R.string.please_open_cell_permision));
                } else {
                    if (objBean == null) {
                        break;
                    }
                    callPhoneWindows = new CustomPopWindows(this);
                    callPhoneWindows.setContentView(View.inflate(this, R.layout.deletepicwindows, null));
                    View deletePicView = callPhoneWindows.getContentView();
                    titleContent = (TextView) deletePicView.findViewById(R.id.titleContent);
                    cancleDelete = (TextView) deletePicView.findViewById(R.id.cancleDelete);
                    sureDelete = (TextView) deletePicView.findViewById(R.id.sureDelete);
                    if (objBean.getHandUserName() != null || objBean.getHandUserName().toString().trim() != "") {
                        titleContent.setText(getResources().getString(R.string.call) + objBean.getHandUserName() + getResources().getString(R.string.peoplephone));
                    }
                    callPhoneWindows.showAtLocation(rootView, Gravity.CENTER, 0, 0);
                    cancleDelete.setOnClickListener(this);
                    sureDelete.setOnClickListener(this);
                }
                break;
            case R.id.cancleDelete:
                callPhoneWindows.dismiss();
                break;
            case R.id.sureDelete:
                String phoneNum = "tel:" + objBean.getHandUserPhone().toString().trim();
                Intent callPhoneIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNum));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callPhoneIntent);
                callPhoneWindows.dismiss();
                break;

            case R.id.voiceLayout:
                Log.e("32s", "voiceLayout");
                playRadio();
                break;
        }
    }

    private void playRadio() {
        if (flag == false) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ToastUtils.showLongToast(getResources().getString(R.string.please_open_record_permision));
            } else {
                flag = true;
                if (voiceAnim == null) {
                    voiceAnim = findViewById(R.id.voiceAnim);
                }
                voiceAnim.setBackgroundResource(R.drawable.v_anim3);
                RadioUtil.openRadio(this, voiceAnim, voiceFileName, this);
            }
        } else {
            flag = false;
            RadioUtil.closeRadio(this, voiceAnim);
        }
    }

    //获取几个Fragment的控件
    private void initFragment() {
        //完成整改
        finishFragment = new FinishFragment();
        //延迟申请
        delayFragment = new DelayFragment();
        //工单派发
        workFragment = new WorkFragment();
        //报告经理
        seriousFragment = new SeriousFragment();
        //延迟日志
        reportFragment = new ReportFragment();
    }

    private int currPoint = 0;

    @Override
    public void onSaveBackClick() {
        RadioUtil.closeRadio(this, voiceAnim);
        switch (currPoint) {
            case 0:
                finishFragment.checkFinishData();
                break;
            case 1:
                delayFragment.checkDelayData();
                break;
            case 2:
                reportFragment.checkFinishData();
                break;
            case 3:
                workFragment.checkDelayData();
                break;
            case 4:
                seriousFragment.checkDelayData();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        switch (checkedId) {
            case R.id.finish:
                TencentCountUtil.count(this, "finish");
                currPoint = 0;
                if (finishFragment == null) {
                    finishFragment = new FinishFragment();
                }
                transaction.replace(R.id.checkDetails_fragment, finishFragment);
                transaction.commit();
                break;
            case R.id.checkDetails_yanshi:
                TencentCountUtil.count(this, "checkDetails_yanshi");
                currPoint = 2;
                if (reportFragment == null) {
                    reportFragment = new ReportFragment();
                }
                transaction.replace(R.id.checkDetails_fragment, reportFragment);
                transaction.commit();
                break;

            case R.id.checkDetails_shenqing:
                TencentCountUtil.count(this, "checkDetails_shenqing");
                currPoint = 1;
                if (delayFragment == null) {
                    delayFragment = new DelayFragment();
                }
                transaction.replace(R.id.checkDetails_fragment, delayFragment);
                transaction.commit();
                break;

            case R.id.checkDetails_paifa:
                TencentCountUtil.count(this, "checkDetails_paifa");
                currPoint = 3;
                if (workFragment == null) {
                    workFragment = new WorkFragment();
                }
                transaction.replace(R.id.checkDetails_fragment, workFragment);
                transaction.commit();
                break;

            case R.id.checkDetails_jingli:
                TencentCountUtil.count(this, "checkDetails_jingli");
                currPoint = 4;
                if (seriousFragment == null) {
                    seriousFragment = new SeriousFragment();
                }
                transaction.replace(R.id.checkDetails_fragment, seriousFragment);
                transaction.commit();
                break;
        }
    }

    @Override
    public CheckDetailsActivityPresenter initPresenter() {
        return new CheckDetailsActivityPresenter(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("32s", "onResume");
        RadioUtil.closeRadio(this, voiceAnim);
        StatService.onResume(CheckDetailsActivity.this);
        StatService.trackBeginPage(CheckDetailsActivity.this, getResources().getString(R.string.check_deal));
    }

    @Override
    protected void onPause() {
        super.onPause();
        RadioUtil.closeRadio(this, voiceAnim);
        StatService.onPause(CheckDetailsActivity.this);
        StatService.trackEndPage(CheckDetailsActivity.this, getResources().getString(R.string.check_deal));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RadioUtil.closeRadio(this, voiceAnim);
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
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, getResources().getString(R.string.please_open_cell_permision), Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }


    @Override
    public void onRadioPlayOver(boolean isPlay) {
        flag = isPlay;
        RadioUtil.closeRadio(this, voiceAnim);
    }

    @Override
    public void enlargeImage(int picAddress) {
        ImagePagerActivity.startActivity(this, pictureList, picAddress);
    }
}