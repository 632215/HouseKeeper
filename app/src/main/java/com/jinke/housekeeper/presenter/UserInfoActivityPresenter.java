package com.jinke.housekeeper.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.service.biz.UserInfoActivityBiz;
import com.jinke.housekeeper.service.impl.UserInfoActivityImpl;
import com.jinke.housekeeper.service.listener.UserInfoActivityListener;
import com.jinke.housekeeper.view.UserInfoActivityView;

/**
 * Created by Administrator on 2017/9/7.
 */

public class UserInfoActivityPresenter extends BasePresenter<UserInfoActivityView> implements UserInfoActivityListener {
    private Context context;
    private UserInfoActivityBiz biz;

    public UserInfoActivityPresenter(Context context) {
        this.context = context;
        biz = new UserInfoActivityImpl(context);
    }
}
