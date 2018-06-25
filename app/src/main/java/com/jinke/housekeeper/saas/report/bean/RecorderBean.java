package com.jinke.housekeeper.saas.report.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/12.
 */
public class RecorderBean implements Serializable {
    private String time;
    private String filePath;
    private String playState = "pause";

    public String getPlayState() {
        return playState;
    }

    public void setPlayState(String playState) {
        this.playState = playState;
    }

    public RecorderBean(String time, String filePath) {
        this.time = time;
        this.filePath = filePath;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}