package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.saas.report.bean.KeyPointBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface KeyPointsActivityView {
    void getGJContentDataOnNext(KeyPointBean info);

    void getGJContentDataOnError(String code, String msg);
}
