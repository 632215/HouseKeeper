package com.jinke.housekeeper.http;


import com.jinke.housekeeper.bean.MailListBean;
import com.jinke.housekeeper.bean.PersonalTaskBean;
import com.jinke.housekeeper.bean.StatisticsgrInfo;
import com.jinke.housekeeper.bean.TestInfo;
import com.jinke.housekeeper.bean.UserPushBean;
import com.jinke.housekeeper.saas.report.bean.AllReportBean;
import com.jinke.housekeeper.saas.report.bean.AnnounceListInfo;
import com.jinke.housekeeper.bean.AppHandleInfo;
import com.jinke.housekeeper.saas.report.bean.KeyPointBean;
import com.jinke.housekeeper.saas.report.bean.MapPointBean;
import com.jinke.housekeeper.bean.OpenIdBean;
import com.jinke.housekeeper.saas.report.bean.NoReadBean;
import com.jinke.housekeeper.saas.report.bean.NodeDelayBean;
import com.jinke.housekeeper.saas.report.bean.NodeDetailBean;
import com.jinke.housekeeper.saas.report.bean.NodeElseBean;
import com.jinke.housekeeper.saas.report.bean.RectifiedBean;
import com.jinke.housekeeper.bean.ScenePageInfo;
import com.jinke.housekeeper.saas.report.bean.SelfHistoryBean;
import com.jinke.housekeeper.saas.report.bean.WaitRectifiedBean;
import com.jinke.housekeeper.bean.ChangTotalInfo;
import com.jinke.housekeeper.saas.report.bean.CheckDetailsInfo;
import com.jinke.housekeeper.bean.CountXMListInfo;
import com.jinke.housekeeper.bean.KnowledgeInfo;
import com.jinke.housekeeper.bean.LibAllInfo;
import com.jinke.housekeeper.bean.LibDetailsInfo;
import com.jinke.housekeeper.bean.LoginInfo;
import com.jinke.housekeeper.bean.MemberListBean;
import com.jinke.housekeeper.saas.report.bean.ProcessDetailBean;
import com.jinke.housekeeper.saas.report.bean.ProcessNodeBean;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;
import com.jinke.housekeeper.bean.RegisterProjectBean;
import com.jinke.housekeeper.bean.EmptyBean;
import com.jinke.housekeeper.bean.StatisticsInfo;
import com.jinke.housekeeper.bean.StatisticstInfo;
import com.jinke.housekeeper.saas.report.bean.TodayChangeInfo;
import com.jinke.housekeeper.saas.report.bean.TodayWorkInfo;
import com.jinke.housekeeper.bean.UpdatePwdBean;
import com.jinke.housekeeper.saas.report.bean.WorkBean;
import com.jinke.housekeeper.saas.report.bean.WorkOrderBean;
import com.jinke.housekeeper.saas.report.bean.WorkToadyInfo;
import com.jinke.housekeeper.bean.MsgBean;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by liukun on 16/3/9.
 */
public interface AppService {
    @FormUrlEncoded
    @POST("Appinterface/XMList")
    Observable<HttpResult<RegisterProjectBean>> getXMList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("Appinterface/CJContent")
    Observable<HttpResult<RegisterDepartmentBean>> getCJContent(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("Appinterface/GJContent")
    Observable<HttpResult<RegisterDepartmentBean>> getgJContent(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/register")
    Observable<HttpResult<TestInfo>> getregister(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("Appinterface/userLogin")
    Observable<HttpResult<LoginInfo>> getuserLogin(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/todayChange")
    Observable<HttpResult<TodayChangeInfo>> gettodayChange(@FieldMap Map<String, String> map, @Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/todayWork")
    Observable<HttpResult<TodayWorkInfo>> gettodayWork(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/announceList")
    Observable<HttpResult<AnnounceListInfo>> getannounceList(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/proAbarGtasks")
    Observable<HttpResult<WorkToadyInfo>> getproAbarGtasks(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/proAbarHisWorking")
    Observable<HttpResult<WorkToadyInfo>> getproAbarHisWorking(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/selfCheckHisWorking")
    Observable<HttpResult<WorkToadyInfo>> getselfCheckHisWorking(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/startInspection")
    Observable<HttpResult<WorkToadyInfo>> getstartInspection(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @Multipart
    @POST("Appinterface/AppUploadImage")
    Observable<HttpResult<WorkToadyInfo>> getAppDownloadImage(@PartMap Map<String, RequestBody> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/getLore")
    Observable<HttpResult<KnowledgeInfo>> getLore(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/ScenePage")
    Observable<HttpResult<LibAllInfo>> getScenePageData(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @POST("Appinterface/AppDownloadImage")
    Observable<HttpResult<WorkToadyInfo>> getAppDownloadImage(@FieldMap MultipartBody.Part file, @FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/updatePwd")
    Observable<HttpResult<UpdatePwdBean>> getUpdatePwd(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/details")
    Observable<HttpResult<CheckDetailsInfo>> getdetails(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/UserList")
    Observable<HttpResult<CheckDetailsInfo>> getUserList(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/checkUserList")
    Observable<HttpResult<MemberListBean>> getMenberList(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/selectUserByScene")
    Observable<HttpResult<RegisterDepartmentBean>> getselectUserByScene(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/appHandle")
    Observable<HttpResult<AppHandleInfo>> getappHandle(@FieldMap Map<String, String> map,@Header("signature") String sign);


    @FormUrlEncoded
    @POST("Appinterface/appTaskSign")
    Observable<HttpResult<AppHandleInfo>> getappTaskSign(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/userLoginOut")
    Observable<HttpResult<AppHandleInfo>> getuserLoginOut(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/userPush")
    Observable<HttpResult<UserPushBean>> userPush(@FieldMap Map<String, String> map, @Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/getKeyId")
    Observable<HttpResult<LibDetailsInfo>> getKeyId(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/UserCheckDelete")
    Observable<HttpResult<TestInfo>> getUserPass(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/processList")
    Observable<HttpResult<ProcessNodeBean>> getProcessNode(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/CountXMList")
    Observable<HttpResult<CountXMListInfo>> getCountXMList(@FieldMap Map<String, String> map,@Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/ChangTotal")
    Observable<HttpResult<ChangTotalInfo>> getChangTotal(@FieldMap Map<String, String> map,@Header("signature") String sign);


    @FormUrlEncoded
    @POST("Appinterface/getStatisticst")
    Observable<HttpResult<StatisticstInfo>> getStatisticst(@FieldMap Map<String, String> map,@Header("signature") String sign);


    @FormUrlEncoded
    @POST("Appinterface/getStatistics")
    Observable<HttpResult<StatisticsInfo>> getStatistics(@FieldMap Map<String, String> map,@Header("signature") String sign);

    //任务跟进详情
    @FormUrlEncoded
    @POST("Appinterface/processDetail")
    Observable<HttpResult<ProcessDetailBean>> getProcessDetail(@FieldMap Map<String, String> map,@Header("signature") String sign);

    //待整改
    @FormUrlEncoded
    @POST("Appinterface/proAbarGtasks")
    Observable<HttpResult<WaitRectifiedBean>> getWaitRectified(@FieldMap Map<String, String> map,@Header("signature") String sign);

    //历史整改
    @FormUrlEncoded
    @POST("Appinterface/proAbarHisWorking")
    Observable<HttpResult<RectifiedBean>> getRectified(@FieldMap Map<String, String> map,@Header("signature") String sign);

    //流程节点详情(发现处理)
    @FormUrlEncoded
    @POST("Appinterface/processDetail")
    Observable<HttpResult<NodeDetailBean>> getNodeDetail(@FieldMap Map<String, String> map,@Header("signature") String sign);

    //流程延时节点详情
    @FormUrlEncoded
    @POST("Appinterface/processDetail")
    Observable<HttpResult<NodeDelayBean>> getNodeDelay(@FieldMap Map<String, String> map,@Header("signature") String sign);

    //流程其他节点详情
    @FormUrlEncoded
    @POST("Appinterface/processDetail")
    Observable<HttpResult<NodeElseBean>> getNodeElse(@FieldMap Map<String, String> map,@Header("signature") String sign);

    //流程工单节点详情
    @FormUrlEncoded
    @POST("Appinterface/processDetail")
    Observable<HttpResult<WorkBean>> getWorkBean(@FieldMap Map<String, String> map,@Header("signature") String sign);

    //自检历史
    @FormUrlEncoded
    @POST("Appinterface/selfCheckHisWorking")
    Observable<HttpResult<SelfHistoryBean>> getSelfHistory(@FieldMap Map<String, String> map,@Header("signature") String sign);

    //全部已提交事件
    @FormUrlEncoded
    @POST("Appinterface/processInfo")
    Observable<HttpResult<AllReportBean>> getAllReport(@FieldMap Map<String, String> map,@Header("signature") String sign);

    //全部已提交事件
    @FormUrlEncoded
    @POST("Appinterface/proList")
    Observable<HttpResult<ScenePageInfo>> getAllScenePage(@FieldMap Map<String, String> map);

    //关键点位
    @FormUrlEncoded
    @POST("Appinterface/CJContentLetter")
    Observable<HttpResult<KeyPointBean>> getKeyPoint(@FieldMap Map<String, String> map,@Header("signature") String sign);

    //地图
    @FormUrlEncoded
    @POST("Appinterface/proMap")
    Observable<HttpResult<MapPointBean>> getMapPoint(@FieldMap Map<String, String> map, @Header("signature") String sign);

    //获取OpenID
    @FormUrlEncoded
    @POST("Appinterface/platformOauth")
    Observable<HttpResult<OpenIdBean>> platformOauth(@FieldMap Map<String, String> map, @Header("signature") String sign);

    //工单池列表
    @FormUrlEncoded
    @POST("Appinterface/workOrderLists")
    Observable<HttpResult<WorkOrderBean>> workOrderLists(@FieldMap Map<String, String> map, @Header("signature") String sign);

    //抢单接口
    @FormUrlEncoded
    @POST("Appinterface/grabSingle")
    Observable<HttpResult<EmptyBean>> grabSingle(@FieldMap Map<String, String> map, @Header("signature") String sign);

    //获得消息列表
    @FormUrlEncoded
    @POST("Appinterface/getMsgList")
    Observable<HttpResult<MsgBean>> getMsgList(@FieldMap Map<String, String> map, @Header("signature") String sign);

    //更新已读
    @FormUrlEncoded
    @POST("Appinterface/updateReadStatus")
    Observable<HttpResult<EmptyBean>> updateReadStatus(@FieldMap Map<String, String> map, @Header("signature") String sign);

    //获得消息列表
    @FormUrlEncoded
    @POST("Appinterface/getNoRead")
    Observable<HttpResult<NoReadBean>> getNoRead(@FieldMap Map<String, String> map, @Header("signature") String sign);

    //小区首页个人任务接口
    @FormUrlEncoded
    @POST("Appinterface/personalTask")
    Observable<HttpResult<PersonalTaskBean>> personalTask(@FieldMap Map<String, String> map, @Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/getStatisticsgr")
    Observable<HttpResult<StatisticsgrInfo>> getStatisticsgr(@FieldMap Map<String, String> map, @Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/IndSta_SponIns")
    Observable<HttpResult<StatisticsgrInfo>> getIndStaSponIns(@FieldMap Map<String, String> map, @Header("signature") String sign);

    @FormUrlEncoded
    @POST("Appinterface/getMailList")
    Observable<HttpResult<MailListBean>> getMailList(@FieldMap Map<String, String> map, @Header("signature") String sign);



}
