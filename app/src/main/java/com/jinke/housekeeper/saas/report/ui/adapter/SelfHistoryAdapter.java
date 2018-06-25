package com.jinke.housekeeper.saas.report.ui.adapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.report.bean.SelfHistoryBean;
import com.jinke.housekeeper.saas.report.util.MediaPlayerManager;
import com.jinke.housekeeper.saas.report.util.ToastUtils;
import com.jinke.housekeeper.ui.adapter.BaseQuickAdapter;
import com.jinke.housekeeper.ui.adapter.BaseViewHolder;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 6/01/17.
 */

public class SelfHistoryAdapter extends BaseQuickAdapter<SelfHistoryBean.ListObjBean> {
    private boolean flag = false;
    private Context mContext;

    public SelfHistoryAdapter(Context mContext, List<SelfHistoryBean.ListObjBean> data, int layoutId) {
        super(mContext, data, layoutId);
        this.mContext = mContext;
    }


    @Override
    public void convert(BaseViewHolder baseViewHolder, final SelfHistoryBean.ListObjBean bean) {
        ImageView imgState = (ImageView) baseViewHolder.itemView.findViewById(R.id.imgState);
        TextView stateText = (TextView) baseViewHolder.itemView.findViewById(R.id.stateText);

        ImageView imgView = (ImageView) baseViewHolder.itemView.findViewById(R.id.imgView);
        TextView textDescribe = (TextView) baseViewHolder.itemView.findViewById(R.id.textDescribe);

        RelativeLayout voiceLayout = (RelativeLayout) baseViewHolder.itemView.findViewById(R.id.voiceLayout);
        final ImageView voiceImg = (ImageView) baseViewHolder.itemView.findViewById(R.id.voiceImg);
        TextView voiceTime = (TextView) baseViewHolder.itemView.findViewById(R.id.voiceTime);

        TextView category = (TextView) baseViewHolder.itemView.findViewById(R.id.category);
        TextView keyPoints = (TextView) baseViewHolder.itemView.findViewById(R.id.keyPoints);

        TextView time = (TextView) baseViewHolder.itemView.findViewById(R.id.time);
        TextView project = (TextView) baseViewHolder.itemView.findViewById(R.id.project);

        if (bean != null) {
            switch (bean.getIsState()) {
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

            stateText.setVisibility(View.VISIBLE);
            stateText.setText(bean.getIsTimeout().equals("1") ? "超时" : bean.getIsHangup().equals("Y") ? "延时" : "");
            stateText.setTextColor(bean.getIsTimeout().equals("1") ? mContext.getResources().getColor(R.color.state_red) : mContext.getResources().getColor(R.color.state_blue));
            if (bean.getImgaddress() != null && bean.getImgaddress().toString().trim() != "" && bean.getImgaddress().toString().trim().contains("|")) {
                Picasso.with(mContext).load(bean.getImgaddress().substring(0, bean.getImgaddress().indexOf("|")))
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//加速内存的回收
                        .placeholder(R.drawable.jk_icon)//加载中
                        .error(R.drawable.jk_icon)//加载失败
                        .into(imgView);

            } else if (bean.getImgaddress() != null && bean.getImgaddress().toString().trim() != "") {
                Picasso.with(mContext).load(bean.getImgaddress().toString())
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//加速内存的回收
                        .placeholder(R.drawable.jk_icon)//加载中
                        .error(R.drawable.jk_icon)//加载失败
                        .into(imgView);
            }

            if (bean.getDescribe() == null || bean.getDescribe().toString().trim() == "") {
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
                            MediaPlayerManager.playSound(bean.getAudioaddress(), new MediaPlayer.OnCompletionListener() {
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
                voiceTime.setText(bean.getAudiolen() + "″");//音频时间
            } else {
                voiceLayout.setVisibility(View.GONE);
                textDescribe.setVisibility(View.VISIBLE);
                textDescribe.setText(bean.getDescribe());
            }
            category.setText(bean.getSceneName() == null || bean.getSceneName().equals("") ? "暂无" : bean.getSceneName());
            keyPoints.setText(bean.getAreaName() == null || bean.getAreaName().equals("") ? "暂无" : bean.getAreaName());
            time.setText(bean.getSuperviseTime() == null || bean.getSuperviseTime().equals("") ? "暂无" : bean.getSuperviseTime());
            project.setText(bean.getOrgName() == null || bean.getOrgName().equals("") ? "暂无" : bean.getOrgName());
        }
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(bean);
                }
            }
        });
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

    OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(SelfHistoryBean.ListObjBean bean);
    }
}
