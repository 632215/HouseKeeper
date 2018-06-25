package com.jinke.housekeeper.saas.report.presenter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.bean.AppHandleInfo;
import com.jinke.housekeeper.saas.patrol.base.BasePresenter;
import com.jinke.housekeeper.saas.report.bean.ProcessDetailBean;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.view.IWaitingResponseView;
import com.jinke.housekeeper.ui.widget.CustomPopWindows;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;

/**
 * Created by root on 18-4-24.
 */

public class WaitingResponsePresent extends BasePresenter<IWaitingResponseView> {
    Context mContext;

    public WaitingResponsePresent(Context mContext) {
        this.mContext = mContext;
    }

    public void getProcessDetail(SortedMap<String, String> map) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<ProcessDetailBean>() {
            @Override
            public void onNext(ProcessDetailBean info) {
                mView.onProcessDetailOnNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                ToastUtils.showShort(Msg);

            }
        };
        HttpMethods.getInstance().getProcessDetail(new ProgressSubscriber<HttpResult<ProcessDetailBean>>(onNextListener, mContext, true), map, MyApplicaption.createSign(map));
    }

    /**
     * 农历时间已扩展至 ： 1900 - 2100年
     */
    public void initLunarPicker(final Context mContext, final TextView textView) {
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                textView.setText(getTime(date));
            }
        }).setType(new boolean[]{true, true, true, true, true, true}).build();
        pvTime.show();

    }


    /**
     * 报事响应
     *
     * @param map
     */
    public void getReportAnswer(SortedMap<String, String> map) {
        if (mView != null) {
            SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<AppHandleInfo>() {
                @Override
                public void onNext(AppHandleInfo info) {
//                mView.onProcessDetailOnNext(info);
                }

                @Override
                public void onError(String Code, String Msg) {
                    ToastUtils.showShort(Msg);

                }
            };
            HttpMethods.getInstance().getReportAnswer(new ProgressSubscriber<HttpResult<AppHandleInfo>>(onNextListener, mContext, true), map, ReportConfig.createSign(map));
        }
    }

    /**
     * 预约时间保存
     *
     * @param map
     */
    public void getAppointment(SortedMap<String, String> map) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<AppHandleInfo>() {
            @Override
            public void onNext(AppHandleInfo info) {
                mView.onAppointment(info);

            }

            @Override
            public void onError(String Code, String Msg) {
                ToastUtils.showShort(Msg);

            }
        };
        HttpMethods.getInstance().getAppointment(new ProgressSubscriber<HttpResult<AppHandleInfo>>(onNextListener, mContext, true), map, ReportConfig.createSign(map));
    }

    public void showTime() {
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }


    public void initDialog(Context mContext, String userName, View rootView) {
        if (mView != null) {


            final CustomPopWindows callPhoneWindows = new CustomPopWindows(mContext);
            callPhoneWindows.setContentView(View.inflate(mContext, R.layout.deletepicwindows, null));
            View deletePicView = callPhoneWindows.getContentView();
            TextView titleContent = (TextView) deletePicView.findViewById(R.id.titleContent);
            TextView cancleDelete = (TextView) deletePicView.findViewById(R.id.cancleDelete);
            TextView sureDelete = (TextView) deletePicView.findViewById(R.id.sureDelete);
            titleContent.setText(mContext.getResources().getString(R.string.call) + userName + mContext.getResources().getString(R.string.peoplephone));
            callPhoneWindows.showAtLocation(rootView, Gravity.CENTER, 0, 0);
            cancleDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callPhoneWindows.dismiss();
                }
            });
            sureDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mView.onCallBack(callPhoneWindows);

                }
            });

        }

    }

    CountDownTimer timer;

    /**
     * 相应倒计时
     *
     * @param textView
     * @param time
     */
    public void getShowCountTime(final TextView textView, final long time) {
        timer = new CountDownTimer(time, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText("工单相应倒计时:" + (timeParse(millisUntilFinished)));
            }

            @Override
            public void onFinish() {
                //如果倒计时只剩下３秒的时候则启动关闭动画
                setTextViewAnimator(textView, textView.getLayoutParams().height, 0);
            }
        };
        timer.start();
    }


    public String timeParse(long duration) {
        //将毫秒转分钟数
        String time = "";
        long minute = duration / 60000;
        long seconds = duration % 60000;
        long second = Math.round((float) seconds / 1000);
        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";
        if (second < 10) {
            time += "0";
        }
        time += second;
        return time;
    }

    /**
     * @param textView
     * @param start
     * @param end
     */
    public void setTextViewAnimator(final TextView textView, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                textView.getLayoutParams().height = value;
                textView.requestLayout();
            }
        });
        animator.start();

    }
}
