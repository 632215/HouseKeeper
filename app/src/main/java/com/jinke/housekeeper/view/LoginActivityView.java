package com.jinke.housekeeper.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.LoginInfo;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface LoginActivityView {
    void getUserLoginDataonError(String code, String msg);

    void getUserLoginDataonNext(LoginInfo info);
}
