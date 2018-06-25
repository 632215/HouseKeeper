package com.jinke.housekeeper.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.service.biz.MemberDetailsActivityBiz;
import com.jinke.housekeeper.service.impl.MemberDetailsActivityImpl;
import com.jinke.housekeeper.service.listener.MemberDetailsActivityListener;
import com.jinke.housekeeper.view.MemberDetailsActivityView;

/**
 * Created by Administrator on 2017/9/7.
 */

public class MemberDetailsActivityPresenter extends BasePresenter<MemberDetailsActivityView> implements MemberDetailsActivityListener {
    private Context context;
    private MemberDetailsActivityBiz biz;

    public MemberDetailsActivityPresenter(Context context) {
        this.context = context;
        biz =new MemberDetailsActivityImpl(context);

    }
}
