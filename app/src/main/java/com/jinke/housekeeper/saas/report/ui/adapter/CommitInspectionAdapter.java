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

public class CommitInspectionAdapter extends RecyclerView.Adapter<CommitInspectionAdapter.ViewHolder> implements View.OnClickListener {
    private OnCommitClickListener listener;
    private Context mContext;
    private static int inspectionTitleList[] = {
            R.string.work_bench_owner_inquiry,
            R.string.work_bench_arrears_inquiry,
            R.string.work_bench_questionnaire,
            R.string.work_bench_access_control,
            R.string.work_bench_parking_management,
            R.string.work_bench_community_monitoring,
            R.string.work_bench_more};

    private static int inspectionIcoList[] = {
            R.drawable.icon_work_bench_owner_inquiry,
            R.drawable.icon_work_bench_arrears_inquiry,
            R.drawable.icon_work_bench_questionnaire,
            R.drawable.icon_work_bench_access_control,
            R.drawable.icon_work_bench_parking_management,
            R.drawable.icon_work_bench_community_monitoring,
            R.drawable.icon_work_bench_more};

    public CommitInspectionAdapter(Context mContext) {
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
        if (listener != null) {
            listener.onCommitClick((Integer) v.getTag());
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


    public interface OnCommitClickListener {
        void onCommitClick(int position);
    }

    public void setListener(OnCommitClickListener listener) {
        this.listener = listener;
    }
}
