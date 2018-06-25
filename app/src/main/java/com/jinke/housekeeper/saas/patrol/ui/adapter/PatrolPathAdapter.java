package com.jinke.housekeeper.saas.patrol.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.bean.IsStartBean;
import com.jinke.housekeeper.saas.patrol.bean.PatrolRecordListBean;
import com.jinke.housekeeper.saas.patrol.bean.PlanListBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PatrolPathAdapter extends BaseAdapter {
    private List<IsStartBean.ListDataBean> infoList;
    private Context context;
    private LayoutInflater inflater;

    public PatrolPathAdapter(Context mContext, List<IsStartBean.ListDataBean> infoList) {
        this.context = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(mContext);
    }

    public void setPatrolRecordListBean(List<IsStartBean.ListDataBean> infoList) {
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
        convertView = inflater.inflate(R.layout.adapter_patrol_path, null);
        holder = new viewHolder();
        holder.pathName = (TextView) convertView.findViewById(R.id.patrol_path_name);
        holder.pathTime = (TextView) convertView.findViewById(R.id.patrol_path_time);
        holder.pathState = (TextView) convertView.findViewById(R.id.patrol_path_state);
        holder.pathEnsLine = (TextView) convertView.findViewById(R.id.patrol_path_ens_line);
        if (position == infoList.size()-1){
            holder.pathEnsLine.setVisibility(View.VISIBLE);
        }
        if(null != infoList.get(position).getPointName()){
            if (null != infoList.get(position).getCompleteTime() && !"".equals(infoList.get(position).getCompleteTime())){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = simpleDateFormat.parse(infoList.get(position).getCompleteTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat showDateFormat = new SimpleDateFormat("HH:mm");
                holder.pathTime.setText(showDateFormat.format(date));
                holder.pathState.setText("已巡");
            }else {
                holder.pathState.setText("未巡");
            }
            holder.pathName.setText(infoList.get(position).getPointName());
        }
        return convertView;
    }

    viewHolder holder;

    public class viewHolder {
        TextView pathName;
        TextView pathTime;
        TextView pathState;
        TextView pathEnsLine;
    }

}
