package com.jinke.housekeeper.saas.equipment.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.bean.IcoBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * function:
 * author: hank
 * date: 2017/9/8
 */

public class EquipmentAppAdapter extends RecyclerView.Adapter<EquipmentAppAdapter.ViewHolder> implements View.OnClickListener {
    private EquipmentAppOnClickListener listener;
    private Context mContext;
    private List<IcoBean> infoList;

    public EquipmentAppAdapter(Context mContext, List<IcoBean> infoList, EquipmentAppOnClickListener listener) {
        this.infoList = infoList;
        this.mContext = mContext;
        this.listener = listener;
    }

    public void setEquipmentAppAdapter(List<IcoBean> infoList) {
        this.infoList = infoList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_equipment_app, null);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(infoList.get(position).getInspectionTitleId());
        Picasso.with(mContext)
                .load(infoList.get(position).getInspectionTitleIcoId())
                .into(holder.image);
        holder.inspectionLayout.setTag(position);
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.equipmentAppOnClickListener((Integer) v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        RelativeLayout inspectionLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.equipment_app_title);
            image = (ImageView) itemView.findViewById(R.id.item_equipment_app_image);
            inspectionLayout = (RelativeLayout) itemView.findViewById(R.id.item_equipment_app_layout);
        }
    }

    public interface EquipmentAppOnClickListener {
        void equipmentAppOnClickListener(int position);
    }
}
