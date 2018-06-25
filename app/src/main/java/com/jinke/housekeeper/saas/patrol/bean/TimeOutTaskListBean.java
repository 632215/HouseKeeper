package com.jinke.housekeeper.saas.patrol.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : huominghao
 * date : 2018/2/5 0005
 * function : 超时任务时返回点位信息，state=3时才返回pointList
 */

public class TimeOutTaskListBean implements Serializable{

    /**
     * pointNum : 2
     * taskName : 001
     * pointList : [{"name":"二int1","id":1517815509046,"state":"N","result":null,"time":null,"taskId":1517815509045,"remark":null,"pointId":"548384a6","sortNum":null,"pointPerson":null},{"name":"一楼","id":1517815509047,"state":"N","result":null,"time":null,"taskId":1517815509045,"remark":null,"pointId":"746679e6","sortNum":null,"pointPerson":null}]
     * state : 3
     * startTime : 13:00
     * endTime : 15:00
     * taskId : 1517815509045
     */

    private int pointNum;
    private String taskName;
    private int state;
    private String startTime;
    private String endTime;
    private long taskId;
    private List<PointListBean> pointList;

    public int getPointNum() {
        return pointNum;
    }

    public void setPointNum(int pointNum) {
        this.pointNum = pointNum;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public List<PointListBean> getPointList() {
        return pointList;
    }

    public void setPointList(List<PointListBean> pointList) {
        this.pointList = pointList;
    }

    public  class PointListBean implements Serializable{
        /**
         * name : 二int1
         * id : 1517815509046
         * state : N
         * result : null
         * time : null
         * taskId : 1517815509045
         * remark : null
         * pointId : 548384a6
         * sortNum : null
         * pointPerson : null
         */

        private String name;
        private long id;
        private String state;
        private Object result;
        private Object time;
        private long taskId;
        private Object remark;
        private String pointId;
        private Object sortNum;
        private Object pointPerson;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }

        public Object getTime() {
            return time;
        }

        public void setTime(Object time) {
            this.time = time;
        }

        public long getTaskId() {
            return taskId;
        }

        public void setTaskId(long taskId) {
            this.taskId = taskId;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getPointId() {
            return pointId;
        }

        public void setPointId(String pointId) {
            this.pointId = pointId;
        }

        public Object getSortNum() {
            return sortNum;
        }

        public void setSortNum(Object sortNum) {
            this.sortNum = sortNum;
        }

        public Object getPointPerson() {
            return pointPerson;
        }

        public void setPointPerson(Object pointPerson) {
            this.pointPerson = pointPerson;
        }
    }
}
