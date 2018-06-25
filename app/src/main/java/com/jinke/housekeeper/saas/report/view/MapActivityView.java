package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.saas.report.bean.MapPointBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface MapActivityView {
    void getMapPointOnNext(MapPointBean info);

    void getMapPointOnError(String code, String msg);
}
