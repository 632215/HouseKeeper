package com.jinke.housekeeper.service.listener;

import com.jinke.housekeeper.bean.ScenePageInfo;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface MainsActivityListener {
    void getAllScenePageonError(String code, String msg);

    void getAllScenePageonNext(ScenePageInfo info);
}
