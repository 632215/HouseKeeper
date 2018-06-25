package com.jinke.housekeeper.saas.report.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */

public class SelfHistoryBean {

    private List<ListObjBean> listObj;

    public List<ListObjBean> getListObj() {
        return listObj;
    }

    public void setListObj(List<ListObjBean> listObj) {
        this.listObj = listObj;
    }

    public static class ListObjBean {
        /**
         * id : 1491548742982
         * isHangup :
         * isTimeout :
         * isState : 2
         * sceneName : 工程管理
         * areaName : 车库出入口
         * imgaddress : https://staticfile.tq-service.com/image/QualitySystem/20170407/d1e2063efd9044c5857af5c0d87c44a8.jpg?x-oss-process=image/resize,h_100,w_100,m_fill,limit_0
         * audioaddress :
         * audiolen : 0
         * describe : iiiiiiiiiii
         * orgName : 测试项目中华坊
         * superviseTime : 2017-04-07 15:17:46
         * superviseName : 唐君左
         */

        private String id;
        private String isHangup;
        private String isTimeout;
        private String isState;
        private String sceneName;
        private String areaName;
        private String imgaddress;
        private String audioaddress;
        private int audiolen;
        private String describe;
        private String orgName;
        private String superviseTime;
        private String superviseName;
        private String roomNum;

        public void setRoomNum(String roomNum) {
            this.roomNum = roomNum;
        }

        public String getRoomNum() {
            return roomNum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getIsState() {
            return isState;
        }

        public void setIsState(String isState) {
            this.isState = isState;
        }

        public String getSceneName() {
            return sceneName;
        }

        public void setSceneName(String sceneName) {
            this.sceneName = sceneName;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getImgaddress() {
            return imgaddress;
        }

        public void setImgaddress(String imgaddress) {
            this.imgaddress = imgaddress;
        }

        public String getAudioaddress() {
            return audioaddress;
        }

        public void setAudioaddress(String audioaddress) {
            this.audioaddress = audioaddress;
        }

        public int getAudiolen() {
            return audiolen;
        }

        public void setAudiolen(int audiolen) {
            this.audiolen = audiolen;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
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
