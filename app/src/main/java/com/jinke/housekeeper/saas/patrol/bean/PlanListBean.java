package com.jinke.housekeeper.saas.patrol.bean;

import java.io.Serializable;

/**
 * author : huominghao
 * date : 2018/1/26 0026
 * function :
 */

public class PlanListBean implements Serializable {
    private IsStartBean isStartBean;
    private PointPlanBean.ListDataBean listDataBean;

    public PointPlanBean.ListDataBean getListDataBean() {
        return listDataBean;
    }

    public void setListDataBean(PointPlanBean.ListDataBean listDataBean) {
        this.listDataBean = listDataBean;
    }

    public IsStartBean getIsStartBean() {
        return isStartBean;
    }

    public void setIsStartBean(IsStartBean isStartBean) {
        this.isStartBean = isStartBean;
    }
}
