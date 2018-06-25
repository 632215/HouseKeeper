package com.jinke.housekeeper.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.StatisticsgrInfo;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface PersonalFragmentView extends BaseView{
    void getStatisticsgrNext(StatisticsgrInfo info);

    void getStatisticsgrError(String code, String msg);

    void getIndStaSponInsNext(StatisticsgrInfo info);

    void getIndStaSponInsError(String code, String msg);
}
