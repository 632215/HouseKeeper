package com.jinke.housekeeper.ui.fragment.activitymain;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseFragmentV4;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.bean.OpenIdBean;
import com.jinke.housekeeper.presenter.WorkBenchFragmentPresenter;
import com.jinke.housekeeper.saas.equipment.ui.activity.EquipmentActivity;
import com.jinke.housekeeper.saas.handoverroom.ui.activity.HandoverRoomActivity;
import com.jinke.housekeeper.saas.patrol.ui.activity.PatrolActivity;
import com.jinke.housekeeper.saas.report.bean.SessionBean;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.ReportRegistActivity;
import com.jinke.housekeeper.saas.report.ui.adapter.CommitInspectionAdapter;
import com.jinke.housekeeper.saas.report.ui.adapter.WorkBenchCommlyAdapter;
import com.jinke.housekeeper.saas.report.ui.adapter.WorkBenchInspectionAdapter;
import com.jinke.housekeeper.saas.report.util.TencentCountUtil;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.GrabSingleActivity;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.QualityInspectionActivity;
import com.jinke.housekeeper.ui.activity.RegisterFirmActivity;
import com.jinke.housekeeper.view.WorkBenchFragmentView;
import com.jinke.housekeeper.utils.CommonlyUtils;

import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.NetWorksUtils;

/**
 * Created by Administrator on 2017/7/27.
 * 新工作台
 */

public class WorkBenchFragment extends BaseFragmentV4<WorkBenchFragmentView, WorkBenchFragmentPresenter> implements
        WorkBenchFragmentView,
        WorkBenchInspectionAdapter.InspectionOnClickListener,
        WorkBenchCommlyAdapter.OnCommlyListener, CommitInspectionAdapter.OnCommitClickListener {
    @Bind(R.id.fragment_workbench_text_title)
    TextView textTitle;
    @Bind(R.id.rv_inspection_application)
    RecyclerView inspectionRecyclerView;
    @Bind(R.id.rv_commonly_application)
    RecyclerView commonlyRecyclerView;
    @Bind(R.id.rv_commit_manager)
    RecyclerView rv_commit_manager;
    private WorkBenchInspectionAdapter inspectionAdapter;
    private WorkBenchCommlyAdapter commonlyAdapter;
    private UserInfo userInfo;
    private int state;
    CommitInspectionAdapter commitInspectionAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_workbench;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initTitle();
        initRecyclerView();
        state = -1;
    }

    @Override
    public WorkBenchFragmentPresenter initPresenter() {
        return new WorkBenchFragmentPresenter(getActivity());
    }

    private void initTitle() {
        userInfo = CommonlyUtils.getUserInfo(getActivity());
        textTitle.setText(userInfo.getLeftOrgName());
    }

    private void initRecyclerView() {
        inspectionRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        inspectionAdapter = new WorkBenchInspectionAdapter(getActivity());
        inspectionRecyclerView.setAdapter(inspectionAdapter);
        inspectionAdapter.setListener(this);

        rv_commit_manager.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        commitInspectionAdapter = new CommitInspectionAdapter(getActivity());
        rv_commit_manager.setAdapter(commitInspectionAdapter);
        commitInspectionAdapter.setListener(this);

        commonlyRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        commonlyAdapter = new WorkBenchCommlyAdapter(getActivity());
        commonlyRecyclerView.setAdapter(commonlyAdapter);
        commonlyAdapter.setListener(this);

        commitInspectionAdapter.setListener(new CommitInspectionAdapter.OnCommitClickListener() {
            @Override
            public void onCommitClick(int position) {
                ToastUtils.showShort("此功能暂未开通!");
            }
        });
    }

    int isFlag = 0;//0工单池,1品质巡检，2报事报修

    @OnClick({R.id.fragment_workbench_text_title, R.id.tv_work_bench_approval,
            R.id.tv_news_report_repair, R.id.tv_work_bench_contacts, R.id.tv_work_order_pool})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_workbench_text_title:
                if (NetWorksUtils.isConnected(getActivity())) {//判断是否联网，有网络则请求网络数据，无网络则加载本地缓存数据
                    Intent intents = new Intent();
                    intents.setClass(getActivity(), RegisterFirmActivity.class);
                    intents.putExtra("firmSelect", "1");
                    intents.putExtra("flag", "4");
                    startActivity(intents);
                    TencentCountUtil.count(getActivity(), "projectSelect");
                } else {
                    showToast("无网络连接");
                }
                break;
            //审批
            case R.id.tv_work_bench_approval:
                showToast(getString(R.string.un_realized));
                break;
            //通讯录
            case R.id.tv_work_bench_contacts:
                showToast(getString(R.string.un_realized));
                break;
            //工单池
            case R.id.tv_work_order_pool:
                state = 0;
                isFlag = 0;
                getAppOauth("SE3Brc18CG0r44QTed1A");
                break;
            //报事报修
            case R.id.tv_news_report_repair:
                isFlag = 2;
                state = 0;
                getAppOauth("SE3Brc18CG0r44QTed1A");
                break;

        }
    }

    @Override
    public void onInspectionItemClick(int position) {
        switch (position) {
            case 0://品质巡检
                state = 0;
                isFlag = 1;
                getAppOauth("SE3Brc18CG0r44QTed1A");
                break;
            case 1://巡更
                state = 1;
                getAppOauth("wGQt7hiz7MeSbQbdyBp1");
                break;
            case 2://设备巡检
                state = 2;
                getAppOauth("0cuFfjWigFVevbGNb6Zw");
                break;
            case 3://设备维保
                ToastUtils.showShort("此功能暂未开通!");
                break;
        }
    }

    @Override
    public void onCommlyItemClick(int position) {
        switch (position) {
            case 0://审批
                showToast(getString(R.string.un_realized));
                break;
            case 1://通讯录
                showToast(getString(R.string.un_realized));
                break;
        }
    }

    private void getAppOauth(String appKey) {
        if (NetWorksUtils.isConnected(getActivity())) {
            SortedMap<String, String> map = new TreeMap<>();
            map.put("userId", MyApplicaption.getUserId());
            map.put("sessionId", MyApplicaption.getSessionId());
            map.put("appKey", appKey);
            presenter.getMapPoint(map);
        } else {
            new AlertDialog.Builder(getActivity()).setTitle("提示")//设置对话框标题
                    .setMessage("网络请求失败，请检查网络再试")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }

    @Override
    public void getMapPointNext(OpenIdBean info) {
        switch (state) {
            case 0://品质巡检
                UserInfo userInfo = CommonlyUtils.getUserInfo(getActivity());
                SortedMap<String, String> map = new TreeMap<>();
                map.put("openId", info.getOpenId());
                map.put("projectId", userInfo.getLeftOrgId());
                presenter.getToken(map);
                break;
            case 1://巡更
                Intent patrolIntent = new Intent(getActivity(), PatrolActivity.class);
                patrolIntent.putExtra("date", info.getOpenId());
                startActivity(patrolIntent);
                break;
            case 2://设备巡检
                Intent equipmentIntent = new Intent(getActivity(), EquipmentActivity.class);
                equipmentIntent.putExtra("openId", info.getOpenId());
                equipmentIntent.putExtra("projectId", CommonlyUtils.getUserInfo(getActivity()).getLeftOrgId());
                equipmentIntent.putExtra("projectName", CommonlyUtils.getUserInfo(getActivity()).getLeftOrgName());
                equipmentIntent.putExtra("userName", CommonlyUtils.getUserInfo(getActivity()).getName());
                equipmentIntent.putExtra("userID", CommonlyUtils.getUserInfo(getActivity()).getUserId());
                startActivity(equipmentIntent);
                break;
            case 3:
                ToastUtils.showShort("此功能暂未开通!");
                break;
            case 4://移动验房
                Intent handoverRoomIntent = new Intent(getActivity(), HandoverRoomActivity.class);
                handoverRoomIntent.putExtra("openId", info.getOpenId());
                handoverRoomIntent.putExtra("projectId", CommonlyUtils.getUserInfo(getActivity()).getLeftOrgId());
                handoverRoomIntent.putExtra("projectName", CommonlyUtils.getUserInfo(getActivity()).getLeftOrgName());
                handoverRoomIntent.putExtra("userName", CommonlyUtils.getUserInfo(getActivity()).getName());
                handoverRoomIntent.putExtra("userID", CommonlyUtils.getUserInfo(getActivity()).getUserId());
                startActivity(handoverRoomIntent);
                break;
        }
    }

    @Override
    public void getMapPointError(String code, String msg) {
        CommonlyUtils.errorState(getActivity(), code);
    }

    @Override
    public void onQualityInspect(SessionBean info) {
        switch (isFlag) {
            case 1://品质巡检
                startActivity(new Intent(getActivity(), QualityInspectionActivity.class));
                break;
            case 2://报事报修
                Intent intent = new Intent(getActivity(), ReportRegistActivity.class);
                intent.putExtra("inspType", "122");
                startActivity(intent);
                break;
            case 0://工单池
                startActivity(new Intent(getActivity(), GrabSingleActivity.class));
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        userInfo = CommonlyUtils.getUserInfo(getActivity());
        textTitle.setText(userInfo.getLeftOrgName());
    }

    @Override
    public void onCommitClick(int position) {
        switch (position) {
            case 0:
                break;
        }
    }
}
