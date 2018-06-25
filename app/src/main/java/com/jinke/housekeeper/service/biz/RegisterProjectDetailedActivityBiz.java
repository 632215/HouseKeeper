package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.presenter.RegisterProjectDetailedActivityPresenter;
import com.jinke.housekeeper.service.listener.RegisterProjectDetailedActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface RegisterProjectDetailedActivityBiz {
    void getXMList(SortedMap<String, String> map, RegisterProjectDetailedActivityListener listener);
}
