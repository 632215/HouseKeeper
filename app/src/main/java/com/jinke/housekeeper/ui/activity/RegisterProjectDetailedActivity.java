package com.jinke.housekeeper.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.adapter.RegisterProjectAdapter;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.bean.RegisterProjectBean;
import com.jinke.housekeeper.bean.ScenePageInfo;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.presenter.RegisterProjectDetailedActivityPresenter;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.ui.widget.RecycleViewDivider;
import com.jinke.housekeeper.utils.ACache;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.jinke.housekeeper.view.RegisterProjectDetailedActivityView;
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

public class RegisterProjectDetailedActivity extends BaseActivity<RegisterProjectDetailedActivityView, RegisterProjectDetailedActivityPresenter> implements
        PullToRefreshBase.OnRefreshListener2<ScrollView>, RegisterProjectDetailedActivityView,
        RegisterProjectAdapter.OnItemClickLitener, LoadingLayout.OnReloadListener {
    @Bind(R.id.rv_registerProjectDetailed)
    RecyclerView rvRegisterProjectDetail;
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    @Bind(R.id.scrollView)
    PullToRefreshScrollView scrollView;
    @Bind(R.id.search_input)
    EditText searchInput;
    private String key = "";
    private List<RegisterProjectBean.ListObjBean> arrylistItemRegisterProject = new ArrayList<>();
    private RegisterProjectAdapter registerProjectAdapter;
    private String cityId = "";
    private int page = 1;
    private boolean isShow = false;
    private ScenePageInfo scenePageInfo;//本地缓存数据
    int postion;
    ACache aCache;
    private String flag;
    private String firmSelect = "0";//表示是否是切换项目

    @Override
    protected int getContentViewId() {
        return R.layout.activity_registerprojectdetailed;
    }

    @Override
    protected void initView() {
        setTitle(getResources().getString(R.string.Project));
        showBackwardView(R.string.empty, true);
        aCache = ACache.get(this);
        scenePageInfo = (ScenePageInfo) aCache.getAsObject("ScenePage");
        firmSelect = getIntent().getStringExtra("firmSelect");
        postion = getIntent().getIntExtra("postion" , 0);
        flag = getIntent().getStringExtra("flag");
        cityId = getIntent().getStringExtra("id");
        searchInput.setHint("请输入所属项目");
        loadLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Loading : LoadingLayout.No_Network);
        loadLayout.setOnReloadListener(this);
        scrollView.setOnRefreshListener(this);
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        initData();
        searchInput.addTextChangedListener(textWatcher);
    }

    private void initData() {
        registerProjectAdapter = new RegisterProjectAdapter(arrylistItemRegisterProject, RegisterProjectDetailedActivity.this);
        rvRegisterProjectDetail.setLayoutManager(new LinearLayoutManager(RegisterProjectDetailedActivity.this));
        rvRegisterProjectDetail.addItemDecoration(new RecycleViewDivider(RegisterProjectDetailedActivity.this, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.home_line)));
        registerProjectAdapter.setOnItemClickLitener(this);
        rvRegisterProjectDetail.setAdapter(registerProjectAdapter);
        if (NetWorksUtils.isConnected(this)) {
            getXMList(cityId, page, key);
        } else {
            if (scenePageInfo == null) {
                loadLayout.setStatus(LoadingLayout.No_Network);
                return;
            }
            for (ScenePageInfo.ObjBean.ListObjBeanX.ListObjBean bean : scenePageInfo.getObj().get(0).getListObj().get(postion).getListObj()) {
                RegisterProjectBean.ListObjBean listObjBean = new RegisterProjectBean.ListObjBean();
                listObjBean.setId(bean.getId());
                listObjBean.setType("1");
                listObjBean.setName(bean.getText());
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
    public void getXMListonError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(RegisterProjectDetailedActivity.this, code);
    }

    @Override
    public void getXMListonNext(RegisterProjectBean info) {
        if (info != null) {
            arrylistItemRegisterProject.addAll(info.getListObj());
            registerProjectAdapter.setData(arrylistItemRegisterProject);
        }
        loadLayout.setStatus(arrylistItemRegisterProject.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
        scrollView.onRefreshComplete();
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
        getXMList(cityId, page, key);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        if (NetWorksUtils.isConnected(this)) {
            arrylistItemRegisterProject.clear();
            page = 1;
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

    @Override
    public void onItemClick(RegisterProjectBean.ListObjBean bean, int postion) {
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
        StatService.onResume(RegisterProjectDetailedActivity.this);
        StatService.trackBeginPage(RegisterProjectDetailedActivity.this, "所属项目");
    }

    @Override
    public RegisterProjectDetailedActivityPresenter initPresenter() {
        return new RegisterProjectDetailedActivityPresenter(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(RegisterProjectDetailedActivity.this);
        StatService.trackEndPage(RegisterProjectDetailedActivity.this, "所属项目");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

}
