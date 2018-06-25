package com.jinke.housekeeper.saas.report.service;
import com.jinke.housekeeper.saas.report.service.listener.QualityInspectionActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface QualityInspectionActivityBiz {
    void gettodayWork(SortedMap<String, String> map, QualityInspectionActivityListener listener);
    void gettodayChange(SortedMap<String, String> map, QualityInspectionActivityListener listener);
}
