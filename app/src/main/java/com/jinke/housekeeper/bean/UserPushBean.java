package com.jinke.housekeeper.bean;

/**
 * author : huominghao
 * date : 2018/2/2 0002
 * function : 配置用户开关。不可关闭的推送模块无论怎么配置都要发送通知；可关闭的推送模块看用户的设置，如果设置为关闭则不接受通知，如果没有配置或者设置可接收则发送通知
 */

public class UserPushBean {


    /**
     * state : Y
     */

    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
