package com.jinke.housekeeper.saas.report.service.listener;

import com.jinke.housekeeper.saas.report.bean.SelfHistoryBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface PersonYesterdayFragmentListener {
    void getSelfHistoryListonNext(SelfHistoryBean bean);

    void getSelfHistoryListonError(String code, String msg);
}
