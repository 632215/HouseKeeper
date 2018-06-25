package com.jinke.housekeeper.saas.patrol.bean;

import java.io.Serializable;
import java.util.List;

/**
 * function: 点位信息列表
 * author: hank
 * date: 2017/7/25
 */

public class SavePointBean implements Serializable {
    /**
     * taskId : 1516865425279
     * completeTime : 2017-10-01 12:12:03
     * listData : [{"pointId":"0464c9e6","inspecTime":"2017-10-01 12:12:03","result":1,"remark":"水dfgh dfgh dfg sdfdg f s费"},{"pointId":"04abc9e6","inspecTime":"2017-10-01 12:12:03","result":1,"remark":"水电费dfg sdf s费"}]
     */

    private int row;//打卡点位
    private int Leak;//未打卡点位
    private String taskId;
    private String completeTime;
    private List<ListDataBean> listData;

    public int getLeak() {
        return Leak;
    }

    public void setLeak(int leak) {
        Leak = leak;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public List<ListDataBean> getListData() {
        return listData;
    }

    public void setListData(List<ListDataBean> listData) {
        this.listData = listData;
    }

    public static class ListDataBean implements Serializable {
        /**
         * pointId : 0464c9e6
         * inspecTime : 2017-10-01 12:12:03
         * result : 1
         * remark : 水dfgh dfgh dfg sdfdg f s费
         */

        private String pointId;
        private String inspecTime;
        private int result;
        private String remark;

        public String getPointId() {
            return pointId;
        }

        public void setPointId(String pointId) {
            this.pointId = pointId;
        }

        public String getInspecTime() {
            return inspecTime;
        }

        public void setInspecTime(String inspecTime) {
            this.inspecTime = inspecTime;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }


//    private SaveBean SaveBeen;
//
//    public SaveBean getSaveBeen() {
//        return SaveBeen;
//    }
//
//    public void setSaveBeen(SaveBean saveBeen) {
//        SaveBeen = saveBeen;
//    }
//
//    public static class SaveBean implements Serializable {
//
//        /**
//         * pointId : 046281a6
//         * punchTime : 2017-08-0112: 12: 03
//         * faultDesc : 24
//         */
//
//        private String pointId;
//        private String pointName;
//        private String punchTime;
//        private String projectId;
//        private String faultDesc;
//
//        public SaveBean(String pointId, String pointName, String punchTime, String projectId, String faultDesc) {
//            this.pointId = pointId;
//            this.pointName = pointName;
//            this.punchTime = punchTime;
//            this.projectId = projectId;
//            this.faultDesc = faultDesc;
//        }
//
//        public SaveBean() {
//        }
//
//        public String getPointId() {
//            return pointId;
//        }
//
//        public void setPointId(String pointId) {
//            this.pointId = pointId;
//        }
//
//        public String getPointName() {
//            return pointName;
//        }
//
//        public void setPointName(String pointName) {
//            this.pointName = pointName;
//        }
//
//        public String getPunchTime() {
//            return punchTime;
//        }
//
//        public void setPunchTime(String punchTime) {
//            this.punchTime = punchTime;
//        }
//
//        public String getProjectId() {
//            return projectId;
//        }
//
//        public void setProjectId(String projectId) {
//            this.projectId = projectId;
//        }
//
//        public String getFaultDesc() {
//            return faultDesc;
//        }
//
//        public void setFaultDesc(String faultDesc) {
//            this.faultDesc = faultDesc;
//        }
//    }


}
