package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.presenter.RectProcessActivityPresenter;
import com.jinke.housekeeper.saas.report.service.listener.RectProcessActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface RectProcessActivityBiz {
    void getDetailsData(SortedMap<String, String> map, RectProcessActivityListener listener);
}
