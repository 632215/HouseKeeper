package com.jinke.housekeeper.saas.patrol.bean;

import java.util.List;

/**
 * function: 打卡记录实体
 * author: hank
 * date: 2017/7/25
 */

public class PatrolRecordBean {

    /**
     * pointNum : 2
     * listData : [{"pointName":"一栋巡更点","punchTime":"2017-07-24 09:41:52"},{"pointName":"二栋巡更点","punchTime":"2017-07-24 09:51:52"}]
     */

    private int pointNum;
    private List<PatrolRecordListBean> listData;

    public int getPointNum() {
        return pointNum;
    }

    public void setPointNum(int pointNum) {
        this.pointNum = pointNum;
    }

    public List<PatrolRecordListBean> getListData() {
        return listData;
    }

    public void setListData(List<PatrolRecordListBean> listData) {
        this.listData = listData;
    }
}
