package com.jinke.housekeeper.saas.patrol.bean;

import java.io.Serializable;
import java.util.List;

/**
 * function: 点位信息列表
 * author: hank
 * date: 2017/7/25
 */

public class SavePointListBean implements Serializable {


    private List<SavePointBean> savePointBeans;

    public List<SavePointBean> getPointListBeen() {
        return savePointBeans;
    }

    public void setPointListBeen(List<SavePointBean> savePointBeans) {
        this.savePointBeans = savePointBeans;
    }
}
