package com.jinke.housekeeper.service.listener;

import com.jinke.housekeeper.bean.RegisterProjectBean;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface RegisterProjectActivityListener {
    void getXMListonNext(RegisterProjectBean info);

    void getXMListonError(String code, String msg);
}
