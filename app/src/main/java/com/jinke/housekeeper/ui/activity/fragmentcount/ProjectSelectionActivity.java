package com.jinke.housekeeper.ui.activity.fragmentcount;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.report.ui.adapter.ProjectSelectionAdapter;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.bean.RegisterProjectBean;
import com.jinke.housekeeper.bean.CountXMListInfo;
import com.jinke.housekeeper.presenter.ProjectSelectionActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.widget.NavigationView;
import com.jinke.housekeeper.saas.report.util.NetWorksUtils;
import com.jinke.housekeeper.view.ProjectSelectionActivityView;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.CommonlyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * Created by root on 17-3-15.
 */

public class ProjectSelectionActivity extends BaseActivity<ProjectSelectionActivityView, ProjectSelectionActivityPresenter>
        implements NavigationView.OnNacigationTitleCallback
        , AdapterView.OnItemClickListener
        , LoadingLayout.OnReloadListener
        , ProjectSelectionActivityView {
    @Bind(R.id.titleBar)
    NavigationView titleBar;
    @Bind(R.id.search_input)
    EditText searchInput;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    //当前用户机构ID
    private String parentId;
    //1，总部 2，分公司，3项目(从登陆获取)
    private String flag;
    private String key = "";
    private String org;//表示上个页面的第几个项目选择按钮传送过来
    List<CountXMListInfo.ListObjBean> listObj = new ArrayList<>();
    ProjectSelectionAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_projectselection;
    }

    @Override
    protected void initView() {
        initTitle();
    }

    private void initTitle() {
        titleBar.setTitle("请选择");
        titleBar.setSaveVISIBLE(View.GONE);
        titleBar.setOnNavigationCallback(this);
        searchInput.setHint("请输入所属项目");
        parentId = getIntent().getStringExtra("parentId");
        flag = getIntent().getStringExtra("flag");
        org = getIntent().getStringExtra("org");
        loadLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Loading : LoadingLayout.No_Network);
        loadLayout.setOnReloadListener(this);
        listView.setOnItemClickListener(this);
        getCountXMList(parentId, flag, key);
        searchInput.addTextChangedListener(textWatcher);
    }

    @Override
    public ProjectSelectionActivityPresenter initPresenter() {
        return new ProjectSelectionActivityPresenter(this);
    }

    public void getCountXMList(String parentId, String flag, String key) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("parentId", parentId);
        map.put("flag", flag);
        map.put("key", key);
        presenter.getCountXMList(map);
    }

    @Override
    public void getCountXMListonNext(CountXMListInfo info) {
        listObj.clear();
        listObj = info.getListObj();
        adapter = new ProjectSelectionAdapter(ProjectSelectionActivity.this, listObj);
        listView.setAdapter(adapter);
        loadLayout.setStatus(listObj.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
    }

    @Override
    public void getCountXMListonErrort(String code, String msg) {
        CommonlyUtils.errorState(ProjectSelectionActivity.this, code);
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onReload(View v) {
        CommonlyUtils.setLoadConnected(loadLayout, ProjectSelectionActivity.this);
        getCountXMList(parentId, flag, key);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CountXMListInfo.ListObjBean bean = (CountXMListInfo.ListObjBean) adapter.getItem(position);
        switch (org) {
            case "inspectionStatisticsByMonth":
                CommonlyUtils.inspectionStatisticsByMonth = new RegisterProjectBean.ListObjBean(bean.getId(), bean.getName());
                break;
            case "inspectProblemByMonth":
                CommonlyUtils.inspectProblemByMonth = new RegisterProjectBean.ListObjBean(bean.getId(), bean.getName());
                break;
            case "inspectProblemByPoint":
                CommonlyUtils.inspectProblemByPoint = new RegisterProjectBean.ListObjBean(bean.getId(), bean.getName());
                break;
        }
        CommonlyUtils.mCountXml = bean;
        finish();
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            searchInput();
        }
    };

    private void searchInput() {
        if (searchInput.getText().toString().trim().length() >= 0) {
            listObj.clear();
            key = searchInput.getText().toString().trim();
            getCountXMList(parentId, flag, key);
        } else {
            ToastUtils.showShort("请输入所属项目");
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

}
