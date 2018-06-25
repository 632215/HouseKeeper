package com.jinke.housekeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.bean.MsgBean;

import java.util.List;


/**
 * Created by Administrator on 2017/8/24.
 */

public class MsgAdapter extends BaseAdapter {
    private static final int MAX_ITEM_COUNT = 4;
    private List<MsgBean.ListBean> infoList;
    private Context context;
    private LayoutInflater inflater;

    public MsgAdapter(Context mContext, List<MsgBean.ListBean> infoList) {
        this.context = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(mContext);
    }

    public void setListBean(List<MsgBean.ListBean> infoList) {
        this.infoList = infoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return Math.min(MAX_ITEM_COUNT,infoList.size());
    }

    @Override
    public Object getItem(int position) {
        return infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_msg, null);
            holder = new ViewHolder();
            holder.state = (TextView) convertView.findViewById(R.id.item_msg_text_state);
            holder.readState = (TextView) convertView.findViewById(R.id.item_msg_text_read_state);
            holder.content = (TextView) convertView.findViewById(R.id.item_msg_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (infoList.get(position).getTypeId()) {
            case "1":
                holder.state.setText(context.getString(R.string.fragment_msg_state_1));
                holder.state.setBackgroundResource(R.drawable.fragment_msg_red);
                break;
            case "2":
                holder.state.setText(context.getString(R.string.fragment_msg_state_2));
                holder.state.setBackgroundResource(R.drawable.fragment_msg_yellow);
                break;
            case "3":
                holder.state.setText(context.getString(R.string.fragment_msg_state_3));
                holder.state.setBackgroundResource(R.drawable.fragment_msg_blue);
                break;
            case "4":
                holder.state.setText(context.getString(R.string.fragment_msg_state_4));
                holder.state.setBackgroundResource(R.drawable.fragment_msg_green);
                break;
            case "5":
                holder.state.setText(context.getString(R.string.fragment_msg_state_5));
                holder.state.setBackgroundResource(R.drawable.fragment_msg_blue);
                break;
        }
        if ("1".equals(infoList.get(position).getIsRead())) {
            holder.readState.setVisibility(View.GONE);
            holder.content.setTextColor(context.getResources().getColor(R.color.fragment_msg_unread_text_color));
        } else {
            holder.readState.setVisibility(View.VISIBLE);
            holder.readState.setText(context.getString(R.string.fragment_msg_state_unread));
            holder.content.setTextColor(context.getResources().getColor(R.color.black));
        }
        holder.content.setText(infoList.get(position).getTitle());
        return convertView;
    }

    public ViewHolder holder;

    public class ViewHolder{
        TextView state;
        TextView readState;
        TextView content;

    }
}
