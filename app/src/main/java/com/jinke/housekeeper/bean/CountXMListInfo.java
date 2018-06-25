package com.jinke.housekeeper.bean;

import java.util.List;

/**
 * Created by root on 17-3-15.
 */

public class CountXMListInfo {


    private List<ListObjBean> listObj;

    public List<ListObjBean> getListObj() {
        return listObj;
    }

    public void setListObj(List<ListObjBean> listObj) {
        this.listObj = listObj;
    }

    public static class ListObjBean {
        /**
         * id : 7511
         * name : 香榭丽都
         */

        private String id;
        private String name;

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
