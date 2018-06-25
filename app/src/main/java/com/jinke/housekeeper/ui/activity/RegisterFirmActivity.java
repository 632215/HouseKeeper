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
import com.jinke.housekeeper.adapter.RegisterFirmAdapter;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.bean.RegisterProjectBean;
import com.jinke.housekeeper.bean.ScenePageInfo;
import com.jinke.housekeeper.bean.SessionInfo;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.presenter.RegisterFirmActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.widget.RecycleViewDivider;
import com.jinke.housekeeper.saas.report.util.StringUtil;
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
import butterknife.OnClick;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;
import www.jinke.com.library.utils.NetWorksUtils;

/**
 * Created by 32 on 2016/12/21.
 */

public class RegisterFirmActivity extends BaseActivity<RegisterFirmActivityView, RegisterFirmActivityPresenter> implements
        RegisterFirmAdapter.OnItemClickLitener, PullToRefreshBase.OnRefreshListener2<ScrollView>
        , LoadingLayout.OnReloadListener, RegisterFirmActivityView {
    @Bind(R.id.rv_registerFirm)
    RecyclerView rvRegisterFirm;
    @Bind(R.id.searchLayout)
    RelativeLayout searchLayout;
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    @Bind(R.id.scrollView)
    PullToRefreshScrollView scrollView;
    @Bind(R.id.search_input)
    EditText searchInput;
    private int page = 1;
    private String key = "";
    private boolean isShow = false;
    private String firmSelect = "0";//表示是否是切换项目 1 切换项目 0不切换项目
    private ACache aCache;
    private String parentId;
    private String flag;
    private List<RegisterProjectBean.ListObjBean> arrylistItemRegisterFirm = new ArrayList<>();
    private RegisterFirmAdapter registerFirmAdapter;
    private ScenePageInfo scenePageInfo;//本地缓存数据

    @Override
    protected int getContentViewId() {
        return R.layout.activity_registerfirm;
    }

    UserInfo info;

    @Override
    protected void initView() {
        aCache = ACache.get(this);
        firmSelect = getIntent().getStringExtra("firmSelect");
        parentId = getIntent().getStringExtra("parentId");
        flag = getIntent().getStringExtra("flag");
        setTitle(getResources().getString(R.string.Project));
        showBackwardView(R.string.empty, true);
        searchInput.setHint("请输入所属项目");
        loadLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Loading : LoadingLayout.Loading);
        loadLayout.setOnReloadListener(this);
        scrollView.setOnRefreshListener(this);
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        info = CommonlyUtils.getUserInfo(this);
        initBaseInfo();
        initData();
    }

    private void initBaseInfo() {
        registerFirmAdapter = new RegisterFirmAdapter(arrylistItemRegisterFirm, RegisterFirmActivity.this);
        rvRegisterFirm.setLayoutManager(new LinearLayoutManager(RegisterFirmActivity.this));
        rvRegisterFirm.addItemDecoration(new RecycleViewDivider(RegisterFirmActivity.this, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.home_line)));
        registerFirmAdapter.setOnItemClickLitener(this);
        rvRegisterFirm.setAdapter(registerFirmAdapter);
    }


    private void initData() {

        if (NetWorksUtils.isConnected(this)) {//判断是否联网，有网络则请求网络数据，无网络则加载本地缓存数据
            getXMList(page, key);
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

    public void getXMList(int page, String key) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("pageInfo", CommonlyUtils.pageInfo(page));//否
        map.put("key", key);
        if (flag.equals("4")) {
            //选择所属项目
            map.put("parentId", MyApplicaption.getUserId());
            map.put("flag", flag);
        } else {
            if (parentId != null && parentId.equals("")) {
                map.put("parentId", parentId);
            } else {
                SessionInfo userInfo = CommonlyUtils.getSessionInfo(RegisterFirmActivity.this);
                map.put("parentId", userInfo.getOrgCodel());
            }
        }
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
        CommonlyUtils.errorState(RegisterFirmActivity.this, code);
    }

    @Override
    public void onReload(View v) {
        CommonlyUtils.setLoadConnected(loadLayout, RegisterFirmActivity.this);
        page = 1;
        isShow = false;
        arrylistItemRegisterFirm.clear();
        getXMList(page, key);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        if (NetWorksUtils.isConnected(this)) {
            page = 1;
            isShow = false;
            arrylistItemRegisterFirm.clear();
            getXMList(page, key);
        } else {
            scrollView.onRefreshComplete();
        }

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        if (NetWorksUtils.isConnected(this)) {
            page++;
            isShow = false;
            getXMList(page, key);
        } else {
            scrollView.onRefreshComplete();
        }

    }

    @Override
    public void onItemClick(RegisterProjectBean.ListObjBean bean, int postion) {
        if (!bean.getType().equals("1")) {
            Intent intent = new Intent(RegisterFirmActivity.this, RegisterProjectActivity.class);
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


    @OnClick({R.id.search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                if (!StringUtil.isEmpty(searchInput.getText().toString())) {
                    page = 1;
                    arrylistItemRegisterFirm.clear();
                    getXMList(page, searchInput.getText().toString().trim());
                } else {
                    ToastUtils.showShort("请输入关键字搜索");
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(RegisterFirmActivity.this);
        StatService.trackBeginPage(RegisterFirmActivity.this, "请输入所属项目");
    }

    @Override
    public RegisterFirmActivityPresenter initPresenter() {
        return new RegisterFirmActivityPresenter(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(RegisterFirmActivity.this);
        StatService.trackEndPage(RegisterFirmActivity.this, "请输入所属项目");
    }


    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }
}
