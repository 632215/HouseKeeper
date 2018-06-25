package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.presenter.RegisterFirmActivityPresenter;
import com.jinke.housekeeper.service.listener.RegisterFirmActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface RegisterFirmActivityBiz {
    void getXMList(SortedMap<String, String> map, RegisterFirmActivityListener listener);
}
