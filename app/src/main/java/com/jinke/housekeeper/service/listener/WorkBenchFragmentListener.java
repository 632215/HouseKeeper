package com.jinke.housekeeper.service.listener;

import com.jinke.housekeeper.bean.OpenIdBean;

/**
 * Created by Administrator on 2017/9/6.
 */

public interface WorkBenchFragmentListener {
    void getMapPointNext(OpenIdBean info);

    void getMapPointError(String code, String msg);
}
