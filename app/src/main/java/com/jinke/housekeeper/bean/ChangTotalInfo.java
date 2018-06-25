package com.jinke.housekeeper.bean;

import java.util.List;

/**
 * Created by root on 17-3-15.
 */

public class ChangTotalInfo {

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
         * name : 10月
         * findNum : 0
         * rectificationNumber : 0
         */

        private String name;
        private String findNum;
        private String rectificationNumber;

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

        public float getRectificationNumber() {
            return Float.parseFloat(rectificationNumber);
        }

        public void setRectificationNumber(String rectificationNumber) {
            this.rectificationNumber = rectificationNumber;
        }
    }
}
