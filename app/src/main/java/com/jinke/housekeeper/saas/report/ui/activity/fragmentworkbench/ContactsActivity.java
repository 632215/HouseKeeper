package com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.adapter.RegisterDepartmentAdapter;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;
import com.jinke.housekeeper.saas.report.presenter.ContactsActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.widget.NavigationView;
import com.jinke.housekeeper.saas.report.util.NetWorksUtils;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.view.ContactsActivityView;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
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
 * 选择整改人
 * Created by root on 8/01/17.
 */

public class ContactsActivity extends BaseActivity<ContactsActivityView, ContactsActivityPresenter> implements
        NavigationView.OnNacigationTitleCallback,
        LoadingLayout.OnReloadListener,
        RegisterDepartmentAdapter.OnItemClickLitener, ContactsActivityView {

    @Bind(R.id.rv_registerDepartment)
    RecyclerView rvRegisterDepartment;

    @Bind(R.id.titleBar)
    NavigationView titleBar;
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    private String projectId;
    private String codeId;
    private String processId;

    private List<RegisterDepartmentBean.ListObjBean> arrylistItemRegisterDepartment;
    private RegisterDepartmentAdapter registerDepartmentAdapter;
    private int page = 1;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void initView() {
        titleBar.setTitle("选择整改人");
        titleBar.setOnNavigationCallback(this);
        projectId = getIntent().getStringExtra("projectId");
        codeId = getIntent().getStringExtra("codeId");
        processId = getIntent().getStringExtra("processId");
        arrylistItemRegisterDepartment = new ArrayList<>();
        loadLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Loading : LoadingLayout.No_Network);
        loadLayout.setOnReloadListener(this);
        registerDepartmentAdapter = new RegisterDepartmentAdapter(arrylistItemRegisterDepartment, ContactsActivity.this);
        rvRegisterDepartment.setLayoutManager(new LinearLayoutManager(ContactsActivity.this));
        rvRegisterDepartment.addItemDecoration(new RecycleViewDivider(ContactsActivity.this, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.home_line)));

        registerDepartmentAdapter.setOnItemClickLitener(this);
        rvRegisterDepartment.setAdapter(registerDepartmentAdapter);
        getUserListData(page);
    }


    public void getUserListData(int page) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("pageInfo", CommonlyUtils.pageInfo(page));
        map.put("orgId", projectId);
        map.put("codeId", codeId);
        map.put("processId", processId);
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", SharedPreferencesUtils.init(this).getString("quality_sessionId"));
        map.put("thirdParty", "0");
        presenter.getUserListData(map);
    }

    @Override
    public void getUserListDataOnError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(ContactsActivity.this, code);
    }

    @Override
    public void getUserListDataOnNext(RegisterDepartmentBean info) {
        if (info != null) {
            arrylistItemRegisterDepartment.addAll(info.getListObj());
            registerDepartmentAdapter.notifyDataSetChanged();
        }
        loadLayout.setStatus(arrylistItemRegisterDepartment.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
    }

    @Override
    public void onBackClick() {
        finish();
    }


    @Override
    public void onItemClick(RegisterDepartmentBean.ListObjBean bean) {
        CommonlyUtils.contactsBean = bean;
        finish();
    }


    @Override
    public void onReload(View v) {
        page = 1;
        CommonlyUtils.setLoadConnected(loadLayout, ContactsActivity.this);
        getUserListData(page);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(ContactsActivity.this);
        StatService.trackBeginPage(ContactsActivity.this, "选择整改人");
    }

    @Override
    public ContactsActivityPresenter initPresenter() {
        return new ContactsActivityPresenter(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(ContactsActivity.this);
        StatService.trackEndPage(ContactsActivity.this, "选择整改人");
    }


}
