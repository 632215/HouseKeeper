package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.saas.report.bean.TodayChangeInfo;
import com.jinke.housekeeper.saas.report.bean.TodayWorkInfo;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface QualityInspectionActivityView {
    void getTodayWorkOnNext(TodayWorkInfo info);

    void getToDayWorkOnError(String code, String msg);

    void getToDayChangeOnNext(TodayChangeInfo info);

    void getToDayChangeOnError(String code, String msg);
}
