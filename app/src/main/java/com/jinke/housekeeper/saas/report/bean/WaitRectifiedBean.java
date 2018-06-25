package com.jinke.housekeeper.saas.report.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pc on 2017/3/17.
 */

public class WaitRectifiedBean implements Serializable{


    /**
     * listObj : [{"id":"1524621874594","taskId":"1160010","isStay":"1502360206422","isHangup":"0","isTimeout":"0","isState":"5","sceneName":"安全","areaName":"车库出入口","superviseTime":"2018-04-25 10:13:42","audioaddress":"","audiolen":0,"orgname":"金科未来城x","describe":"红色警戒那你你姐姐","img_address":"https://staticfile.tq-service.com/image/QualitySystem/20180425/126f3fc4d0664133b5ca51a4e928f353.jpg?x-oss-process=image/resize,h_100,w_100,m_fill,limit_0","orgId":"208","checkState":"","roomId":"1","roomNum":"公区报事","phone":"13110278921","superviseName":"包强测试","isManager":"0","type":"-1"},{"id":"1524560537992","taskId":"1147510","isStay":"1502360206422","isHangup":"0","isTimeout":"1","isState":"5","sceneName":"四四","areaName":"","superviseTime":"2018-04-24 17:25:56","audioaddress":"","audiolen":0,"orgname":"金科未来城x","describe":"回到家呵呵好吧","img_address":"https://staticfile.tq-service.com/image/QualitySystem/20180424/cead92de70c14582b7aa1c307f462c3c.jpg?x-oss-process=image/resize,h_100,w_100,m_fill,limit_0","orgId":"208","checkState":"","roomId":"1","roomNum":"公区报事","phone":"13110278921","superviseName":"包强测试","isManager":"0","type":"0"},{"id":"1524551393964","taskId":"1125024","isStay":"1502360206422","isHangup":"N","isTimeout":"0","isState":"5","sceneName":"安全管理","areaName":"车库","superviseTime":"2018-04-24 14:35:50","audioaddress":"","audiolen":0,"orgname":"金科未来城x","describe":"ces","img_address":"https://staticfile.tq-service.com/image/QualitySystem/20180424/abee2a16901a4d7dba221c924eb2c804.gif?x-oss-process=image/resize,h_100,w_100,m_fixed","orgId":"208","checkState":"","roomId":"1","roomNum":"公区报事","phone":"13110000000","superviseName":"测试1111","isManager":"0","type":"-1"}]
     * wait : 7
     * already : 0
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
         * id : 1524621874594
         * taskId : 1160010
         * isStay : 1502360206422
         * isHangup : 0
         * isTimeout : 0
         * isState : 5
         * sceneName : 安全
         * areaName : 车库出入口
         * superviseTime : 2018-04-25 10:13:42
         * audioaddress :
         * audiolen : 0
         * orgname : 金科未来城x
         * describe : 红色警戒那你你姐姐
         * img_address : https://staticfile.tq-service.com/image/QualitySystem/20180425/126f3fc4d0664133b5ca51a4e928f353.jpg?x-oss-process=image/resize,h_100,w_100,m_fill,limit_0
         * orgId : 208
         * checkState :
         * roomId : 1
         * roomNum : 公区报事
         * phone : 13110278921
         * superviseName : 包强测试
         * isManager : 0
         * type : -1
         */

        private String id;
        private String taskId;
        private String isStay;
        private String isHangup;
        private String isTimeout;
        private String isState;
        private String sceneName;
        private String areaName;
        private String superviseTime;
        private String audioaddress;
        private int audiolen;
        private String orgname;
        private String describe;
        private String img_address;
        private String orgId;
        private String checkState;
        private String roomId;
        private String roomNum;
        private String phone;
        private String superviseName;
        private String isManager;
        private String type;

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

        public String getSuperviseTime() {
            return superviseTime;
        }

        public void setSuperviseTime(String superviseTime) {
            this.superviseTime = superviseTime;
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

        public String getOrgname() {
            return orgname;
        }

        public void setOrgname(String orgname) {
            this.orgname = orgname;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getImg_address() {
            return img_address;
        }

        public void setImg_address(String img_address) {
            this.img_address = img_address;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getCheckState() {
            return checkState;
        }

        public void setCheckState(String checkState) {
            this.checkState = checkState;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getRoomNum() {
            return roomNum;
        }

        public void setRoomNum(String roomNum) {
            this.roomNum = roomNum;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSuperviseName() {
            return superviseName;
        }

        public void setSuperviseName(String superviseName) {
            this.superviseName = superviseName;
        }

        public String getIsManager() {
            return isManager;
        }

        public void setIsManager(String isManager) {
            this.isManager = isManager;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
