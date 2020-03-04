package com.anfly.smartgirl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * 什么是接口
 * 什么是接口回调：类A调用类B的方法b()，类B再回调类A的方法a()，其中方法a()是定义在接口中的，由类A来实现。这是一个双向调用的过程
 * <p>
 * <p>
 * 点击事件的步骤
 * 1.定义接口，接口中定义方法
 * 2.把接口声明一个变量，并set方法
 * 3.条目view做点击事件
 * 4.adapter做点击事件，实现具体功能
 */
class SmartGirlAdapter extends RecyclerView.Adapter<SmartGirlAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SmartBean.ResultsBean> list;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;


    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 声明onItemClickListener对象是因为接下来，接口实现类调用了这个方法我要用传过来的
     * onItemClickListener去做操作，去调用onItemClick(),来实现定义的具体操作
     * onItemClick()
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SmartGirlAdapter(Context context, ArrayList<SmartBean.ResultsBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_smart_girl, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        SmartBean.ResultsBean resultsBean = list.get(position);
        Glide.with(context).load(resultsBean.getUrl()).into(holder.iv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(position);
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
        private ImageView iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }
}
