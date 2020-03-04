package com.anfly.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
    private ArrayList<FoodBean.DataBean> list;
    private Context context;

    public RvAdapter(ArrayList<FoodBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    /**
     * 创建viewholder并加载布局
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_one, null);
        return new ViewHolder(view);
    }

    /**
     * 绑定viewholder，并赋值
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodBean.DataBean dataBean = list.get(position);
        Glide.with(context).load(dataBean.getPic()).into(holder.iv_item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_item = itemView.findViewById(R.id.iv_item);
        }
    }
}
