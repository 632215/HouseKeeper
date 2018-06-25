package com.jinke.housekeeper.saas.equipment.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.equipment.bean.DailyPatrolBean;
import com.jinke.housekeeper.saas.equipment.bean.EmptyBean;
import com.jinke.housekeeper.saas.equipment.bean.HttpResult;
import com.jinke.housekeeper.saas.equipment.bean.NoPointBean;
import com.jinke.housekeeper.saas.equipment.bean.TaskListBean;
import com.jinke.housekeeper.saas.equipment.http.HttpMethods;
import com.jinke.housekeeper.saas.equipment.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.equipment.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestCompleteTaskListener;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestTaskListener;
import com.jinke.housekeeper.saas.equipment.service.DailyPatrolBiz;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/10/11
 */

public class DailyPatrolBizIml implements DailyPatrolBiz {

    private Context mContext;

    public DailyPatrolBizIml(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getStatistics(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<DailyPatrolBean>() {

            @Override
            public void onNext(DailyPatrolBean noPointBean) {
                if (null != noPointBean) {
                    listener.onSuccess(noPointBean);
                } else {
                    listener.onError("001", "获取数据失败");
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getStatistics(new ProgressSubscriber<HttpResult<DailyPatrolBean>>(onNextListener, mContext), map);
    }

    @Override
    public void getTask(Map<String, String> map, final OnRequestTaskListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<TaskListBean>() {

            @Override
            public void onNext(TaskListBean taskListBean) {
                if (null != taskListBean) {
                    listener.onTaskSuccess(taskListBean);
                } else {
                    listener.onError("001", "获取数据失败");
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getTask(new ProgressSubscriber<HttpResult<TaskListBean>>(onNextListener, mContext), map);
    }

    @Override
    public void completeTask(Map<String, String> map, final OnRequestCompleteTaskListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<EmptyBean>() {

            @Override
            public void onNext(EmptyBean emptyBean) {
                listener.completeTaskSuccess(emptyBean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().completeTask(new ProgressSubscriber<HttpResult<EmptyBean>>(onNextListener, mContext), map);
    }

}
