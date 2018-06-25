package com.jinke.housekeeper.saas.report.bean;

/**
 * @description 录音/文字输入实体类
 * @time 2017/3/9 11:04
 */
public class TVRecord {
    String text;//文字消息
    String filePath;//文件路径

    public TVRecord(String text, String filePath) {
        this.text = text;
        this.filePath = filePath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}