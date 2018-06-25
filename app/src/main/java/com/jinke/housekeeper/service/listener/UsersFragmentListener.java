package com.jinke.housekeeper.service.listener;

import com.jinke.housekeeper.bean.AppHandleInfo;

/**
 * Created by Administrator on 2017/9/12.
 */

public interface UsersFragmentListener {

    void getLoinOutonError(String code, String msg);

    void getLoinOutonNext(AppHandleInfo info);
}
