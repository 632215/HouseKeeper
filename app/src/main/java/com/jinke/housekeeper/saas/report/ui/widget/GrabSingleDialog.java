package com.jinke.housekeeper.saas.report.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.WaitingResponseActivity;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.WorkOrderDetailsActivity;

/**
 * Created by root on 18-4-18.
 */

public class GrabSingleDialog extends Dialog {

    private Context mContext;
    private ImageView img_grab_state;
    private TextView tx_window_state;

    public GrabSingleDialog(@NonNull Context context) {
        super(context, R.style.dialog_custom);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_grab_single);
        img_grab_state = (ImageView) findViewById(R.id.img_grab_state);
        tx_window_state = (TextView) findViewById(R.id.tx_window_state);
    }

    @Override
    public void show() {
        super.show();
    }


    public void setImg_grab_state(int drawble) {
        img_grab_state.setBackground(mContext.getResources().getDrawable(drawble));
    }

    public void setTx_window_state(String tx) {
        tx_window_state.setText(tx);
    }

    CountDownTimer time;

    public void getStartTime() {
        time = new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                //如果倒计时只剩下３秒的时候则启动关闭动画
                if (listener != null) {
                    listener.startTime();
                }
            }
        };
        time.start();
    }

    public void setOnStartTimerListener(onStartTimerListener listener) {
        this.listener = listener;
    }

    onStartTimerListener listener;

    public interface onStartTimerListener {
        void startTime();
    }
}
