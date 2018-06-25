package com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.report.ui.adapter.QualityInspectionAdapter;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.saas.report.bean.TodayChangeInfo;
import com.jinke.housekeeper.saas.report.bean.TodayWorkInfo;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.saas.report.presenter.QualityInspectionActivityPresenter;
import com.jinke.housekeeper.saas.report.util.TextUtils;
import com.jinke.housekeeper.saas.report.view.QualityInspectionActivityView;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentmsg.ReportDealActivity;
import com.jinke.housekeeper.saas.report.ui.widget.MagicProgressCircle;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.tencent.stat.StatService;

import java.util.Calendar;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/28.
 * 新品质巡检
 */

public class QualityInspectionActivity extends BaseActivity implements
        QualityInspectionAdapter.InspectionOnClickListener {
    @Bind(R.id.activity_quality_inspection_recyclerview)
    RecyclerView recyclerView;
    private QualityInspectionAdapter adapter;
    private UserInfo userInfo;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_quality_inspection;
    }

    @Override
    protected void initView() {
        userInfo = CommonlyUtils.getUserInfo(this);
        initRecycler();
    }

    @Override
    public QualityInspectionActivityPresenter initPresenter() {
        return new QualityInspectionActivityPresenter(this);
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new QualityInspectionAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);
    }

    @Override
    public void onInspectionItemClick(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, ReportDealActivity.class));//巡检处理
                count("巡检处理");
                break;

            case 1:
                startActivity(new Intent(this, ReportSearchActivity.class));//巡检查询
                count("巡检查询");
                break;
            case 2:
                Intent commit = new Intent(this, ReportCommitActivity.class);//我提交报事
                commit.putExtra("isSelf", "1");//isSelf  判断是否是自检历史界面   0为历史整改1为自检历史2为全部
                startActivity(commit);
                count("我提交报事");
                break;

            case 3://离线上传
                startActivity(new Intent(this, FileUploadingActivity.class));
                count("离线上传");
                break;
        }
    }

    @OnClick({R.id.activity_quality_inspection_text_reback})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_quality_inspection_text_reback:
                finish();
                break;
        }
    }

    private void count(String qualityInspection) {
        // 统计按钮被点击次数，统计对象：OK按钮
        Properties prop = new Properties();
        prop.setProperty("name", qualityInspection);
        StatService.trackCustomKVEvent(this, "QualityInspectionActivity" + qualityInspection + "_click", prop);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (CommonlyUtils.bean != null) {
            UserInfo info = CommonlyUtils.getUserInfo(this);
            //判断总公司
            if (info.getRoleType().equals("3")) {
                info.setOrgCodel(CommonlyUtils.bean.getId());
                info.setOrgName(CommonlyUtils.bean.getName());
                CommonlyUtils.saveUserInfo(this, info);
            }
        }
    }

}
