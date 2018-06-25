package com.jinke.housekeeper.http;


import android.os.Message;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.MailListBean;
import com.jinke.housekeeper.bean.PersonalTaskBean;
import com.jinke.housekeeper.bean.StatisticsgrInfo;
import com.jinke.housekeeper.bean.TestInfo;
import com.jinke.housekeeper.bean.UserPushBean;
import com.jinke.housekeeper.saas.report.bean.AllReportBean;
import com.jinke.housekeeper.saas.report.bean.AnnounceListInfo;
import com.jinke.housekeeper.bean.AppHandleInfo;
import com.jinke.housekeeper.bean.OpenIdBean;
import com.jinke.housekeeper.bean.ChangTotalInfo;
import com.jinke.housekeeper.saas.report.bean.CheckDetailsInfo;
import com.jinke.housekeeper.bean.CountXMListInfo;
import com.jinke.housekeeper.saas.report.bean.KeyPointBean;
import com.jinke.housekeeper.bean.KnowledgeInfo;
import com.jinke.housekeeper.bean.LibAllInfo;
import com.jinke.housekeeper.bean.LibDetailsInfo;
import com.jinke.housekeeper.bean.LoginInfo;
import com.jinke.housekeeper.saas.report.bean.MapPointBean;
import com.jinke.housekeeper.bean.MemberListBean;
import com.jinke.housekeeper.saas.report.bean.NoReadBean;
import com.jinke.housekeeper.saas.report.bean.NodeDelayBean;
import com.jinke.housekeeper.saas.report.bean.NodeDetailBean;
import com.jinke.housekeeper.saas.report.bean.NodeElseBean;
import com.jinke.housekeeper.saas.report.bean.ProcessDetailBean;
import com.jinke.housekeeper.saas.report.bean.ProcessNodeBean;
import com.jinke.housekeeper.saas.report.bean.RectifiedBean;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;
import com.jinke.housekeeper.bean.RegisterProjectBean;
import com.jinke.housekeeper.bean.ScenePageInfo;
import com.jinke.housekeeper.saas.report.bean.SelfHistoryBean;
import com.jinke.housekeeper.bean.EmptyBean;
import com.jinke.housekeeper.bean.MsgBean;
import com.jinke.housekeeper.bean.StatisticsInfo;
import com.jinke.housekeeper.bean.StatisticstInfo;
import com.jinke.housekeeper.saas.report.bean.TodayChangeInfo;
import com.jinke.housekeeper.saas.report.bean.TodayWorkInfo;
import com.jinke.housekeeper.bean.UpdatePwdBean;
import com.jinke.housekeeper.saas.report.bean.WaitRectifiedBean;
import com.jinke.housekeeper.saas.report.bean.WorkBean;
import com.jinke.housekeeper.saas.report.bean.WorkOrderBean;
import com.jinke.housekeeper.saas.report.bean.WorkToadyInfo;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liukun on 16/3/9.
 */
public class HttpMethods {

    public static final String BASE_URL = MyApplicaption.getInstance().getAppUrl();//正式环境

    public static final String BASEPIC_URL = BASE_URL + "Appinterface/AppDownloadImage?url=";
    public static final String LIB_URL = "http://cruise.tq-service.com";
    //    public static final String CSLIB_URL = "http://oa.tq-service.com";//公告正式
    public static final String CSLIB_URL = "http://api.tq-service.com";//公告正式
//    public static final String CSLIB_URL = "http://10.15.208.180:8080";//公告测试
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
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getErrcode() != 1) {
                Message message = new Message();
                message.obj = httpResult;
                requestToast.sendMessage(message);
                throw new ApiException(httpResult.getErrcode(), httpResult.getErrmsg());
            } else {
                Message message = new Message();
                message.obj = httpResult;
                requestToast.sendMessage(message);
            }
            return httpResult.getData();
        }
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
     * 用户注册接口
     *
     * @param subscriber
     * @param map
     */
    public void getRegisterData(Subscriber<HttpResult<TestInfo>> subscriber, Map<String, String> map) {
        Observable observable = movieService.getregister(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * 用户登陆接口
     *
     * @param subscriber
     * @param map
     */
    public void getUserLoginData(Subscriber<HttpResult<LoginInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getuserLogin(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 知识库首页
     *
     * @param subscriber
     * @param map
     */
    public void getLoreData(Subscriber<HttpResult<KnowledgeInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getLore(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * 知识库首页
     *
     * @param subscriber
     * @param map
     */
    public void getScenePageData(Subscriber<HttpResult<LibAllInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getScenePageData(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 修改密码接口
     *
     * @param subscriber
     * @param map
     */
    public void getUpdatePwd(Subscriber<HttpResult<UpdatePwdBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getUpdatePwd(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * 成员列表接口
     *
     * @param subscriber
     * @param map
     */
    public void getMenberList(Subscriber<HttpResult<MemberListBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getMenberList(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 人员审核接口
     *
     * @param subscriber
     * @param map
     */
    public void getUserPass(Subscriber<HttpResult<TestInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getUserPass(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 退出登录
     *
     * @param subscriber
     * @param map
     */
    public void getuserLoginOut(Subscriber<HttpResult<AppHandleInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getuserLoginOut(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 开关推送
     *
     * @param subscriber
     * @param map
     */
    public void userPush(Subscriber<HttpResult<UserPushBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.userPush(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 知识库详情页面
     *
     * @param subscriber
     * @param map
     */
    public void getKeyId(Subscriber<HttpResult<LibDetailsInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getKeyId(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * 流程节点接口
     *
     * @param subscriber
     * @param map
     */
    public void getCountXMList(Subscriber<HttpResult<CountXMListInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getCountXMList(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param subscriber
     * @param map
     */
    public void getChangTotal(Subscriber<HttpResult<ChangTotalInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getChangTotal(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param subscriber
     * @param map
     */
    public void getStatisticst(Subscriber<HttpResult<StatisticstInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getStatisticst(map, sign);
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
     * 获得所有项目
     *
     * @param subscriber
     * @param map
     */
    public void getAllScenePage(Subscriber<HttpResult<ScenePageInfo>> subscriber, Map<String, String> map) {
        Observable observable = movieService.getAllScenePage(map);
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
     * 获取OpenID
     *
     * @param subscriber
     * @param map
     */
    public void platformOauth(Subscriber<HttpResult<OpenIdBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.platformOauth(map, sign);
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
     * 小区首页个人任务接口
     *
     * @param subscriber
     * @param map
     */
    public void personalTask(Subscriber<HttpResult<PersonalTaskBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.personalTask(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 流程节点接口
     *
     * @param subscriber
     * @param map
     */
    public void getStatisticsgr(Subscriber<HttpResult<StatisticsgrInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getStatisticsgr(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * @param subscriber
     * @param map
     */
    public void getIndStaSponIns(Subscriber<HttpResult<StatisticsgrInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getIndStaSponIns(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 通讯录
     *
     * @param subscriber
     * @param map
     */
    public void getMailList(Subscriber<HttpResult<MailListBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = movieService.getMailList(map, sign);
        toSubscribe(observable, subscriber);
    }


}