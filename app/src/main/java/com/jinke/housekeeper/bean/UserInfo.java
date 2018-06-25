package com.jinke.housekeeper.bean;

import java.io.Serializable;

/**
 * Created by root on 5/01/17.
 */

public class UserInfo implements Serializable {
    private String userName;
    private String passWord;
    private String userId;
    private String sessionid;
    private String name;
    private String roleType;
    private String type;
    private String orgCodel;
    private String orgName;
    private String power;
    private String mobile;
    private String deptName;
    private String commpany;
    private String fenCompany;
    private String position;
    private String sex;


    private String leftOrgId;
    private String leftOrgName;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLeftOrgName() {
        return leftOrgName;
    }

    public void setLeftOrgName(String leftOrgName) {
        this.leftOrgName = leftOrgName;
    }

    public String getLeftOrgId() {
        return leftOrgId;
    }

    public void setLeftOrgId(String leftOrgId) {
        this.leftOrgId = leftOrgId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCodel() {
        return orgCodel;
    }

    public void setOrgCodel(String orgCodel) {
        this.orgCodel = orgCodel;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 职务
     */
    private String roles;
    /**
     * 项目名称
     */
    private String organization;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
