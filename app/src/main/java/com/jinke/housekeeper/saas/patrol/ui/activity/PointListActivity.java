package com.jinke.housekeeper.saas.patrol.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.base.BaseActivity;
import com.jinke.housekeeper.saas.patrol.bean.PointListBean;
import com.jinke.housekeeper.saas.patrol.precenter.PatrolPresenter;
import com.jinke.housekeeper.saas.patrol.ui.adapter.PointListAdapter;
import com.jinke.housekeeper.saas.patrol.view.PaltrolView;
import com.jinke.housekeeper.utils.CommonlyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

import static com.jinke.housekeeper.saas.patrol.ui.activity.PatrolReplaceActivity.CALL_BACK_CODE;

/**
 * function: 点位列表
 * author: hank
 * date: 2017/11/13
 */

public class PointListActivity extends BaseActivity<PaltrolView, PatrolPresenter> implements PaltrolView {

    @Bind(R.id.point_list)
    ListView pointList;

    private PointListAdapter pointListAdapter;
    private List<PointListBean> pointListBeans;
    private Map<String, String> map = new HashMap<>();

    @Override
    public PatrolPresenter initPresenter() {
        return new PatrolPresenter();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_point_list;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.replace_point));
        showBackwardView(R.string.empty, true);
        initAdapter();
        initDate();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void getPointListBean(List<PointListBean> list) {
        if (null != list) {
            pointListBeans = list;
            pointListAdapter.setPointList(pointListBeans);
        }
    }

    @Override
    public void onError(String msg) {

    }

    private void initDate() {
        if (null != CommonlyUtils.getUserInfo(this).getLeftOrgId() && !"".equals(CommonlyUtils.getUserInfo(this).getLeftOrgId())) {
            map.put("projectId", CommonlyUtils.getUserInfo(this).getLeftOrgId());
            presenter.getPointList(map);
        } else {
            ToastUtils.showShort( "获取点位列表失败");
            finish();
        }
    }

    private void initAdapter() {
        pointListBeans = new ArrayList<>();
        pointListAdapter = new PointListAdapter(PointListActivity.this, pointListBeans);
        pointList.setAdapter(pointListAdapter);
        pointList.setOnItemClickListener(onItemClickListener);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent callBackIntent = new Intent();
            callBackIntent.putExtra("date", pointListBeans.get(position));
            setResult(CALL_BACK_CODE, callBackIntent);
            finish();
        }
    };

}
