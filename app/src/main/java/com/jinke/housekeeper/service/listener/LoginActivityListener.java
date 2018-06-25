package com.jinke.housekeeper.service.listener;

import com.jinke.housekeeper.bean.LoginInfo;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface LoginActivityListener {
    void getUserLoginDataonError(String code, String msg);

    void getUserLoginDataonNext(LoginInfo info);
}
