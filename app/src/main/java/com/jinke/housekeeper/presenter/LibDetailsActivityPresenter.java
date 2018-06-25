package com.jinke.housekeeper.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.LibDetailsInfo;
import com.jinke.housekeeper.service.impl.LibDetailsActivityImpl;
import com.jinke.housekeeper.service.listener.LibDetailsActivityListener;
import com.jinke.housekeeper.view.LibDetailsActivityView;
import com.jinke.housekeeper.service.biz.LibDetailsActivityBiz;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public class LibDetailsActivityPresenter extends BasePresenter<LibDetailsActivityView> implements LibDetailsActivityListener {
    private Context context;
    private LibDetailsActivityBiz biz;


    public LibDetailsActivityPresenter(Context context) {
        biz = new LibDetailsActivityImpl(context);
        this.context = context;
    }

    public void getDetailsData(SortedMap<String, String> map) {
        biz.getDetailsData(map, this);
    }

    @Override
    public void getDetailsDataonNext(LibDetailsInfo info) {
        if (mView != null)
            mView.getDetailsDataonNext(info);
    }

    @Override
    public void getDetailsDataonError(String code, String msg) {
        if (mView != null)
            mView.getDetailsDataonError(code, msg);
    }
}
