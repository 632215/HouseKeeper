package com.jinke.housekeeper.saas.report.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pc on 2017/3/7.
 */

public class ProcessNodeBean {

    private List<ObjBean> obj;

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean implements Serializable {
        /**
         * audioAddress : https://staticfile.tq-service.com/image/dgj/20170328/6dc69b7433254f0aa3da4bb41e4ce574.mp3
         * id :
         * len : 4
         * name : 问题发起
         * pid : 1490605602210
         * processId : 1490605602209
         * taskId : 0
         * time : 2017-03-28 10:13:59
         * type : 1
         */

        private String audioAddress;
        private String id;
        private int len;
        private String name;
        private long pid;
        private String puId;
        private String ifManyi;
        private String userName;
        private String remark;


        private String processId;

        private String taskId;
        private String time;
        private String type;
        private String phone;

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone() {
            return phone;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRemark() {
            return remark;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setIfManyi(String ifManyi) {
            this.ifManyi = ifManyi;
        }

        public String getIfManyi() {
            return ifManyi;
        }

        public String getAudioAddress() {
            return audioAddress;
        }

        public String getPuId() {
            return puId;
        }

        public void setPuId(String puId) {
            this.puId = puId;
        }

        public void setAudioAddress(String audioAddress) {
            this.audioAddress = audioAddress;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getLen() {
            return len;
        }

        public void setLen(int len) {
            this.len = len;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getPid() {
            return pid;
        }

        public void setPid(long pid) {
            this.pid = pid;
        }

        public String getProcessId() {
            return processId;
        }

        public void setProcessId(String processId) {
            this.processId = processId;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
