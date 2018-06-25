package com.jinke.housekeeper.saas.patrol.precenter;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.patrol.base.BasePresenter;
import com.jinke.housekeeper.saas.patrol.bean.PointBean;
import com.jinke.housekeeper.saas.patrol.bean.TokenBean;
import com.jinke.housekeeper.saas.patrol.config.PatrolConfig;
import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.listener.IAddPointListener;
import com.jinke.housekeeper.saas.patrol.service.RequestBiz;
import com.jinke.housekeeper.saas.patrol.service.impl.RequestBizIml;
import com.jinke.housekeeper.saas.patrol.view.PaltrolView;

import java.util.Map;

public class PatrolPresenter extends BasePresenter<PaltrolView> implements OnRequestListener<TokenBean>, IAddPointListener.OnGetPoinListListener<PointBean> {
    private RequestBiz requestBiz;

    public PatrolPresenter() {
        requestBiz = new RequestBizIml(MyApplicaption.getInstance());
    }

    public void getTokenData(Map<String, String> map) {
        if (null != mView)
            mView.showLoading();
        requestBiz.requestForData(map, this);
    }

    public void getPointList(Map<String, String> map) {
        if (null != mView) {
            if (null != PatrolConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", PatrolConfig.getTokenBean().getToken());
                requestBiz.getPointList(map, this);
            } else {
                mView.onError("未获取Token");
            }
        }
    }

    @Override
    public void onSuccess(TokenBean bean) {
        //保存platrol token变量
        if (null != mView) {
            PatrolConfig.setTokenBean(bean);
            mView.showMessage(bean.getToken());
        }
    }


    @Override
    public void OnGetPoinListSuccess(PointBean pointBean) {
        if (null != mView)
            mView.getPointListBean(pointBean.getListData());
    }

    @Override
    public void onError(String code, String msg) {
        if (null != mView)
            mView.onError(msg);
    }
}