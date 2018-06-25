package com.jinke.housekeeper.saas.patrol.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : huominghao
 * date : 2018/1/26 0026
 * function :点击任务获取点位
 */

public class IsStartBean implements Serializable {


    /**
     * istrue : 1 1为可执行，2为任务超时，3为任务未开始，4为任务已经完成
     * listData : [{"pointState":"Y","completeTime":"15:55","pointName":"10年城车库通道88号车位","pointId":"14d783e6"},{"pointName":"1O年城6O栋33层","pointId":"0464c9e6","pointState":"Y","completeTime":"15:55"},{"completeTime":"15:55","pointState":"Y","pointName":"10年城北区链家门市","pointId":"04abc9e6"},{"completeTime":"15:55","pointName":"10年城60栋22层","pointState":"Y","pointId":"145bcbe6"}]
     */

    private String istrue;
    private List<ListDataBean> listData;

    public String isIstrue() {
        return istrue;
    }

    public void setIstrue(String istrue) {
        this.istrue = istrue;
    }

    public List<ListDataBean> getListData() {
        return listData;
    }

    public void setListData(List<ListDataBean> listData) {
        this.listData = listData;
    }

    public static class ListDataBean implements Serializable {
        /**
         * pointState : Y
         * completeTime : 15:55
         * pointName : 10年城车库通道88号车位
         * pointId : 14d783e6
         */

        private String pointState;
        private String completeTime;
        private String pointName;
        private String pointId;

        public String getPointState() {
            return pointState;
        }

        public void setPointState(String pointState) {
            this.pointState = pointState;
        }

        public String getCompleteTime() {
            return completeTime;
        }

        public void setCompleteTime(String completeTime) {
            this.completeTime = completeTime;
        }

        public String getPointName() {
            return pointName;
        }

        public void setPointName(String pointName) {
            this.pointName = pointName;
        }

        public String getPointId() {
            return pointId;
        }

        public void setPointId(String pointId) {
            this.pointId = pointId;
        }
    }
}
