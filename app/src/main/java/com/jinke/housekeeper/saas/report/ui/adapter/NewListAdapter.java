package com.jinke.housekeeper.saas.report.ui.adapter;

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
 * Created by Administrator on 2017/7/27.
 */

public class NewListAdapter extends BaseAdapter {
    private List<MsgBean.ListBean> infoList;
    private Context mContext;
    private LayoutInflater inflater;

    public NewListAdapter(Context mContext, List<MsgBean.ListBean> infoList) {
        this.mContext = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(mContext);
    }

    public void setListBean(List<MsgBean.ListBean> infoList) {
        this.infoList = infoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int psiont) {
        return infoList.get(psiont);
    }

    @Override
    public long getItemId(int psiont) {
        return psiont;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_newslist, null);
            holder.txwNewsState = (TextView) view.findViewById(R.id.txwNewsState);
            holder.txwNewsTitle = (TextView) view.findViewById(R.id.txwNewsTitle);
            holder.txwNewsFrom = (TextView) view.findViewById(R.id.txwNewsFrom);
            holder.txwNewsTime = (TextView) view.findViewById(R.id.txwNewsTime);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if ("1".equals(infoList.get(position).getIsRead())) {
            holder.txwNewsState.setText(R.string.news_read);
            holder.txwNewsState.setBackgroundResource(R.drawable.shape_read);
        } else {
            holder.txwNewsState.setText(R.string.news_unRead);
            holder.txwNewsState.setBackgroundResource(R.drawable.shape_txw_btn_blue);
        }
        holder.txwNewsFrom.setText(infoList.get(position).getName());
        holder.txwNewsTime.setText(infoList.get(position).getCreateTime());
        holder.txwNewsTitle.setText(infoList.get(position).getTitle());
        return view;
    }

    private ViewHolder holder;

    private class ViewHolder {
        TextView txwNewsState;
        TextView txwNewsTitle;
        TextView txwNewsFrom;
        TextView txwNewsTime;
    }
}
