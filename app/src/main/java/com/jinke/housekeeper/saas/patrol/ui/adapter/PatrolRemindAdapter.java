package com.jinke.housekeeper.saas.patrol.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.bean.AlarmClockBean;
import com.jinke.housekeeper.saas.patrol.bean.AlarmClockListBean;
import com.jinke.housekeeper.saas.patrol.config.AlarmClockConfig;
import com.jinke.housekeeper.saas.patrol.utils.LogUtil;
import com.loonggg.lib.alarmmanager.clock.AlarmManagerUtil;

import java.util.List;

public class PatrolRemindAdapter extends BaseAdapter {
    private List<AlarmClockBean> infoList;
    private Context context;
    private LayoutInflater inflater;

    public PatrolRemindAdapter(Context mContext, List<AlarmClockBean> infoList) {
        this.context = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(mContext);
    }

    public void setRemindTimeList(List<AlarmClockBean> infoList) {
        this.infoList = infoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int i) {
        return infoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_patrol_remind, null);
            holder = new viewHolder();
            holder.remindTime = (TextView) convertView.findViewById(R.id.patrol_remind_time);
            holder.remindSwitch = (ImageView) convertView.findViewById(R.id.patrol_remind_switch);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }

        if (infoList.get(position).isStateAlarmClock()) {
            holder.remindTime.setTextColor(context.getResources().getColor(R.color.text_color_14));
        } else {
            holder.remindTime.setTextColor(context.getResources().getColor(R.color.text_color_15));
        }
        holder.remindTime.setText(infoList.get(position).getStartTime());
        if (infoList.get(position).isStateAlarmClock()) {
            holder.remindSwitch.setBackgroundResource(R.drawable.patrol_remind_on);
        } else {
            holder.remindSwitch.setBackgroundResource(R.drawable.patrol_remind_off);
        }
        holder.remindSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmClockListBean alarmClockListBean = AlarmClockConfig.getAlarmClockBean(context);
                if (infoList.get(position).isStateAlarmClock()) {
                    ToastUtils.showShort("巡更提醒已关闭");
                } else {
                    ToastUtils.showShort( "巡更提醒已开启");
//                    View view = inflater.inflate(R.layout.toast_notice, null);
//                    TextView t = (TextView) view.findViewById(R.id.toast_notice);
                }
                infoList.get(position).setStateAlarmClock(!infoList.get(position).isStateAlarmClock());
                setClock(infoList);
                alarmClockListBean.setAlarmClockList(infoList);
                AlarmClockConfig.setAlarmClockBean(context, alarmClockListBean);
                setRemindTimeList(infoList);
            }
        });
//        holder.remindSwitch.setChecked(infoList.get(position).isStateAlarmClock());
//        holder.remindSwitch.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(View view, boolean isChecked) {
//                AlarmClockListBean alarmClockListBean = AlarmClockConfig.getAlarmClockBean(context);
//                if (isChecked) {
//                    ToastUtils.showShort(context, "巡更提醒已开启");
//                } else {
//                    ToastUtils.showShort(context, "巡更提醒已关闭");
////                    View view = inflater.inflate(R.layout.toast_notice, null);
////                    TextView t = (TextView) view.findViewById(R.id.toast_notice);
//                }
//                infoList.get(position).setStateAlarmClock(isChecked);
//                setClock(infoList);
//                alarmClockListBean.setAlarmClockList(infoList);
//                AlarmClockConfig.setAlarmClockBean(context, alarmClockListBean);
//                setRemindTimeList(infoList);
//            }
//        });
//        holder.remindSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                AlarmClockListBean alarmClockListBean = AlarmClockConfig.getAlarmClockBean(context);
//                if (isChecked) {
//                    ToastUtils.showShort(context ,"巡更提醒已开启" );
//                } else {
//                    ToastUtils.showShort(context ,"巡更提醒已关闭" );
////                    View view = inflater.inflate(R.layout.toast_notice, null);
////                    TextView t = (TextView) view.findViewById(R.id.toast_notice);
//                }
//                infoList.get(position).setStateAlarmClock(isChecked);
//                setClock(infoList);
//                alarmClockListBean.setAlarmClockList(infoList);
//                AlarmClockConfig.setAlarmClockBean(context, alarmClockListBean);
//                setRemindTimeList(infoList);
//            }
//        });

        return convertView;
    }

    viewHolder holder;


    public class viewHolder {
        TextView remindTime;
        //        Switch remindSwitch;
//        SwitchView remindSwitch;
        ImageView remindSwitch;
    }


    private void setClock(List<AlarmClockBean> clockBeanList) {
        for (AlarmClockBean clockBean : clockBeanList) {
            if (clockBean.isStateAlarmClock()) {
                String[] times = clockBean.getStartTime().split(":");
                AlarmManagerUtil.setAlarm(context, clockBean.getAlarmId(), Integer.parseInt(times[0]), Integer.parseInt(times[1])
                        , clockBean.getAlarmId(), 0, "闹钟" + clockBean.getStartTime() + "响了", 2);
                LogUtil.loge(clockBean.getAlarmId() + "闹钟" + clockBean.getStartTime() + "设置成功");
            } else {
                AlarmManagerUtil.cancelAlarm(context, clockBean.getAlarmId());
                LogUtil.loge("取消"+clockBean.getAlarmId()+"闹钟" + clockBean.getStartTime() + "设置成功");
            }
        }
    }
}
