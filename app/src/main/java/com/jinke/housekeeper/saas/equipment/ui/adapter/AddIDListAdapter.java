package com.jinke.housekeeper.saas.equipment.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.bean.AddPointBean;
import com.jinke.housekeeper.saas.equipment.ui.activity.EquipmentSelectionActivity;

import java.util.List;

import static com.jinke.housekeeper.saas.equipment.ui.activity.StandBookActivity.EQUIPMENT_SELECTION;

/**
 * function:
 * author: hank
 * date: 2017/9/29
 */

public class AddIDListAdapter extends BaseAdapter {

    private List<AddPointBean> infoList;
    private Activity context;
    private LayoutInflater inflater;

    public AddIDListAdapter(Activity mContext, List<AddPointBean> infoList) {
        this.context = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(mContext);
    }

    public void setInfoListBean(List<AddPointBean> infoList) {
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
            convertView = inflater.inflate(R.layout.adapter_add_id_list, null);
            holder = new viewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.adapter_add_id_list_id);
            holder.location = (TextView) convertView.findViewById(R.id.adapter_add_id_list_location);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        if (null != infoList.get(position).getCardId()) {
            holder.id.setText(infoList.get(position).getCardId());
            holder.id.setBackground(null);
            final int datePosition = position;
            holder.location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent addIDListIntent = new Intent(context,EquipmentSelectionActivity.class);
                    addIDListIntent.putExtra("date","1");
                    addIDListIntent.putExtra("dateId",infoList.get(datePosition).getCardId());
                    context.startActivityForResult(addIDListIntent,EQUIPMENT_SELECTION);
                }
            });
            if (null != infoList.get(position).getDeviceId()) {
                holder.location.setText(infoList.get(position).getDeviceName());
                holder.location.setBackground(null);
            } else {
                holder.location.setHint(context.getString(R.string.add_id_list_hint_3));
            }
        }else {
            holder.id.setHint(context.getString(R.string.add_id_list_hint_1));
            holder.id.setText(null);
            holder.id.setBackgroundResource(R.drawable.blue_border_background);
            holder.location.setHint(context.getString(R.string.add_id_list_hint_2));
            holder.location.setText(null);

            holder.location.setBackgroundResource(R.drawable.blue_border_background);
        }
        return convertView;
    }

    viewHolder holder;

    public class viewHolder {
        TextView id;
        TextView location;
    }
}
