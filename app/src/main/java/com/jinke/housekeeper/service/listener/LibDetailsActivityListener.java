package com.jinke.housekeeper.service.listener;

import com.jinke.housekeeper.bean.LibDetailsInfo;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface LibDetailsActivityListener {
    void getDetailsDataonNext(LibDetailsInfo info);

    void getDetailsDataonError(String code, String msg);
}
