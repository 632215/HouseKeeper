package com.jinke.housekeeper.saas.patrol.listener;

/**
 * Created by root on 17-7-24.
 */

public interface IOpenIdListener {

    void onBackOpenID();

    void onError(String Code, String Msg);
}
