package com.jinke.housekeeper.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by root on 29/12/16.
 */

public class UpProgressBar extends Handler {
    private Context mContext;
    ProgressBar bar;

    public UpProgressBar(Context mContext, @NonNull ProgressBar bar) {
        this.mContext = mContext.getApplicationContext();
        this.bar = bar;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        bar.setProgress((int) msg.obj);
    }

    public void updata(int number) {
        if (number >= 100) {
            bar.setVisibility(View.GONE);
        } else {
            Message message = new Message();
            message.obj = number;
            sendMessage(message);
        }

    }

}
