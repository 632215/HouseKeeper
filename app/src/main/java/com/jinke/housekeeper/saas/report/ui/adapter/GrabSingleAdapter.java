package com.jinke.housekeeper.saas.report.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.report.bean.WorkOrderBean;
import com.jinke.housekeeper.saas.report.util.RadioUtil;
import com.jinke.housekeeper.saas.report.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * function:
 * author: hank
 * date: 2017/8/14
 */

public class GrabSingleAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<WorkOrderBean.ListObjBean> infoList = new ArrayList<>();

    public GrabSingleAdapter(Context mContext, List<WorkOrderBean.ListObjBean> infoList) {
        this.mContext = mContext;
        this.infoList = infoList;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<WorkOrderBean.ListObjBean> infoList) {
        this.infoList = infoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private boolean flag = false;

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.adapter_grab_single, null);
            holder.title = (TextView) view.findViewById(R.id.tv_work_order_title);
            holder.image_work_order = (ImageView) view.findViewById(R.id.image_work_order);
            holder.rl_video_view = (RelativeLayout) view.findViewById(R.id.voiceLayout);
            holder.tv_grab_time = (TextView) view.findViewById(R.id.tv_grab_time);
            holder.voiceTime = view.findViewById(R.id.voiceTime);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final WorkOrderBean.ListObjBean bean = infoList.get(position);
        holder.title.setText(bean.getDescribe());
        holder.tv_grab_time.setText(bean.getSuperviseTime());
        holder.rl_video_view.setVisibility(StringUtil.isEmpty(bean.getAudioaddress()) ? View.GONE : View.VISIBLE);

        if (bean.getImg_address().size() > 0) {
            Glide.with(mContext).load(bean.getImg_address().get(0)).error(R.drawable.lib).placeholder(R.drawable.lib).into(holder.image_work_order);
        } else {
            holder.image_work_order.setBackground(mContext.getResources().getDrawable(R.drawable.lib));
        }
        holder.rl_video_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.audioAddress(bean.getAudioaddress(), holder.voiceTime);
                }
            }
        });
        return view;
    }

    public void setOnAudioAddressListener(OnAudioAddressListener listener) {
        this.listener = listener;
    }

    OnAudioAddressListener listener;

    public interface OnAudioAddressListener {
        void audioAddress(String address, View view);
    }

    private ViewHolder holder;


    private class ViewHolder {
        TextView title;
        TextView tv_grab_time;
        ImageView image_work_order;
        RelativeLayout rl_video_view;
        View voiceTime;
    }

    /**
     * 格式时间
     *
     * @param systemTime
     */
    private String getTimeText(String systemTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(systemTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat showDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        if (null != date) {
            return showDateFormat.format(date);
        } else {
            return "";
        }
    }

}