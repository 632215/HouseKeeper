package com.jinke.housekeeper.saas.equipment.bean;

/**
 * function: 巡检详情列表
 * author: hank
 * date: 2017/9/14
 */

public class DailyPatrolRecordListBean {
    private TaskBean taskBean;
    private boolean showState;//true 展开  false收起

    public TaskBean getTaskBean() {
        return taskBean;
    }

    public void setTaskBean(TaskBean taskBean) {
        this.taskBean = taskBean;
    }

    public boolean isShowState() {
        return showState;
    }

    public void setShowState(boolean showState) {
        this.showState = showState;
    }
}
