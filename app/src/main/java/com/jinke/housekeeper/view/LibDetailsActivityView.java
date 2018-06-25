package com.jinke.housekeeper.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.LibDetailsInfo;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface LibDetailsActivityView {
    void getDetailsDataonNext(LibDetailsInfo info);

    void getDetailsDataonError(String code, String msg);
}
