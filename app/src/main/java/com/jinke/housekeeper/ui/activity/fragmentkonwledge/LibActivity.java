package com.jinke.housekeeper.ui.activity.fragmentkonwledge;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.adapter.LibStandardsAdapter;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.bean.KnowledgeInfo;
import com.jinke.housekeeper.presenter.LibActivityPresenter;
import com.jinke.housekeeper.ui.widget.RecycleViewDivider;
import com.jinke.housekeeper.view.LibActivityView;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.CommonlyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import www.jinke.com.library.widget.NavigationView;

/**
 * Created by Administrator on 2017/8/29.
 */

public class LibActivity extends BaseActivity<LibActivityView, LibActivityPresenter> implements
        LoadingLayout.OnReloadListener,
        NavigationView.OnNacigationTitleCallback,
        LibActivityView {
    @Bind(R.id.activity_lib_recyclerview)
    RecyclerView libRecyclerView;
    @Bind(R.id.activity_lib_navigationview)
    NavigationView titleBar;
    private LibStandardsAdapter libAdapter;
    private List<KnowledgeInfo.ListObjBean> list = new ArrayList<>();

    @Override
    public LibActivityPresenter initPresenter() {
        return new LibActivityPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_lib;
    }

    @Override
    protected void initView() {
        initTitle();
        initData();
        getLore();
    }

    private void initData() {
        libAdapter = new LibStandardsAdapter(list, this);
        libRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        libRecyclerView.addItemDecoration(new RecycleViewDivider(this,
                LinearLayoutManager.HORIZONTAL, 0, getResources().getColor(R.color.activity_main_default_bg_color)));
        libRecyclerView.setAdapter(libAdapter);
        libAdapter.setOnItemClickLitener(new LibStandardsAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(String id) {
                Intent intent = new Intent(LibActivity.this, LibAllActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("sessionId", MyApplicaption.getSessionId());
                intent.putExtra("userId", MyApplicaption.getUserId());
                startActivity(intent);
            }
        });
    }


    private void initTitle() {
        titleBar.setTitle(getResources().getString(R.string.libpage));
        titleBar.setBackVisible(View.VISIBLE);
        titleBar.setOnNavigationCallback(this);

    }

    public void getLore() {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("sessionId", MyApplicaption.getSessionId());
        map.put("userId", MyApplicaption.getUserId());
        presenter.getLore(map);
    }

    @Override
    public void getLoreonError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(LibActivity.this, code);
    }

    @Override
    public void getLoreonNext(KnowledgeInfo info) {
        list.clear();
        list.addAll(info.getListObj());
        libAdapter.setData(list);
    }

    @Override
    public void onReload(View v) {
        getLore();
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

}
