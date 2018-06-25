package com.jinke.housekeeper.saas.patrol.view;

import com.jinke.housekeeper.saas.patrol.bean.PointListBean;
import com.jinke.housekeeper.saas.patrol.bean.TimeOutTaskListBean;

import java.util.List;

/**
 * Created by root on 17-7-24.
 */

public interface PatrolManageView {
    void showLoading();

    void showMessage();

    void onRefreshData(List<PointListBean> list);

    void getTimeOutTask(List<TimeOutTaskListBean> list);

    void onDeletePoint();
}
