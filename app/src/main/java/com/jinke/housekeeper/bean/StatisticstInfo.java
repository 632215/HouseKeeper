package com.jinke.housekeeper.bean;

import java.util.List;

/**
 * Created by root on 17-3-21.
 */

public class StatisticstInfo {


    private List<ListObjBean> listObj;
    private String maxValue;
    public List<ListObjBean> getListObj() {
        return listObj;
    }

    public void setListObj(List<ListObjBean> listObj) {
        this.listObj = listObj;
    }


    public float getMaxValue() {
        return Float.parseFloat(maxValue)* 2;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public static class ListObjBean {
        /**
         * id : 1
         * name : 小区出入口
         * findNum : 0
         * dealNum : 0
         */

        private String id;
        private String name;
        private String findNum;
        private String dealNum;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getFindNum() {
            return Float.parseFloat(findNum);
        }

        public void setFindNum(String findNum) {
            this.findNum = findNum;
        }

        public float getDealNum() {
            return Float.parseFloat(dealNum);
        }

        public void setDealNum(String dealNum) {
            this.dealNum = dealNum;
        }
    }
}
