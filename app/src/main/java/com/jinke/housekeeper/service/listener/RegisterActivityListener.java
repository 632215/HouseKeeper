package com.jinke.housekeeper.service.listener;

import com.jinke.housekeeper.bean.TestInfo;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface RegisterActivityListener {
    void getRegisterDataonNext(TestInfo info);

    void getRegisterDataonError(String code, String msg);
}
