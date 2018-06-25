package com.jinke.housekeeper.saas.equipment.bean;

import java.util.List;

/**
 * function:
 * author: hank
 * date: 2017/11/18
 */

public class ElectricDataBean {


    /**
     * waterSList : [{"y":"0","x":"01"},{"x":"02","y":"0"},{"y":"0","x":"03"},{"x":"04","y":"0"},{"y":"0","x":"05"},{"y":"0","x":"06"},{"y":"0","x":"07"},{"y":"0","x":"08"},{"y":"0","x":"09"},{"x":"10","y":"0"},{"x":"11","y":"0"},{"y":"0","x":"12"},{"y":"0","x":"13"},{"x":"14","y":"0"},{"x":"15","y":"0"},{"x":"16","y":"0"},{"y":"0","x":"17"},{"x":"18","y":"0"},{"y":"0","x":"19"},{"y":"0","x":"20"},{"y":"0","x":"21"},{"x":"22","y":"0"},{"y":"0","x":"23"},{"y":"0","x":"24"},{"y":"0","x":"25"},{"x":"26","y":"0"},{"y":"0","x":"27"},{"x":"28","y":"0"},{"x":"29","y":"0"},{"x":"30","y":"0"},{"y":"0","x":"31"}]
     * waterList : [{"x":"01","y":"0"},{"y":"0","x":"02"},{"y":"0","x":"03"},{"y":"0","x":"04"},{"y":"0","x":"05"},{"y":"0","x":"06"},{"x":"07","y":"0"},{"y":"0","x":"08"},{"y":"0","x":"09"},{"x":"10","y":"0"},{"y":"0","x":"11"},{"y":"0","x":"12"},{"x":"13","y":"0"},{"x":"14","y":"0"},{"x":"15","y":"0"},{"y":"0","x":"16"},{"x":"17","y":"0"},{"y":"0","x":"18"},{"y":"0","x":"19"},{"x":"20","y":"0"},{"x":"21","y":"0"},{"y":"0","x":"22"},{"x":"23","y":"0"},{"x":"24","y":"0"},{"x":"25","y":"0"},{"x":"26","y":"0"},{"x":"27","y":"0"},{"x":"28","y":"0"},{"y":"0","x":"29"},{"y":"0","x":"30"}]
     * maxSW : 0
     * maxSE : 380
     * maxE : 680
     * electricList : [{"y":"0","x":"01"},{"y":"0","x":"02"},{"y":"0","x":"03"},{"y":"0","x":"04"},{"x":"05","y":"0"},{"x":"06","y":"0"},{"x":"07","y":"0"},{"y":"0","x":"08"},{"y":"0","x":"09"},{"x":"10","y":"0"},{"y":"0","x":"11"},{"x":"12","y":"0"},{"y":"0","x":"13"},{"x":"14","y":"0"},{"x":"15","y":"420"},{"x":"16","y":"500"},{"y":"620","x":"17"},{"y":"0","x":"18"},{"y":"0","x":"19"},{"x":"20","y":"0"},{"y":"0","x":"21"},{"y":"0","x":"22"},{"y":"0","x":"23"},{"x":"24","y":"0"},{"y":"0","x":"25"},{"y":"0","x":"26"},{"y":"0","x":"27"},{"y":"0","x":"28"},{"x":"29","y":"0"},{"y":"680","x":"30"}]
     * electricSList : [{"y":"300","x":"01"},{"y":"0","x":"02"},{"x":"03","y":"0"},{"y":"0","x":"04"},{"x":"05","y":"0"},{"y":"0","x":"06"},{"y":"0","x":"07"},{"y":"0","x":"08"},{"x":"09","y":"0"},{"x":"10","y":"0"},{"y":"0","x":"11"},{"x":"12","y":"0"},{"y":"0","x":"13"},{"y":"0","x":"14"},{"y":"0","x":"15"},{"y":"0","x":"16"},{"y":"0","x":"17"},{"y":"350","x":"18"},{"y":"0","x":"19"},{"x":"20","y":"0"},{"y":"0","x":"21"},{"x":"22","y":"0"},{"x":"23","y":"0"},{"x":"24","y":"0"},{"x":"25","y":"0"},{"y":"0","x":"26"},{"x":"27","y":"0"},{"x":"28","y":"0"},{"y":"0","x":"29"},{"y":"0","x":"30"},{"y":"380","x":"31"}]
     * maxW : 0
     */

    private int maxSW;
    private int maxSE;
    private int maxE;
    private int maxW;
    private List<PointBean> waterSList;
    private List<PointBean> waterList;
    private List<PointBean> electricList;
    private List<PointBean> electricSList;

    public int getMaxSW() {
        return maxSW;
    }

    public void setMaxSW(int maxSW) {
        this.maxSW = maxSW;
    }

    public int getMaxSE() {
        return maxSE;
    }

    public void setMaxSE(int maxSE) {
        this.maxSE = maxSE;
    }

    public int getMaxE() {
        return maxE;
    }

    public void setMaxE(int maxE) {
        this.maxE = maxE;
    }

    public int getMaxW() {
        return maxW;
    }

    public void setMaxW(int maxW) {
        this.maxW = maxW;
    }

    public List<PointBean> getWaterSList() {
        return waterSList;
    }

    public void setWaterSList(List<PointBean> waterSList) {
        this.waterSList = waterSList;
    }

    public List<PointBean> getWaterList() {
        return waterList;
    }

    public void setWaterList(List<PointBean> waterList) {
        this.waterList = waterList;
    }

    public List<PointBean> getElectricList() {
        return electricList;
    }

    public void setElectricList(List<PointBean> electricList) {
        this.electricList = electricList;
    }

    public List<PointBean> getElectricSList() {
        return electricSList;
    }

    public void setElectricSList(List<PointBean> electricSList) {
        this.electricSList = electricSList;
    }

}
