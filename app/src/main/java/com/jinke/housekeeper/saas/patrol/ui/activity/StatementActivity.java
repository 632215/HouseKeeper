package com.jinke.housekeeper.saas.patrol.ui.activity;


import android.content.Intent;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.saas.patrol.base.BaseActivity;
import com.jinke.housekeeper.saas.patrol.bean.PointDataBean;
import com.jinke.housekeeper.saas.patrol.precenter.StatementPresenter;
import com.jinke.housekeeper.saas.patrol.ui.adapter.PatrolStatementPagerAdapter;
import com.jinke.housekeeper.saas.patrol.ui.fragment.PatrolContrastiveFragment;
import com.jinke.housekeeper.saas.patrol.ui.fragment.PatrolStatCaleFragment;
import com.jinke.housekeeper.saas.patrol.ui.fragment.PatrolStatementFragment;
import com.jinke.housekeeper.saas.patrol.view.StatementView;
import com.jinke.housekeeper.utils.CommonlyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.NetWorksUtils;

import static com.tangxiaolv.telegramgallery.Utils.AndroidUtilities.showToast;

public class StatementActivity extends BaseActivity<StatementView, StatementPresenter> implements StatementView {

    @Bind(R.id.patrol_statement_corporation)
    TextView patrolStatementCorporation;
    @Bind(R.id.patrol_statement_statistics)
    TextView patrolStatementStatistics;
    @Bind(R.id.patrol_statement_plan)
    TextView patrolStatementPlan;
    @Bind(R.id.patrol_statement_omit)
    TextView patrolStatementOmit;
    @Bind(R.id.patrol_statement_point)
    TextView patrolStatementPoint;
    @Bind(R.id.patrol_statement_complete)
    TextView patrolStatementComplete;

    @Bind(R.id.patrol_statement_table)
    ViewPager patrolStatementTable;
    @Bind(R.id.patrol_statement_type)
    RadioGroup patrolStatementType;


    private UserInfo userInfo;
    private List<Fragment> fragmentList;
    private PatrolStatementFragment bookFragment;
    private PatrolStatementFragment omitBookFragment;
    private PatrolContrastiveFragment contrastiveFragment;
    private PatrolStatCaleFragment calendarBookFragment;
    private PatrolStatementPagerAdapter viewPageAdapter;


    @Override
    public StatementPresenter initPresenter() {
        return new StatementPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_statement;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.patrol_statement));
        showBackwardView(R.string.empty, true);
        userInfo = CommonlyUtils.getUserInfo(this);
        patrolStatementCorporation.setText(userInfo.getLeftOrgName());
        initNetDate(userInfo);
        initFragment();
        initPageView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    private void initPageView() {
        viewPageAdapter = new PatrolStatementPagerAdapter(getSupportFragmentManager(), fragmentList);
        patrolStatementTable.setAdapter(viewPageAdapter);
        patrolStatementTable.addOnPageChangeListener(onPageChangeListener);
        patrolStatementType.setOnCheckedChangeListener(onCheckedChangeListener);
        patrolStatementType.check(R.id.patrol_statement_book);
    }

    @OnClick({R.id.patrol_statement_corporation_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.patrol_statement_corporation_layout:
                if (NetWorksUtils.isConnected(this)) {
                    //判断是否联网，有网络则请求网络数据，无网络则加载本地缓存数据,调用大管家
                    Intent intents = new Intent();
                    intents.setClass(this, SwitchProjectActivity.class);
                    intents.putExtra("date", userInfo);
                    startActivityForResult(intents, 9211);
                } else {
                    showToast("无网络连接");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 9211:
                if (null != data) {
                    userInfo = (UserInfo) data.getSerializableExtra("date");
                    initNetDate(userInfo);
                    updatePage();
                    viewPageAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    private void updatePage() {
        Message message = new Message();
        message.what = 1;
        message.obj = userInfo;
        bookFragment.handler.sendMessage(message);
        Message message2 = new Message();
        message2.what = 1;
        message2.obj = userInfo;
        omitBookFragment.handler.sendMessage(message2);
        Message message3 = new Message();
        message3.what = 1;
        message3.obj = userInfo;
        contrastiveFragment.handler.sendMessage(message3);
        Message message4 = new Message();
        message4.what = 1;
        message4.obj = userInfo;
        calendarBookFragment.handler.sendMessage(message4);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    patrolStatementType.check(R.id.patrol_statement_book);
                    break;
                case 1:
                    patrolStatementType.check(R.id.patrol_statement_omit_book);
                    break;
                case 2:
                    patrolStatementType.check(R.id.patrol_statement_analysis);
                    break;
                case 3:
                    patrolStatementType.check(R.id.patrol_statement_calendar_book);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.patrol_statement_book:
                    patrolStatementTable.setCurrentItem(0);
                    break;
                case R.id.patrol_statement_omit_book:
                    patrolStatementTable.setCurrentItem(1);
                    break;
                case R.id.patrol_statement_analysis:
                    patrolStatementTable.setCurrentItem(2);
                    break;
                case R.id.patrol_statement_calendar_book:
                    patrolStatementTable.setCurrentItem(3);
                    break;
            }
        }
    };

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        fragmentList = new ArrayList<>();
        //巡更报表
        bookFragment = new PatrolStatementFragment();
        bookFragment.setTag(1);
        fragmentList.add(bookFragment);
        //漏检报表
        omitBookFragment = new PatrolStatementFragment();
        omitBookFragment.setTag(2);
        fragmentList.add(omitBookFragment);
        //对比分析
        contrastiveFragment = new PatrolContrastiveFragment();
        fragmentList.add(contrastiveFragment);
        //日历报表
        calendarBookFragment = new PatrolStatCaleFragment();
        fragmentList.add(calendarBookFragment);
        updatePage();
    }

    private void initNetDate(UserInfo userInfo) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", userInfo.getUserId());
        map.put("orgId", userInfo.getLeftOrgId());
        presenter.pointData(map);
    }

    @Override
    public void isStartBean(final PointDataBean dataBean) {
        if (null != dataBean) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    patrolStatementCorporation.setText(userInfo.getLeftOrgName());
                    patrolStatementStatistics.setText(dataBean.getReportNum());
                    patrolStatementPlan.setText(dataBean.getPointTotal());
                    patrolStatementOmit.setText(dataBean.getToDayLou());
                    patrolStatementPoint.setText(dataBean.getToDayPlan());
                    patrolStatementComplete.setText(dataBean.getToDayComplent());
                }
            });
        }
    }

    @Override
    public void onError(String msg) {

    }
}
