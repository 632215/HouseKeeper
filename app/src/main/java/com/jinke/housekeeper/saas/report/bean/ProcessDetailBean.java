package com.jinke.housekeeper.saas.report.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/20.
 */

public class ProcessDetailBean {

    /**
     * obj : {"areaName":"车库出入口","audioAdd":"","audioAddLen":0,"audioAdds":"","audioAddsLen":0,"handTime":"2017-04-07 15:17:46","handUserName":"唐君左","handUserPhone":"13110278921","pageType":"-1","proName":"测试项目中华坊","sceneName":"工程管理","staffDescribe":"","staffPotoadd":"","staffPotoadds":"","superviseDescribe":"iiiiiiiiiii","supervisePotoadd":"https://staticfile.tq-service.com/image/QualitySystem/20170407/d1e2063efd9044c5857af5c0d87c44a8.jpg","supervisePotoadds":"https://staticfile.tq-service.com/image/QualitySystem/20170407/d1e2063efd9044c5857af5c0d87c44a8.jpg?x-oss-process=image/resize,h_100,w_100,m_fill,limit_0","time":"","userName":"","userPhone":""}
     */

    private ObjBean obj;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean implements Serializable{
        /**
         * areaName : 车库出入口
         * audioAdd :
         * audioAddLen : 0
         * audioAdds :
         * audioAddsLen : 0
         * handTime : 2017-04-07 15:17:46
         * handUserName : 唐君左
         * handUserPhone : 13110278921
         * pageType : -1
         * proName : 测试项目中华坊
         * sceneName : 工程管理
         * staffDescribe :
         * staffPotoadd :
         * staffPotoadds :
         * superviseDescribe : iiiiiiiiiii
         * supervisePotoadd : https://staticfile.tq-service.com/image/QualitySystem/20170407/d1e2063efd9044c5857af5c0d87c44a8.jpg
         * supervisePotoadds : https://staticfile.tq-service.com/image/QualitySystem/20170407/d1e2063efd9044c5857af5c0d87c44a8.jpg?x-oss-process=image/resize,h_100,w_100,m_fill,limit_0
         * time :
         * userName :
         * userPhone :
         */

        private String areaName;
        private String audioAdd;
        private int audioAddLen;
        private String audioAdds;
        private int audioAddsLen;
        private String handTime;
        private String handUserName;
        private String handUserPhone;
        private String pageType;
        private String proName;
        private String sceneName;
        private String staffDescribe;
        private String staffPotoadd;
        private String staffPotoadds;
        private String superviseDescribe;
        private String supervisePotoadd;
        private String supervisePotoadds;
        private String time;
        private String userName;
        private String userPhone;
        private String roomNum;

        public void setRoomNum(String roomNum) {
            this.roomNum = roomNum;
        }

        public String getRoomNum() {
            return roomNum;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getAudioAdd() {
            return audioAdd;
        }

        public void setAudioAdd(String audioAdd) {
            this.audioAdd = audioAdd;
        }

        public int getAudioAddLen() {
            return audioAddLen;
        }

        public void setAudioAddLen(int audioAddLen) {
            this.audioAddLen = audioAddLen;
        }

        public String getAudioAdds() {
            return audioAdds;
        }

        public void setAudioAdds(String audioAdds) {
            this.audioAdds = audioAdds;
        }

        public int getAudioAddsLen() {
            return audioAddsLen;
        }

        public void setAudioAddsLen(int audioAddsLen) {
            this.audioAddsLen = audioAddsLen;
        }

        public String getHandTime() {
            return handTime;
        }

        public void setHandTime(String handTime) {
            this.handTime = handTime;
        }

        public String getHandUserName() {
            return handUserName;
        }

        public void setHandUserName(String handUserName) {
            this.handUserName = handUserName;
        }

        public String getHandUserPhone() {
            return handUserPhone;
        }

        public void setHandUserPhone(String handUserPhone) {
            this.handUserPhone = handUserPhone;
        }

        public String getPageType() {
            return pageType;
        }

        public void setPageType(String pageType) {
            this.pageType = pageType;
        }

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public String getSceneName() {
            return sceneName;
        }

        public void setSceneName(String sceneName) {
            this.sceneName = sceneName;
        }

        public String getStaffDescribe() {
            return staffDescribe;
        }

        public void setStaffDescribe(String staffDescribe) {
            this.staffDescribe = staffDescribe;
        }

        public String getStaffPotoadd() {
            return staffPotoadd;
        }

        public void setStaffPotoadd(String staffPotoadd) {
            this.staffPotoadd = staffPotoadd;
        }

        public String getStaffPotoadds() {
            return staffPotoadds;
        }

        public void setStaffPotoadds(String staffPotoadds) {
            this.staffPotoadds = staffPotoadds;
        }

        public String getSuperviseDescribe() {
            return superviseDescribe;
        }

        public void setSuperviseDescribe(String superviseDescribe) {
            this.superviseDescribe = superviseDescribe;
        }

        public String getSupervisePotoadd() {
            return supervisePotoadd;
        }

        public void setSupervisePotoadd(String supervisePotoadd) {
            this.supervisePotoadd = supervisePotoadd;
        }

        public String getSupervisePotoadds() {
            return supervisePotoadds;
        }

        public void setSupervisePotoadds(String supervisePotoadds) {
            this.supervisePotoadds = supervisePotoadds;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }
    }
}
