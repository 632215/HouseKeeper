package com.jinke.housekeeper.saas.report.service.listener;

import com.jinke.housekeeper.saas.report.bean.MapPointBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface MapActivityListener {
    void getMapPointOnNext(MapPointBean info);

    void getMapPointOnError(String code, String msg);
}
