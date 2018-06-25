package com.jinke.housekeeper.saas.equipment.bean;

import java.util.List;

/**
 * function:三表详情
 * author: hank
 * date: 2017/11/11
 */

public class ParameterBean {

    private List<ListSubjectBean> listSubject;
    private List<ListParameterBean> listParameter;

    public List<ListSubjectBean> getListSubject() {
        return listSubject;
    }

    public void setListSubject(List<ListSubjectBean> listSubject) {
        this.listSubject = listSubject;
    }

    public List<ListParameterBean> getListParameter() {
        return listParameter;
    }

    public void setListParameter(List<ListParameterBean> listParameter) {
        this.listParameter = listParameter;
    }

    public static class ListSubjectBean {
        /**
         * id : 1509420258007
         * subject : 23
         * threshold : 123
         * unit : 123
         * ifFirst : Y
         * yesterDay : 昨天数据
         */

        private long id;
        private String subject;
        private String threshold;
        private String unit;
        private String ifFirst;
        private String yesterDay;
        private String Today;//今天数据

        public String getToday() {
            return Today;
        }

        public void setToday(String today) {
            Today = today;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getThreshold() {
            return threshold;
        }

        public void setThreshold(String threshold) {
            this.threshold = threshold;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getIfFirst() {
            return ifFirst;
        }

        public void setIfFirst(String ifFirst) {
            this.ifFirst = ifFirst;
        }

        public String getYesterDay() {
            return yesterDay;
        }

        public void setYesterDay(String yesterDay) {
            this.yesterDay = yesterDay;
        }
    }

    public static class ListParameterBean {
        /**
         * id : 1509420258005
         * parameter : 1
         */

        private long id;
        private String parameter;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getParameter() {
            return parameter;
        }

        public void setParameter(String parameter) {
            this.parameter = parameter;
        }
    }
}
