package com.jinke.housekeeper.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinke.housekeeper.saas.report.util.NetWorksUtils;
import com.jinke.housekeeper.saas.report.util.ToastUtils;

import butterknife.ButterKnife;

/**
 * Created by root on 17-7-7.
 */

public abstract class BaseFragmentV4<V, T extends BasePresenter<V>> extends Fragment {
    protected BaseActivity mActivity;
    public T presenter;

    //获取布局文件ID
    protected abstract int getLayoutId();

    public boolean isConnected = false;

    protected abstract void initView(View view, Bundle savedInstanceState);

    //获取宿主Activity
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isConnected = NetWorksUtils.isConnected(getActivity());
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        presenter = initPresenter();
        if (null != presenter)
            presenter.attach((V) this);
        initView(view, savedInstanceState);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != presenter)
            presenter.dettach();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != presenter)
            presenter.attach((V) this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public abstract T initPresenter();

    public void showToast(String msg) {
        ToastUtils.showLong(getActivity(), msg);
    }


}
