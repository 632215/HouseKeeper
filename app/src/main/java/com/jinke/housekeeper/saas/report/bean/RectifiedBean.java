package com.jinke.housekeeper.saas.report.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */
public class RectifiedBean {

    private List<ListObjBean> listObj;

    public List<ListObjBean> getListObj() {
        return listObj;
    }

    public void setListObj(List<ListObjBean> listObj) {
        this.listObj = listObj;
    }

    public static class ListObjBean implements Serializable{
        /**
         * id : 1491468583966
         * isHangup :
         * isTimeout :
         * isState : 4
         * sceneName : 工程管理
         * areaName : 车库
         * imgaddress : https://staticfile.tq-service.com/image/QualitySystem/20170406/45180d90cc6049ee86d0244b646e6912.jpg?x-oss-process=image/resize,h_100,w_100,m_fill,limit_0
         * audioaddress :
         * describe : fff
         * audiolen : 0
         * superviseTime : 2017-04-06 17:28:54
         * superviseName : 测试项目管理人员（客服）
         */

        private long id;
        private String isHangup;
        private String isTimeout;
        private String isState;
        private String sceneName;
        private String areaName;
        private String imgaddress;
        private String audioaddress;
        private String describe;
        private int audiolen;
        private String superviseTime;
        private String superviseName;
        private String roomNum;

        public String getRoomNum() {
            return roomNum;
        }

        public void setRoomNum(String roomNum) {
            this.roomNum = roomNum;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
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

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public int getAudiolen() {
            return audiolen;
        }

        public void setAudiolen(int audiolen) {
            this.audiolen = audiolen;
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
