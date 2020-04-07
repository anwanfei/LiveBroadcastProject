package com.anfly.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anfly.finalproject.R;
import com.anfly.finalproject.bean.FoodBean;
import com.bumptech.glide.Glide;

import java.util.List;

public class ExamSiixAdapter extends RecyclerView.Adapter<ExamSiixAdapter.ViewHolder> {
    private Context context;
    private List<FoodBean.DataBean> list;

    public ExamSiixAdapter(Context context, List<FoodBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_exam_six, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        FoodBean.DataBean dataBean = list.get(position);
        Glide.with(context).load(dataBean.getPic()).into(holder.iv_e6);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_e6;

        public ViewHolder(@NonNull View rootView) {
            super(rootView);
            this.iv_e6 = (ImageView) rootView.findViewById(R.id.iv_e6);
        }
    }
}
