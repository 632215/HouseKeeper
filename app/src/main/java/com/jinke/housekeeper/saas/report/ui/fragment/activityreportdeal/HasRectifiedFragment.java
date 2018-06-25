package com.jinke.housekeeper.saas.report.ui.fragment.activityreportdeal;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseFragmentV4;
import com.jinke.housekeeper.saas.report.presenter.HasRectifiedFragmentPresenter;
import com.jinke.housekeeper.saas.report.ui.fragment.fragmenthasrectified.YesDayFragment;
import com.jinke.housekeeper.saas.report.view.HasRectifiedFragmentView;
import com.jinke.housekeeper.saas.report.ui.fragment.fragmenthasrectified.AllFragment;
import com.jinke.housekeeper.saas.report.ui.fragment.fragmenthasrectified.FilterFragment;
import com.jinke.housekeeper.saas.report.ui.fragment.fragmenthasrectified.TodayFragment;
import com.jinke.housekeeper.saas.report.ui.widget.MyPopWindows;
import com.jinke.housekeeper.saas.report.util.MediaPlayerManager;
import com.tencent.stat.StatService;
import java.util.Properties;

import butterknife.Bind;

/**
 * 我的报事
 * Created by Administrator on 2017/3/23.
 */
public class HasRectifiedFragment extends BaseFragmentV4<HasRectifiedFragmentView, HasRectifiedFragmentPresenter> implements
        RadioGroup.OnCheckedChangeListener,
        View.OnClickListener,
        HasRectifiedFragmentView {
    @Bind(R.id.radiogroup)
    RadioGroup radiogroup;
    @Bind(R.id.filterHasRectified)
    RadioButton filterHasRectified;
    FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private MyPopWindows myPopWindows;
    private AllFragment allFragment = new AllFragment();
    private TodayFragment todayFragment = new TodayFragment();
    private YesDayFragment yesdayFragment = new YesDayFragment();
    private FilterFragment filterFragment = new FilterFragment();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hasrectified;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        MyApplicaption.isSelf = "0";
        radiogroup.check(R.id.allHasRectified);
        fragmentManager = getChildFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.hasrectified, allFragment);
        transaction.commit();
        filterHasRectified.setOnClickListener(this);
        radiogroup.check(R.id.allHasRectified);
        radiogroup.setOnCheckedChangeListener(this);
        myPopWindows = new MyPopWindows(getActivity());
    }

    @Override
    public HasRectifiedFragmentPresenter initPresenter() {
        return new HasRectifiedFragmentPresenter(getActivity());
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (myPopWindows != null) {
            myPopWindows.dismiss();
        }
        MediaPlayerManager.release();
        fragmentManager = getChildFragmentManager();
        transaction = fragmentManager.beginTransaction();
        switch (checkedId) {
            case R.id.allHasRectified:
                count("AllFragment");
                if (allFragment == null) {
                    allFragment = new AllFragment();
                }
                setAngleDown();
                transaction.replace(R.id.hasrectified, allFragment);
                break;
            case R.id.todayHasRectified:
                count("TodayFragment");
                if (todayFragment == null) {
                    todayFragment = new TodayFragment();
                }
                setAngleDown();
                transaction.replace(R.id.hasrectified, todayFragment);
                break;
            case R.id.yesterdayHasRectified:
                count("YesDayFragment");
                if (yesdayFragment == null) {
                    yesdayFragment = new YesDayFragment();
                }
                setAngleDown();
                transaction.replace(R.id.hasrectified, yesdayFragment);
                break;

            case R.id.filterHasRectified:
                count("FilterFragment");
                if (filterFragment == null) {
                    filterFragment = new FilterFragment();
                }
                showPopWindows();
                setAngleUp();
                transaction.replace(R.id.hasrectified, filterFragment);
                break;
        }
        if (transaction != null) {
            transaction.commit();
        }
    }

    private void setAngleUp() {
        Drawable angle = getResources().getDrawable(R.drawable.angleup);
        angle.setBounds(0, 0, angle.getMinimumWidth(), angle.getMinimumHeight());
        filterHasRectified.setCompoundDrawables(null, null, angle, null);
    }

    private void setAngleDown() {
        Drawable angle = getResources().getDrawable(R.drawable.angledown);
        angle.setBounds(0, 0, angle.getMinimumWidth(), angle.getMinimumHeight());
        filterHasRectified.setCompoundDrawables(null, null, angle, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filterHasRectified:
                showPopWindows();
                setAngleUp();
                break;
        }
    }

    private void showPopWindows() {
        if (myPopWindows != null && myPopWindows.isShowing()) {
        } else {
            myPopWindows.setContentView(View.inflate(getActivity(), R.layout.filterpopwindows, null));
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
    public void onResume() {
        super.onResume();
        if (myPopWindows != null && myPopWindows.isShowing()) {
            myPopWindows.dismiss();
            radiogroup.check(R.id.allHasRectified);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (myPopWindows != null && myPopWindows.isShowing()) {
            myPopWindows.dismiss();
            radiogroup.check(R.id.allHasRectified);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MediaPlayerManager.release();
    }

    private void count(String reportRegistration) {
        // 统计按钮被点击次数，统计对象：OK按钮
        Properties prop = new Properties();
        prop.setProperty("name", reportRegistration);
        StatService.trackCustomKVEvent(getActivity(), "HasRectifiedFragment" + reportRegistration + "_click", prop);
    }

}