package com.jinke.housekeeper.saas.patrol.bean;

import java.util.List;

/**
 * function: 签到返回signId实体
 * author: hank
 * date: 2017/7/25
 */

public class SignPatrolListBean {


    private List<ListDataBean> listData;

    public List<ListDataBean> getListData() {
        return listData;
    }

    public void setListData(List<ListDataBean> listData) {
        this.listData = listData;
    }

    public static class ListDataBean {
        /**
         * signId : 8a8f50345d87db1d015d87db2cc30002
         * signTime : 2017-07-24 09:41:52
         */

        private String signId;
        private String signTime;

        public String getSignId() {
            return signId;
        }

        public void setSignId(String signId) {
            this.signId = signId;
        }

        public String getSignTime() {
            return signTime;
        }

        public void setSignTime(String signTime) {
            this.signTime = signTime;
        }
    }
}
