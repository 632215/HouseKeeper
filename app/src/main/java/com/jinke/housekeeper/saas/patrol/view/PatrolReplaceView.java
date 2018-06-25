package com.jinke.housekeeper.saas.patrol.view;

/**
 * function:
 * author: hank
 * date: 2017/11/13
 */

public interface PatrolReplaceView {
    /**
     * 替换成功
     */
    void replacePointSuccess();

    void showLoading();

    void onError(String msg);
}
