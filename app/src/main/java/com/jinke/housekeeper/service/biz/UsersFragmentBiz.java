package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.service.listener.UsersFragmentListener;
import com.jinke.housekeeper.service.listener.userPushListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/12.
 */

public interface UsersFragmentBiz {
    void getLoginOut(SortedMap<String, String> map, UsersFragmentListener listener);

    void userPush(SortedMap<String, String> map, userPushListener listener);
}
