package com.jinke.housekeeper.saas.patrol.listener;

/**
 * function:
 * author: hank
 * date: 2017/7/25
 */

public interface ISignOutListener {

    void onSignOut();

    void onError(String code, String msg);
}
