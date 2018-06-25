package com.jinke.housekeeper.saas.equipment.http;


import com.jinke.housekeeper.saas.equipment.bean.AddPointCallBackBean;
import com.jinke.housekeeper.saas.equipment.bean.DailyPatrolBean;
import com.jinke.housekeeper.saas.equipment.bean.DeviceInfoBean;
import com.jinke.housekeeper.saas.equipment.bean.DeviceTypeBean;
import com.jinke.housekeeper.saas.equipment.bean.ElectricDataBean;
import com.jinke.housekeeper.saas.equipment.bean.ElectricMonthData;
import com.jinke.housekeeper.saas.equipment.bean.EmptyBean;
import com.jinke.housekeeper.saas.equipment.bean.HttpResult;
import com.jinke.housekeeper.saas.equipment.bean.NoPointBean;
import com.jinke.housekeeper.saas.equipment.bean.ParameterBean;
import com.jinke.housekeeper.saas.equipment.bean.ReadWatchBean;
import com.jinke.housekeeper.saas.equipment.bean.TaskListBean;
import com.jinke.housekeeper.saas.equipment.bean.TokenBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


public interface AppService {
    /**
     * 获取token以及用户信息
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/getToken")
    Observable<HttpResult<TokenBean>> getToken(@FieldMap Map<String, String> map);

    /**
     * 获取是否有未添加的设备
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/getNoPoint")
    Observable<HttpResult<NoPointBean>> getNoPoint(@FieldMap Map<String, String> map);

    /**
     * 获取设备类型
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/getDeviceType")
    Observable<HttpResult<DeviceTypeBean>> getDeviceType(@FieldMap Map<String, String> map);

    /**
     * 获取设备详情
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/deviceInfo")
    Observable<HttpResult<DeviceInfoBean>> getDeviceInfo(@FieldMap Map<String, String> map);

    /**
     * 绑定巡检设备
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/addPoint")
    Observable<HttpResult<AddPointCallBackBean>> getAddPoint(@FieldMap Map<String, String> map);

    /**
     * 获取巡检统计数据
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/getStatistics")
    Observable<HttpResult<DailyPatrolBean>> getStatistics(@FieldMap Map<String, String> map);

    /**
     * 获取当前部门下的所有任务
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/getTask")
    Observable<HttpResult<TaskListBean>> getTask(@FieldMap Map<String, String> map);

    /**
     * 上传完成的任务
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/completeTask")
    Observable<HttpResult<EmptyBean>> completeTask(@FieldMap Map<String, String> map);

    /**
     * 三表列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/readWatch")
    Observable<HttpResult<ReadWatchBean>> readWatch(@FieldMap Map<String, String> map);

    /**
     * 获取三表详情
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/getParameter")
    Observable<HttpResult<ParameterBean>> getParameter(@FieldMap Map<String, String> map);

    /**
     * 上传完成的任务
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/uploadData")
    Observable<HttpResult<EmptyBean>> uploadData(@FieldMap Map<String, String> map);

    /**
     * 获取每日统计表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/getElectricData")
    Observable<HttpResult<ElectricDataBean>> getElectricData(@FieldMap Map<String, String> map);

    /**
     * 获取每月统计表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/getElectricMonthData")
    Observable<HttpResult<ElectricMonthData>> getElectricMonthData(@FieldMap Map<String, String> map);

}
