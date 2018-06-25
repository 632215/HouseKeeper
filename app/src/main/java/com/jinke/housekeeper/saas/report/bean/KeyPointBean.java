package com.jinke.housekeeper.saas.report.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */
public class KeyPointBean implements Serializable{

    private List<ListObjBean> listObj;

    public List<ListObjBean> getListObj() {
        return listObj;
    }

    public void setListObj(List<ListObjBean> listObj) {
        this.listObj = listObj;
    }

    public static class ListObjBean implements Serializable{
        /**
         * id : 1523326950246
         * name : ccc
         * ename : C
         * type : 1
         */

        private String id;
        private String name;
        private String ename;
        private String type;

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

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
