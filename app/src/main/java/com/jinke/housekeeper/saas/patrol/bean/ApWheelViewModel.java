package com.jinke.housekeeper.saas.patrol.bean;

import java.util.List;

/**
 * function:
 * author: hank
 * date: 2017/7/27
 */

public class ApWheelViewModel {
    private String name;
    private List<HourWheelViewModel> hourList;

    public ApWheelViewModel() {
        super();
    }

    public ApWheelViewModel(String name, List<HourWheelViewModel> hourList) {
        super();
        this.name = name;
        this.hourList = hourList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HourWheelViewModel> getHourList() {
        return hourList;
    }

    public void setHourList(List<HourWheelViewModel> hourList) {
        this.hourList = hourList;
    }

    @Override
    public String toString() {
        return "AP [name=" + name + ", hourList=" + hourList + "]";
    }
}
