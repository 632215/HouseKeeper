package com.jinke.housekeeper.bean;

/**
 * Created by root on 18-4-16.
 */

public class WorkBenchBean {
    private String name;
    private int icon;

    public WorkBenchBean(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
