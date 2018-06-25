package com.jinke.housekeeper.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Administrator on 2017/8/24.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> implements View.OnClickListener {
    private OnCommlyListener listener;
    private Context mContext;

    public ContactAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_contact, null);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txHead.setText("JK");
        holder.txHeadOffice.setText("重庆金科物业服务集团");
        ContactProjectAdapter adapter = new ContactProjectAdapter(mContext);
        holder.recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
        holder.recycler_view.setAdapter(adapter);
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
        TextView txHeadOffice;
        RecyclerView recycler_view;

        public ViewHolder(View itemView) {
            super(itemView);
            txHead = (TextView) itemView.findViewById(R.id.item_contact_tx_head);
            txHeadOffice = (TextView) itemView.findViewById(R.id.item_contact_tx_head_office);
            recycler_view = (RecyclerView) itemView.findViewById(R.id.item_contact_recycler_view);
        }
    }

    public interface OnCommlyListener {
        void onCommlyItemClick(int position);
    }

    public void setListener(OnCommlyListener listener) {
        this.listener = listener;
    }
}
