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

import com.blankj.utilcode.util.StringUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.report.bean.RectifiedBean;
import com.jinke.housekeeper.saas.report.util.MediaPlayerManager;
import com.jinke.housekeeper.saas.report.util.ToastUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 6/01/17.
 */

public class RectifiedAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<RectifiedBean.ListObjBean> businessProcessList = new ArrayList();
    private boolean flag = false;

    public RectifiedAdapter(Context mContext, List<RectifiedBean.ListObjBean> listObj) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.businessProcessList = listObj;
    }

    public void setData(List<RectifiedBean.ListObjBean> listObj) {
        this.businessProcessList = listObj;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return businessProcessList.size();
    }

    @Override
    public Object getItem(int position) {
        return businessProcessList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_wait_rectified, parent, false);
            holder = new ViewHolder();
            holder.imgState = (ImageView) convertView.findViewById(R.id.imgState);
            holder.stateText = (TextView) convertView.findViewById(R.id.stateText);

            holder.imgView = (ImageView) convertView.findViewById(R.id.imgView);
            holder.textDescribe = (TextView) convertView.findViewById(R.id.textDescribe);

            holder.voiceLayout = (RelativeLayout) convertView.findViewById(R.id.voiceLayout);
            holder.voiceImg = (ImageView) convertView.findViewById(R.id.voiceImg);
            holder.voiceTime = (TextView) convertView.findViewById(R.id.voiceTime);

            holder.category = (TextView) convertView.findViewById(R.id.category);
            holder.keyPoints = (TextView) convertView.findViewById(R.id.keyPoints);

            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.project = (TextView) convertView.findViewById(R.id.project);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final RectifiedBean.ListObjBean bean = businessProcessList.get(position);
        if (bean != null) {
            switch (bean.getIsState()) {
                //待抢单1   已抢单2  已改派3  已指派4    处理中5    已归档 9  待回访 10
                case "1":
                    holder.imgState.setImageResource(R.drawable.icon_work_dai_qiang_dan);
                    break;
                case "2":
                    holder.imgState.setImageResource(R.drawable.icon_work_qiang_dan_zhong);
                    break;
                case "3":
                    holder.imgState.setImageResource(R.drawable.icon_work_yi_gai_pai);
                    break;
                case "4":
                    holder.imgState.setImageResource(R.drawable.icon_work_yi_zhi_pai);
                    break;
                case "5":
                    holder.imgState.setImageResource(R.drawable.icon_work_chu_li_zhong);
                    break;
                case "9":
                    holder.imgState.setImageResource(R.drawable.icon_work_yi_gui_dan);
                    break;
                case "10":
                    holder.imgState.setImageResource(R.drawable.icon_work_hui_fan);
                    break;
            }

//            if (bean.getIsHangup().equals("N") || bean.getIsHangup().equals("") && bean.getIsTimeout().equals("0") || bean.getIsTimeout().equals("")) {
//                holder.stateText.setVisibility(View.GONE);
//            } else {
            holder.stateText.setVisibility(View.VISIBLE);
            //判断是否超时,如果没有超时，就判断是否延时
            holder.stateText.setText(bean.getIsTimeout().equals("1") ? "超时" : bean.getIsHangup().equals("Y") ? "延时" : "");
            holder.stateText.setTextColor(bean.getIsTimeout().equals("1") ? mContext.getResources().getColor(R.color.state_red) : mContext.getResources().getColor(R.color.state_blue));
//            }

            if (bean.getImgaddress() != null && bean.getImgaddress().toString().trim() != "" && bean.getImgaddress().toString().trim().contains("|")) {
                Picasso.with(mContext).load(bean.getImgaddress().substring(0, bean.getImgaddress().indexOf("|")))
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//加速内存的回收
                        .placeholder(R.drawable.jk_icon)//加载中
                        .error(R.drawable.jk_icon)//加载失败
                        .into(holder.imgView);
            } else if (bean.getImgaddress() != null && bean.getImgaddress().toString().trim() != "") {
                Picasso.with(mContext).load(bean.getImgaddress().toString())
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//加速内存的回收
                        .placeholder(R.drawable.jk_icon)//加载中
                        .error(R.drawable.jk_icon)//加载失败
                        .into(holder.imgView);
            }

            if (bean.getDescribe() == null || bean.getDescribe().toString().trim() == "") {
                if (bean.getAudioaddress() != null && bean.getAudioaddress().toString().trim() != "") {
                    holder.textDescribe.setVisibility(View.GONE);
                    holder.voiceLayout.setVisibility(View.VISIBLE);
                    holder.voiceLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //播放录音
                            if (flag == false) {
                                if (!checkPermission()) {
                                    return;
                                }
                                Drawable drawable = mContext.getResources().getDrawable(R.drawable.play_anim);
                                holder.voiceImg.setBackgroundDrawable(drawable);
                                final AnimationDrawable animation = (AnimationDrawable) holder.voiceImg.getBackground();
                                animation.start();
                                MediaPlayerManager.playSound(bean.getAudioaddress(), new MediaPlayer.OnCompletionListener() {
                                    public void onCompletion(MediaPlayer mp) {
                                        //播放完成
                                        animation.stop();
                                        holder.voiceImg.setBackgroundResource(R.drawable.v_anim3);
                                    }
                                });
                                flag = true;
                            } else {
                                MediaPlayerManager.release();
                                flag = false;
                            }
                        }
                    });
                    holder.voiceTime.setText(bean.getAudiolen() + "″");//音频时间
                }
            } else {
                holder.voiceLayout.setVisibility(View.GONE);
                holder.textDescribe.setVisibility(View.VISIBLE);
                holder.textDescribe.setText(bean.getDescribe());
            }

            holder.category.setText(bean.getSceneName());
            holder.keyPoints.setVisibility(StringUtils.isEmpty(bean.getAreaName()) ? View.GONE : View.VISIBLE);
            holder.keyPoints.setText(bean.getAreaName());
            holder.time.setText(bean.getSuperviseTime());
            holder.project.setVisibility(View.GONE);
        }
        return convertView;
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

    public ViewHolder holder;

    class ViewHolder {
        ImageView imgState;//状态图片
        TextView stateText;//状态

        ImageView imgView;//图片
        TextView textDescribe;//文字描述
        RelativeLayout voiceLayout;//语音模块
        ImageView voiceImg;//语音图片
        TextView voiceTime;//语音时间
        TextView category;//类别
        TextView keyPoints;//关键点位
        TextView time;//时间
        TextView project;//项目
    }
}
