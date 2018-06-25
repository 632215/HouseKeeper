package com.jinke.housekeeper.saas.report.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.squareup.picasso.Picasso;


/**
 * Created by Administrator on 2017/8/24.
 */

public class WorkBenchInspectionAdapter extends RecyclerView.Adapter<WorkBenchInspectionAdapter.ViewHolder> implements View.OnClickListener {
    private InspectionOnClickListener listener;
    private Context mContext;
    private static int inspectionTitleList[] = {
            R.string.fragment_workbench_quality_inspection,
            R.string.fragment_workbench_patrol,
            R.string.fragment_workbench_equipment_inspection,
            R.string.work_bench_equipment_maintenance};

    private static int inspectionIcoList[] = {
            R.drawable.fragment_workbench_quality_inspection,
            R.drawable.fragment_workbench_patrol,
            R.drawable.fragment_workbench_equipment_inspection,
            R.drawable.icon_work_bench_equipment_maintenance};

    public WorkBenchInspectionAdapter(Context mContext) {
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_workbench, null);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(inspectionTitleList[position]);
        Picasso.with(mContext).load(inspectionIcoList[position]).into(holder.image);
        holder.inspectionLayout.setTag(position);
    }

    @Override
    public int getItemCount() {
        return inspectionTitleList.length;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onInspectionItemClick((Integer) v.getTag());
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        RelativeLayout inspectionLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_workbench_text);
            image = (ImageView) itemView.findViewById(R.id.item_workbench_image);
            inspectionLayout = (RelativeLayout) itemView.findViewById(R.id.item_workbench_inspection_layout);
        }
    }


    public interface InspectionOnClickListener{
        void onInspectionItemClick(int position);
    }

    public void setListener(InspectionOnClickListener listener) {
        this.listener = listener;
    }
}
