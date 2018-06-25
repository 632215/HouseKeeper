package com.jinke.housekeeper.saas.equipment.view;

import com.jinke.housekeeper.saas.equipment.bean.ParameterBean;
import com.jinke.housekeeper.saas.equipment.bean.ReadWatchBean;

/**
 * function:
 * author: hank
 * date: 2017/11/7
 */

public interface ThreeTableView {

    void readWatchSuccess(ReadWatchBean readWatchBean);

    void getParameterSuccess(ParameterBean parameterBean);

    void completeTaskSuccess();

    void showLoading();

    void onError(String msg);
}
