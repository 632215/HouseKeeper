package com.jinke.housekeeper.saas.patrol.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.base.BaseActivity;
import com.jinke.housekeeper.saas.patrol.bean.IsStartBean;
import com.jinke.housekeeper.saas.patrol.bean.PointPlanBean;
import com.jinke.housekeeper.saas.patrol.precenter.PointPlanPresenter;
import com.jinke.housekeeper.saas.patrol.ui.adapter.PointPlanListAdapter;
import com.jinke.housekeeper.saas.patrol.view.PointPlanView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class PointPlanListActivity extends BaseActivity<PointPlanView, PointPlanPresenter> implements PointPlanView {

    @Bind(R.id.point_plan_list)
    ListView pointPlanList;

    private PointPlanListAdapter pointPlanListAdapter;
    private List<PointPlanBean.ListDataBean> infoList;
    private PointPlanBean.ListDataBean pointPlanBean;

    @Override
    public PointPlanPresenter initPresenter() {
        return new PointPlanPresenter();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_point_plan_list;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_point_plan_list));
        showBackwardView(R.string.empty, true);
        initAdapter();
        initDate();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }


    private void initDate() {
        pointPlanBean = new PointPlanBean.ListDataBean();
        Map<String, String> map = new HashMap<>();
        presenter.getTaskInfo(map);
    }

    private void initAdapter() {
        infoList = new ArrayList<>();
        pointPlanListAdapter = new PointPlanListAdapter(this, infoList);
        pointPlanList.setAdapter(pointPlanListAdapter);
        pointPlanList.setOnItemClickListener(onItemClickListener);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, String> map = new HashMap<>();
            pointPlanBean = infoList.get(position);
            map.put("taskId", String.valueOf(infoList.get(position).getTaskId()));
            presenter.isStart(map);
        }
    };

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void getPointListBean(PointPlanBean pointPlanBean) {
        if (null != pointPlanBean.getListData()) {
            infoList = pointPlanBean.getListData();
            pointPlanListAdapter.setPointPlanBean(infoList);
        } else {
            Toast.makeText(this, "当前项目不存在巡更任务", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    public void isStartBean(IsStartBean isStartBean) {
        if (null != isStartBean) {
            switch (isStartBean.isIstrue()) {
                //为可执行
                case "1":
                    Intent callBackIntent = new Intent();
                    callBackIntent.putExtra("date", isStartBean);
                    callBackIntent.putExtra("dateName", pointPlanBean);
                    setResult(9211, callBackIntent);
                    finish();
                    break;
                //为任务超时
                case "2":
                    Toast.makeText(this, "当前巡更任务已超时", Toast.LENGTH_SHORT).show();
                    break;
                //为任务未开始
                case "3":
                    Toast.makeText(this, "当前巡更任务执行时间未到", Toast.LENGTH_SHORT).show();
                    break;
                //为任务已经完成
                case "4":
                    Toast.makeText(this, "当前巡更任务执行已完成", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {

        }
    }

    @Override
    public void onError(String msg) {

    }

}
