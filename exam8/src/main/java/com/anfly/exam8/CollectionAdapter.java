package com.anfly.exam8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> list;
    private OnLongClickItemListener longClickItemListener;

    public void setLongClickItemListener(OnLongClickItemListener longClickItemListener) {
        this.longClickItemListener = longClickItemListener;
    }

    public CollectionAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_collection, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String name = list.get(position);
        holder.tv_item.setText(name);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickItemListener != null) {
                    longClickItemListener.onLongClickItem(position);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_item;
        public TextView tv_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_item = (ImageView) itemView.findViewById(R.id.iv_item);
            this.tv_item = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }

    interface OnLongClickItemListener {
        void onLongClickItem(int position);
    }
}
