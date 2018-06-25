package com.jinke.housekeeper.saas.equipment.bean;

import java.io.Serializable;
import java.util.List;

/**
 * function: 当前任务列表
 * author: hank
 * date: 2017/9/22
 */

public class TaskListBean implements Serializable{

    private List<TaskBean> listData;

    public List<TaskBean> getListData() {
        return listData;
    }

    public void setListData(List<TaskBean> listData) {
        this.listData = listData;
    }
}
