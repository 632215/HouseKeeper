package com.jinke.housekeeper.saas.equipment.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.bean.TaskBean;
import com.jinke.housekeeper.saas.equipment.ui.activity.BluetoothLinkActivity;
import com.jinke.housekeeper.saas.equipment.ui.activity.CheckingInformationActivity;
import com.jinke.housekeeper.saas.equipment.ui.widget.CustomDialog;
import com.jinke.housekeeper.saas.report.util.ToastUtils;

import java.util.List;

/**
 * function: 当前任务详细列表
 * author: hank
 * date: 2017/9/14
 */

public class DailyPatrolRecordDetailsAdapter extends BaseAdapter {

    private List<TaskBean.PointListBean> infoList;
    private TaskBean.PointListBean pointListBean;
    private Context context;
    private LayoutInflater inflater;
    private String taskTime;
    private String taskId;
    private String taskStatus;

    public DailyPatrolRecordDetailsAdapter(Context mContext, List<TaskBean.PointListBean> infoList, String taskTime, String taskId, String taskStatus) {
        this.context = mContext;
        this.infoList = infoList;
        this.taskTime = taskTime;
        this.taskId = taskId;
        this.taskStatus = taskStatus;
        inflater = LayoutInflater.from(mContext);
    }

    public void setInfoListBean(List<TaskBean.PointListBean> infoList, String taskTime, String taskId, String taskStatus) {
        this.infoList = infoList;
        this.taskTime = taskTime;
        this.taskId = taskId;
        this.taskStatus = taskStatus;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int i) {
        return null!= infoList?infoList.get(i):null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//
//            convertView.setTag(holder);
//        } else {
//            holder = (viewHolder) convertView.getTag();
//        }
        holder = new viewHolder();
        convertView = inflater.inflate(R.layout.adapter_daily_patrol_record_details, null);
        holder.pollingDetailsImg = (ImageView) convertView.findViewById(R.id.equipment_polling_details_time);
        holder.pollingDetailsName = (TextView) convertView.findViewById(R.id.equipment_polling_details_name);
        holder.pollingDetailsLoctian = (TextView) convertView.findViewById(R.id.equipment_polling_details_location);
        holder.pollingDetailsState = (TextView) convertView.findViewById(R.id.equipment_polling_details_state);
        holder.pollingDetailsName.setText(infoList.get(position).getDeviceName());
        holder.pollingDetailsLoctian.setText(infoList.get(position).getInstallationOcation());
        if ("1".equals(infoList.get(position).getStatus())) {
            holder.pollingDetailsImg.setVisibility(View.INVISIBLE);
            holder.pollingDetailsState.setText(context.getString(R.string.daily_patrol_complete));
            holder.pollingDetailsState.setTextColor(context.getResources().getColor(R.color.equipment_text_2));
            holder.pollingDetailsState.setBackground(null);
        } else if ("2".equals(infoList.get(position).getStatus())) {
            holder.pollingDetailsImg.setVisibility(View.VISIBLE);
            holder.pollingDetailsState.setText(context.getString(R.string.daily_patrol_checked));
            holder.pollingDetailsState.setTextColor(context.getResources().getColor(R.color.equipment_text_2));
            holder.pollingDetailsState.setBackground(null);
        } else {
            holder.pollingDetailsImg.setVisibility(View.INVISIBLE);
            holder.pollingDetailsState.setText(context.getString(R.string.daily_patrol_start_task));
            holder.pollingDetailsState.setTextColor(context.getResources().getColor(R.color.equipment_text_6));
            holder.pollingDetailsState.setBackgroundResource(R.drawable.gray_border_bg);
            pointListBean = infoList.get(position);
            //1未开始，2待执行，3已完成，4已超时，5已删除
            if (TaskBean.TASK_TO_PERFORM.equals(taskStatus)) {
            } else if (TaskBean.TASK_NO_TSTARTED.equals(taskStatus)) {
            } else{ //(TaskBean.TASK_TIMEOUT.equals(taskStatus))
                holder.pollingDetailsImg.setVisibility(View.INVISIBLE);
                holder.pollingDetailsState.setText(context.getString(R.string.daily_patrol_pack_timeout));
                holder.pollingDetailsState.setTextColor(context.getResources().getColor(R.color.equipment_text_19));
                holder.pollingDetailsState.setBackground(null);
            }
        }
        return convertView;
    }

    viewHolder holder;

    public class viewHolder {
        ImageView pollingDetailsImg;
        TextView pollingDetailsName;
        TextView pollingDetailsLoctian;
        TextView pollingDetailsState;
    }

}