package com.jinke.housekeeper.saas.equipment.config;

import com.jinke.housekeeper.saas.equipment.bean.TokenBean;

/**
 * function:设备巡检相关配置
 * author: hank
 * date: 2017/9/20
 */

public class EquipmentConfig {
    /**
     * 设备巡检平台token
     */
    private static TokenBean tokenBean;

    /**
     * 设备巡检平台projectId项目ID
     */
    private static String projectId;

    /**
     * 设备巡检平台项目人员名称
     */
    private static String userName;
    private static String userID;


    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        EquipmentConfig.userID = userID;
    }

    public static String getProjectName() {
        return projectName;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        EquipmentConfig.userName = userName;
    }

    public static void setProjectName(String projectName) {
        EquipmentConfig.projectName = projectName;
    }

    private static String projectName;

    public static TokenBean getTokenBean() {
        if (null == tokenBean){
            tokenBean = new TokenBean();
        }
        return tokenBean;
    }

    public static void setTokenBean(TokenBean tokenBea) {
        tokenBean = tokenBea;
    }

    public static String getProjectId() {
        return projectId;
    }

    public static void setProjectId(String projectId) {
        EquipmentConfig.projectId = projectId;
    }
}
