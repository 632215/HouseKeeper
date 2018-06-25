package com.jinke.housekeeper.saas.report.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.report.bean.ProcessNodeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 1/10/17.
 */

public class RectProcessNodeAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    List<ProcessNodeBean.ObjBean> nodeInfos = new ArrayList<>();

    public RectProcessNodeAdapter(Context mContext, List<ProcessNodeBean.ObjBean> nodeInfos) {
        this.mContext = mContext;
        this.nodeInfos = nodeInfos;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<ProcessNodeBean.ObjBean> nodeInfos){
        this.nodeInfos=nodeInfos;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return nodeInfos.size();
    }

    @Override
    public Object getItem(int psiont) {
        return nodeInfos.get(psiont);
    }

    @Override
    public long getItemId(int psiont) {
        return psiont;
    }

    @Override
    public View getView(int psiont, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_process_node, null);
            holder = new viewHolder();
            holder.upLine =  view.findViewById(R.id.upline);
            holder.downLine =  view.findViewById(R.id.downline);
            holder.processNode = (TextView) view.findViewById(R.id.processNode);
            holder.processTime = (TextView) view.findViewById(R.id.processTime);
            holder.devider = (TextView) view.findViewById(R.id.devider);
            holder.image= (ImageView) view.findViewById(R.id.image);
            view.setTag(holder);
        } else {
            holder = (viewHolder) view.getTag();
        }
        ProcessNodeBean.ObjBean bean = nodeInfos.get(psiont);
        if (bean!=null) {
            if (nodeInfos.size() == 1) {
                holder.upLine.setVisibility(View.INVISIBLE);
                holder.downLine.setVisibility(View.INVISIBLE);
                holder.image.setImageResource(R.drawable.circleblue);
                holder.devider.setVisibility(View.GONE);
            } else {
                if (psiont == 0) {
                    holder.upLine.setVisibility(View.INVISIBLE);
                    holder.downLine.setVisibility(View.VISIBLE);
                    holder.image.setImageResource(R.drawable.circlegray);
                    holder.devider.setVisibility(View.VISIBLE);
                } else if (psiont == nodeInfos.size() - 1) {
                    holder.downLine.setVisibility(View.INVISIBLE);
                    holder.image.setImageResource(R.drawable.circleblue);
                    holder.devider.setVisibility(View.GONE);
                }
            }
            holder.processNode.setText(bean.getName());
            holder.processTime.setText(bean.getTime());
        }
        return view;
    }

    private viewHolder holder;

    class viewHolder {
        View upLine;
        View downLine;
        TextView processNode;
        TextView processTime;
        TextView devider;
        ImageView image;
    }
}
