package com.jinke.housekeeper.bean;

import java.util.List;

/**
 * Created by root on 17-3-21.
 */

public class StatisticsInfo {

    private String maxValue;
    private List<ListObjBean> listObj;

    public List<ListObjBean> getListObj() {
        return listObj;
    }

    public void setListObj(List<ListObjBean> listObj) {
        this.listObj = listObj;
    }

    public float getMaxValue() {
        return Float.parseFloat(maxValue) * 2;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public static class ListObjBean {
        /**
         * month : 08月
         * findNumber : 0
         * rectificationNumber : 0
         */

        private String month;
        private String findNumber;
        private String rectificationNumber;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public float getFindNumber() {
            return Float.parseFloat(findNumber);
        }

        public void setFindNumber(String findNumber) {
            this.findNumber = findNumber;
        }

        public float getRectificationNumber() {
            return Float.parseFloat(rectificationNumber);
        }

        public void setRectificationNumber(String rectificationNumber) {
            this.rectificationNumber = rectificationNumber;
        }
    }
}
