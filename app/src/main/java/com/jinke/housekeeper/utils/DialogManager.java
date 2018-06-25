package com.jinke.housekeeper.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jinke.housekeeper.R;

/**
 * @param
 * @author ldm
 * @description 对话框管理工具类
 * @time 2016/6/25 11:53
 */
public class DialogManager {
    //弹出对话框
    private Dialog mDialog;
    //录音图标
    private ImageView iconMicrophone;
    //音量显示 图标
    private ImageView iconVolume;
    //对话框上提示文字
    private TextView tips;
    //上下文对象
    private Context mContext;


    public DialogManager(Context context) {
        this.mContext = context;
    }

    /**
     * @param
     * @description 显示对话框
     * @author ldm
     * @time 2016/6/25 9:56
     */
    public void showRecordingDialog() {
        //根据指定sytle实例化Dialog
        mDialog = new Dialog(mContext, R.style.AudioDialog);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_recorder, null);
        mDialog.setContentView(view);
        iconMicrophone = (ImageView) view.findViewById(R.id.iconMicrophone);
        iconVolume = (ImageView) view.findViewById(R.id.iconVolume);
        tips = (TextView) view.findViewById(R.id.tips);
        mDialog.show();
    }

    /**
     * @param
     * @description 正在录音状态的对话框
     * @author ldm
     * @time 2016/6/25 10:08
     */
    public void recording() {
        if (mDialog != null && mDialog.isShowing()) {
            iconMicrophone.setVisibility(View.VISIBLE);
            iconVolume.setVisibility(View.VISIBLE);
            tips.setVisibility(View.VISIBLE);
            iconMicrophone.setImageResource(R.drawable.recorder);
            tips.setText("手指上滑，取消发送");
        }
    }

    /**
     * @description 取消录音状态对话框
     */
    public void wantToCancel() {
        if (mDialog != null && mDialog.isShowing()) {
            iconMicrophone.setVisibility(View.VISIBLE);
            iconVolume.setVisibility(View.VISIBLE);
            tips.setVisibility(View.VISIBLE);
            iconMicrophone.setImageResource(R.drawable.cancelsend);
            tips.setBackgroundResource(R.drawable.cancle_voice);
            tips.setText("松开手指，取消发送");
        }
    }

    /**
     * @description 时间过短提示的对话框
     */
    public void tooShort() {
        if (mDialog != null && mDialog.isShowing()) { //显示状态
            mDialog.dismiss();

            mDialog = new Dialog(mContext, R.style.AudioDialog);
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.dialog_recorder, null);
            mDialog.setContentView(view);
            iconMicrophone = (ImageView) view.findViewById(R.id.iconMicrophone);
            iconVolume = (ImageView) view.findViewById(R.id.iconVolume);
            tips = (TextView) view.findViewById(R.id.tips);

            iconMicrophone.setImageResource(R.drawable.cancle);
            iconMicrophone.setVisibility(View.VISIBLE);
            iconVolume.setVisibility(View.GONE);
            tips.setVisibility(View.VISIBLE);
            tips.setText("录音时间太短！");
            mDialog.show();
        }
    }

    /**
     * @description 取消（关闭）对话框
     */
    public void dimissDialog() {
        if (mDialog != null && mDialog.isShowing()) { //显示状态
            mDialog.dismiss();
            mDialog = null;
        }
    }

    // 显示更新音量级别的对话框
    public void updateVoiceLevel(int level) {
        if (mDialog != null && mDialog.isShowing()) { //显示状态
            iconMicrophone.setVisibility(View.VISIBLE);
            iconVolume.setVisibility(View.VISIBLE);
            tips.setVisibility(View.VISIBLE);
            //设置图片的id，我们放在drawable中的声音图片是以v+数字格式的
            int resId = mContext.getResources().getIdentifier("v" + level, "drawable", mContext.getPackageName());
            iconVolume.setImageResource(resId);
        }
    }

}
