package com.jinke.housekeeper.saas.handoverroom.http;


import com.jinke.housekeeper.saas.handoverroom.bean.EmptyBean;
import com.jinke.housekeeper.saas.handoverroom.bean.FindListDataBean;
import com.jinke.housekeeper.saas.handoverroom.bean.FindStateBean;
import com.jinke.housekeeper.saas.handoverroom.bean.HandoverRoomBean;
import com.jinke.housekeeper.saas.handoverroom.bean.HttpResult;
import com.jinke.housekeeper.saas.handoverroom.bean.TokenBean;

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
    @POST("house/getTokenAndUser")
    Observable<HttpResult<TokenBean>> getUserToken(@FieldMap Map<String, String> map);

    /**
     * 获取项目、设备列表接口
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("house/findListData")
    Observable<HttpResult<FindListDataBean>> findListData(@FieldMap Map<String, String> map);

    /**
     * 设备绑定接口
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("house/bindingDevice")
    Observable<HttpResult<EmptyBean>> bindingDevice(@FieldMap Map<String, String> map);

    /**
     * 开始接房流程
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("house/handleHouse")
    Observable<HttpResult<EmptyBean>> handleHouse(@FieldMap Map<String, String> map);

    /**
     * 获取当前接房用户的信息
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("house/getHouseInfo")
    Observable<HttpResult<HandoverRoomBean>> getHouseInfo(@FieldMap Map<String, String> map);

    /**
     * 查询用户接房状态接口
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("house/findState")
    Observable<HttpResult<FindStateBean>> findState(@FieldMap Map<String, String> map);

    /**
     * 结束接房流程
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("house/takeHouse")
    Observable<HttpResult<EmptyBean>> takeHouse(@FieldMap Map<String, String> map);

}
