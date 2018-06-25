package com.jinke.housekeeper.saas.equipment.precenter;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.equipment.base.BasePresenter;
import com.jinke.housekeeper.saas.equipment.bean.DailyPatrolBean;
import com.jinke.housekeeper.saas.equipment.bean.EmptyBean;
import com.jinke.housekeeper.saas.equipment.bean.TaskListBean;
import com.jinke.housekeeper.saas.equipment.config.EquipmentConfig;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestCompleteTaskListener;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestTaskListener;
import com.jinke.housekeeper.saas.equipment.service.DailyPatrolBiz;
import com.jinke.housekeeper.saas.equipment.service.impl.DailyPatrolBizIml;
import com.jinke.housekeeper.saas.equipment.view.DailyPatrolView;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/10/11
 */

public class DailyPatrolPresenter extends BasePresenter<DailyPatrolView>
        implements OnRequestListener<DailyPatrolBean>, OnRequestTaskListener<TaskListBean> , OnRequestCompleteTaskListener<EmptyBean> {

    private DailyPatrolBiz requestBiz;

    public DailyPatrolPresenter(Context mContext) {
        requestBiz = new DailyPatrolBizIml(mContext);
    }

    /**
     * 获取巡检统计数据
     *
     * @param map
     */
    public void getStatistics(Map<String, String> map) {
        if (null != mView) {
            if (null != EquipmentConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("projectId", EquipmentConfig.getProjectId());
                map.put("token", EquipmentConfig.getTokenBean().getToken());
                requestBiz.getStatistics(map, this);
            }else {
                mView.onError("err:000");//token为空
            }
        }
    }

    /**
     * 获取当前部门下的所有任务
     *
     * @param map
     */
    public void getTask(Map<String, String> map) {
        if (null != mView) {
            if (null != EquipmentConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("projectId", EquipmentConfig.getProjectId());
                map.put("token", EquipmentConfig.getTokenBean().getToken());
                requestBiz.getTask(map, this);
            }else {
                mView.onError("err:000");//token为空
            }
        }
    }

    /**
     * 上传巡检数据
     *
     * @param map resultList
     */
    public void completeTask(Map<String, String> map) {
        if (null != mView) {
            if (null != EquipmentConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("projectId", EquipmentConfig.getProjectId());
                map.put("token", EquipmentConfig.getTokenBean().getToken());
                requestBiz.completeTask (map, this);
            }else {
                mView.onError("err:000");//token为空
            }
        }
    }

    @Override
    public void onSuccess(DailyPatrolBean dailyPatrolBean) {
        if (null != mView) {
            mView.getStatisticsSuccess(dailyPatrolBean);
        }
    }

    @Override
    public void onTaskSuccess(TaskListBean taskListBean) {
        if (null != mView) {
            mView.getTaskSuccess(taskListBean);
        }
    }

    @Override
    public void completeTaskSuccess(EmptyBean emptyBean) {
        if (null != mView) {
            mView.completeTaskSuccess();
        }
    }

    @Override
    public void onError(String code, String msg) {
        if (null != mView)
            mView.onError(msg);
    }
}
