package com.jinke.housekeeper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinke.housekeeper.R;


/**
 * Created by Administrator on 2017/8/24.
 */

public class ContactProjectAdapter extends RecyclerView.Adapter<ContactProjectAdapter.ViewHolder> implements View.OnClickListener {
    private OnCommlyListener listener;
    private Context mContext;

    public ContactProjectAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_contact_project, null);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txProject.setText("十年城");
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onCommlyItemClick((Integer) v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txNum;
        TextView txProject;

        public ViewHolder(View itemView) {
            super(itemView);
            txProject = (TextView) itemView.findViewById(R.id.item_contact_project_tx_project);
        }
    }

    public interface OnCommlyListener {
        void onCommlyItemClick(int position);
    }

    public void setListener(OnCommlyListener listener) {
        this.listener = listener;
    }
}
