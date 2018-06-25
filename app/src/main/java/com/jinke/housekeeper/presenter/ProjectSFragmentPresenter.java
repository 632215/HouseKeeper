package com.jinke.housekeeper.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.ChangTotalInfo;
import com.jinke.housekeeper.bean.StatisticsInfo;
import com.jinke.housekeeper.bean.StatisticstInfo;
import com.jinke.housekeeper.service.biz.ProjectSFragmentBiz;
import com.jinke.housekeeper.service.impl.ProjectSFragmentImpl;
import com.jinke.housekeeper.service.listener.ProjectSFragmentListener;
import com.jinke.housekeeper.view.ProjectSFragmentView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public class ProjectSFragmentPresenter extends BasePresenter<ProjectSFragmentView> implements ProjectSFragmentListener {
    private Context mContext;
    private ProjectSFragmentBiz mBiz;

    public ProjectSFragmentPresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new ProjectSFragmentImpl(mContext);
    }

    /**
     * （1，总部，2分公司，3项目）
     * 第1个列表--巡查统计
     *
     * @param map
     */
    public void getChangTotal(SortedMap<String, String> map) {
        mBiz.getChangTotal(map, this);
    }

    @Override
    public void getChangTotalNext(ChangTotalInfo info) {
        mView.hideLoading();
        mView.getChangTotalNext(info);
    }

    @Override
    public void getChangTotalError(String code, String msg) {
        mView.hideLoading();
        mView.getChangTotalError(code, msg);
    }

    /**
     * 第二个列表
     *
     * @param map
     */
    public void getStatistics(SortedMap<String, String> map) {
        mBiz.getStatistics(map, this);
    }

    @Override
    public void getStatisticsNext(StatisticsInfo info) {
        mView.hideLoading();
        mView.getStatisticsNext(info);
    }

    @Override
    public void getStatisticsError(String code, String msg) {
        mView.hideLoading();
        mView.getStatisticsError(code, msg);
    }

    /**
     * 第三个列表
     *
     * @param map
     */
    public void getStatisticst(SortedMap<String, String> map) {
        mBiz.getStatisticst(map, this);
    }

    @Override
    public void getStatisticstNext(StatisticstInfo info) {
        mView.hideLoading();
        mView.getStatisticstNext(info);
    }

    @Override
    public void getStatisticstError(String code, String msg) {
        mView.hideLoading();
        mView.getStatisticstError(code, msg);
    }
}
