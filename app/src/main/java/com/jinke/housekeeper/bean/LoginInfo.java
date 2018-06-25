package com.jinke.housekeeper.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 5/01/17.
 */

public class LoginInfo implements Serializable {

    /**
     * userId : 1490060633984
     * sessionId : dcadd96abe6f4534988d505620d27805
     * username : cq04
     * name : 韩斌
     * mobile : 13667629670
     * power : -1
     * type : 0
     * roleType : 1
     * roles : [{"roleCode":1471828189174,"roleName":"公司总部领导"},{"roleCode":1470919027857,"roleName":"公司总部负责人"},{"roleCode":1477453625547,"roleName":"公司总部物管中心"},{"roleCode":1477453625550,"roleName":"公司总部接管中心"}]
     * organization : [{"orgCode":"0","orgName":"金科物业服务有限公司","type":"0"}]
     * organization2 : {"orgCode":"6","orgName":"北京廊桥水岸"}
     * deptName : 工程管理
     * commpany : 金科物业服务有限公司
     * fenCompany :
     * sex : 男
     * position :
     */

    private long userId;
    private String sessionId;
    private String username;
    private String name;
    private String mobile;
    private String power;
    private String type;
    private String roleType;
    private Organization2Bean organization2;
    private String deptName;
    private String commpany;
    private String fenCompany;
    private String sex;
    private String position;
    private List<RolesBean> roles;
    private List<OrganizationBean> organization;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public Organization2Bean getOrganization2() {
        return organization2;
    }

    public void setOrganization2(Organization2Bean organization2) {
        this.organization2 = organization2;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCommpany() {
        return commpany;
    }

    public void setCommpany(String commpany) {
        this.commpany = commpany;
    }

    public String getFenCompany() {
        return fenCompany;
    }

    public void setFenCompany(String fenCompany) {
        this.fenCompany = fenCompany;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<RolesBean> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesBean> roles) {
        this.roles = roles;
    }

    public List<OrganizationBean> getOrganization() {
        return organization;
    }

    public void setOrganization(List<OrganizationBean> organization) {
        this.organization = organization;
    }

    public static class Organization2Bean implements Serializable{
        /**
         * orgCode : 6
         * orgName : 北京廊桥水岸
         */

        private String orgCode;
        private String orgName;

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }
    }

    public static class RolesBean implements Serializable{
        /**
         * roleCode : 1471828189174
         * roleName : 公司总部领导
         */

        private long roleCode;
        private String roleName;

        public long getRoleCode() {
            return roleCode;
        }

        public void setRoleCode(long roleCode) {
            this.roleCode = roleCode;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }
    }

    public static class OrganizationBean implements Serializable{
        /**
         * orgCode : 0
         * orgName : 金科物业服务有限公司
         * type : 0
         */

        private String orgCode;
        private String orgName;
        private String type;

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
