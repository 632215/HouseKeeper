package com.jinke.housekeeper.bean;

import java.util.List;

/**
 * Created by hank on 2017/12/18 0018.
 */

public class MailListBean {

    private List<ListObjBean> listObj;

    public List<ListObjBean> getListObj() {
        return listObj;
    }

    public void setListObj(List<ListObjBean> listObj) {
        this.listObj = listObj;
    }

    public static class ListObjBean {
        /**
         * orgName : 涪陵黄金海岸
         * userList : [{"id":"1476940667977","userName":"游元明","userFax":"项目主管","userPhone":"13883932767"},{"id":"1502155256128","userName":"侯亚容","userFax":"","userPhone":""},{"id":"1502155256130","userName":"施慧灵","userFax":"","userPhone":""},{"id":"1502155256132","userName":"常文馨","userFax":"","userPhone":""},{"id":"1502155256134","userName":"张燕","userFax":"","userPhone":""},{"id":"1502155256136","userName":"张健","userFax":"","userPhone":""},{"id":"1490060633978","userName":"韩斌","userFax":"","userPhone":"13111111111"},{"id":"1490147119144","userName":"韩斌","userFax":"","userPhone":"13111111111"},{"id":"1502360241820","userName":"张燕","userFax":"","userPhone":"15223875496"},{"id":"1502360241821","userName":"刘德玉","userFax":"客服主管","userPhone":"15123617373"},{"id":"1502360241822","userName":"常文馨","userFax":"客服助理","userPhone":"13658267309"},{"id":"1502360241823","userName":"汪锦芳","userFax":"客服助理","userPhone":"15803660520"},{"id":"1502360241824","userName":"袁昌合","userFax":"班长","userPhone":"15823655254"},{"id":"1502360241826","userName":"杨洪","userFax":"班长","userPhone":"15520173887"},{"id":"1502360241829","userName":"施慧灵","userFax":"","userPhone":"15803677705"},{"id":"1502360241830","userName":"王敏","userFax":"","userPhone":"13272608248"},{"id":"1502360241832","userName":"陈兴惠","userFax":"","userPhone":"18996814125"},{"id":"1502360241833","userName":"潘玉林","userFax":"","userPhone":"13452520721"},{"id":"1502360241834","userName":"罗敏","userFax":"","userPhone":"18723862393"},{"id":"1502360241836","userName":"夏祥惠","userFax":"工程主管","userPhone":"13594523088"},{"id":"1502360241837","userName":"夏祥惠","userFax":"工程主管","userPhone":"13594523088"},{"id":"1502360241838","userName":"张健","userFax":"客服助理","userPhone":"13996857866"},{"id":"1476940667894","userName":"罗敏","userFax":"客服助理","userPhone":"18723862393"},{"id":"1490060633980","userName":"韩斌02","userFax":"","userPhone":"13667629670"},{"id":"1502360241905","userName":"陈兴恵","userFax":"","userPhone":"18996814125"},{"id":"1502360241835","userName":"黄明","userFax":"维修班长","userPhone":"13996802627"},{"id":"1502360241912","userName":"黄明","userFax":"","userPhone":"13996802627"},{"id":"1476940667888","userName":"夏祥惠","userFax":"工程管理","userPhone":"13594523088"},{"id":"1476940667980","userName":"柴瑞","userFax":"智能化管理员","userPhone":"15330511717"},{"id":"1493197227345","userName":"张勇","userFax":"绿化班长","userPhone":"18723862687"},{"id":"1476940667887","userName":"潘玉林","userFax":"见习协管队长、S50储备经理","userPhone":"13452520721"},{"id":"1476940667897","userName":"刘德玉（产假中）","userFax":"客服主管、S50储备经理","userPhone":"15123617373"},{"id":"1476940667890","userName":"汪锦芳","userFax":"客服助理","userPhone":"15803660520"},{"id":"1502360242040","userName":"李渝","userFax":"中控室","userPhone":"18896062869"},{"id":"1502360312433","userName":"吴兰","userFax":"队员","userPhone":"15826294887"},{"id":"1490261183975","userName":"简传宝","userFax":"","userPhone":"13667629670"},{"id":"1484019079178","userName":"周银","userFax":"","userPhone":"13912386719"},{"id":"1502360383229","userName":"张俞","userFax":"维修","userPhone":"13996539671"},{"id":"1476940667896","userName":"郭金芬","userFax":"保洁主管","userPhone":"13594597773"}]
         */

        private String orgName;
        private List<UserListBean> userList;

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public List<UserListBean> getUserList() {
            return userList;
        }

        public void setUserList(List<UserListBean> userList) {
            this.userList = userList;
        }

        public static class UserListBean {
            /**
             * id : 1476940667977
             * userName : 游元明
             * userFax : 项目主管
             * userPhone : 13883932767
             */

            private String id;
            private String userName;
            private String userFax;
            private String userPhone;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserFax() {
                return userFax;
            }

            public void setUserFax(String userFax) {
                this.userFax = userFax;
            }

            public String getUserPhone() {
                return userPhone;
            }

            public void setUserPhone(String userPhone) {
                this.userPhone = userPhone;
            }
        }
    }
}
