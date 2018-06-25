package com.jinke.housekeeper.service.listener;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface PwdActivityListener {
    void getUpdatePwdError(String code, String msg);

    void getUpdatePwdNext();
}
