package com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.report.ui.activity.processnode.ReturnNodeActivity;
import com.jinke.housekeeper.saas.report.ui.activity.processnode.StartProgressActivity;
import com.jinke.housekeeper.saas.report.ui.adapter.RectProcessNodeAdapter;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.saas.report.bean.ProcessDetailBean;
import com.jinke.housekeeper.saas.report.bean.ProcessNodeBean;
import com.jinke.housekeeper.saas.report.bean.WaitRectifiedBean;
import com.jinke.housekeeper.saas.report.presenter.RectProcessActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.activity.processnode.DealProblemActivity;
import com.jinke.housekeeper.saas.report.ui.activity.processnode.DelayProgressActivity;
import com.jinke.housekeeper.saas.report.ui.activity.processnode.FoundProblemActivity;
import com.jinke.housekeeper.saas.report.ui.activity.processnode.ProcessDetailActivity;
import com.jinke.housekeeper.saas.report.ui.widget.FillListView;
import com.jinke.housekeeper.saas.report.ui.widget.NavigationView;
import com.jinke.housekeeper.saas.report.util.MediaPlayerManager;
import com.jinke.housekeeper.saas.report.util.NetWorksUtils;
import com.jinke.housekeeper.saas.report.util.RadioUtil;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.util.StringUtil;
import com.jinke.housekeeper.saas.report.util.ToastUtils;
import com.jinke.housekeeper.saas.report.view.RectProcessActivityView;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;

/**
 * 巡检流程信息
 * Created by pc on 2017/3/7.
 */

public class RectProcessActivity extends BaseActivity<RectProcessActivityView, RectProcessActivityPresenter> implements
        NavigationView.OnNacigationTitleCallback,
        View.OnClickListener,
        LoadingLayout.OnReloadListener,
        PullToRefreshBase.OnRefreshListener2<ScrollView>,
        RectProcessActivityView, RadioUtil.RadioPlayListener,
        AdapterView.OnItemClickListener {
    @Bind(R.id.titleBar)
    NavigationView titleBar;
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    @Bind(R.id.scrollview_rpa)
    PullToRefreshScrollView scrollview;
    @Bind(R.id.listView)
    FillListView listView;
    @Bind(R.id.sponer)
    TextView sponer;
    @Bind(R.id.scene)
    TextView scene;
    @Bind(R.id.keyPoint)
    TextView keyPoint;
    @Bind(R.id.tv_checkdetails_problem)
    TextView tv_checkdetails_problem;
    @Bind(R.id.voiceLayout)
    RelativeLayout voiceLayout;
    @Bind(R.id.voiceAnim)
    View voiceAnim;
    @Bind(R.id.voiceTime)
    TextView voiceTime;
    @Bind(R.id.tv_check_details_room)
    TextView tv_check_details_room;
    private Drawable drawable; //音频动画图片
    private AnimationDrawable animation;//音频动画
    private boolean flag = false;//表示音频是否打开

    private List<ProcessNodeBean.ObjBean> list = new ArrayList<>();
    private RectProcessNodeAdapter adapter;
    private ProcessNodeBean.ObjBean nodeInfosBean;
    private WaitRectifiedBean.ListObjBean listObjBean; //接收上个页面传来的数据类
    private WaitRectifiedBean.ListObjBean rectifiedBean; //接收已整改数据类
    private WaitRectifiedBean.ListObjBean waitBean; //接收待整改数据类
    private ProcessDetailBean.ObjBean objBeanDetail;//节点详情

    @Override
    protected int getContentViewId() {
        return R.layout.activity_rectprocess;
    }

    @Override
    protected void initView() {
        checkCallPermission();
        titleBar.setTitle("巡检流程");
        titleBar.setOnNavigationCallback(this);
        loadLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Loading : LoadingLayout.No_Network);
        scrollview.setMode(PullToRefreshBase.Mode.BOTH);

        scrollview.setOnRefreshListener(this);
        scrollview.setOnClickListener(this);
        loadLayout.setOnReloadListener(this);
        listObjBean = new WaitRectifiedBean.ListObjBean();
        rectifiedBean = (WaitRectifiedBean.ListObjBean) getIntent().getSerializableExtra("listObjBean");
        waitBean = (WaitRectifiedBean.ListObjBean) getIntent().getSerializableExtra("waitBean");


        //流程节点信息上端信息
        objBeanDetail = (ProcessDetailBean.ObjBean) getIntent().getSerializableExtra("objBeanDetail");
        initData();
        voiceLayout.setOnClickListener(this);
        if (rectifiedBean != null) {
            listObjBean = rectifiedBean;
        } else if (waitBean != null) {
            listObjBean = waitBean;
        }
        adapter = new RectProcessNodeAdapter(RectProcessActivity.this, list);
        listView.setAdapter(adapter);
        getDetailsData();
        listView.setOnItemClickListener(this);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    }

    private void initData() {
        if (objBeanDetail != null) {
            sponer.setText(StringUtils.isEmpty(objBeanDetail.getHandUserName()) ? "暂无" : objBeanDetail.getHandUserName());
            scene.setText(StringUtils.isEmpty(objBeanDetail.getSceneName()) ? "暂无" : objBeanDetail.getSceneName());
            keyPoint.setText(StringUtils.isEmpty(objBeanDetail.getAreaName()) ? "暂无" : objBeanDetail.getAreaName());

            tv_check_details_room.setText(StringUtil.isEmpty(objBeanDetail.getRoomNum()) ? "暂无" : objBeanDetail.getRoomNum());
            tv_checkdetails_problem.setVisibility(View.VISIBLE);
            if (objBeanDetail.getSuperviseDescribe() == null || objBeanDetail.getSuperviseDescribe().equals("")) {
                tv_checkdetails_problem.setVisibility(View.GONE);
                if (objBeanDetail.getAudioAdd() == null || objBeanDetail.getAudioAdd().toString().trim() == "" || objBeanDetail.getAudioAddLen() == 0) {
                    tv_checkdetails_problem.setVisibility(View.VISIBLE);
                    tv_checkdetails_problem.setText("暂无");
                    voiceLayout.setVisibility(View.GONE);
                } else {
                    voiceLayout.setVisibility(View.VISIBLE);
                    voiceTime.setText(objBeanDetail.getAudioAddLen() + "″");
                    voiceLayout.setVisibility(View.VISIBLE);
                }
            } else {
                tv_checkdetails_problem.setVisibility(View.VISIBLE);
                tv_checkdetails_problem.setText(objBeanDetail.getSuperviseDescribe());
            }
        }
    }

    @Override
    public void onBackClick() {
        finish();
    }


    public void getDetailsData() {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", SharedPreferencesUtils.init(this).getString("quality_sessionId"));
        map.put("processId", listObjBean.getId());
        presenter.getDetailsData(map);
    }

    @Override
    public void getDetailsDataonNext(ProcessNodeBean processNodeBean) {
        if (processNodeBean != null) {
            list.addAll(processNodeBean.getObj());
            adapter.setData(list);
        }
        loadLayout.setStatus(list.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
        scrollview.onRefreshComplete();
    }

    @Override
    public void getDetailsDataonError(String code, String msg) {
        com.blankj.utilcode.util.ToastUtils.showShort(msg);
        CommonlyUtils.errorState(RectProcessActivity.this, code);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                ToastUtils.showLongToast(getResources().getString(R.string.please_open_record_permision));
            } else {
                flag = true;
                if (voiceAnim == null) {
                    voiceAnim = findViewById(R.id.voiceAnim);
                }
                voiceAnim.setBackgroundResource(R.drawable.v_anim3);
                RadioUtil.openRadio(this, voiceAnim, objBeanDetail.getAudioAdd(), this);
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
        StatService.onPause(RectProcessActivity.this);
        StatService.trackEndPage(RectProcessActivity.this, "巡检流程");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaPlayerManager.release();
    }

    @Override
    public RectProcessActivityPresenter initPresenter() {
        return new RectProcessActivityPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaPlayerManager.release();
        if (voiceAnim == null) {
            voiceAnim = findViewById(R.id.voiceAnim);
        }
        voiceAnim.setBackgroundResource(R.drawable.v_anim3);
        StatService.onResume(RectProcessActivity.this);
        StatService.trackBeginPage(RectProcessActivity.this, "巡检流程");
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        list.clear();
        initView();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        list.clear();
        initView();
    }

    @Override
    public void onReload(View v) {
        CommonlyUtils.setLoadConnected(loadLayout, RectProcessActivity.this);
        list.clear();
        getDetailsData();
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
                    Toast.makeText(this, "请在应用管理中打开“电话”访问权限！", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    @Override
    public void onRadioPlayOver(boolean isPlay) {
        flag = isPlay;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        nodeInfosBean = list.get(position);
        Intent intent = new Intent();
        switch (nodeInfosBean.getType()) {
            case "1":
                //问题发起
                intent.setClass(RectProcessActivity.this, FoundProblemActivity.class);
                intent.putExtra("nodeInfosBean", nodeInfosBean);
                startActivity(intent);
                break;
            case "2":
                intent.setClass(RectProcessActivity.this, DelayProgressActivity.class);
                intent.putExtra("nodeInfosBean", nodeInfosBean);
                startActivity(intent);
                break;
            case "3":
//                        intent.setClass(RectProcessActivity.this, ProcessDetailActivity.class);
//                        break;
            case "4":
                intent.setClass(RectProcessActivity.this, ProcessDetailActivity.class);
                intent.putExtra("nodeInfosBean", nodeInfosBean);
                startActivity(intent);
                break;
            case "5":
//                        intent.setClass(RectProcessActivity.this, DealProblemActivity.class);
//                        break;
            case "6":
                intent.setClass(RectProcessActivity.this, DealProblemActivity.class);
                intent.putExtra("nodeInfosBean", nodeInfosBean);

                startActivity(intent);
                break;
            //回访　
            case "10":
                intent.setClass(RectProcessActivity.this, ReturnNodeActivity.class);
                intent.putExtra("nodeInfosBean", nodeInfosBean);
                intent.putExtra("objBeanDetail", objBeanDetail);
                startActivity(intent);
                break;
            //抢单
            case "11":
                intent.setClass(RectProcessActivity.this, StartProgressActivity.class);
                ProcessNodeBean.ObjBean bean = list.get(0);
                intent.putExtra("bean", bean);
                intent.putExtra("objBeanDetail", objBeanDetail);
                intent.putExtra("nodeInfosBean", nodeInfosBean);
                startActivity(intent);
                break;
        }

    }
}

