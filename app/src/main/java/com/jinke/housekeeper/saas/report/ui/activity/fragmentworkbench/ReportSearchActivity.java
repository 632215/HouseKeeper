package com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.saas.report.presenter.ReportSearchActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.adapter.CountViewPageAdapter;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.report.util.TextUtils;
import com.jinke.housekeeper.saas.report.view.ReportSearchActivityView;
import com.jinke.housekeeper.saas.report.ui.fragment.activtiysearch.InquireAllFragment;
import com.jinke.housekeeper.saas.report.ui.fragment.activtiysearch.InquireFilterFragment;
import com.jinke.housekeeper.saas.report.ui.fragment.activtiysearch.InquireTodayFragment;
import com.jinke.housekeeper.saas.report.ui.fragment.activtiysearch.InquireYesterdayFragment;
import com.jinke.housekeeper.saas.report.ui.widget.MyPopWindows;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.jinke.housekeeper.saas.report.util.MediaPlayerManager;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 全部提交报事
 * Created by Administrator on 2017/3/29.
 */
public class ReportSearchActivity extends BaseActivity<ReportSearchActivityView,ReportSearchActivityPresenter> implements
        View.OnClickListener,
        ViewPager.OnPageChangeListener,
        RadioGroup.OnCheckedChangeListener,
        ReportSearchActivityView{
    @Bind(R.id.activity_report_search_text_address)
    TextView address;
    @Bind(R.id.radiogroup)
    RadioGroup radiogroup;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.filterHasRectified)
    RadioButton filterHasRectified;
    CountViewPageAdapter adapter;
    List<Fragment> list = new ArrayList<>();
    private MyPopWindows myPopWindows;
    private InquireAllFragment allFragment = new InquireAllFragment();
    private InquireTodayFragment todayFragment = new InquireTodayFragment();
    private InquireYesterdayFragment yesdayFragment = new InquireYesterdayFragment();
    private InquireFilterFragment filterFragment = new InquireFilterFragment();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_reportsearch;
    }

    @Override
    protected void initView() {
        MyApplicaption.isSelf = "2";
        TextUtils.setText(address,getString(R.string.activity_report_search_address),"#414141",CommonlyUtils.getUserInfo(this).getLeftOrgName());
        initPage();
    }

    private void initPage() {
        radiogroup.check(R.id.allHasRectified);
        viewPager.setOnPageChangeListener(this);
        radiogroup.setOnCheckedChangeListener(this);
        list.clear();
        list.add(allFragment);
        list.add(todayFragment);
        list.add(yesdayFragment);
        list.add(filterFragment);
        adapter = new CountViewPageAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        myPopWindows = new MyPopWindows(this);
        LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View v = mLayoutInflater.inflate(R.layout.activity_reportsearch, null);
        v.setFocusableInTouchMode(true);
        v.setOnKeyListener(new android.view.View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (myPopWindows.isShowing()) {
                    myPopWindows.dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.customtitle_back,R.id.filterHasRectified,R.id.map,R.id.activity_report_search_layout_address})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customtitle_back:
                finish();
                break;
            case R.id.filterHasRectified:
                showPopuWindow();
                break;
            case R.id.map:
                count("map");
                startActivity(new Intent(this,MapActivity.class));
                break;
            case R.id.activity_report_search_layout_address:
//                Intent intents = new Intent();
//                intents.setClass(this, RegisterFirmActivity.class);
//                intents.putExtra("firmSelect", "1");
//                startActivity(intents);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (myPopWindows != null) {
            myPopWindows.dismiss();
        }
        MediaPlayerManager.release();
        switch (checkedId) {
            case R.id.allHasRectified:
                viewPager.setCurrentItem(0);
                break;
            case R.id.todayHasRectified:
                viewPager.setCurrentItem(1);
                break;
            case R.id.yesterdayHasRectified:
                viewPager.setCurrentItem(2);
                break;
            case R.id.filterHasRectified:
                viewPager.setCurrentItem(3);
                showPopuWindow();
                break;
        }
    }

    private void showPopuWindow() {
        try{
            if (myPopWindows != null && myPopWindows.isShowing()) {
                myPopWindows.dismiss();
            } else {
                myPopWindows.setIsAll("1");
                myPopWindows.setContentView(View.inflate(this, R.layout.filterpopwindows, null));
                myPopWindows.setFocusable(true);
                myPopWindows.setBackgroundDrawable(new BitmapDrawable());
                myPopWindows.setOutsideTouchable(true);
                myPopWindows.setWidth(radiogroup.getWidth());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    myPopWindows.showAsDropDown(radiogroup, 0, 2, Gravity.BOTTOM);
                }else {
                    myPopWindows.showAsDropDown(radiogroup, 0, 2);
                }
            }
            setAngleUp();
        }
        catch (ArithmeticException e){
            StatService.reportException(this, e);
        }
    }

    private void setAngleUp() {
        Drawable angle = getResources().getDrawable(R.drawable.angleup);
        angle.setBounds(0, 0, angle.getMinimumWidth(), angle.getMinimumHeight());
        filterHasRectified.setCompoundDrawables(angle, null, null, null);
    }

    private void setAngleDown() {
        Drawable angle = getResources().getDrawable(R.drawable.angledown);
        angle.setBounds(0, 0, angle.getMinimumWidth(), angle.getMinimumHeight());
        filterHasRectified.setCompoundDrawables(angle, null, null, null);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                count("allHasRectified");
                radiogroup.check(R.id.allHasRectified);
                setAngleDown();
                break;
            case 1:
                count("todayHasRectified");
                radiogroup.check(R.id.todayHasRectified);
                setAngleDown();
                break;
            case 2:
                count("yesterdayHasRectified");
                radiogroup.check(R.id.yesterdayHasRectified);
                setAngleDown();
                break;
            case 3:
                count("filterHasRectified");
                radiogroup.check(R.id.filterHasRectified);
                showPopuWindow();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MediaPlayerManager.release();
    }

    @Override
    public ReportSearchActivityPresenter initPresenter() {
        return new ReportSearchActivityPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(ReportSearchActivity.this);
        StatService.trackBeginPage(ReportSearchActivity.this, "全部事件事件提交");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(ReportSearchActivity.this);
        StatService.trackEndPage(ReportSearchActivity.this, "全部事件事件提交");
    }

    private void count(String reportRegistration) {
        // 统计按钮被点击次数，统计对象：OK按钮
        Properties prop = new Properties();
        prop.setProperty("name", reportRegistration);
        StatService.trackCustomKVEvent(this, "ReportSearchActivity"+reportRegistration+"_click", prop);
    }


}