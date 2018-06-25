package com.jinke.housekeeper.saas.patrol.ui.activity;

import android.view.View;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.base.BaseActivity;
import com.jinke.housekeeper.saas.patrol.base.BasePresenter;
import com.jinke.housekeeper.saas.patrol.bean.TimeOutTaskListBean;
import com.jinke.housekeeper.saas.patrol.ui.adapter.OutTaskDetailsAdapter;
import com.jinke.housekeeper.saas.patrol.ui.widget.CustomListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class OutTaskDetailsActivity extends BaseActivity {

    @Bind(R.id.out_task_details_layout)
    CustomListView outTaskDetailsLayout;

    private List<TimeOutTaskListBean.PointListBean> pointListBeans;
    private OutTaskDetailsAdapter outTaskDetailsAdapter;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_out_task_details;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_out_task_details));
        hindTitleLine();
        showBackwardView(R.string.empty, true);
        initDate();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    private void initDate() {
        if (null != getIntent().getSerializableExtra("date")) {
            TimeOutTaskListBean timeOutTaskListBean = (TimeOutTaskListBean) getIntent().getSerializableExtra("date");
            if (null != timeOutTaskListBean.getPointList()) {
                pointListBeans = new ArrayList<>();
                for (TimeOutTaskListBean.PointListBean bean : timeOutTaskListBean.getPointList()){
                    if ("N".equals(bean.getState())){
                        pointListBeans.add(bean);
                    }
                }
                outTaskDetailsAdapter = new OutTaskDetailsAdapter(this, pointListBeans
                        , timeOutTaskListBean.getTaskName() +"  "+ timeOutTaskListBean.getStartTime() + "-" + timeOutTaskListBean.getEndTime());
                outTaskDetailsLayout.setAdapter(outTaskDetailsAdapter);
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

}
