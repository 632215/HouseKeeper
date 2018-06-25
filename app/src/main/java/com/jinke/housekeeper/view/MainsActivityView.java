package com.jinke.housekeeper.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.ScenePageInfo;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface MainsActivityView {
    void getAllScenePageOnError(String code, String msg);

    void getAllScenePageOnNext(ScenePageInfo info);
}
