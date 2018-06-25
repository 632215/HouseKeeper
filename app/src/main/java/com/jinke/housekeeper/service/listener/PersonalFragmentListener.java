package com.jinke.housekeeper.service.listener;

import com.jinke.housekeeper.bean.StatisticsgrInfo;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface PersonalFragmentListener {
    void getStatisticsgrError(String code, String msg);

    void getStatisticsgrNext(StatisticsgrInfo info);

    void getIndStaSponInsNext(StatisticsgrInfo info);

    void getIndStaSponInsError(String code, String msg);
}
