package com.jinke.housekeeper.bean;

/**
 * Created by root on 17-1-12.
 */

public class LibDetailsInfo {

    /**
     * obj : {"href":"/Cruiselch/manual/getId?id=1484103336471","title":"小区出入口"}
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
         * href : /Cruiselch/manual/getId?id=1484103336471
         * title : 小区出入口
         */

        private String href;
        private String title;

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
