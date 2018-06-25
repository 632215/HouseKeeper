package com.jinke.housekeeper.ui.activity;

import android.content.Intent;
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
import com.jinke.housekeeper.adapter.RegisterProjectAdapter;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.bean.RegisterProjectBean;
import com.jinke.housekeeper.bean.ScenePageInfo;
import com.jinke.housekeeper.bean.SessionInfo;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.presenter.RegisterProjectActivityPresenter;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.ACache;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.jinke.housekeeper.saas.report.ui.widget.RecycleViewDivider;
import com.jinke.housekeeper.view.RegisterProjectActivityView;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;
import www.jinke.com.library.utils.NetWorksUtils;

/**
 * Created by 32 on 2016/12/21.
 */

public class RegisterProjectActivity extends BaseActivity<RegisterProjectActivityView, RegisterProjectActivityPresenter> implements
        PullToRefreshBase.OnRefreshListener2<ScrollView>, LoadingLayout.OnReloadListener, RegisterProjectAdapter.OnItemClickLitener,
        RegisterProjectActivityView {
    @Bind(R.id.rv_registerProject)
    RecyclerView rvRegisterProject;
    @Bind(R.id.searchLayout)
    RelativeLayout searchLayout;
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    @Bind(R.id.scrollView)
    PullToRefreshScrollView scrollView;
    @Bind(R.id.search_input)
    EditText searchInput;

    private List<RegisterProjectBean.ListObjBean> arrylistItemRegisterProject = new ArrayList<>();
    private RegisterProjectAdapter registerProjectAdapter;
    private String cityId = "";
    private int page = 1;
    private String key = "";
    private boolean isShow = false;
    private ACache aCache;
    private ScenePageInfo scenePageInfo;//本地缓存数据
    private int postion;
    private String flag;
    private String firmSelect = "0";//表示是否是切换项目
    private UserInfo info;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_registerproject;
    }

    @Override
    protected void initView() {
        info = CommonlyUtils.getUserInfo(this);
        aCache = ACache.get(this);
        scenePageInfo = (ScenePageInfo) aCache.getAsObject("ScenePage");
        firmSelect = getIntent().getStringExtra("firmSelect");
        postion = getIntent().getIntExtra("postion", 0);
        flag = getIntent().getStringExtra("flag");
        setTitle(getResources().getString(R.string.Project));
        showBackwardView(R.string.empty, true);
        cityId = getIntent().getStringExtra("id");
        searchInput.setHint("请输入所属项目");
        loadLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Loading : LoadingLayout.Loading);
        loadLayout.setOnReloadListener(this);
        scrollView.setOnRefreshListener(this);
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        initBaseInfo();
        initData();
        searchInput.addTextChangedListener(textWatcher);
    }

    private void initBaseInfo() {
        registerProjectAdapter = new RegisterProjectAdapter(arrylistItemRegisterProject, RegisterProjectActivity.this);
        rvRegisterProject.setLayoutManager(new LinearLayoutManager(RegisterProjectActivity.this));
        rvRegisterProject.addItemDecoration(new RecycleViewDivider(RegisterProjectActivity.this, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.home_line)));
        registerProjectAdapter.setOnItemClickLitener(this);
        rvRegisterProject.setAdapter(registerProjectAdapter);
    }

    private void initData() {
        if (flag.equals("3")){
            searchLayout.setVisibility(View.GONE);
        }
        if (NetWorksUtils.isConnected(this)) {
            getXMList(cityId, page, key);
        } else {
            if (scenePageInfo == null) {
                loadLayout.setStatus(LoadingLayout.No_Network);
                return;
            }
            for (ScenePageInfo.ObjBean.ListObjBeanX listObjBeanX : scenePageInfo.getObj().get(postion).getListObj()) {
                RegisterProjectBean.ListObjBean listObjBean = new RegisterProjectBean.ListObjBean();
                listObjBean.setType("0");
                listObjBean.setName(listObjBeanX.getText());
                listObjBean.setId(listObjBeanX.getId());
                arrylistItemRegisterProject.add(listObjBean);
            }
            loadLayout.setStatus(LoadingLayout.Success);
            registerProjectAdapter.setData(arrylistItemRegisterProject);
        }
    }

    public void getXMList(String cityId, int page, String key) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("key", key);//否
        map.put("parentId", cityId);
        map.put("pageInfo", CommonlyUtils.pageInfo(page));
        presenter.getXMList(map);
    }

    @Override
    public void getXMListonNext(RegisterProjectBean info) {
        if (info != null) {
            arrylistItemRegisterProject.addAll(info.getListObj());
            registerProjectAdapter.setData(arrylistItemRegisterProject);
        }
        scrollView.onRefreshComplete();
        loadLayout.setStatus(arrylistItemRegisterProject.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
    }

    @Override
    public void getXMListonError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(RegisterProjectActivity.this, code);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void onReload(View v) {
        page = 1;
        isShow = false;
        arrylistItemRegisterProject.clear();
        getXMList(cityId, page, key);
    }

    @Override
    public void onItemClick(RegisterProjectBean.ListObjBean bean, int postion) {
        if (!bean.getType().equals("1")) {
            Intent intent = new Intent(RegisterProjectActivity.this, RegisterProjectDetailedActivity.class);
            intent.putExtra("id", bean.getId());
            intent.putExtra("postion", postion);
            intent.putExtra("flag", flag);
            if (firmSelect != null && firmSelect.equals("1")) {
                intent.putExtra("firmSelect", firmSelect);
            }
            startActivity(intent);
            finish();
        } else {
            if (firmSelect != null && firmSelect.equals("1")) {
                CommonlyUtils.beanHome = bean;
                CommonlyUtils.bean = bean;
                UserInfo userInfo = CommonlyUtils.getUserInfo(this);
                userInfo.setLeftOrgId(bean.getId());
                userInfo.setLeftOrgName(bean.getName());
                CommonlyUtils.saveUserInfo(this, userInfo);
                CommonlyUtils.inspectionStatisticsByMonth = bean;
                CommonlyUtils.inspectProblemByMonth = bean;
                CommonlyUtils.inspectProblemByPoint = bean;
            } else {
                CommonlyUtils.bean = bean;
            }
            finish();
        }

    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        if (NetWorksUtils.isConnected(this)) {
            page = 1;
            arrylistItemRegisterProject.clear();
            isShow = false;
            getXMList(cityId, page, key);
        } else {
            scrollView.onRefreshComplete();
        }

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        if (NetWorksUtils.isConnected(this)) {
            page++;
            isShow = false;
            getXMList(cityId, page, key);
        } else {
            scrollView.onRefreshComplete();
        }

    }


    private void searchInput() {
        if (searchInput.getText().toString().trim().length() >= 0) {
            page = 1;
            arrylistItemRegisterProject.clear();
            getXMList(cityId, page, searchInput.getText().toString().trim());
        } else {
            ToastUtils.showShort("请输入关键字搜索");
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(RegisterProjectActivity.this);
        StatService.trackBeginPage(RegisterProjectActivity.this, "选择所属机构");
    }

    @Override
    public RegisterProjectActivityPresenter initPresenter() {
        return new RegisterProjectActivityPresenter(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(RegisterProjectActivity.this);
        StatService.trackEndPage(RegisterProjectActivity.this, "选择所属机构");
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

}
