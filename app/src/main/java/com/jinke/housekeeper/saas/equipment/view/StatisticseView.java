package com.jinke.housekeeper.saas.equipment.view;

import com.jinke.housekeeper.saas.equipment.bean.ElectricDataBean;
import com.jinke.housekeeper.saas.equipment.bean.ElectricMonthData;

/**
 * function:
 * author: hank
 * date: 2017/11/18
 */

public interface StatisticseView {

    void getElectricDataSuccess(ElectricDataBean electricDataBean);

    void getElectricMonthDataSuccess(ElectricMonthData electricMonthData);

    void showLoading();

    void onError(String msg);
}
