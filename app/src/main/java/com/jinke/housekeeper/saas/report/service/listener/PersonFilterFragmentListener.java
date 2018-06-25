package com.jinke.housekeeper.saas.report.service.listener;

import com.jinke.housekeeper.saas.report.bean.SelfHistoryBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface PersonFilterFragmentListener {
    void getSelfHistoryListonError(String code, String msg);

    void getSelfHistoryListonNext(SelfHistoryBean bean);
}
