package com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.saas.report.bean.KeyPointBean;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;
import com.jinke.housekeeper.saas.report.presenter.KeyPointsActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.widget.NavigationView;
import com.jinke.housekeeper.saas.report.ui.widget.RecycleViewDivider;
import com.jinke.housekeeper.saas.report.util.NetWorksUtils;
import com.jinke.housekeeper.saas.report.view.KeyPointsActivityView;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.ACache;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * Created by 32 on 2016/12/21.
 */

public class KeyPointsActivity extends BaseActivity<KeyPointsActivityView, KeyPointsActivityPresenter> implements
        NavigationView.OnNacigationTitleCallback,
        LoadingLayout.OnReloadListener,
        ExpandableListView.OnGroupClickListener,
        KeyPointsActivityView {
    @Bind(R.id.expandableView)
    RecyclerView rvRegisterDepartment;

    @Bind(R.id.titleBar)
    NavigationView titleBar;
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    @Bind(R.id.search_input)
    EditText searchInput;

    private List<KeyPointBean.ListObjBean> keyPointList = new ArrayList<>();
    private int page = 1;
    private ACache aCache;
    private KeyPointBean keyPointBean;
    private String projectId;
    private BaseQuickAdapter<KeyPointBean.ListObjBean, BaseViewHolder> adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_keypoints;
    }

    @Override
    protected void initView() {
        titleBar.setTitle(getResources().getString(R.string.keypoints));
        titleBar.setOnNavigationCallback(this);
        searchInput.setHint("输入报事位置");
        aCache = ACache.get(this);
        projectId = getIntent().getStringExtra("projectId");
        keyPointBean = (KeyPointBean) aCache.getAsObject("KeyPointBean");
        loadLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Loading : LoadingLayout.Loading);
        loadLayout.setOnReloadListener(this);
        rvRegisterDepartment.setLayoutManager(new LinearLayoutManager(this));
        rvRegisterDepartment.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.home_line)));

        adapter = new BaseQuickAdapter<KeyPointBean.ListObjBean, BaseViewHolder>(R.layout.item_keypoint, keyPointList) {
            @Override
            protected void convert(BaseViewHolder helper, KeyPointBean.ListObjBean item) {
                TextView tv_item_department = (TextView) helper.itemView.findViewById(R.id.tv_item_registerdepartment);
                tv_item_department.setText(item.getName());


            }
        };
        rvRegisterDepartment.setAdapter(adapter);

        searchInput.addTextChangedListener(textWatcher);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                KeyPointBean.ListObjBean bean = (KeyPointBean.ListObjBean) adapter.getItem(position);
                if (bean.getType().equals("1")) {
                    RegisterDepartmentBean.ListObjBean pointsBean = new RegisterDepartmentBean.ListObjBean();
                    pointsBean.setName(bean.getName());
                    pointsBean.setId(bean.getId());
                    CommonlyUtils.pointsBean = pointsBean;
                    finish();
                } else {
                    //判断是否是最后一级菜单
                    keyPointList.clear();
                    page = 1;
                    parentId = bean.getId();
                    getGJContentData(page);
                    loadLayout.setStatus(LoadingLayout.Loading);
                }

            }
        });

        if (NetWorksUtils.isConnected(this)) {
            getGJContentData(page);
        } else {
            if (keyPointBean == null) {
                loadLayout.setStatus(LoadingLayout.No_Network);
                return;
            }
            loadLayout.setStatus(LoadingLayout.Success);
            keyPointList.addAll(keyPointBean.getListObj());
        }
    }

    @Override
    public KeyPointsActivityPresenter initPresenter() {
        return new KeyPointsActivityPresenter(this);
    }

    public static int CACHINGTIME = 60 * 100 * 60;
    private String parentId = "";

    private void getGJContentData(int page) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("pageInfo", CommonlyUtils.pageInfo(page));
        map.put("key", searchInput.getText().toString().trim().length() > 0 ? searchInput.getText().toString().trim() : "");
        map.put("userId", MyApplicaption.getUserId());
        map.put("parentId", parentId);
        map.put("sessionId", MyApplicaption.getSessionId());
        map.put("projectId", projectId);
        map.put("thirdParty", "0");
        presenter.getGJContentData(map);
    }

    @Override
    public void getGJContentDataOnNext(KeyPointBean info) {
        if (info != null) {
            aCache.put("KeyPointBean", info, CACHINGTIME);
            keyPointList.addAll(info.getListObj());
            adapter.notifyDataSetChanged();
        }
        loadLayout.setStatus(keyPointList.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
    }

    @Override
    public void getGJContentDataOnError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(KeyPointsActivity.this, code);
    }

    @Override
    public void onBackClick() {
        finish();
    }


    @Override
    public void onReload(View v) {
        page = 1;
        CommonlyUtils.setLoadConnected(loadLayout, KeyPointsActivity.this);
        getGJContentData(page);
    }


    private void searchInput() {
        if (searchInput.getText().toString().trim().length() >= 0) {
            page = 1;
            keyPointList.clear();
            getGJContentData(page);
        } else {
            ToastUtils.showShort("请输入关键字搜索");
        }
    }

    //自动搜索
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
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(KeyPointsActivity.this);
        StatService.trackBeginPage(KeyPointsActivity.this, "选择关键点位");
    }


    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(KeyPointsActivity.this);
        StatService.trackEndPage(KeyPointsActivity.this, "选择关键点位");
    }


}
