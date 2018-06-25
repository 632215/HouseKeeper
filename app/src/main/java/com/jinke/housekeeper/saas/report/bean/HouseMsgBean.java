package com.jinke.housekeeper.saas.report.bean;

import java.util.List;

/**
 * author : huominghao
 * date : 2018/4/21 0021
 * function :
 */

public class HouseMsgBean {

    private List<ListObjBean> listObj;

    public List<ListObjBean> getListObj() {
        return listObj;
    }

    public void setListObj(List<ListObjBean> listObj) {
        this.listObj = listObj;
    }

    public static class ListObjBean {
        /**
         * id : 1
         * name : 公区报事
         * ownerName : 张珊珊
         * namownerHonee : 135265525
         */

        private String id;
        private String name;
        private String ownerName;
        private String namownerHonee;

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

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getNamownerHonee() {
            return namownerHonee;
        }

        public void setNamownerHonee(String namownerHonee) {
            this.namownerHonee = namownerHonee;
        }
    }
}
