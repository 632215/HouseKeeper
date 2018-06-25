package com.jinke.housekeeper.saas.report.ui.activity.fragmentmsg;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.saas.report.ui.adapter.CountViewPageAdapter;
import com.jinke.housekeeper.saas.report.bean.NoReadBean;
import com.jinke.housekeeper.saas.report.presenter.NewsActivityPresenter;
import com.jinke.housekeeper.saas.report.util.TencentCountUtil;
import com.jinke.housekeeper.saas.report.view.NewsActivityView;
import com.jinke.housekeeper.saas.report.ui.fragment.activitynews.NewsFragment;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/25.
 * 新消息界面
 */

public class NewsActivity extends BaseActivity<NewsActivityView, NewsActivityPresenter> implements NewsActivityView{
    @Bind(R.id.news_radio_group)
    RadioGroup newsRadioGroup;
    @Bind(R.id.news_view_page)
    ViewPager newsViewPage;
    @Bind(R.id.news_type_warming)
    RadioButton newsTypeWarming;
    @Bind(R.id.news_type_pending)
    RadioButton newsTypePending;
    @Bind(R.id.news_type_order)
    RadioButton newsTypeOrder;
    @Bind(R.id.news_type_announce)
    RadioButton newsTypeAnnounce;
    @Bind(R.id.news_type_remind)
    RadioButton newsTypeRemind;
    private CountViewPageAdapter viewPageAdapter;
    private List<Fragment> fragmentList;
    //1 警
    private final static String STATE_WARMING = "1";
    private NewsFragment warmingFragment;
    //2 超
    private final static String STATE_PENDING = "2";
    private NewsFragment pendingFragment;
    //3 工
    private final static String STATE_ORDER = "3";
    private NewsFragment orderFragment;
    //4 告
    private final static String STATE_ANNOUNCE = "4";
    private NewsFragment announceFragment;
    //5 醒
    private final static String STATE_REMIND = "5";
    private NewsFragment remindFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_news;
    }

    @Override
    protected void initView() {
        initFragmentList();
        initPageAdapter();
    }

    @Override
    public NewsActivityPresenter initPresenter() {
        return new NewsActivityPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateReadStatus();
        StatService.onResume(this);
        StatService.trackBeginPage(this, "消息");
    }


    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
        StatService.trackEndPage(this, "消息");
    }

    @OnClick({R.id.news_back})
    protected void newsOnClickListener(View view) {
        switch (view.getId()) {
            case R.id.news_back:
                finish();
                break;
        }
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    TencentCountUtil.count(NewsActivity.this,"typeWarming");
                    newsRadioGroup.check(R.id.news_type_warming);
                    break;
                case 1:
                    TencentCountUtil.count(NewsActivity.this,"typePending");
                    newsRadioGroup.check(R.id.news_type_pending);
                    break;
                case 2:
                    TencentCountUtil.count(NewsActivity.this,"typeOrder");
                    newsRadioGroup.check(R.id.news_type_order);
                    break;
                case 3:
                    TencentCountUtil.count(NewsActivity.this,"typeAnnounce");
                    newsRadioGroup.check(R.id.news_type_announce);
                    break;
                case 4:
                    TencentCountUtil.count(NewsActivity.this,"typeRemind");
                    newsRadioGroup.check(R.id.news_type_remind);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.news_type_warming:
                    newsViewPage.setCurrentItem(0);
                    break;
                case R.id.news_type_pending:
                    newsViewPage.setCurrentItem(1);
                    break;
                case R.id.news_type_order:
                    newsViewPage.setCurrentItem(2);
                    break;
                case R.id.news_type_announce:
                    newsViewPage.setCurrentItem(3);
                    break;
                case R.id.news_type_remind:
                    newsViewPage.setCurrentItem(4);
                    break;
            }
        }
    };

    private SpannableString creatText(String s, int middlePosition) {
        SpannableString styledText = new SpannableString(s);
        styledText.setSpan(new TextAppearanceSpan(this, R.style.radio_button_b), 0, middlePosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(this, R.style.radio_button_n), middlePosition, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return styledText;
    }

    private void initFragmentList() {
        fragmentList = new ArrayList<>();
        //警告
        warmingFragment = new NewsFragment();
        warmingFragment.setMsgType(STATE_WARMING);
        fragmentList.add(warmingFragment);
        //待处理
        pendingFragment = new NewsFragment();
        pendingFragment.setMsgType(STATE_PENDING);
        fragmentList.add(pendingFragment);
        //工单
        orderFragment = new NewsFragment();
        orderFragment.setMsgType(STATE_ORDER);
        fragmentList.add(orderFragment);
        //公告
        announceFragment = new NewsFragment();
        announceFragment.setMsgType(STATE_ANNOUNCE);
        fragmentList.add(announceFragment);
        //提醒
        remindFragment = new NewsFragment();
        remindFragment.setMsgType(STATE_REMIND);
        fragmentList.add(remindFragment);
    }

    private void initPageAdapter() {
        viewPageAdapter = new CountViewPageAdapter(getSupportFragmentManager(), fragmentList);
        newsViewPage.setAdapter(viewPageAdapter);
        newsViewPage.addOnPageChangeListener(onPageChangeListener);
        newsRadioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        newsRadioGroup.check(R.id.news_type_warming);
    }

    /**
     * 消息条数
     */
    private void updateReadStatus() {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", MyApplicaption.getSessionId());
        presenter.updateReadStatus(map);
    }

    @Override
    public void updateReadStatusNext(NoReadBean info) {
        for (NoReadBean.ListBean listBean : info.getList()) {
            String sizeString;
            if (99 > Integer.parseInt(listBean.getTotalNum()) && 0 < Integer.parseInt(listBean.getTotalNum())) {
                sizeString = listBean.getTotalNum();
            } else if (0 == Integer.parseInt(listBean.getTotalNum())) {
                sizeString = "";
            } else {
                sizeString = "99+";
            }
            switch (listBean.getId()) {
                case "1":
                    newsTypeWarming.setText(creatText(getString(R.string.activity_news_warming) + sizeString
                            , getString(R.string.activity_news_warming).length()), TextView.BufferType.SPANNABLE);
                    break;
                case "2":
                    newsTypePending.setText(creatText(getString(R.string.activity_news_pending) + sizeString
                            , getString(R.string.activity_news_pending).length()), TextView.BufferType.SPANNABLE);
                    break;
                case "3":
                    newsTypeOrder.setText(creatText(getString(R.string.activity_news_order) + sizeString
                            , getString(R.string.activity_news_order).length()), TextView.BufferType.SPANNABLE);
                    break;
                case "4":
                    newsTypeAnnounce.setText(creatText(getString(R.string.activity_news_announce) + sizeString
                            , getString(R.string.activity_news_announce).length()), TextView.BufferType.SPANNABLE);
                    break;
                case "5":
                    newsTypeRemind.setText(creatText(getString(R.string.activity_news_remind) + sizeString
                            , getString(R.string.activity_news_remind).length()), TextView.BufferType.SPANNABLE);
                    break;
            }
        }
    }

    @Override
    public void updateReadStatusError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(NewsActivity.this, code);
    }


}
