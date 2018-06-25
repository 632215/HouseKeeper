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

public class QualityInspectionAdapter extends RecyclerView.Adapter<QualityInspectionAdapter.ViewHolder> implements View.OnClickListener {
    private InspectionOnClickListener listener;
    private Context mContext;
    private static int titleList[] = {
//            R.string.activity_quality_inspection_inspection_registration,
            R.string.activity_quality_inspection_inspection_processing,
            R.string.activity_quality_inspection_inspection_inquiries,
            R.string.activity_quality_inspection_submit_the_news,
            R.string.activity_quality_inspection_offline_upload};
    private static int icoList[] = {
//            R.drawable.activity_quality_inspection_inspection_registration,
            R.drawable.activity_quality_inspection_inspection_processing,
            R.drawable.activity_quality_inspection_inspection_inquiries,
            R.drawable.activity_quality_inspection_submit_the_news,
            R.drawable.activity_quality_inspection_offline_upload};

    public QualityInspectionAdapter(Context mContext) {
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_quality_inspection, null);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(titleList[position]);
        Picasso.with(mContext)
                .load(icoList[position])
                .into(holder.image);
        holder.inspectionLayout.setTag(position);
    }

    @Override
    public int getItemCount() {
        return titleList.length;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onInspectionItemClick((Integer) v.getTag());
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        RelativeLayout inspectionLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_quality_text);
            image = (ImageView) itemView.findViewById(R.id.item_quality_image);
            inspectionLayout = (RelativeLayout) itemView.findViewById(R.id.item_quality_layout);
        }
    }


    public interface InspectionOnClickListener {
        void onInspectionItemClick(int position);
    }

    public void setListener(InspectionOnClickListener listener) {
        this.listener = listener;
    }
}
