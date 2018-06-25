package com.jinke.housekeeper.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 32 on 2016/12/22.
 */
public class RegisterDepartmentBean implements Serializable{

    private List<ListObjBean> listObj;

    public List<ListObjBean> getListObj() {
        return listObj;
    }

    public void setListObj(List<ListObjBean> listObj) {
        this.listObj = listObj;
    }

    public static class ListObjBean implements Serializable{
        /**
         * id : 1473241663255
         * name : 客服管理
         */

        private String id;
        private String name;
        private String type="";

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        private boolean isSelecter=false;

        public boolean isSelecter() {
            return isSelecter;
        }

        public void setSelecter(boolean selecter) {
            isSelecter = selecter;
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
