package com.jinke.housekeeper.saas.report.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.bean.CountXMListInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 32 on 2016/12/22.
 */

public class ProjectSelectionAdapter extends BaseAdapter {

    private Context mContext;
    LayoutInflater inflater;
    private List<CountXMListInfo.ListObjBean> mArrayList = new ArrayList<>();
    private OnItemClickLitener mOnItemClickLitener;//监听item

    public interface OnItemClickLitener {
        void onItemClick(String keyId);
    }

    public void setData(List<CountXMListInfo.ListObjBean> mArrayList) {
        this.mArrayList = mArrayList;
        notifyDataSetChanged();

    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public ProjectSelectionAdapter(Context mContext, List<CountXMListInfo.ListObjBean> mArrayList) {
        this.mArrayList = mArrayList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_projectselection, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.tv_item_registerdepartment);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CountXMListInfo.ListObjBean bean = mArrayList.get(position);
        holder.text.setText(bean.getName());
        return convertView;
    }

    ViewHolder holder;


    class ViewHolder {
        TextView text;
    }
}
