package com.jinke.housekeeper.service.listener;


import com.jinke.housekeeper.bean.TestInfo;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface MemberDetailActivityListener {
    void getUserDeleteNext(TestInfo testInfo);

    void getUserDeleteError(String code, String msg);
}
