package com.jinke.housekeeper.saas.equipment.bean;

import java.util.List;

/**
 * function:
 * author: hank
 * date: 2017/11/18
 */

public class ElectricMonthData {

    /**
     * electricList : [{"y":"0","x":"1"},{"y":"0","x":"2"},{"x":"3","y":"0"},{"x":"4","y":"0"},{"x":"5","y":"0"},{"y":"0","x":"6"},{"x":"7","y":"0"},{"y":"0","x":"8"},{"x":"9","y":"0"},{"y":"380","x":"10"},{"y":"680","x":"11"},{"x":"12","y":"0"}]
     * waterSList : [{"y":"0","x":"1"},{"y":"0","x":"2"},{"y":"0","x":"3"},{"x":"4","y":"0"},{"y":"0","x":"5"},{"x":"6","y":"0"},{"x":"7","y":"0"},{"y":"0","x":"8"},{"y":"0","x":"9"},{"x":"10","y":"0"},{"x":"11","y":"0"},{"x":"12","y":"0"}]
     * electricSList : [{"y":"0","x":"1"},{"x":"2","y":"0"},{"x":"3","y":"0"},{"y":"0","x":"4"},{"x":"5","y":"0"},{"y":"0","x":"6"},{"x":"7","y":"0"},{"x":"8","y":"0"},{"x":"9","y":"0"},{"x":"10","y":"0"},{"x":"11","y":"0"},{"y":"0","x":"12"}]
     * maxSW : 0
     * maxSE : 0
     * maxE : 680
     * waterList : [{"y":"0","x":"1"},{"y":"0","x":"2"},{"y":"0","x":"3"},{"x":"4","y":"0"},{"y":"0","x":"5"},{"y":"0","x":"6"},{"x":"7","y":"0"},{"x":"8","y":"0"},{"x":"9","y":"0"},{"x":"10","y":"0"},{"y":"0","x":"11"},{"y":"0","x":"12"}]
     * maxW : 0
     */

    private double maxSW;
    private double maxSE;
    private double maxE;
    private double maxW;
    private List<PointBean> electricList;
    private List<PointBean> waterSList;
    private List<PointBean> electricSList;
    private List<PointBean> waterList;

    public double getMaxSW() {
        return maxSW;
    }

    public void setMaxSW(double maxSW) {
        this.maxSW = maxSW;
    }

    public double getMaxSE() {
        return maxSE;
    }

    public void setMaxSE(double maxSE) {
        this.maxSE = maxSE;
    }

    public double getMaxE() {
        return maxE;
    }

    public void setMaxE(double maxE) {
        this.maxE = maxE;
    }

    public double getMaxW() {
        return maxW;
    }

    public void setMaxW(double maxW) {
        this.maxW = maxW;
    }

    public List<PointBean> getElectricList() {
        return electricList;
    }

    public void setElectricList(List<PointBean> electricList) {
        this.electricList = electricList;
    }

    public List<PointBean> getWaterSList() {
        return waterSList;
    }

    public void setWaterSList(List<PointBean> waterSList) {
        this.waterSList = waterSList;
    }

    public List<PointBean> getElectricSList() {
        return electricSList;
    }

    public void setElectricSList(List<PointBean> electricSList) {
        this.electricSList = electricSList;
    }

    public List<PointBean> getWaterList() {
        return waterList;
    }

    public void setWaterList(List<PointBean> waterList) {
        this.waterList = waterList;
    }
}
