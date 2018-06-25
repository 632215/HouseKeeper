package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.service.listener.PwdActivityListener;
import com.jinke.housekeeper.service.listener.UsersFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface PwdActivityBiz {
    void getUpdatePwd(SortedMap<String, String> map, PwdActivityListener listener);

    void getLoginOut(SortedMap<String, String> map, UsersFragmentListener listener);

}
