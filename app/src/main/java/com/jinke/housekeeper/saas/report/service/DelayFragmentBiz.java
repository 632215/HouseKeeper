package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.DelayFragmentListener;
import com.lidroid.xutils.http.RequestParams;

/**
 * Created by Administrator on 2017/9/18.
 */

public interface DelayFragmentBiz {
    void getappHandleData(RequestParams params, DelayFragmentListener listener);

}
