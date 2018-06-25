package com.jinke.housekeeper.saas.equipment.bean;

import java.util.List;

/**
 * function:
 * author: hank
 * date: 2017/11/7
 */

public class ReadWatchBean {

    private List<ListDataBean> listData;

    public List<ListDataBean> getListData() {
        return listData;
    }

    public void setListData(List<ListDataBean> listData) {
        this.listData = listData;
    }

    public static class ListDataBean {
        /**
         * position : 1
         * id : 1509420258004
         * name : 2
         */

        private String position;
        private long id;
        private String name;

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
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
