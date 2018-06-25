package com.jinke.housekeeper.saas.report.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 6/01/17.
 */

public class WorkToadyInfo implements Serializable{


    /**
     * listObj : [{"id":"1483438699678","taskId":"207510","isStay":"1","isHangup":"1","isTimeout":"0","sceneId":"1482908574156","sceneName":"12345678绿化标准12345678901234567890","areaId":"1482908574309","areaName":"门哨管理","superviseTime":"2017-01-03 18:22:54.0"},{"id":"1482892398799","taskId":"197523","isStay":"0","isHangup":"0","isTimeout":"1","sceneId":"1474850888671","sceneName":"工程管理","areaId":"1482892398783","areaName":"小区出入口1","superviseTime":"2016-12-28 11:43:49.0"},{"id":"1482892398787","taskId":"197510","isStay":"1","isHangup":"0","isTimeout":"1","sceneId":"1474850888671","sceneName":"工程管理","areaId":"1482892398783","areaName":"小区出入口1","superviseTime":"2016-12-28 11:37:35.0"},{"id":"1482470175775","taskId":"192510","isStay":"0","isHangup":"0","isTimeout":"1","sceneId":"1474850888671","sceneName":"工程管理","areaId":"1","areaName":"小区出入口","superviseTime":"2016-12-23 13:18:48.0"},{"id":"1482371151283","taskId":"175010","isStay":"1","isHangup":"0","isTimeout":"1","sceneId":"1474850888671","sceneName":"工程管理","areaId":"1","areaName":"小区出入口","superviseTime":"2016-12-22 09:48:49.0"},{"id":"1482115566287","taskId":"137510","isStay":"1","isHangup":"0","isTimeout":"1","sceneId":"","sceneName":"","areaId":"1","areaName":"小区出入口","superviseTime":"2016-12-19 10:48:00.0"},{"id":"1482112554954","taskId":"135046","isStay":"1","isHangup":"0","isTimeout":"1","sceneId":"1474850888671","sceneName":"工程管理","areaId":"1","areaName":"小区出入口","superviseTime":"2016-12-19 10:42:19.0"},{"id":"1482112554953","taskId":"135034","isStay":"1","isHangup":"0","isTimeout":"1","sceneId":"1474850888671","sceneName":"工程管理","areaId":"1","areaName":"小区出入口","superviseTime":"2016-12-19 10:41:17.0"},{"id":"1481769395404","taskId":"75010","isStay":"1","isHangup":"0","isTimeout":"1","sceneId":"1471307782692","sceneName":"花圃管理","areaId":"","areaName":"","superviseTime":"2016-12-15 10:37:36.0"},{"id":"1481708419137","taskId":"65010","isStay":"1","isHangup":"0","isTimeout":"1","sceneId":"1471307782692","sceneName":"花圃管理","areaId":"1481501878617","areaName":"消防栓","superviseTime":"2016-12-14 17:41:43.0"}]
     * wait : 14
     * already : 1
     */

    private int wait;
    private int already;
    private List<ListObjBean> listObj;

    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }

    public int getAlready() {
        return already;
    }

    public void setAlready(int already) {
        this.already = already;
    }

    public List<ListObjBean> getListObj() {
        return listObj;
    }

    public void setListObj(List<ListObjBean> listObj) {
        this.listObj = listObj;
    }

    public static class ListObjBean implements Serializable{
        /**
         * id : 1483438699678
         * taskId : 207510
         * isStay : 1
         * isHangup : 1
         * isTimeout : 0
         * sceneId : 1482908574156
         * sceneName : 12345678绿化标准12345678901234567890
         * areaId : 1482908574309
         * areaName : 门哨管理
         * superviseTime : 2017-01-03 18:22:54.0
         */
        private String orgId;
        private String id;
        private String taskId;
        private String isState;


        public String getIsState() {
            return isState;
        }

        public void setIsState(String isState) {
            this.isState = isState;
        }

        public String getStaffTime() {
            return staffTime;
        }

        public void setStaffTime(String staffTime) {
            this.staffTime = staffTime;
        }

        private String staffTime;

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        private String isStay;
        private String isHangup;
        private String isTimeout;
        private String sceneId;
        private String sceneName;
        private String areaId;
        private String areaName;
        private String superviseTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getIsStay() {
            return isStay;
        }

        public void setIsStay(String isStay) {
            this.isStay = isStay;
        }

        public String getIsHangup() {
            return isHangup;
        }

        public void setIsHangup(String isHangup) {
            this.isHangup = isHangup;
        }

        public String getIsTimeout() {
            return isTimeout;
        }

        public void setIsTimeout(String isTimeout) {
            this.isTimeout = isTimeout;
        }

        public String getSceneId() {
            return sceneId;
        }

        public void setSceneId(String sceneId) {
            this.sceneId = sceneId;
        }

        public String getSceneName() {
            return sceneName;
        }

        public void setSceneName(String sceneName) {
            this.sceneName = sceneName;
        }

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getSuperviseTime() {
            return superviseTime;
        }

        public void setSuperviseTime(String superviseTime) {
            this.superviseTime = superviseTime;
        }
    }
}
