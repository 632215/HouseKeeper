package com.jinke.housekeeper.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */

public class MsgBean implements Serializable {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * id : 123123
         * createTime : 2017-07-25 00:00:00
         * title : 这是个爆炸性新闻
         * name : 警
         * msgTypeId : 6
         * typeId : 1
         * url :
         * isRead : 1 是否已读 1:已读，其他：未读
         */

        private String id;
        private String createTime;
        private String title;
        private String name;
        private String msgTypeId;
        private String typeId;
        private String url;
        private String isRead;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMsgTypeId() {
            return msgTypeId;
        }

        public void setMsgTypeId(String msgTypeId) {
            this.msgTypeId = msgTypeId;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIsRead() {
            return isRead;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }
    }
}
