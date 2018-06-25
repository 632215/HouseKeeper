package com.jinke.housekeeper.saas.handoverroom.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.handoverroom.bean.FindListDataBean;
import com.jinke.housekeeper.saas.handoverroom.bean.KeyValueBean;

import java.util.ArrayList;
import java.util.List;

/**
 * function: 选择设备Adapter
 * author: hank
 * date: 2017/9/13
 */

public class HandRoomSelectionAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<KeyValueBean> infoList;

    public HandRoomSelectionAdapter(Context mContext
            , List<FindListDataBean.ProjectListDataBean> projectListDataBeans, List<FindListDataBean.DeviceListDataBean> DeviceListDataBean) {
        this.context = mContext;
        inflater = LayoutInflater.from(mContext);
        infoList = new ArrayList<>();
        if (null != projectListDataBeans) {
            for (FindListDataBean.ProjectListDataBean dataBean : projectListDataBeans){
                KeyValueBean valueBean  = new KeyValueBean();
                valueBean.setKey(dataBean.getProjectId());
                valueBean.setValue(dataBean.getProjectName());
                infoList.add(valueBean);
            }
        } else {
            for (FindListDataBean.DeviceListDataBean dataBean : DeviceListDataBean){
                KeyValueBean valueBean  = new KeyValueBean();
                valueBean.setKey(dataBean.getDeviceSerial());
                valueBean.setValue(dataBean.getDeviceName());
                infoList.add(valueBean);
            }
        }
    }

    public void setInfoListBean( List<FindListDataBean.ProjectListDataBean> projectListDataBeans, List<FindListDataBean.DeviceListDataBean> DeviceListDataBean) {
        infoList = new ArrayList<>();
        if (null != projectListDataBeans) {
            for (FindListDataBean.ProjectListDataBean dataBean : projectListDataBeans){
                KeyValueBean valueBean  = new KeyValueBean();
                valueBean.setKey(dataBean.getProjectId());
                valueBean.setValue(dataBean.getProjectName());
                infoList.add(valueBean);
            }
        } else {
            for (FindListDataBean.DeviceListDataBean dataBean : DeviceListDataBean){
                KeyValueBean valueBean  = new KeyValueBean();
                valueBean.setKey(dataBean.getDeviceSerial());
                valueBean.setValue(dataBean.getDeviceName());
                infoList.add(valueBean);
            }
        }
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
            convertView = inflater.inflate(R.layout.adapter_hand_room_selection, null);
            holder = new viewHolder();
            holder.value = (TextView) convertView.findViewById(R.id.hand_room_selection_value);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.value.setText(infoList.get(position).getValue());
        return convertView;
    }

    viewHolder holder;

    public class viewHolder {
        TextView value;
    }
}
