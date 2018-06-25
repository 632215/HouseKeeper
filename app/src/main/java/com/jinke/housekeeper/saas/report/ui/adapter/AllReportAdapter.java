package com.jinke.housekeeper.saas.report.ui.adapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.report.bean.AllReportBean;
import com.jinke.housekeeper.saas.report.util.MediaPlayerManager;
import com.jinke.housekeeper.saas.report.util.ToastUtils;
import com.jinke.housekeeper.ui.adapter.BaseQuickAdapter;
import com.jinke.housekeeper.ui.adapter.BaseViewHolder;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by root on 6/01/17.
 */

public class AllReportAdapter extends BaseQuickAdapter<AllReportBean.ListObjBean> {
    private Context mContext;
    private boolean flag = false;

    public AllReportAdapter(Context context, List<AllReportBean.ListObjBean> data, int layoutId) {
        super(context, data, layoutId);
        this.mContext=context;
    }

    @Override
    public void convert(BaseViewHolder helper, final AllReportBean.ListObjBean item) {
        ImageView imgState = (ImageView) helper.itemView.findViewById(R.id.imgState);
        TextView stateText = (TextView) helper.itemView.findViewById(R.id.stateText);

        ImageView imgView = (ImageView) helper.itemView.findViewById(R.id.imgView);
        TextView textDescribe = (TextView) helper.itemView.findViewById(R.id.textDescribe);

        RelativeLayout voiceLayout = (RelativeLayout) helper.itemView.findViewById(R.id.voiceLayout);
        final ImageView voiceImg = (ImageView) helper.itemView.findViewById(R.id.voiceImg);
        TextView voiceTime = (TextView) helper.itemView.findViewById(R.id.voiceTime);

        TextView category = (TextView) helper.itemView.findViewById(R.id.category);
        TextView keyPoints = (TextView) helper.itemView.findViewById(R.id.keyPoints);

        TextView time = (TextView) helper.itemView.findViewById(R.id.time);
        TextView project = (TextView) helper.itemView.findViewById(R.id.project);


        switch (item.getIsState()) {
            //待抢单1   已抢单2  已改派3  已指派4    处理中5    已归档 9  待回访 10
            case "1":
                imgState.setImageResource(R.drawable.icon_work_dai_qiang_dan);
                break;
            case "2":
                imgState.setImageResource(R.drawable.icon_work_qiang_dan_zhong);
                break;
            case "3":
                imgState.setImageResource(R.drawable.icon_work_yi_gai_pai);
                break;
            case "4":
                imgState.setImageResource(R.drawable.icon_work_yi_zhi_pai);
                break;
            case "5":
                imgState.setImageResource(R.drawable.icon_work_chu_li_zhong);
                break;
            case "9":
                imgState.setImageResource(R.drawable.icon_work_yi_gui_dan);
                break;
            case "10":
                imgState.setImageResource(R.drawable.icon_work_hui_fan);
                break;
        }

        if (item.getHangOrTime().equals("")) {
            stateText.setVisibility(View.GONE);
        } else {
            stateText.setVisibility(View.VISIBLE);
            stateText.setText(item.getHangOrTime().equals("3") ? "超时" : item.getHangOrTime().equals("2") ? "延时" : "");
            stateText.setTextColor(item.getHangOrTime().equals("3") ? mContext.getResources().getColor(R.color.state_red) : mContext.getResources().getColor(R.color.state_blue));
        }

        if (item.getSupervisePotoadd() != null && item.getSupervisePotoadd().toString().trim() != "" && item.getSupervisePotoadd().toString().trim().contains("|")) {
            Picasso.with(mContext).load(item.getSupervisePotoadd().substring(0, item.getSupervisePotoadd().indexOf("|")))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//加速内存的回收
                    .placeholder(R.drawable.jk_icon)//加载中
                    .error(R.drawable.jk_icon)//加载失败
                    .into(imgView);
        } else if (item.getSupervisePotoadd() != null && item.getSupervisePotoadd().toString().trim() != "") {
            Picasso.with(mContext).load(item.getSupervisePotoadd().toString())
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//加速内存的回收
                    .placeholder(R.drawable.jk_icon)//加载中
                    .error(R.drawable.jk_icon)//加载失败
                    .into(imgView);
        }

        if (item.getSuperviseDescribe() == null || item.getSuperviseDescribe().toString().trim() == "") {
            textDescribe.setVisibility(View.GONE);
            voiceLayout.setVisibility(View.VISIBLE);
            voiceLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //播放录音
                    if (flag == false) {
                        if (!checkPermission()) {
                            return;
                        }
                        Drawable drawable = mContext.getResources().getDrawable(R.drawable.play_anim);
                        voiceImg.setBackgroundDrawable(drawable);
                        final AnimationDrawable animation = (AnimationDrawable) voiceImg.getBackground();
                        animation.start();
                        MediaPlayerManager.playSound(item.getAudioAdd(), new MediaPlayer.OnCompletionListener() {
                            public void onCompletion(MediaPlayer mp) {
                                //播放完成
                                animation.stop();
                                voiceImg.setBackgroundResource(R.drawable.v_anim3);
                            }
                        });
                        flag = true;
                    } else {
                        MediaPlayerManager.release();
                        flag = false;
                    }
                }
            });
            voiceTime.setText(item.getAudioAddLength() + "″");//音频时间
        } else {
            voiceLayout.setVisibility(View.GONE);
            textDescribe.setVisibility(View.VISIBLE);
            textDescribe.setText(item.getSuperviseDescribe());
        }
        time.setText(item.getSuperviseTime().equals("") ? "暂无" : item.getSuperviseTime());
        keyPoints.setText(item.getCruxName().equals("") ? "暂无" : item.getCruxName());
        project.setVisibility(View.GONE);
        category.setText(item.getSceneName().equals("") ? "暂无" : item.getSceneName());
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ToastUtils.showLongToast(mContext.getResources().getString(R.string.please_open_record_permision));
            return false;
        } else {
            return true;
        }
    }
}
