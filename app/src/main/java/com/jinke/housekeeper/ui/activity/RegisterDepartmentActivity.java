package com.jinke.housekeeper.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.adapter.RegisterDepartmentAdapter;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;
import com.jinke.housekeeper.presenter.RegisterDepartmentPresenter;
import com.jinke.housekeeper.saas.report.util.StringUtil;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.ui.widget.RecycleViewDivider;
import com.jinke.housekeeper.utils.ACache;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.jinke.housekeeper.view.RegisterDepartmentActivityView;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;
import www.jinke.com.library.utils.NetWorksUtils;

/**
 * Created by 32 on 2016/12/21.
 */

public class RegisterDepartmentActivity extends BaseActivity<RegisterDepartmentActivityView, RegisterDepartmentPresenter> implements
        PullToRefreshBase.OnRefreshListener2<ScrollView>,
        LoadingLayout.OnReloadListener, RegisterDepartmentAdapter.OnItemClickLitener, RegisterDepartmentActivityView {
    @Bind(R.id.rv_registerDepartment)
    RecyclerView rvRegisterDepartment;
    @Bind(R.id.scrollView)
    PullToRefreshScrollView scrollView;
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    @Bind(R.id.search_input)
    EditText search_input;

    private List<RegisterDepartmentBean.ListObjBean> arrylistItemRegisterDepartment;
    private RegisterDepartmentAdapter registerDepartmentAdapter;
    private int page = 1;
    private String projectId;
    ACache aCache;
    RegisterDepartmentBean registerDepartmentBean;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_registerdepartment;
    }

    @Override
    protected void initView() {
        initTitle();
        aCache = ACache.get(RegisterDepartmentActivity.this);
        registerDepartmentBean = (RegisterDepartmentBean) aCache.getAsObject("DepartmentBean");
        arrylistItemRegisterDepartment = new ArrayList<>();
        loadLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Loading : LoadingLayout.Loading);
        loadLayout.setOnReloadListener(this);
        scrollView.setOnRefreshListener(this);
        //项目id
        projectId = getIntent().getStringExtra("projectId");
        registerDepartmentAdapter = new RegisterDepartmentAdapter(arrylistItemRegisterDepartment, RegisterDepartmentActivity.this);
        rvRegisterDepartment.setLayoutManager(new LinearLayoutManager(RegisterDepartmentActivity.this));
        rvRegisterDepartment.addItemDecoration(new RecycleViewDivider(RegisterDepartmentActivity.this, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.home_line)));

        registerDepartmentAdapter.setOnItemClickLitener(this);
        scrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        rvRegisterDepartment.setAdapter(registerDepartmentAdapter);
        search_input.setHint("请输入所属类型");
        if (NetWorksUtils.isConnected(this)) {
            getCJContentData(page, "");
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

    private void initTitle() {
        setTitle(getResources().getString(R.string.Department));
        showBackwardView(R.string.empty, true);
    }

    public static int CACHINGTIME = 60 * 100 * 60;

    private String name = "";

    public void getCJContentData(int page, String parentId) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("pageInfo", CommonlyUtils.pageInfo(page));
        map.put("parentId", parentId);
        map.put("name", name);
        map.put("projectId", projectId);
        map.put("thirdParty", "0");
        presenter.getCJContentData(map);
    }

    @OnClick({R.id.search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                name = search_input.getText().toString().trim();
                if (StringUtil.isEmpty(name)) {
                    ToastUtils.showShort("请输入关键字!");
                    return;
                }
                page = 1;
                getCJContentData(page, "");
                break;
        }
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
    public void getCJContentDataOnError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(RegisterDepartmentActivity.this, code);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void onItemClick(RegisterDepartmentBean.ListObjBean bean) {
        //判断type是否为１
        if (bean.getType().equals("1")) {
            CommonlyUtils.listObjBean = bean;
            finish();
        } else {
            page = 1;
            arrylistItemRegisterDepartment.clear();
            getCJContentData(page, bean.getId());
        }

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page = 1;
        arrylistItemRegisterDepartment.clear();
        getCJContentData(page, name);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page++;
        getCJContentData(page, name);
    }

    @Override
    public void onReload(View v) {
        page = 1;
        CommonlyUtils.setLoadConnected(loadLayout, RegisterDepartmentActivity.this);
        getCJContentData(page, name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(RegisterDepartmentActivity.this);
        StatService.trackBeginPage(RegisterDepartmentActivity.this, "选择所属部门");
    }

    @Override
    public RegisterDepartmentPresenter initPresenter() {
        return new RegisterDepartmentPresenter(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(RegisterDepartmentActivity.this);
        StatService.trackEndPage(RegisterDepartmentActivity.this, "选择所属部门");
    }

}
