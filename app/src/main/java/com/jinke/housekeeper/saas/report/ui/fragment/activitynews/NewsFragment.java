package com.jinke.housekeeper.saas.report.ui.fragment.activitynews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.report.ui.adapter.NewListAdapter;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseFragmentV4;
import com.jinke.housekeeper.bean.EmptyBean;
import com.jinke.housekeeper.bean.MsgBean;
import com.jinke.housekeeper.saas.report.presenter.NewsFragmentPresenter;
import com.jinke.housekeeper.saas.report.ui.widget.FillListView;
import com.jinke.housekeeper.saas.report.util.NetWorksUtils;
import com.jinke.housekeeper.saas.report.view.NewsFragmentView;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.GrabSingleActivity;
import com.jinke.housekeeper.ui.activity.fragmentuser.MemberlistActivity;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentmsg.NoticeInfoActivity;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentmsg.ReportDealActivity;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.CommonlyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;

/**
 * Created by Administrator on 2017/8/28.
 */

public class NewsFragment extends BaseFragmentV4<NewsFragmentView, NewsFragmentPresenter> implements NewsFragmentView {
    @Bind(R.id.news_load_layout)
    LoadingLayout newsLoadLayout;
    @Bind(R.id.news_fragment)
    PullToRefreshScrollView newsFragment;
    @Bind(R.id.news_list_view)
    FillListView newsListView;
    private String msgType;
    private NewListAdapter adapter;
    private List<MsgBean.ListBean> msgList;
    private int page;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        newsLoadLayout.setStatus(LoadingLayout.Loading);
        newsLoadLayout.setOnReloadListener(onReloadListener);
        newsFragment.setMode(PullToRefreshBase.Mode.BOTH);
        newsFragment.setOnRefreshListener(onRefreshListener);
        msgList = new ArrayList<>();
        adapter = new NewListAdapter(getActivity(), msgList);
        newsListView.setAdapter(adapter);
        newsListView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NetWorksUtils.isConnected(getActivity())) {
            page = 1;
            initMagData();
        } else {
            if (0 == msgList.size()) {
                newsLoadLayout.setStatus(LoadingLayout.No_Network);
            }
        }
    }

    @Override
    public NewsFragmentPresenter initPresenter() {
        return new NewsFragmentPresenter(getActivity());
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if ("1".equals(msgList.get(position).getIsRead())) {
                switchActivity(position);
            } else {
                updateReadStatus(position);
            }
        }
    };

    private LoadingLayout.OnReloadListener onReloadListener = new LoadingLayout.OnReloadListener() {
        @Override
        public void onReload(View v) {
            page = 1;
            initMagData();
        }
    };

    private PullToRefreshBase.OnRefreshListener2<ScrollView> onRefreshListener = new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
            page = 1;
            initMagData();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
            initMagData();
        }
    };

    /**
     * 跳转Activity
     *
     * @param position
     */
    private void switchActivity(int position) {
        switch (msgList.get(position).getMsgTypeId()) {
            case "6":
                break;
            case "7":
                startActivity(new Intent(getActivity(), ReportDealActivity.class));
                break;
            case "8":
                startActivity(new Intent(getActivity(), ReportDealActivity.class));
                break;
            case "9":
                Intent infoIntent = new Intent(getActivity(), NoticeInfoActivity.class);
                infoIntent.putExtra("title", msgList.get(position).getTitle());
                infoIntent.putExtra("issuetime", msgList.get(position).getCreateTime());
                infoIntent.putExtra("url", msgList.get(position).getUrl());
                startActivity(infoIntent);
                break;
            case "10":
                startActivity(new Intent(getActivity(), MemberlistActivity.class));
                break;
            case "11":
                startActivity(new Intent(getActivity(), GrabSingleActivity.class));
                break;
            case "12":
                startActivity(new Intent(getActivity(), MemberlistActivity.class));
                break;
        }
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    /**
     * 获取消息列表
     */
    private void initMagData() {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", MyApplicaption.getSessionId());
        map.put("msgType", msgType);
        map.put("pageInfo", CommonlyUtils.pageInfo(page));
        presenter.initMagData(map);
    }

    @Override
    public void initMagDataNext(MsgBean info) {
        newsFragment.onRefreshComplete();
        if (null != info.getList() && 0 != info.getList().size()) {
            if (1 == page) {
                msgList.clear();
                adapter.setListBean(msgList);
            }
            page++;
            msgList.addAll(info.getList());
            adapter.setListBean(msgList);
        }
        newsLoadLayout.setStatus(msgList.size() == 0 ? LoadingLayout.Empty : LoadingLayout.Success);
    }

    @Override
    public void initMagDataError(String code, String msg) {
        newsFragment.onRefreshComplete();
        newsLoadLayout.setStatus(LoadingLayout.Error);
        showToast(msg);
        CommonlyUtils.errorState(getActivity(), code);
    }

    /**
     * 更新消息状态
     */
    private void updateReadStatus(int position) {
        final int p = position;
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", MyApplicaption.getSessionId());
        map.put("id", msgList.get(position).getId());
        presenter.updateReadStatus(map,p);
    }

    @Override
    public void updateReadStatusError(String code, String msg) {
        showToast(msg);
        CommonlyUtils.errorState(getActivity(), code);
    }

    @Override
    public void updateReadStatusNext(EmptyBean info, int p) {
        switchActivity(p);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }
}
