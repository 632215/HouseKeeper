package com.jinke.housekeeper.saas.patrol.http;


import com.jinke.housekeeper.saas.patrol.bean.ContrastDataBean;
import com.jinke.housekeeper.saas.patrol.bean.EmptyBean;
import com.jinke.housekeeper.saas.patrol.bean.HttpResult;
import com.jinke.housekeeper.saas.patrol.bean.IsStartBean;
import com.jinke.housekeeper.saas.patrol.bean.PatrolRecordBean;
import com.jinke.housekeeper.saas.patrol.bean.PointBean;
import com.jinke.housekeeper.saas.patrol.bean.PointDataBean;
import com.jinke.housekeeper.saas.patrol.bean.PointPlanBean;
import com.jinke.housekeeper.saas.patrol.bean.PointProjectDataBean;
import com.jinke.housekeeper.saas.patrol.bean.RemindTimeBean;
import com.jinke.housekeeper.saas.patrol.bean.SignPatrolBean;
import com.jinke.housekeeper.saas.patrol.bean.SignPatrolListBean;
import com.jinke.housekeeper.saas.patrol.bean.SystemTimeBean;
import com.jinke.housekeeper.saas.patrol.bean.TimeDataBean;
import com.jinke.housekeeper.saas.patrol.bean.TimeOutTaskListBean;
import com.jinke.housekeeper.saas.patrol.bean.TokenBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by liukun on 16/3/9.
 */
public interface AppService {
    @FormUrlEncoded
    @POST("app/getToken")
    Observable<HttpResult<TokenBean>> getToken(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/getPointList")
    Observable<HttpResult<PointBean>> getPointList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/getTimeOutTask")
    Observable<HttpResult<List<TimeOutTaskListBean>>> getTimeOutTask(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/addPoint")
    Observable<HttpResult<EmptyBean>> addPoint(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/signPatrol")
    Observable<HttpResult<SignPatrolBean>> signPatrol(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/signOutPatrol")
    Observable<HttpResult<EmptyBean>> signOutPatrol(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/patrolPunchCard")
    Observable<HttpResult<EmptyBean>> patrolPunchCard(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/getPatrolRecord")
    Observable<HttpResult<PatrolRecordBean>> getPatrolRecord(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/getSystemTime")
    Observable<HttpResult<SystemTimeBean>> getSystemTime(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/ifSignOut")
    Observable<HttpResult<SignPatrolListBean>> getIfSignOut(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/delPoint")
    Observable<HttpResult<EmptyBean>> delPoint(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/getRemindTime")
    Observable<HttpResult<RemindTimeBean>> getRemindTime(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/replacePoint")
    Observable<HttpResult<EmptyBean>> replacePoint(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/getTaskInfo")
    Observable<HttpResult<PointPlanBean>> getTaskInfo(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/isStart")
    Observable<HttpResult<IsStartBean>> isStart(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/pointData")
    Observable<HttpResult<List<PointDataBean>>> pointData(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/timeData")
    Observable<HttpResult<List<TimeDataBean>>> timeData(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/pointProjectData")
    Observable<HttpResult<List<PointProjectDataBean>>> pointProjectData(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("app/contrastData")
    Observable<HttpResult<List<ContrastDataBean>>> contrastData(@FieldMap Map<String, String> map);

}
