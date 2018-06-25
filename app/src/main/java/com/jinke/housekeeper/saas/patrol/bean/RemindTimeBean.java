package com.jinke.housekeeper.saas.patrol.bean;

import java.util.List;

/**
 * function:
 * author: hank
 * date: 2017/8/3
 */

public class RemindTimeBean {

    private List<ListDataBean> listData;

    public List<ListDataBean> getListData() {
        return listData;
    }

    public void setListData(List<ListDataBean> listData) {
        this.listData = listData;
    }

    public static class ListDataBean {
        /**
         * endTime : 16:30
         * startTime : 16:00
         * id : 1501741821663
         */

        private String endTime;
        private String startTime;
        private String id;

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
