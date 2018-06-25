package com.jinke.housekeeper.saas.equipment.view;

import com.jinke.housekeeper.saas.equipment.bean.DailyPatrolBean;
import com.jinke.housekeeper.saas.equipment.bean.TaskListBean;

/**
 * function:
 * author: hank
 * date: 2017/10/11
 */

public interface DailyPatrolView {

    void getStatisticsSuccess(DailyPatrolBean dailyPatrolBean);

    void getTaskSuccess(TaskListBean taskListBean);

    void completeTaskSuccess();

    void showLoading();

    void onError(String msg);
}
