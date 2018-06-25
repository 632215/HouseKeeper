package com.jinke.housekeeper.ui.activity.fragmentuser;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.presenter.MemberlistActivityPresenter;
import com.jinke.housekeeper.adapter.MemberAllAdapter;
import com.jinke.housekeeper.adapter.MemberListAdapter;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.MemberListBean;
import com.jinke.housekeeper.saas.report.ui.widget.FillListView;
import com.jinke.housekeeper.saas.report.ui.widget.NavigationView;
import com.jinke.housekeeper.saas.report.util.NetWorksUtils;
import com.jinke.housekeeper.view.MemberlistActivityView;
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

/**
 * Created by 32 on 2017/1/9.
 */
public class MemberlistActivity extends BaseActivity<MemberlistActivityView, MemberlistActivityPresenter> implements
        NavigationView.OnNacigationTitleCallback
        , PullToRefreshBase.OnRefreshListener2<ScrollView>
        , LoadingLayout.OnReloadListener
        , MemberListAdapter.OnNotifGroupListener
        , MemberlistActivityView {
    @Bind(R.id.titleBar)
    NavigationView title;
    @Bind(R.id.departFillListView)
    FillListView departFillListView;
    @Bind(R.id.fillListView)
    FillListView fillListView;
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    @Bind(R.id.scrollview)
    PullToRefreshScrollView scrollview;
    private int page = 1;
    private MemberListAdapter memberListAdapter;
    private ArrayList<MemberListBean.ObjBean> arrayList;
    private List<MemberListBean.ObjBean> obj = new ArrayList<>();
    private MemberAllAdapter memberAllAdapter;//全部人员信息
    private ArrayList<MemberListBean.UserBean> memberAllList;
    private boolean isShow = false;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_memberlist;
    }

    @Override
    protected void initView() {
        initTitle();
        initAdapter();
        getMenberList(page);
    }

    @Override
    public MemberlistActivityPresenter initPresenter() {
        return new MemberlistActivityPresenter(this);
    }

    private void initTitle() {
        title.setTitle(getResources().getString(R.string.fragment_user_people_review));
        title.setOnNavigationCallback(this);
        loadLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Loading : LoadingLayout.No_Network);
        loadLayout.setOnReloadListener(this);
        scrollview.setOnRefreshListener(this);
        scrollview.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void initAdapter() {
        arrayList = new ArrayList<>();
        memberAllList = new ArrayList<>();
        memberListAdapter = new MemberListAdapter(MemberlistActivity.this, obj);
        departFillListView.setAdapter(memberListAdapter);
        memberAllAdapter = new MemberAllAdapter(MemberlistActivity.this, memberAllList);
        fillListView.setAdapter(memberAllAdapter);
        memberListAdapter.setOnNotifGroupListener(this);
        departFillListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MemberListBean.ObjBean objBean = (MemberListBean.ObjBean) memberListAdapter.getItem(position);
                Intent intent = new Intent(MemberlistActivity.this, MemberDetailsActivity.class);
                intent.putExtra("obj", objBean);
                startActivity(intent);
            }
        });
    }

    /**
     * 获取人员名单
     *
     * @param page
     */
    private void getMenberList(int page) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("pageInfo", CommonlyUtils.pageInfo(page));
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", MyApplicaption.getSessionId());
        map.put("orgId",  CommonlyUtils.getUserInfo(this).getLeftOrgId());
        presenter.getMenberList(map, isShow);
    }

    @Override
    public void getMenberListNext(MemberListBean dataBean) {
        if (dataBean != null) {
            obj.addAll(dataBean.getObj());
            memberListAdapter.setData(obj);
            memberAllList = (ArrayList<MemberListBean.UserBean>) dataBean.getUser();
            memberAllAdapter.setData(memberAllList);
        }
        loadLayout.setStatus(dataBean.getUser().size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
        scrollview.onRefreshComplete();
    }

    @Override
    public void getMenberListError(String code, String msg) {
        CommonlyUtils.errorState(MemberlistActivity.this, code);
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onReload(View v) {
        page = 1;
        CommonlyUtils.setLoadConnected(loadLayout, MemberlistActivity.this);
        getMenberList(page);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page = 1;
        isShow = false;
        obj.clear();
        arrayList.clear();
        memberAllList.clear();
        getMenberList(page);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page++;
        isShow = false;
        obj.clear();
        arrayList.clear();
        memberAllList.clear();
        getMenberList(page);
    }

    @Override
    public void notifGroup() {
        isShow = true;
        page = 1;
        obj.clear();
        arrayList.clear();
        memberAllList.clear();
        getMenberList(page);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(MemberlistActivity.this);
        StatService.trackBeginPage(MemberlistActivity.this, "人员信息列表");
    }


    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(MemberlistActivity.this);
        StatService.trackEndPage(MemberlistActivity.this, "人员信息列表");
    }


}
