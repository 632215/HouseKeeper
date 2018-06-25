package com.jinke.housekeeper.saas.report.ui.fragment.avtivitycommit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.report.ui.adapter.SelfHistoryAdapter;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseFragmentV4;
import com.jinke.housekeeper.saas.report.bean.ProcessDetailBean;
import com.jinke.housekeeper.saas.report.bean.SelfHistoryBean;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.saas.report.bean.WaitRectifiedBean;
import com.jinke.housekeeper.saas.report.presenter.PersonAllFragmentPresenter;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.RectProcessActivity;
import com.jinke.housekeeper.saas.report.ui.widget.FillListView;
import com.jinke.housekeeper.saas.report.ui.widget.RecycleViewDivider;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.view.PersonAllFragmentView;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;

/**
 * Created by pc on 2017/3/15.
 */

public class PersonAllFragment extends BaseFragmentV4<PersonAllFragmentView, PersonAllFragmentPresenter> implements
        View.OnClickListener,
        OnRefreshListener, OnLoadmoreListener,
        LoadingLayout.OnReloadListener,
        PersonAllFragmentView, SelfHistoryAdapter.OnItemClickListener {
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    @Bind(R.id.scrollView)
    SmartRefreshLayout scrollView;

    @Bind(R.id.fillListView)
    RecyclerView allListView;

    private UserInfo userInfo;

    private WaitRectifiedBean.ListObjBean waitBean; //发送给流程节点整改数据类
    private ProcessDetailBean.ObjBean objBeanDetail = new ProcessDetailBean.ObjBean();//节点详情传递给流程节点上端的数据类

    private SelfHistoryAdapter selfHistoryAdapter;//自检报事
    private List<SelfHistoryBean.ListObjBean> selfHistoryList = new ArrayList();

    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        loadLayout.setStatus(isConnected ? LoadingLayout.Loading : LoadingLayout.No_Network);
        scrollView.setOnRefreshListener(this);
        scrollView.setOnClickListener(this);
        scrollView.setOnLoadmoreListener(this);
        loadLayout.setOnReloadListener(this);
        allListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        allListView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.home_line)));

        userInfo = CommonlyUtils.getUserInfo(getActivity());
        selfHistoryAdapter = new SelfHistoryAdapter(getActivity(), selfHistoryList, R.layout.item_wait_rectified);
        allListView.setAdapter(selfHistoryAdapter);
        selfHistoryAdapter.setOnItemClickListener(this);
    }

    private void getSelfHistoryList(int pageInfo) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("sessionId", SharedPreferencesUtils.init(getActivity()).getString("quality_sessionId"));
        map.put("userId", MyApplicaption.getUserId());
        map.put("pageInfo", CommonlyUtils.pageInfo(pageInfo));
        presenter.getSelfHistoryList(map);
    }


    @Override
    public void getSelfHistoryListonError(String code, String msg) {
        showToast(msg);
        CommonlyUtils.errorState(getActivity(), code);
    }

    @Override
    public void getSelfHistoryListonNext(SelfHistoryBean bean) {
        if (bean != null) {
            selfHistoryList.addAll(bean.getListObj());
            selfHistoryAdapter.setData(selfHistoryList);
        }
        loadLayout.setStatus(selfHistoryList.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onReload(View v) {
        CommonlyUtils.setLoadConnected(loadLayout, this.getActivity());
        selfHistoryList.clear();
        page = 1;
        getSelfHistoryList(page);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        refreshlayout.finishRefresh(1000);
        selfHistoryList.clear();
        page = 1;
        getSelfHistoryList(page);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        refreshlayout.finishLoadmore(1000);
        page++;
        getSelfHistoryList(page);
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        selfHistoryList.clear();
        getSelfHistoryList(page);
    }

    @Override
    public PersonAllFragmentPresenter initPresenter() {
        return new PersonAllFragmentPresenter(getActivity());
    }


    @Override
    public void onItemClick(SelfHistoryBean.ListObjBean selfHistoryBean) {
        waitBean = new WaitRectifiedBean.ListObjBean();
        waitBean.setId(String.valueOf(selfHistoryBean.getId()));//类型转化
        waitBean.setIsState(String.valueOf(selfHistoryBean.getIsState()));
        Intent intent = new Intent(getContext(), RectProcessActivity.class);
        if (selfHistoryBean != null) {
            objBeanDetail.setHandUserName(selfHistoryBean.getSuperviseName());
            objBeanDetail.setSceneName(selfHistoryBean.getSceneName());
            objBeanDetail.setAreaName(selfHistoryBean.getAreaName());
            objBeanDetail.setRoomNum(selfHistoryBean.getRoomNum());
            if (selfHistoryBean.getAudioaddress() != null || selfHistoryBean.getAudioaddress() != "") {
                objBeanDetail.setAudioAdd(selfHistoryBean.getAudioaddress());
                objBeanDetail.setAudioAddLen(selfHistoryBean.getAudiolen());

            }
            if (selfHistoryBean.getDescribe() != null || selfHistoryBean.getDescribe() != "") {
                objBeanDetail.setSuperviseDescribe(selfHistoryBean.getDescribe());

            }
        }
        intent.putExtra("objBeanDetail", objBeanDetail);
        intent.putExtra("waitBean", waitBean);
        startActivity(intent);
    }

}
