package com.jinke.housekeeper.saas.report.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.saas.report.bean.HouseMsgBean;
import com.jinke.housekeeper.saas.report.presenter.report.RoomCheckPresent;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.util.StringUtil;
import com.jinke.housekeeper.saas.report.view.repot.IRoomCheckView;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.CommonlyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;

public class RoomActivity extends BaseActivity<IRoomCheckView, RoomCheckPresent> implements IRoomCheckView,
        PullToRefreshBase.OnRefreshListener2<ScrollView>, BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.show_list_info)
    RecyclerView rv_show_List_Info;
    @Bind(R.id.layout_root)
    LoadingLayout layout_root;
    @Bind(R.id.search_input)
    EditText search_input;
    @Bind(R.id.search)
    TextView search;
    @Bind(R.id.scrollview)
    PullToRefreshScrollView scrollview;

    private BaseQuickAdapter<HouseMsgBean.ListObjBean, BaseViewHolder> roomListAdapter;
    private List<HouseMsgBean.ListObjBean> infoList;
    private String projectId;//项目ID

    @Override
    public RoomCheckPresent initPresenter() {
        return new RoomCheckPresent(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_room;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.tvg_room_project_value));
        showBackwardView("", true);
        layout_root.setStatus(LoadingLayout.Loading);
        projectId = getIntent().getStringExtra("projectId");
        scrollview.setOnRefreshListener(this);
        scrollview.setMode(PullToRefreshBase.Mode.BOTH);
        search_input.setHint("请输入要报事的房间");
        initDate();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    private void initDate() {
        infoList = new ArrayList<>();
        rv_show_List_Info.setLayoutManager(new LinearLayoutManager(this));
        getProjectMap("", page);
        initAdapter();
    }

    @OnClick({R.id.search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                infoList.clear();
                if (StringUtil.isEmpty(search_input.getText().toString().trim())) {
                    ToastUtils.showShort("请输入关键字!");
                    return;
                }
                page = 1;
                getProjectMap(search_input.getText().toString().trim(), page);
                break;
        }
    }

    private int page = 1;

    public void getProjectMap(String houseName, int page) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("pageInfo", CommonlyUtils.pageInfo(page));//分页信息，以json字符串方式填写，具体格式参考返回示例
        map.put("userId", MyApplicaption.getUserId());//用户唯一标识
        map.put("sessionId", MyApplicaption.getSessionId());//登陆唯一标识
        map.put("projectId", projectId);//项目id
        map.put("thirdParty", "0");//0表示内部调用，1表示三方钉钉调用
        if (!StringUtils.isEmpty(houseName)) {
            map.put("houseName", houseName);//房屋名称
        }
        presenter.getHouseMsg(map);
    }


    public void initAdapter() {
        roomListAdapter = new BaseQuickAdapter<HouseMsgBean.ListObjBean, BaseViewHolder>(R.layout.adapter_room_list, infoList) {
            @Override
            protected void convert(BaseViewHolder helper, HouseMsgBean.ListObjBean item) {
                TextView room_list_name = (TextView) helper.itemView.findViewById(R.id.room_list_name);
                room_list_name.setText(item.getName());
            }
        };
        rv_show_List_Info.setAdapter(roomListAdapter);
    }

    @Override
    public void onHouseMsgBean(HouseMsgBean bean) {
        infoList.addAll(bean.getListObj());
        layout_root.setStatus(infoList.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
        roomListAdapter.notifyDataSetChanged();
        roomListAdapter.setOnItemClickListener(this);
        scrollview.onRefreshComplete();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //选择报事房号
        HouseMsgBean.ListObjBean bean = (HouseMsgBean.ListObjBean) adapter.getItem(position);
        SharedPreferencesUtils.init(this)
                .put("roomId", bean.getId())
                .put("roomName", bean.getName());
        finish();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page = 1;
        infoList.clear();
        getProjectMap(search_input.getText().toString().trim(), page);

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page++;
        getProjectMap(search_input.getText().toString().trim(), page);
    }
}
