package com.jinke.housekeeper.saas.report.http;


import android.util.Log;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.AppHandleInfo;
import com.jinke.housekeeper.bean.EmptyBean;
import com.jinke.housekeeper.bean.MsgBean;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;
import com.jinke.housekeeper.bean.RegisterProjectBean;
import com.jinke.housekeeper.bean.StatisticsInfo;
import com.jinke.housekeeper.saas.report.bean.AllReportBean;
import com.jinke.housekeeper.saas.report.bean.HouseMsgBean;
import com.jinke.housekeeper.saas.report.bean.KeyPointBean;
import com.jinke.housekeeper.saas.report.bean.MapPointBean;
import com.jinke.housekeeper.saas.report.bean.NoReadBean;
import com.jinke.housekeeper.saas.report.bean.NodeDelayBean;
import com.jinke.housekeeper.saas.report.bean.NodeDetailBean;
import com.jinke.housekeeper.saas.report.bean.NodeElseBean;
import com.jinke.housekeeper.saas.report.bean.ProcessDetailBean;
import com.jinke.housekeeper.saas.report.bean.ProcessNodeBean;
import com.jinke.housekeeper.saas.report.bean.RectifiedBean;
import com.jinke.housekeeper.saas.report.bean.SelfHistoryBean;
import com.jinke.housekeeper.saas.report.bean.SessionBean;
import com.jinke.housekeeper.saas.report.bean.TodayChangeInfo;
import com.jinke.housekeeper.saas.report.bean.TodayWorkInfo;
import com.jinke.housekeeper.saas.report.bean.WaitRectifiedBean;
import com.jinke.housekeeper.saas.report.bean.WorkBean;
import com.jinke.housekeeper.saas.report.bean.WorkOrderBean;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liukun on 16/3/9.
 */
public class HttpMethods {

    public static final String BASE_URL = MyApplicaption.getInstance().getReportUrl();
    public static final String BASEPIC_URL = BASE_URL + "Appinterface/AppDownloadImage?url=";
    public static final String LIB_URL = "http://cruise.tq-service.com";
    //    public static final String CSLIB_URL = "http://oa.tq-service.com";//公告正式
    public static final String CSLIB_URL = "http://10.15.208.180:8080";//公告测试
    //    public static final String LIB_URL="http://10.15.8.158:8080";

    private static final int DEFAULT_TIMEOUT = 120;
    private RequestToast requestToast;
    private Retrofit retrofit;
    private AppService movieService;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(new LogInterceptor());
        builder.addInterceptor(new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY));

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request().newBuilder()
                        .addHeader("uid", MyApplicaption.getUserId())
                        .build();
                return chain.proceed(originalRequest);
            }
        });

        Log.i("wan", "BASE_URL" + BASE_URL);
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        movieService = retrofit.create(AppService.class);
        requestToast = new RequestToast();
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * 场景标准接口
     *
     * @param subscriber
     * @param map
     */
    public void getCJContentData(Subscriber<HttpResult<RegisterDepartmentBean>> subscriber, Map<String, String> map) {
        Observable observable = movieService.getCJContent(map);
        toSubscribe(observable, subscriber);
    }


    /**
     * 关键内容接口
     *
     * @param subscriber
     * @param map
     */
    public void getGJContentData(Subscriber<HttpResult<RegisterDepartmentBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getgJContent(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * 今日整改
     *
     * @param subscriber
     * @param map
     */
    public void getTodayChangeData(Subscriber<HttpResult<TodayChangeInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.gettodayChange(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 今日工作
     *
     * @param subscriber
     * @param map
     */
    public void getTodayWorkData(Subscriber<HttpResult<TodayWorkInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.gettodayWork(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * 根据项目获取该项目所拥有的用户列表信息的接口
     *
     * @param subscriber
     * @param map
     */
    public void getselectUserBySceneData(Subscriber<HttpResult<RegisterDepartmentBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getselectUserByScene(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * 任务签收
     *
     * @param subscriber
     * @param map
     */
    public void getAppTaskSignData(Subscriber<HttpResult<AppHandleInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getappTaskSign(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * 流程节点接口
     *
     * @param subscriber
     * @param map
     */
    public void getProcessNode(Subscriber<HttpResult<ProcessNodeBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getProcessNode(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * @param subscriber
     * @param map
     */
    public void getStatistics(Subscriber<HttpResult<StatisticsInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getStatistics(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 任务跟进详情
     *
     * @param subscriber
     * @param map
     */
    public void getProcessDetail(Subscriber<HttpResult<ProcessDetailBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getProcessDetail(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * //待整改
     *
     * @param subscriber
     * @param map
     */
    public void getWaitRectified(Subscriber<HttpResult<WaitRectifiedBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getWaitRectified(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * //历史整改
     *
     * @param subscriber
     * @param map
     */
    public void getRectified(Subscriber<HttpResult<RectifiedBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getRectified(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 流程节点详情(发现问题)
     *
     * @param subscriber
     * @param map
     */
    public void getNodeDetail(Subscriber<HttpResult<NodeDetailBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getNodeDetail(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 流程节点详情（延时）
     *
     * @param subscriber
     * @param map
     */
    public void getNodeDelay(Subscriber<HttpResult<NodeDelayBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getNodeDelay(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 流程节点详情（其他）
     *
     * @param subscriber
     * @param map
     */
    public void getNodeElse(Subscriber<HttpResult<NodeElseBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getNodeElse(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 流程节点详情（工单）
     *
     * @param subscriber
     * @param map
     */
    public void getWorkBean(Subscriber<HttpResult<WorkBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getWorkBean(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 自检历史
     *
     * @param subscriber
     * @param map
     */
    public void getSelfHistory(Subscriber<HttpResult<SelfHistoryBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getSelfHistory(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 全部已提交事件
     *
     * @param subscriber
     * @param map
     */
    public void getAllReport(Subscriber<HttpResult<AllReportBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getAllReport(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * 关键点位
     *
     * @param subscriber
     * @param map
     */
    public void getKeyPoint(Subscriber<HttpResult<KeyPointBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getKeyPoint(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 地图
     *
     * @param subscriber
     * @param map
     */
    public void getMapPoint(Subscriber<HttpResult<MapPointBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getMapPoint(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * 工单池列表
     *
     * @param subscriber
     * @param map
     */
    public void workOrderLists(Subscriber<HttpResult<WorkOrderBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.workOrderLists(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 抢单接口
     *
     * @param subscriber
     * @param map
     */
    public void grabSingle(Subscriber<HttpResult<EmptyBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.grabSingle(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获得消息列表
     *
     * @param subscriber
     * @param map
     */
    public void getMsgList(Subscriber<HttpResult<MsgBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getMsgList(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 更新已读
     *
     * @param subscriber
     * @param map
     */
    public void updateReadStatus(Subscriber<HttpResult<EmptyBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.updateReadStatus(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获得消息列表
     *
     * @param subscriber
     * @param map
     */
    public void getNoRead(Subscriber<HttpResult<NoReadBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getNoRead(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * 获取所属房间
     *
     * @param subscriber
     * @param map
     */
    public void getHouseMsg(Subscriber<HttpResult<HouseMsgBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getHouseMsg(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 项目列表接口
     *
     * @param subscriber
     * @param map
     */
    public void getXMListData(Subscriber<HttpResult<RegisterProjectBean>> subscriber, Map<String, String> map) {
        Observable observable = movieService.getXMList(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * 抢单接口
     *
     * @param subscriber
     * @param map
     */
    public void getGrabSingle(Subscriber<HttpResult<HouseMsgBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getGrabSingle(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * 抢单接口
     *
     * @param subscriber
     * @param map
     */
    public void getappHandle(Subscriber<HttpResult<AppHandleInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getappHandle(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 报事响应
     *
     * @param subscriber
     * @param map
     */
    public void getReportAnswer(Subscriber<HttpResult<AppHandleInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getReportAnswer(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * 报事响应
     *
     * @param subscriber
     * @param map
     */
    public void getAppointment(Subscriber<HttpResult<AppHandleInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getAppointment(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 报事响应
     *
     * @param subscriber
     * @param map
     */
    public void getToken(Subscriber<HttpResult<SessionBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getToken(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 报事响应
     *
     * @param subscriber
     * @param map
     */
    public void getCJContentParent(Subscriber<HttpResult<RegisterDepartmentBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getCJContentParent(map, sign);
        toSubscribe(observable, subscriber);
    }
}