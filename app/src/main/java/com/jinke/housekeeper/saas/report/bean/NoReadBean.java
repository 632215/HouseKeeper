package com.jinke.housekeeper.saas.report.bean;

import java.util.List;

/**
 * function: 消息列表实体
 * author: hank
 * date: 2017/8/31
 */

public class NoReadBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1
         * totalNum : 0
         */

        private String id;
        private String totalNum;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(String totalNum) {
            this.totalNum = totalNum;
        }
    }
}
