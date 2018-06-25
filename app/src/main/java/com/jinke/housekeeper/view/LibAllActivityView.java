package com.jinke.housekeeper.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.LibAllInfo;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface LibAllActivityView extends BaseView{
    void getScenePageonNext(LibAllInfo info);

    void getScenePageonError(String code, String msg);
}
