package com.jinke.housekeeper.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.RegisterProjectBean;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface RegisterFirmActivityView {
    void getXMListonNext(RegisterProjectBean info);

    void getXMListonError(String code, String msg);
}
