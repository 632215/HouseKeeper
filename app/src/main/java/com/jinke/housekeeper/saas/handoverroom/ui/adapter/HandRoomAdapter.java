package com.jinke.housekeeper.saas.handoverroom.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.handoverroom.bean.HandoverRoomBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * function: 选择设备Adapter
 * author: hank
 * date: 2017/9/13
 */

public class HandRoomAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<HandoverRoomBean.ListDataBean> infoList;

    public HandRoomAdapter(Context mContext, List<HandoverRoomBean.ListDataBean> infoList) {
        this.context = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(mContext);
    }

    public void setInfoListBean(List<HandoverRoomBean.ListDataBean> infoList) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_hand_room, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.endHandoverRoomProject.setText(infoList.get(position).getProjectName());
        holder.handoverRoomNumber.setText(infoList.get(position).getLineNo());
        holder.handoverRoomAddress.setText(infoList.get(position).getBuilding() + infoList.get(position).getUnit() + infoList.get(position).getRoom());
        holder.handoverRoomName.setText(infoList.get(position).getUserName());
        holder.handoverRoomPhone.setText(infoList.get(position).getUserPhone());
        return convertView;
    }

    ViewHolder holder;


    static class ViewHolder {
        @Bind(R.id.end_handover_room_project)
        TextView endHandoverRoomProject;
        @Bind(R.id.handover_room_number)
        TextView handoverRoomNumber;
        @Bind(R.id.handover_room_address)
        TextView handoverRoomAddress;
        @Bind(R.id.handover_room_name)
        TextView handoverRoomName;
        @Bind(R.id.handover_room_phone)
        TextView handoverRoomPhone;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
