package com.jinke.housekeeper.saas.report.ui.activity.fragmentmsg;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.saas.report.presenter.ReportDealActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.widget.NavigationView;
import com.jinke.housekeeper.saas.report.view.ReportDealActivityView;
import com.jinke.housekeeper.saas.report.ui.fragment.activityreportdeal.HasRectifiedFragment;
import com.jinke.housekeeper.saas.report.ui.fragment.activityreportdeal.WaitRectifiedFragment;
import com.jinke.housekeeper.saas.report.util.MediaPlayerManager;
import com.tencent.stat.StatService;

import java.util.Properties;
import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/23.
 */

public class ReportDealActivity extends BaseActivity<ReportDealActivityView, ReportDealActivityPresenter> implements
        NavigationView.OnNacigationTitleCallback,
        RadioGroup.OnCheckedChangeListener,
        ReportDealActivityView {
    @Bind(R.id.titleBar)
    NavigationView titleBar;
    @Bind(R.id.radiogroup)
    RadioGroup radiogroup;
    private FragmentManager mfragmentManager;
    private WaitRectifiedFragment waitRectifiedFragment;
    private HasRectifiedFragment hasRectifiedFragment;
    FragmentTransaction transaction;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_reportdeal;
    }

    @Override
    protected void initView() {
        titleBar.setTitle("巡检处理");
        titleBar.setOnNavigationCallback(this);
        setDefaultRadio();
        radiogroup.setOnCheckedChangeListener(this);
    }

    @Override
    public ReportDealActivityPresenter initPresenter() {
        return new ReportDealActivityPresenter(this);
    }

    private void setDefaultRadio() {
        mfragmentManager = getSupportFragmentManager();
        transaction = mfragmentManager.beginTransaction();
        if (null != getIntent().getStringExtra("date")
                && "pending".equals(getIntent().getStringExtra("date"))) {
            hasRectifiedFragment = new HasRectifiedFragment();
            transaction.replace(R.id.frameLayout, hasRectifiedFragment);
            transaction.commit();
            radiogroup.check(R.id.hasRectified);
        } else {
            waitRectifiedFragment = new WaitRectifiedFragment();
            transaction.replace(R.id.frameLayout, waitRectifiedFragment);
            transaction.commit();
            radiogroup.check(R.id.toBeRectified);
        }
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        mfragmentManager = getSupportFragmentManager();
        transaction = mfragmentManager.beginTransaction();
        MediaPlayerManager.release();
        switch (checkedId) {
            case R.id.toBeRectified:
                //如果tabFragment1为空，说明还没创建Tab1
                if (waitRectifiedFragment == null) {
                    waitRectifiedFragment = new WaitRectifiedFragment();
                }
                //如果isAdded == true 表示 tab1 已加入布局中
                if (!waitRectifiedFragment.isAdded()) {
                    transaction.add(R.id.frameLayout, waitRectifiedFragment);
                } else {
                    //如果tab2不为空，把tab2隐藏就是、
                    if (hasRectifiedFragment != null) {
                        transaction.hide(hasRectifiedFragment);
                    }
                }
                //显示tab1
                transaction.show(waitRectifiedFragment);
                break;
            case R.id.hasRectified:
                //如果tabFragment2为空，说明还没创建Tab2
                if (hasRectifiedFragment == null) {
                    hasRectifiedFragment = new HasRectifiedFragment();
                }
                //如果isAdded == true 表示 tab2 已加入布局中
                if (!hasRectifiedFragment.isAdded()) {
                    transaction.add(R.id.frameLayout, hasRectifiedFragment);
                } else {
                    //如果tab1不为空，把tab1隐藏就是、
                    if (waitRectifiedFragment != null) {
                        transaction.hide(waitRectifiedFragment);
                    }
                }
                //显示tab2
                transaction.show(hasRectifiedFragment);
                break;
        }
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(ReportDealActivity.this);
        StatService.trackBeginPage(ReportDealActivity.this, "巡检处理");
    }


    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(ReportDealActivity.this);
        StatService.trackEndPage(ReportDealActivity.this, "巡检处理");
    }

    private void count(String reportRegistration) {
        // 统计按钮被点击次数，统计对象：OK按钮
        Properties prop = new Properties();
        prop.setProperty("name", reportRegistration);
        StatService.trackCustomKVEvent(this, "ReportDealActivity" + reportRegistration + "_click", prop);
    }

}