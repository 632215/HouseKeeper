package com.jinke.housekeeper.bean;

/**
 * Created by 32 on 2017/1/6.
 */

public class UpdatePwdBean {

    /**
     * data :
     * total : 0
     * errmsg : 成功
     * pageInfo : {"currentPage":1,"order":"asc","pageSize":20,"total":11,"totalPage":1}
     * errcode : 1
     * sessionId : fec9a48b217a4128b0499221ba071fe9
     * userId : 1490060633978
     */

    private String data;
    private int total;
    private String errmsg;
    private String pageInfo;
    private String errcode;
    private String sessionId;
    private String userId;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(String pageInfo) {
        this.pageInfo = pageInfo;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
