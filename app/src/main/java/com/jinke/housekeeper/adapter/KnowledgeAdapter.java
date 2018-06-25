package com.jinke.housekeeper.adapter;

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
public class KnowledgeAdapter extends RecyclerView.Adapter<KnowledgeAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private KnowledgeItemClickListener listener;
    private static int commonlyTitleList[] = {R.string.fragment_knowledge_quality_inspection,
            R.string.fragment_knowledge_equipment_inspection,
            R.string.fragment_knowledge_equipment_maintenance,
            R.string.fragment_knowledge_equipment_project,
            R.string.fragment_knowledge_equipment_work};
    private static int commonlyIcoList[] = {R.drawable.fragment_workbench_quality_inspection,
            R.drawable.fragment_workbench_equipment_inspection,
            R.drawable.icon_work_bench_equipment_maintenance,
            R.drawable.icon_equipment_project,
            R.drawable.icon_equipment_work
    };
    public KnowledgeAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setListener(KnowledgeItemClickListener listener) {
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
        holder.title.setText(commonlyTitleList[position]);
        Picasso.with(mContext).load(commonlyIcoList[position]).into(holder.image);
        holder.layout.setTag(position);
    }
    @Override
    public int getItemCount() {
        return commonlyTitleList.length;
    }
    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onKnowledgeItemClick((Integer) v.getTag());
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        RelativeLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_workbench_text);
            image = (ImageView) itemView.findViewById(R.id.item_workbench_image);
            layout = (RelativeLayout) itemView.findViewById(R.id.item_workbench_inspection_layout);
        }
    }
    public interface KnowledgeItemClickListener {
        void onKnowledgeItemClick(int position);
    }
}