package com.jinke.housekeeper.saas.report.bean;

import java.util.List;

/**
 * Created by root on 1/9/17.
 */

public class AnnounceListInfo {


    private List<AnnounceBean> announce;

    public List<AnnounceBean> getAnnounce() {
        return announce;
    }

    public void setAnnounce(List<AnnounceBean> announce) {
        this.announce = announce;
    }

    public static class AnnounceBean {
        /**
         * id : 1482457741818
         * title : 中国军人零下27度严寒仍坚持训练2
         * issuetime : 2016-12-23
         */

        private long id;
        private String title;
        private String issuetime;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIssuetime() {
            return issuetime;
        }

        public void setIssuetime(String issuetime) {
            this.issuetime = issuetime;
        }
    }
}
