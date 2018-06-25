package com.jinke.housekeeper.saas.report.bean;

/**
 * Created by Administrator on 2017/3/29.
 */
public class WorkBean {


    /**
     * obj : {"audioAddr":"https://staticfile.tq-service.com/image/QualitySystem/20170406/6862722815e34dc29471ccec7817719a.mp3","audioLen":1,"createTime":"2017-04-06 17:29:40","describes":"fff","id":1491468583969,"mobilePhone":"18893678790","receiveIdName":"测试项目管理人员（客服）","staffIdName":"测试项目管理人员（安全）"}
     */

    private ObjBean obj;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * audioAddr : https://staticfile.tq-service.com/image/QualitySystem/20170406/6862722815e34dc29471ccec7817719a.mp3
         * audioLen : 1
         * createTime : 2017-04-06 17:29:40
         * describes : fff
         * id : 1491468583969
         * mobilePhone : 18893678790
         * receiveIdName : 测试项目管理人员（客服）
         * staffIdName : 测试项目管理人员（安全）
         */

        private String audioAddr;
        private int audioLen;
        private String createTime;
        private String describes;
        private long id;
        private String mobilePhone;
        private String receiveIdName;
        private String staffIdName;

        public String getAudioAddr() {
            return audioAddr;
        }

        public void setAudioAddr(String audioAddr) {
            this.audioAddr = audioAddr;
        }

        public int getAudioLen() {
            return audioLen;
        }

        public void setAudioLen(int audioLen) {
            this.audioLen = audioLen;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDescribes() {
            return describes;
        }

        public void setDescribes(String describes) {
            this.describes = describes;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public String getReceiveIdName() {
            return receiveIdName;
        }

        public void setReceiveIdName(String receiveIdName) {
            this.receiveIdName = receiveIdName;
        }

        public String getStaffIdName() {
            return staffIdName;
        }

        public void setStaffIdName(String staffIdName) {
            this.staffIdName = staffIdName;
        }
    }
}
