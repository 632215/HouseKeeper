package com.jinke.housekeeper.ui.activity.fragmentkonwledge;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.adapter.LibAllAdapter;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.bean.LibAllInfo;
import com.jinke.housekeeper.presenter.LibAllActivityPresenter;
import com.jinke.housekeeper.ui.widget.CustomGridView;
import com.jinke.housekeeper.view.LibAllActivityView;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;
import www.jinke.com.library.utils.NetWorksUtils;
import www.jinke.com.library.widget.NavigationView;

import com.jinke.housekeeper.saas.report.util.KeyBoardUtils;


/**
 * Created by 32 on 2017/1/5.
 */

public class LibAllActivity extends BaseActivity<LibAllActivityView, LibAllActivityPresenter> implements
        LibAllAdapter.OnItemClickLitener,
        View.OnClickListener
        , LoadingLayout.OnReloadListener,
        PullToRefreshBase.OnRefreshListener2<ScrollView>
        , NavigationView.OnNacigationTitleCallback,
        LibAllActivityView {
    @Bind(R.id.loadLayout)
    com.jinke.housekeeper.ui.widget.LoadingLayout loadLayout;
    @Bind(R.id.scrollview)
    PullToRefreshScrollView scrollview;
    @Bind(R.id.search_input)
    EditText searchInput;
    private LibAllAdapter allAdapter;
    @Bind(R.id.titleBar)
    NavigationView title;
    private String sessionId;

    @Bind(R.id.rv_liball)
    CustomGridView rvLibAll;
    private String id = "";
    private int page = 1;
    private boolean isShow = false;
    private List<LibAllInfo.ListObjBean> arrayListLibAll = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_liball;
    }

    @Override
    protected void initView() {
        title.setTitle(getResources().getString(R.string.libpage));
        title.setOnNavigationCallback(this);
        searchInput.setHint("请输入关键字搜索");
        loadLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Loading : LoadingLayout.No_Network);
        scrollview.setMode(PullToRefreshBase.Mode.BOTH);
        scrollview.setOnRefreshListener(this);
        scrollview.setOnClickListener(this);
        loadLayout.setOnReloadListener(this);
        title.setOnNavigationCallback(this);
        initDate();
        searchInput.addTextChangedListener(textWatcher);
    }

    @Override
    public LibAllActivityPresenter initPresenter() {
        return new LibAllActivityPresenter(this);
    }

    private void initDate() {
        id = getIntent().getStringExtra("id");
        sessionId = getIntent().getStringExtra("sessionId");
        userId = getIntent().getStringExtra("userId");
        allAdapter = new LibAllAdapter(arrayListLibAll, this);
        allAdapter.setOnItemClickLitener(this);
        rvLibAll.setAdapter(allAdapter);
        getScenePage(page);
    }

    private String userId;


    public void getScenePage(int page) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("pageInfo", CommonlyUtils.pageInfo(page));
        if (searchInput.getText().toString().trim() != null) {
            map.put("query", searchInput.getText().toString().trim());
        }
        map.put("sceneId", id);
        map.put("userId", userId);
        map.put("sessionId", sessionId);
        presenter.getScenePage(map);
    }

    @Override
    public void getScenePageonNext(LibAllInfo info) {
        if (info != null) {
            arrayListLibAll.addAll(info.getListObj());
            allAdapter.setData(arrayListLibAll);
        }
        loadLayout.setStatus(arrayListLibAll.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
        scrollview.onRefreshComplete();
    }

    @Override
    public void getScenePageonError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(LibAllActivity.this, code);
    }

    @Override
    public void onItemClick(String keyId) {
        Intent intent = new Intent(LibAllActivity.this, LibDetailsActivity.class);
        intent.putExtra("keyId", keyId);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scrollview:
                KeyBoardUtils.closeKeybord(searchInput, LibAllActivity.this);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(LibAllActivity.this);
        StatService.trackBeginPage(LibAllActivity.this, "知识库");
    }


    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(LibAllActivity.this);
        StatService.trackEndPage(LibAllActivity.this, "知识库");
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        isShow = false;
        page = 1;
        arrayListLibAll.clear();
        getScenePage(page);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page++;
        getScenePage(page);
    }

    @Override
    public void onReload(View v) {
        CommonlyUtils.setLoadConnected(loadLayout, LibAllActivity.this);
        page = 1;
        arrayListLibAll.clear();
        getScenePage(page);
    }

    private void searchInput() {
        if (searchInput.getText().toString().trim().length() >= 0) {
            page = 1;
            arrayListLibAll.clear();
            getScenePage(page);
        } else {
            ToastUtils.showShort("请输入关键字搜索");
        }
    }

    @Override
    public void onBackClick() {
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

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

}
