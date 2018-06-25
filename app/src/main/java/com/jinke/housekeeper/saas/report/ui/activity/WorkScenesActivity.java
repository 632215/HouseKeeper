package com.jinke.housekeeper.saas.report.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;
import com.jinke.housekeeper.saas.patrol.base.BaseActivity;
import com.jinke.housekeeper.saas.patrol.base.BasePresenter;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.ui.activity.RegisterDepartmentActivity;
import com.jinke.housekeeper.ui.widget.RecycleViewDivider;
import com.jinke.housekeeper.utils.CommonlyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * Created by root on 18-5-3.
 * 工单派发
 */

public class WorkScenesActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener{
    @Bind(R.id.recycler_view)
    RecyclerView recycler_view;
    List<RegisterDepartmentBean.ListObjBean> list = new ArrayList<>();
    BaseQuickAdapter<RegisterDepartmentBean.ListObjBean, BaseViewHolder> adapter;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_work_scenes;
    }

    @Override
    protected void initView() {
        getCJContentParent();
        setTitle(getString(R.string.Department));
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.home_line)));

        adapter = new BaseQuickAdapter<RegisterDepartmentBean.ListObjBean, BaseViewHolder>(R.layout.item_work_scenes, list) {
            @Override
            protected void convert(BaseViewHolder helper, RegisterDepartmentBean.ListObjBean item) {
                TextView tv_item_department = (TextView) helper.itemView.findViewById(R.id.tv_item_department);
                tv_item_department.setText(item.getName());
            }
        };
        adapter.setOnItemClickListener(this);
        recycler_view.setAdapter(adapter);
    }

    public void getCJContentParent() {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("pageInfo", CommonlyUtils.pageInfo(1));
        map.put("thirdParty", "0");
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<RegisterDepartmentBean>() {
            @Override
            public void onNext(RegisterDepartmentBean info) {
                list.addAll(info.getListObj());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String Code, String Msg) {
                ToastUtils.showShort(Msg);
            }
        };
        HttpMethods.getInstance().getCJContentParent(new ProgressSubscriber<HttpResult<RegisterDepartmentBean>>(onNextListener, this, true), map, ReportConfig.createSign(map));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //获取分类
        CommonlyUtils.listObjBean= (RegisterDepartmentBean.ListObjBean) adapter.getItem(position);
        finish();
    }
}
