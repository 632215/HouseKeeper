package com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.saas.report.presenter.ReportCommitActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.adapter.CountViewPageAdapter;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.report.view.ReportCommitActivityView;
import com.jinke.housekeeper.saas.report.ui.fragment.avtivitycommit.PersonAllFragment;
import com.jinke.housekeeper.saas.report.ui.fragment.avtivitycommit.PersonFilterFragment;
import com.jinke.housekeeper.saas.report.ui.fragment.avtivitycommit.PersonTodayFragment;
import com.jinke.housekeeper.saas.report.ui.fragment.avtivitycommit.PersonYesterdayFragment;
import com.jinke.housekeeper.saas.report.ui.widget.MyPopWindows;
import com.jinke.housekeeper.saas.report.util.MediaPlayerManager;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 个人—— 已提交的报事自检历史
 * Created by Administrator on 2017/3/29.
 */
public class ReportCommitActivity extends BaseActivity<ReportCommitActivityView, ReportCommitActivityPresenter> implements
        View.OnClickListener,
        ViewPager.OnPageChangeListener,
        RadioGroup.OnCheckedChangeListener, ReportCommitActivityView {
    @Bind(R.id.title)//标题栏
            TextView title;
    @Bind(R.id.customtitle_back)
    ImageView customtitle_back;
    @Bind(R.id.customtitle_save)
    TextView customtitle_save;
    @Bind(R.id.radiogroup)
    RadioGroup radiogroup;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.filterHasRectified)
    RadioButton filterHasRectified;
    private CountViewPageAdapter adapter;
    private List<Fragment> list = new ArrayList<>();
    private MyPopWindows myPopWindows;
    private PersonAllFragment allFragment = new PersonAllFragment();
    private PersonTodayFragment todayFragment = new PersonTodayFragment();
    private PersonYesterdayFragment yesdayFragment = new PersonYesterdayFragment();
    private PersonFilterFragment filterFragment = new PersonFilterFragment();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_reportcommit;
    }

    @Override
    protected void initView() {
        MyApplicaption.isSelf = "1";
        initPage();
        customtitle_back.setOnClickListener(this);
        customtitle_save.setOnClickListener(this);
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
        filterHasRectified.setOnClickListener(this);
        myPopWindows = new MyPopWindows(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customtitle_back:
                finish();
                break;
            case R.id.customtitle_save:
                //我提交的报事
                Intent intent = new Intent(this, ReportRegistActivity.class);
                intent.putExtra("inspType", "122");
                startActivity(intent);
                break;
            case R.id.filterHasRectified:
                showPopWindows();
                break;
        }
    }

    private void showPopWindows() {
        if (myPopWindows != null && myPopWindows.isShowing()) {
            myPopWindows.dismiss();
        } else {
            myPopWindows.setContentView(View.inflate(this, R.layout.filterpopwindows, null));
            myPopWindows.setWidth(radiogroup.getWidth());
            myPopWindows.setOutsideTouchable(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                myPopWindows.showAsDropDown(radiogroup, 0, 2, Gravity.BOTTOM);
            } else {
                myPopWindows.showAsDropDown(radiogroup, 0, 2);
            }
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
                showPopWindows();
                break;
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
        if (myPopWindows != null) {
            myPopWindows.dismiss();
        }
        switch (position) {
            case 0:
                radiogroup.check(R.id.allHasRectified);
                setAngleDown();
                break;
            case 1:
                radiogroup.check(R.id.todayHasRectified);
                setAngleDown();
                break;
            case 2:
                radiogroup.check(R.id.yesterdayHasRectified);
                setAngleDown();
                break;
            case 3:
                radiogroup.check(R.id.filterHasRectified);
                showPopWindows();
                setAngleUp();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (myPopWindows != null && myPopWindows.isShowing()) {
            myPopWindows.dismiss();
            viewPager.setCurrentItem(0);
            radiogroup.check(R.id.allHasRectified);
        }
        StatService.onResume(ReportCommitActivity.this);
        StatService.trackBeginPage(ReportCommitActivity.this, "已提交的报事自检历史");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(ReportCommitActivity.this);
        StatService.trackEndPage(ReportCommitActivity.this, "已提交的报事自检历史");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MediaPlayerManager.release();
    }

    @Override
    public ReportCommitActivityPresenter initPresenter() {
        return new ReportCommitActivityPresenter(this);
    }


}