package com.jinke.housekeeper.service.listener;

import com.jinke.housekeeper.bean.LibAllInfo;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface LibAllActivityListener {
    void getScenePageonNext(LibAllInfo info);

    void getScenePageonError(String code, String msg);
}
