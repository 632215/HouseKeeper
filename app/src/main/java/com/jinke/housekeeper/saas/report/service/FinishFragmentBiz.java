package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.service.listener.FinishFragmentListener;
import com.lidroid.xutils.http.RequestParams;

/**
 * Created by Administrator on 2017/9/19.
 */

public interface FinishFragmentBiz {
    void getFileUp(RequestParams params, FinishFragmentListener listener);
}
