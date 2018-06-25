package com.jinke.housekeeper.saas.report.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jinke.housekeeper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 18-4-18.
 */

public class WaitingResponseAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> list = new ArrayList<>();

    private LayoutInflater inflater;

    public WaitingResponseAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    ViewHolder holder;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_work_order_details_pic_list, null);
            holder = new ViewHolder();
            holder.imgPicture = (ImageView) convertView.findViewById(R.id.imgPicture);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(list.get(position)).error(R.drawable.lib).placeholder(R.drawable.lib).into(holder.imgPicture);
        holder.imgPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemImage(position);
                }

            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView imgPicture;
    }

    public void setOnClickImageListener(OnClickImageListener listener) {
        this.listener = listener;
    }

    OnClickImageListener listener;

    public interface OnClickImageListener {
        void onItemImage(int item);

    }
}
