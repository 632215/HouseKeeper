package com.jinke.housekeeper.bean;

import java.util.List;

/**
 * Created by root on 17-3-15.
 */

public class StatisticsgrInfo {


    private List<ListObjBean> listObj;
    private String maxValue;

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
        private int findNumber;
        private int rectificationNumber;

        private int completeNum;
        private String percent;
        private int planNum;

        public int getCompleteNum() {
            return completeNum;
        }

        public void setCompleteNum(int completeNum) {
            this.completeNum = completeNum;
        }

        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
        }

        public int getPlanNum() {
            return planNum;
        }

        public void setPlanNum(int planNum) {
            this.planNum = planNum;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public int getFindNumber() {
            return findNumber;
        }

        public void setFindNumber(int findNumber) {
            this.findNumber = findNumber;
        }

        public int getRectificationNumber() {
            return rectificationNumber;
        }

        public void setRectificationNumber(int rectificationNumber) {
            this.rectificationNumber = rectificationNumber;
        }
    }
}
