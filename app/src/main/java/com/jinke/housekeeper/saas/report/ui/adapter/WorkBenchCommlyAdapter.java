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

public class WorkBenchCommlyAdapter extends RecyclerView.Adapter<WorkBenchCommlyAdapter.ViewHolder> implements View.OnClickListener {
    private OnCommlyListener listener;
    private Context mContext;
    private static int commonlyTitleList[] = {R.string.work_bench_mobile_home_inspection};
    private static int commonlyIcoList[] = {R.drawable.fragment_workbench_approval};

    public WorkBenchCommlyAdapter(Context mContext) {
        this.mContext = mContext;
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
        holder.title.setText(commonlyTitleList[position]);
        Picasso.with(mContext)
                .load(commonlyIcoList[position])
                .into(holder.image);
        holder.inspectionLayout.setTag(position);
    }

    @Override
    public int getItemCount() {
        return commonlyTitleList.length;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onCommlyItemClick((Integer) v.getTag());
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

    public interface OnCommlyListener {
        void onCommlyItemClick(int position);
    }

    public void setListener(OnCommlyListener listener) {
        this.listener = listener;
    }
}
