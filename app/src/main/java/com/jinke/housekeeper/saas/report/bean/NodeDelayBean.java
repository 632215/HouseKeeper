package com.jinke.housekeeper.saas.report.bean;

/**
 * Created by Administrator on 2017/3/28.
 */
public class NodeDelayBean {

    /**
     * obj : {"audioAddr":"https://staticfile.tq-service.com/image/QualitySystem/20170406/0cd1e797d3774c63b78191d0967a328d.mp3","audioLen":3,"completeTime":"2017-04-07 04:34:03","descrbe":"hhh","handdleNum":0,"handdleResult":"待处理","imgUrl":"https://staticfile.tq-service.com/image/QualitySystem/20170405/5133fef3262447deb695f708a4c762c7.jpg?x-oss-process=image/resize,h_100,w_100,m_fill,limit_0","nickName":"测试项目管理人员（客服）","nodeName":"延迟申请","proId":"1491363005607","tskId":"735058","userPhone":"13983025716"}
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
         * audioAddr : https://staticfile.tq-service.com/image/QualitySystem/20170406/0cd1e797d3774c63b78191d0967a328d.mp3
         * audioLen : 3
         * completeTime : 2017-04-07 04:34:03
         * descrbe : hhh
         * handdleNum : 0
         * handdleResult : 待处理
         * imgUrl : https://staticfile.tq-service.com/image/QualitySystem/20170405/5133fef3262447deb695f708a4c762c7.jpg?x-oss-process=image/resize,h_100,w_100,m_fill,limit_0
         * nickName : 测试项目管理人员（客服）
         * nodeName : 延迟申请
         * proId : 1491363005607
         * tskId : 735058
         * userPhone : 13983025716
         */

        private String audioAddr;
        private int audioLen;
        private String completeTime;
        private String descrbe;
        private int handdleNum;
        private String handdleResult;
        private String imgUrl;
        private String nickName;
        private String nodeName;
        private String proId;
        private String tskId;
        private String userPhone;

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

        public String getCompleteTime() {
            return completeTime;
        }

        public void setCompleteTime(String completeTime) {
            this.completeTime = completeTime;
        }

        public String getDescrbe() {
            return descrbe;
        }

        public void setDescrbe(String descrbe) {
            this.descrbe = descrbe;
        }

        public int getHanddleNum() {
            return handdleNum;
        }

        public void setHanddleNum(int handdleNum) {
            this.handdleNum = handdleNum;
        }

        public String getHanddleResult() {
            return handdleResult;
        }

        public void setHanddleResult(String handdleResult) {
            this.handdleResult = handdleResult;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public String getProId() {
            return proId;
        }

        public void setProId(String proId) {
            this.proId = proId;
        }

        public String getTskId() {
            return tskId;
        }

        public void setTskId(String tskId) {
            this.tskId = tskId;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }
    }
}
