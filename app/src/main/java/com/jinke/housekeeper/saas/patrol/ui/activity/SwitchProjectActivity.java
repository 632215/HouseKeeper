package com.jinke.housekeeper.saas.patrol.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.adapter.RegisterFirmAdapter;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.bean.RegisterProjectBean;
import com.jinke.housekeeper.bean.ScenePageInfo;
import com.jinke.housekeeper.bean.SessionInfo;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.presenter.RegisterFirmActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.widget.RecycleViewDivider;
import com.jinke.housekeeper.ui.activity.RegisterFirmActivity;
import com.jinke.housekeeper.ui.activity.RegisterProjectActivity;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.ACache;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.jinke.housekeeper.view.RegisterFirmActivityView;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;
import www.jinke.com.library.utils.NetWorksUtils;

public class SwitchProjectActivity extends BaseActivity<RegisterFirmActivityView, RegisterFirmActivityPresenter> implements
        RegisterFirmAdapter.OnItemClickLitener, PullToRefreshBase.OnRefreshListener2<ScrollView>
        , LoadingLayout.OnReloadListener, RegisterFirmActivityView {
    @Bind(R.id.rv_registerFirm)
    RecyclerView rvRegisterFirm;
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    @Bind(R.id.scrollView)
    PullToRefreshScrollView scrollView;
    private int page = 1;
    private ACache aCache;
    private List<RegisterProjectBean.ListObjBean> arrylistItemRegisterFirm = new ArrayList<>();
    private RegisterFirmAdapter registerFirmAdapter;
    private ScenePageInfo scenePageInfo;//本地缓存数据
    private UserInfo userInfo;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_switch_project;
    }

    UserInfo info;

    @Override
    protected void initView() {
        aCache = ACache.get(this);
        userInfo = (UserInfo) getIntent().getSerializableExtra("date");
        setTitle(getResources().getString(R.string.Project));
        showBackwardView(R.string.empty, true);
        loadLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Loading : LoadingLayout.Loading);
        loadLayout.setOnReloadListener(this);
        scrollView.setOnRefreshListener(this);
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        info = CommonlyUtils.getUserInfo(this);
        initBaseInfo();
        initData();
    }

    private void initBaseInfo() {
        registerFirmAdapter = new RegisterFirmAdapter(arrylistItemRegisterFirm, this);
        rvRegisterFirm.setLayoutManager(new LinearLayoutManager(this));
        rvRegisterFirm.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.home_line)));
        registerFirmAdapter.setOnItemClickLitener(this);
        rvRegisterFirm.setAdapter(registerFirmAdapter);
    }


    private void initData() {
        if (NetWorksUtils.isConnected(this)) {//判断是否联网，有网络则请求网络数据，无网络则加载本地缓存数据
            getXMList(page);
        } else {
            scenePageInfo = (ScenePageInfo) aCache.getAsObject("ScenePage");
            if (scenePageInfo == null) {
                loadLayout.setStatus(LoadingLayout.No_Network);
                return;
            }
            scenePageInfo = (ScenePageInfo) aCache.getAsObject("ScenePage");
            //组合数据
            for (ScenePageInfo.ObjBean bean : scenePageInfo.getObj()) {
                RegisterProjectBean.ListObjBean listObjBean = new RegisterProjectBean.ListObjBean();
                listObjBean.setId(bean.getId());
                listObjBean.setName(bean.getText());
                listObjBean.setType("0");
                arrylistItemRegisterFirm.add(listObjBean);
            }

            loadLayout.setStatus(LoadingLayout.Success);
            registerFirmAdapter.setData(arrylistItemRegisterFirm);
        }
    }

    public static int CACHINGTIME = 60 * 100 * 60;

    public void getXMList(int page) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("pageInfo", CommonlyUtils.pageInfo(page));//否
        map.put("parentId", MyApplicaption.getUserId());
        map.put("flag", "3");
        presenter.getXMList(map);
    }

    @Override
    public void getXMListonNext(RegisterProjectBean info) {
        if (info != null) {
            aCache.put("ProjectBean", info, CACHINGTIME);
            arrylistItemRegisterFirm.addAll(info.getListObj());
            registerFirmAdapter.setData(arrylistItemRegisterFirm);
        }
        scrollView.onRefreshComplete();
        loadLayout.setStatus(arrylistItemRegisterFirm.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
    }

    @Override
    public void getXMListonError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(this, code);
    }

    @Override
    public void onReload(View v) {
        CommonlyUtils.setLoadConnected(loadLayout, this);
        page = 1;
        arrylistItemRegisterFirm.clear();
        getXMList(page);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        if (NetWorksUtils.isConnected(this)) {
            page = 1;
            arrylistItemRegisterFirm.clear();
            getXMList(page);
        } else {
            scrollView.onRefreshComplete();
        }

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        if (NetWorksUtils.isConnected(this)) {
            page++;
            getXMList(page);
        } else {
            scrollView.onRefreshComplete();
        }

    }

    @Override
    public void onItemClick(RegisterProjectBean.ListObjBean bean, int postion) {
        userInfo.setLeftOrgId(bean.getId());
        userInfo.setLeftOrgName(bean.getName());
        Intent callBackIntent = new Intent();
        callBackIntent.putExtra("date", userInfo);
        setResult(9211,callBackIntent);
        finish();
    }


    @Override
    public RegisterFirmActivityPresenter initPresenter() {
        return new RegisterFirmActivityPresenter(this);
    }


    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }
}
