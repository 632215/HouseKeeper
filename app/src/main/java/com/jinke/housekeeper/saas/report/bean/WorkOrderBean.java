package com.jinke.housekeeper.saas.report.bean;

import com.blankj.utilcode.util.TimeUtils;
import com.jinke.housekeeper.saas.report.util.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * function: 工单池列表
 * author: hank
 * date: 2017/8/11
 */

public class WorkOrderBean implements Serializable {


    private List<ListObjBean> listObj;

    public List<ListObjBean> getListObj() {
        return listObj;
    }

    public void setListObj(List<ListObjBean> listObj) {
        this.listObj = listObj;
    }

    public static class ListObjBean implements Serializable {
        /**
         * id : 1524484329903
         * taskId : 1102510
         * isState : 1
         * sceneName : 工程管理
         * areaName : 车库
         * superviseTime : 2018/04/23 20:03:33
         * audioaddress :
         * audiolen : 0
         * orgname : 金科未来城x
         * describe : 家KKK家居
         * img_address : https://staticfile.tq-service.com/image/QualitySystem/20180423/d65f34177928481fadee05c5b9f5cbb9.jpg
         * orgId : 208
         * userName : 韩斌02
         * phone : 13111111111
         * reportType : 121
         * roomId : 1
         * roomNum : 1
         */

        private String id;
        private String taskId;
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
        private String userName;
        private String phone;
        private String reportType;
        private String roomId;
        private String roomNum;

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

        public List<String> getImg_address() {
            List<String> list=new ArrayList<>();
            if (StringUtil.isEmpty(img_address)){
                return list;
            }
            String[] img = img_address.split("\\|");
            list= Arrays.asList(img);
            return list;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getReportType() {
            return reportType;
        }

        public void setReportType(String reportType) {
            this.reportType = reportType;
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
    }
}
