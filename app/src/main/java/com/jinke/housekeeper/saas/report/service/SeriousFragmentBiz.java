package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.SeriousFragmentListener;
import com.lidroid.xutils.http.RequestParams;

/**
 * Created by Administrator on 2017/9/20.
 */

public interface SeriousFragmentBiz {
    void getappHandleData(RequestParams params, SeriousFragmentListener listener);
}
