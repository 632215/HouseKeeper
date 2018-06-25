package com.jinke.housekeeper.saas.patrol.bean;

import java.util.List;

/**
 * function:
 * author: hank
 * date: 2017/7/27
 */

public class HourWheelViewModel {

    private String name;
    private List<MinuteWheelViewModel> minuteList;

    public HourWheelViewModel() {
        super();
    }

    public HourWheelViewModel(String name, List<MinuteWheelViewModel> minuteList) {
        super();
        this.name = name;
        this.minuteList = minuteList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MinuteWheelViewModel> getMinuteList() {
        return minuteList;
    }

    public void setMinuteList(List<MinuteWheelViewModel> minuteList) {
        this.minuteList = minuteList;
    }

    @Override
    public String toString() {
        return "Hour [name=" + name + ", minuteList=" + minuteList + "]";
    }
}
