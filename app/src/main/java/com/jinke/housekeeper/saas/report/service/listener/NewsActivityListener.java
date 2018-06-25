package com.jinke.housekeeper.saas.report.service.listener;

import com.jinke.housekeeper.saas.report.bean.NoReadBean;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface NewsActivityListener {
    void updateReadStatusNext(NoReadBean info);

    void updateReadStatusError(String code, String msg);
}
