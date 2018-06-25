package com.jinke.housekeeper.saas.report.service.listener;

import com.jinke.housekeeper.saas.report.bean.KeyPointBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface KeyPointsActivityListener {
    void getGJContentDataOnNext(KeyPointBean info);

    void getGJContentDataOnError(String code, String msg);
}
