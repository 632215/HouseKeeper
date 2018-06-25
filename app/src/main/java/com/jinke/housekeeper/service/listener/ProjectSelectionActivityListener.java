package com.jinke.housekeeper.service.listener;

import com.jinke.housekeeper.bean.CountXMListInfo;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface ProjectSelectionActivityListener {
    void getCountXMListonNext(CountXMListInfo info);

    void getCountXMListonErrort(String code, String msg);
}
