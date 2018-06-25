package com.jinke.housekeeper.saas.report.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */
public class NodeElseBean {


    private List<ObjBean> obj;

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * userName : 韩斌
         * time : 2017-04-06 18:18:06
         * recode : 问题提交到经理处理
         * moblePhone : 13667629670
         * remark : fff
         */

        private String userName;
        private String time;
        private String recode;
        private String moblePhone;
        private String remark;
        private String audioAddr;
        private String create_time;
        private String audioLen;

        public String getAudioAddr() {
            return audioAddr;
        }

        public void setAudioAddr(String audioAddr) {
            this.audioAddr = audioAddr;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getAudioLen() {
            return audioLen;
        }

        public void setAudioLen(String audioLen) {
            this.audioLen = audioLen;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getRecode() {
            return recode;
        }

        public void setRecode(String recode) {
            this.recode = recode;
        }

        public String getMoblePhone() {
            return moblePhone;
        }

        public void setMoblePhone(String moblePhone) {
            this.moblePhone = moblePhone;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
