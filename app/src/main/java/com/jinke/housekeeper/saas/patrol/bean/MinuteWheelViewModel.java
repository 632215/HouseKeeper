package com.jinke.housekeeper.saas.patrol.bean;

/**
 * function:
 * author: hank
 * date: 2017/7/27
 */

public class MinuteWheelViewModel {

    private String name;

    public MinuteWheelViewModel() {
        super();
    }

    public MinuteWheelViewModel(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Minute [name=" + name +"]";
    }
}
