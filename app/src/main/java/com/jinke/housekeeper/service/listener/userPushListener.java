package com.jinke.housekeeper.service.listener;

import com.jinke.housekeeper.bean.UserPushBean;

/**
 * author : huominghao
 * date : 2018/2/2 0002
 * function :
 */

public interface userPushListener {

    void userPushError(String code, String msg);

    void userPushNext(UserPushBean info);
}
