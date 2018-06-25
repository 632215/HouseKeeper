package com.jinke.housekeeper.saas.patrol.bean;
/**
 * author : huominghao
 * date : 2018/3/1 0001
 * function :  对比报表数据
 */

public    class ContrastDataBean   {

    /**
     * planName : 重庆分公司
     * toDayLou : 0
     * yesterdayLou : 0
     * flag
     */

    private String planName;
    private String toDayLou;
    private String yesterdayLou;
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getToDayLou() {
        return toDayLou;
    }

    public void setToDayLou(String toDayLou) {
        this.toDayLou = toDayLou;
    }

    public String getYesterdayLou() {
        return yesterdayLou;
    }

    public void setYesterdayLou(String yesterdayLou) {
        this.yesterdayLou = yesterdayLou;
    }
}
