package com.jinke.housekeeper.saas.patrol.view;

import com.jinke.housekeeper.saas.patrol.bean.SignPatrolListBean;

/**
 * function:
 * author: hank
 * date: 2017/7/28
 */

public interface PatrolBeganView {

    void showLoading();

    void showMessage();

    void onSuccess(SignPatrolListBean bean);
}
