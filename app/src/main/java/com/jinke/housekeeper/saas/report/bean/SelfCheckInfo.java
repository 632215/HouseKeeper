package com.jinke.housekeeper.saas.report.bean;

import java.io.File;

/**
 * Created by root on 6/01/17.
 */

public class SelfCheckInfo {
    private File photoFile;
    private File voiceFile;
    private String sceneId;    //	string	场景标准ID
    private String orgId;//是	string	项目ID
    private String areaId;//是	string	关键区域ID
    private String superviseDescribe;    //是	string	检查描述
    private String isown;    //是	string	是否自己整改 0：否 1：是
    private String longitude;    //是	double	经度
    private String latitude;    //是	double	纬度
    private String mapName;//
    private String inspTypeId;//报事来源(122 巡检报事123 巡更报事124 设备报事126 大社区报事127 400报事128 前台报事148 公众号报事)
    private String roomId;//房屋id
    private String roomNum;//房屋号

    public void setInspTypeId(String inspTypeId) {
        this.inspTypeId = inspTypeId;
    }

    public String getInspTypeId() {
        return inspTypeId;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }


    public File getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(File photoFile) {
        this.photoFile = photoFile;
    }

    public File getVoiceFile() {
        return voiceFile;
    }

    public void setVoiceFile(File voiceFile) {
        this.voiceFile = voiceFile;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getSuperviseDescribe() {
        return superviseDescribe;
    }

    public void setSuperviseDescribe(String superviseDescribe) {
        this.superviseDescribe = superviseDescribe;
    }

    public String getIsown() {
        return isown;
    }

    public void setIsown(String isown) {
        this.isown = isown;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
}
