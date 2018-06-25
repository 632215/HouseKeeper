package com.jinke.housekeeper.ui.activity.fragmentcount;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.adapter.RegisterDepartmentAdapter;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;
import com.jinke.housekeeper.presenter.DepartmentSelectActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.widget.NavigationView;
import com.jinke.housekeeper.saas.report.util.NetWorksUtils;
import com.jinke.housekeeper.view.DepartmentSelectActivityView;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.ACache;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.jinke.housekeeper.saas.report.ui.widget.RecycleViewDivider;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;

/**
 * Created by 32 on 2016/12/21.
 */

public class DepartmentSelectActivity extends BaseActivity<DepartmentSelectActivityView, DepartmentSelectActivityPresenter>
        implements NavigationView.OnNacigationTitleCallback,
        PullToRefreshBase.OnRefreshListener2<ScrollView>,
        LoadingLayout.OnReloadListener,
        RegisterDepartmentAdapter.OnItemClickLitener,
        View.OnClickListener,
        DepartmentSelectActivityView {
    @Bind(R.id.tv_all)
    TextView tv_all;
    @Bind(R.id.rv_registerDepartment)
    RecyclerView rvRegisterDepartment;

    @Bind(R.id.titleBar)
    NavigationView titleBar;
    @Bind(R.id.scrollView)
    PullToRefreshScrollView scrollView;
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;

    private List<RegisterDepartmentBean.ListObjBean> arrylistItemRegisterDepartment;
    private RegisterDepartmentAdapter registerDepartmentAdapter;
    private int page = 1;
    ACache aCache;
    RegisterDepartmentBean registerDepartmentBean;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_departmentselect;
    }

    @Override
    protected void initView() {
        titleBar.setTitle(getResources().getString(R.string.Department));
        titleBar.setOnNavigationCallback(this);
        aCache = ACache.get(DepartmentSelectActivity.this);
        registerDepartmentBean = (RegisterDepartmentBean) aCache.getAsObject("DepartmentBean");
        arrylistItemRegisterDepartment = new ArrayList<>();
        loadLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Loading : LoadingLayout.Loading);
        loadLayout.setOnReloadListener(this);
        scrollView.setOnRefreshListener(this);
        tv_all.setOnClickListener(this);
        registerDepartmentAdapter = new RegisterDepartmentAdapter(arrylistItemRegisterDepartment, DepartmentSelectActivity.this);
        rvRegisterDepartment.setLayoutManager(new LinearLayoutManager(DepartmentSelectActivity.this));
        rvRegisterDepartment.addItemDecoration(new RecycleViewDivider(DepartmentSelectActivity.this, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.home_line)));

        registerDepartmentAdapter.setOnItemClickLitener(this);
        scrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        rvRegisterDepartment.setAdapter(registerDepartmentAdapter);

        if (NetWorksUtils.isConnected(this)) {
            getCJContentData(page);
        } else {
            if (registerDepartmentBean == null) {
                loadLayout.setStatus(LoadingLayout.No_Network);
                return;
            }
            loadLayout.setStatus(LoadingLayout.Success);
            arrylistItemRegisterDepartment.addAll(registerDepartmentBean.getListObj());
            registerDepartmentAdapter.notifyDataSetChanged();
        }
    }

    public static int CACHINGTIME = 60 * 100 * 60;

    public void getCJContentData(int page) {
        SortedMap<String, String> map = new TreeMap<>();
        presenter.getCJContentData(map);
    }

    @Override
    public void getCJContentDataonNext(RegisterDepartmentBean info) {
        if (info != null) {
            aCache.put("DepartmentBean", info, CACHINGTIME);
            arrylistItemRegisterDepartment.addAll(info.getListObj());
            registerDepartmentAdapter.notifyDataSetChanged();
        }
        scrollView.onRefreshComplete();
        loadLayout.setStatus(arrylistItemRegisterDepartment.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
    }

    @Override
    public void getCJContentDataonError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(DepartmentSelectActivity.this, code);
    }

    @Override
    public void onBackClick() {
        finish();
    }


    @Override
    public void onItemClick(RegisterDepartmentBean.ListObjBean bean) {
        CommonlyUtils.listObjBean = bean;
        finish();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page = 1;
        arrylistItemRegisterDepartment.clear();
        getCJContentData(page);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page++;
        getCJContentData(page);
    }

    @Override
    public void onReload(View v) {
        page = 1;
        CommonlyUtils.setLoadConnected(loadLayout, DepartmentSelectActivity.this);
        getCJContentData(page);
    }

    @Override
    public void onClick(View v) {
        RegisterDepartmentBean.ListObjBean bean = new RegisterDepartmentBean.ListObjBean();
        bean.setName("所有类别");
        bean.setId("");
        bean.setSelecter(false);
        finish();
        CommonlyUtils.listObjBean = bean;
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(DepartmentSelectActivity.this);
        StatService.trackBeginPage(DepartmentSelectActivity.this, "请选择所属部门");
    }

    @Override
    public DepartmentSelectActivityPresenter initPresenter() {
        return new DepartmentSelectActivityPresenter(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(DepartmentSelectActivity.this);
        StatService.trackEndPage(DepartmentSelectActivity.this, "请选择所属部门");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

}
