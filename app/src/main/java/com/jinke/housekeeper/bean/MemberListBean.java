package com.jinke.housekeeper.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 32 on 2017/1/9.
 */
public class MemberListBean implements Serializable{

    private List<ObjBean> obj;
    private List<UserBean> user;

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public static class ObjBean implements Serializable{
        /**
         * name : 客服管理
         * ulist : [{"id":1486611351587,"nickName":"客服管理","checkStatus":"Y","fax":"职位","phone":"18888888888","scenes":"客服管理","org":"香榭丽都"}]
         */

        private String name;
        private List<UlistBean> ulist;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<UlistBean> getUlist() {
            return ulist;
        }

        public void setUlist(List<UlistBean> ulist) {
            this.ulist = ulist;
        }

        public static class UlistBean implements Serializable {
            /**
             * id : 1486611351587
             * nickName : 客服管理
             * checkStatus : Y
             * fax : 职位
             * phone : 18888888888
             * scenes : 客服管理
             * org : 香榭丽都
             */

            private long id;
            private String nickName;
            private String checkStatus;
            private String fax;
            private String phone;
            private String scenes;
            private String org;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getCheckStatus() {
                return checkStatus;
            }

            public void setCheckStatus(String checkStatus) {
                this.checkStatus = checkStatus;
            }

            public String getFax() {
                return fax;
            }

            public void setFax(String fax) {
                this.fax = fax;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getScenes() {
                return scenes;
            }

            public void setScenes(String scenes) {
                this.scenes = scenes;
            }

            public String getOrg() {
                return org;
            }

            public void setOrg(String org) {
                this.org = org;
            }
        }
    }

    public static class UserBean implements Serializable{
        /**
         * id : 1486611351587
         * nickName : 客服管理
         * checkStatus : Y
         * fax : 职位
         * phone : 18888888888
         * scenes : 客服管理
         * org : 香榭丽都
         */

        private long id;
        private String nickName;
        private String checkStatus;
        private String fax;
        private String phone;
        private String scenes;
        private String org;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(String checkStatus) {
            this.checkStatus = checkStatus;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getScenes() {
            return scenes;
        }

        public void setScenes(String scenes) {
            this.scenes = scenes;
        }

        public String getOrg() {
            return org;
        }

        public void setOrg(String org) {
            this.org = org;
        }
    }
}
