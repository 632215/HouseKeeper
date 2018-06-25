package com.jinke.housekeeper.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.MailListBean;
import com.jinke.housekeeper.bean.MsgBean;
import com.jinke.housekeeper.service.biz.ContactActivityBiz;
import com.jinke.housekeeper.service.impl.ContactActivityBizImpl;
import com.jinke.housekeeper.service.listener.ContactActivityListener;
import com.jinke.housekeeper.service.listener.MsgFragmentListener;
import com.jinke.housekeeper.view.ContactActivityView;

import java.util.SortedMap;


/**
 * Created by Administrator on 2017/9/25.
 */

public class ContactActivityPresenter extends BasePresenter<ContactActivityView> implements ContactActivityListener {
    private Context mContext;
    private ContactActivityBiz mBiz;

    public ContactActivityPresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new ContactActivityBizImpl(mContext);
    }

    public void getMailList(SortedMap<String, String> map) {
//        mView.showLoading();
        mBiz.getMailList(map, this);
    }


    @Override
    public void getMailListNext(MailListBean info) {
//        mView.getMailListNext(info);
    }

    @Override
    public void getMailListError(String code, String msg) {
//        mView.getMailListError(code, msg);
    }
}
