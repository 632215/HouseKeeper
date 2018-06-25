package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.saas.report.bean.NoReadBean;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface NewsActivityView {
    void updateReadStatusError(String code, String msg);

    void updateReadStatusNext(NoReadBean info);
}
