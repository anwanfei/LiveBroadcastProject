package com.anfly.wanproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PublicAdapter extends RecyclerView.Adapter<PublicAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PublicBean.DataBean.DatasBean> list;

    public PublicAdapter(Context context, ArrayList<PublicBean.DataBean.DatasBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_public, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PublicBean.DataBean.DatasBean datasBean = list.get(position);
        Glide.with(context).load(datasBean.getEnvelopePic()).into(holder.iv_item);
        holder.tv_item.setText(datasBean.getDesc());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_item;
        public ImageView iv_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.tv_item);
            iv_item = itemView.findViewById(R.id.iv_item);
        }
    }
}
