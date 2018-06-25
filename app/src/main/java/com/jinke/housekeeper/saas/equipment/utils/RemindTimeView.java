package com.jinke.housekeeper.saas.equipment.utils;

import com.jinke.housekeeper.saas.patrol.bean.RemindTimeBean;

/**
 * Created by root on 17-7-24.
 */

public interface RemindTimeView {
    void showLoading();

    void showMessage();

    void onRefreshData(RemindTimeBean remindTimeBean);
}
