package com.jinke.housekeeper.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 32 on 2016/12/22.
 */
public class RegisterProjectBean implements Serializable{


    private List<ListObjBean> listObj;

    public List<ListObjBean> getListObj() {
        return listObj;
    }

    public void setListObj(List<ListObjBean> listObj) {
        this.listObj = listObj;
    }

    public static class ListObjBean implements Serializable{
        /**
         * value : 0
         * text : 金科物业服务有限公司
         */
        private String id;
        private String name;
        private String type;

        public ListObjBean() {
        }

        public ListObjBean(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}