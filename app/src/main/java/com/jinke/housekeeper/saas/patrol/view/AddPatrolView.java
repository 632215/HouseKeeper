package com.jinke.housekeeper.saas.patrol.view;

/**
 * Created by root on 17-7-24.
 */

public interface AddPatrolView {
    /**
     * 添加成功
     */
    void addPatrolSuccess();

    void showLoading();

    void onError(String msg);
}
