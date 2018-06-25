package com.jinke.housekeeper.saas.patrol.listener;

/**
 * function:
 * author: hank
 * date: 2017/7/25
 */

public interface IPunchCardListener {
    
    void onBackPunchCard();

    void onError(String code, String msg);
}
