package com.jinke.housekeeper.ui.activity.fragmentuser;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SearchView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.adapter.ContactAdapter;
import com.jinke.housekeeper.adapter.FrequentContactAdapter;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.presenter.ContactActivityPresenter;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.jinke.housekeeper.view.ContactActivityView;

import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;
import www.jinke.com.library.utils.NetWorksUtils;

/**
 * Created by Administrator on 2017/9/25.
 */

public class ContactActivity extends BaseActivity<ContactActivityView, ContactActivityPresenter> implements
        LoadingLayout.OnReloadListener,
        PullToRefreshBase.OnRefreshListener2<ScrollView> {
    @Bind(R.id.activity_contacts_loadingLayout)
    LoadingLayout loadingLayout;
    @Bind(R.id.activity_contacts_scrollView)
    PullToRefreshScrollView scrollView;
    @Bind(R.id.activity_contacts_recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.activity_contacts_recycler_view_frequent)
    RecyclerView recyclerViewFrequent;
    private ContactAdapter adapter;
    private FrequentContactAdapter frequentAdapter;

    @Override
    public ContactActivityPresenter initPresenter() {
        return new ContactActivityPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_new_contacts;
    }

    @Override
    protected void initView() {
        initTitle();
        initDate();
        initLoading();
        initAdapter();
        initFrequentAdapter();
    }

    private void initDate() {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", MyApplicaption.getSessionId());
        map.put("pageInfo", CommonlyUtils.pageInfo(1));
        presenter.getMailList(map);
    }

    private void initFrequentAdapter() {
        frequentAdapter = new FrequentContactAdapter(this);
        recyclerViewFrequent.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFrequent.setAdapter(frequentAdapter);
    }

    private void initAdapter() {
        adapter = new ContactAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        loadingLayout.setStatus(LoadingLayout.Success);
    }

    private void initLoading() {
        loadingLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Loading : LoadingLayout.No_Network);
        loadingLayout.setOnReloadListener(this);
        scrollView.setOnRefreshListener(this);
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void initTitle() {
        setTitle(R.string.activity_new_contacts);
        showBackwardView(R.string.empty, true);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void onReload(View v) {

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

    }
}
