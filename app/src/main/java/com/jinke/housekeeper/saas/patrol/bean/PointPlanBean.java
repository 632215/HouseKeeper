package com.jinke.housekeeper.saas.patrol.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : huominghao
 * date : 2018/1/25 0025
 * function : 任务列表
 */

public class PointPlanBean {

    /**
     * projectId : 128
     * listData 任务列表: [{"taskId":1516865425274,"startTime":"02:00","endTime":"02:00","taskName":"10年城东区1"},{"taskName":"10年城东区2","endTime":"18:00","startTime":"15:00","taskId":1516865425279},{"endTime":"02:00","startTime":"01:00","taskId":1516866341986,"taskName":"10年城东区3"}]
     * projectName : 10年城东区
     */

    private String projectId;
    private String projectName;
    private List<ListDataBean> listData;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<ListDataBean> getListData() {
        return listData;
    }

    public void setListData(List<ListDataBean> listData) {
        this.listData = listData;
    }

    public static class ListDataBean implements Serializable{
        /**
         * taskId : 1516865425274
         * startTime : 02:00
         * endTime : 02:00
         * taskName : 10年城东区1
         */

        private long taskId;
        private String startTime;
        private String endTime;
        private String taskName;

        public long getTaskId() {
            return taskId;
        }

        public void setTaskId(long taskId) {
            this.taskId = taskId;
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

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }
    }
}
