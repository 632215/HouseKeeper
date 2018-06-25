package com.jinke.housekeeper.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.housekeeper.R;


/**
 * Created by Administrator on 2017/8/24.
 */

public class FrequentContactAdapter extends RecyclerView.Adapter<FrequentContactAdapter.ViewHolder> implements View.OnClickListener {
    private OnCommlyListener listener;
    private Context mContext;

    public FrequentContactAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_frequent_contact, null);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txHead.setText("JK");
        holder.txName.setText("里斯");
        holder.txPhone.setText("12345678911");
        holder.txPost.setText("经理");
        holder.imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onCommlyItemClick((Integer) v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txHead;
        TextView txName;
        TextView txPost;
        TextView txPhone;
        ImageView imgPhone;
        public ViewHolder(View itemView) {
            super(itemView);
            txHead = (TextView) itemView.findViewById(R.id.item_frequent_contact_tx_head);
            txName = (TextView) itemView.findViewById(R.id.item_frequent_contact_tx_name);
            txPost = (TextView) itemView.findViewById(R.id.item_frequent_contact_tx_post);
            txPhone = (TextView) itemView.findViewById(R.id.item_frequent_contact_tx_phone);
            imgPhone = (ImageView) itemView.findViewById(R.id.item_frequent_contact_img_phone);

        }
    }

    public interface OnCommlyListener {
        void onCommlyItemClick(int position);
    }

    public void setListener(OnCommlyListener listener) {
        this.listener = listener;
    }
}
