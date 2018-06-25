package com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentmsg.ReportDealActivity;
import com.jinke.housekeeper.saas.report.ui.adapter.CountViewPageAdapter;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.saas.report.presenter.GrabSingleActivityPresenter;
import com.jinke.housekeeper.saas.report.view.GrabSingleActivityView;
import com.jinke.housekeeper.saas.report.ui.fragment.GrabSingleFragment;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * function:
 * author: hank
 * date: 2017/8/11
 */

public class GrabSingleActivity extends BaseActivity<GrabSingleActivityView, GrabSingleActivityPresenter> implements
        GrabSingleActivityView, RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    @Bind(R.id.grab_single_radio_group)
    RadioGroup radioGroup;
    @Bind(R.id.grab_single_view_page)
    ViewPager grabSingleViewPage;
    private List<Fragment> fragmentList;
    //全部工单
    private GrabSingleFragment allFragment;
    private GrabSingleFragment serviceFragment;
    private GrabSingleFragment projectFragment;
    private CountViewPageAdapter viewPageAdapter;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_grab_single;
    }

    @Override
    protected void initView() {
        initFragment();
        initPageAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(GrabSingleActivity.this);
        StatService.trackBeginPage(GrabSingleActivity.this, getResources().getString(R.string.activity_grab_single_grab_work));
    }

    @Override
    public GrabSingleActivityPresenter initPresenter() {
        return new GrabSingleActivityPresenter(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(GrabSingleActivity.this);
        StatService.trackEndPage(GrabSingleActivity.this, getResources().getString(R.string.activity_grab_single_grab_work));
    }

    @OnClick({R.id.back, R.id.tv_work_order_deal})
    protected void grabSingleOnClickListener(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            //巡查处理
            case R.id.tv_work_order_deal:
                startActivity(new Intent(this, ReportDealActivity.class));
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                count("typeAll");
                radioGroup.check(R.id.grab_single_type_all);
                break;
            case 1:
                count("typeService");
                radioGroup.check(R.id.grab_single_type_service);
                break;
            case 2:
                count("typeProject");
                radioGroup.check(R.id.grab_single_type_project);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.grab_single_type_all:
                grabSingleViewPage.setCurrentItem(0);
                break;
            case R.id.grab_single_type_service:
                grabSingleViewPage.setCurrentItem(1);
                break;
            case R.id.grab_single_type_project:
                grabSingleViewPage.setCurrentItem(2);
                break;
        }
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        fragmentList = new ArrayList<>();
        //全部
        allFragment = new GrabSingleFragment();
        Bundle allBundle = new Bundle();
        allBundle.putString("reportType", "");
        allFragment.setArguments(allBundle);
        fragmentList.add(allFragment);
        //业主报事
        serviceFragment = new GrabSingleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("reportType", "120");
        serviceFragment.setArguments(bundle);
        fragmentList.add(serviceFragment);
        //内部报事
        projectFragment = new GrabSingleFragment();
        Bundle projectBundle = new Bundle();
        projectBundle.putString("reportType", "121");
        projectFragment.setArguments(projectBundle);
        fragmentList.add(projectFragment);
    }


    private void initPageAdapter() {
        //初始化
        viewPageAdapter = new CountViewPageAdapter(getSupportFragmentManager(), fragmentList);
        grabSingleViewPage.setAdapter(viewPageAdapter);
        grabSingleViewPage.addOnPageChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.check(R.id.grab_single_type_all);
    }

    private void count(String reportRegistration) {
        // 统计按钮被点击次数，统计对象：OK按钮
        Properties prop = new Properties();
        prop.setProperty("name", reportRegistration);
        StatService.trackCustomKVEvent(this, "GrabSingleActivity" + reportRegistration + "_click", prop);
    }

}
