package com.jinke.housekeeper.saas.report.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */
public class AllReportBean {

    private List<ListObjBean> listObj;

    public List<ListObjBean> getListObj() {
        return listObj;
    }

    public void setListObj(List<ListObjBean> listObj) {
        this.listObj = listObj;
    }

    public static class ListObjBean implements Serializable{
        /**
         * supervisePotoadd : https://staticfile.tq-service.com/image/QualitySystem/20170406/7727114cd0e149cf973879732ff9e74c.jpg?x-oss-process=image/resize,h_100,w_100,m_fill,limit_0
         * superviseDescribe : vhh
         * isState : 2
         * audioAdd :
         * hangOrTime : 3
         * orgName : 香榭丽都
         * sceneName : 工程管理
         * cruxName : 车库出入口
         * audioAddLength : 0
         * id : 1491451205019
         * superviseTime : 2017-04-06
         * superviseName : 测试项目管理人员（安全）
         */

        private String supervisePotoadd;
        private String superviseDescribe;
        private String isState;
        private String audioAdd;
        private String hangOrTime;
        private String orgName;
        private String sceneName;
        private String cruxName;
        private String audioAddLength;
        private String id;
        private String superviseTime;
        private String superviseName;
        private String roomNum;

        public void setRoomNum(String roomNum) {
            this.roomNum = roomNum;
        }

        public String getRoomNum() {
            return roomNum;
        }

        public String getSupervisePotoadd() {
            return supervisePotoadd;
        }

        public void setSupervisePotoadd(String supervisePotoadd) {
            this.supervisePotoadd = supervisePotoadd;
        }

        public String getSuperviseDescribe() {
            return superviseDescribe;
        }

        public void setSuperviseDescribe(String superviseDescribe) {
            this.superviseDescribe = superviseDescribe;
        }

        public String getIsState() {
            return isState;
        }

        public void setIsState(String isState) {
            this.isState = isState;
        }

        public String getAudioAdd() {
            return audioAdd;
        }

        public void setAudioAdd(String audioAdd) {
            this.audioAdd = audioAdd;
        }

        public String getHangOrTime() {
            return hangOrTime;
        }

        public void setHangOrTime(String hangOrTime) {
            this.hangOrTime = hangOrTime;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getSceneName() {
            return sceneName;
        }

        public void setSceneName(String sceneName) {
            this.sceneName = sceneName;
        }

        public String getCruxName() {
            return cruxName;
        }

        public void setCruxName(String cruxName) {
            this.cruxName = cruxName;
        }

        public String getAudioAddLength() {
            return audioAddLength;
        }

        public void setAudioAddLength(String audioAddLength) {
            this.audioAddLength = audioAddLength;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSuperviseTime() {
            return superviseTime;
        }

        public void setSuperviseTime(String superviseTime) {
            this.superviseTime = superviseTime;
        }

        public String getSuperviseName() {
            return superviseName;
        }

        public void setSuperviseName(String superviseName) {
            this.superviseName = superviseName;
        }
    }
}
