package com.anfly.recyclerview;

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

/**
 * 多布局适配器写法：
 * 1.自定义一个适配器继承自RecyclerView.Adapter，泛型是<RecyclerView.ViewHolder>
 * 2.多（相对于单布局）重写一个方法getItemViewType()，条目view分类型
 * 3.重写onCreateViewHolder，分类型加载不同布局
 * 4.重写onBindViewHolder，根据holder获取类型，然后分类型赋值
 */
class MultiLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<FoodBean.DataBean> list;
    private ArrayList<BannerBean> banners;
    private Context context;
    private int VIEW_ITEM_TYPE_ONE = 1;
    private int VIEW_ITEM_TYPE_TWO = 2;
    private int VIEW_ITEM_TYPE_THREE = 3;


    public MultiLayoutAdapter(ArrayList<FoodBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM_TYPE_ONE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_rv_one, null);
            return new ViewHolderOne(view);
        } else if (viewType == VIEW_ITEM_TYPE_TWO) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_rv_two, null);
            return new ViewHolderTwo(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_rv_two, null);
            return new ViewHolderTwo(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        FoodBean.DataBean dataBean = list.get(position);
        int itemViewType = holder.getItemViewType();

        if (itemViewType == VIEW_ITEM_TYPE_ONE) {
            //banners，position==0
            ViewHolderOne viewHolderOne = (ViewHolderOne) holder;
            viewHolderOne.tv_item.setText(dataBean.getTitle());
            Glide.with(context).load(dataBean.getPic()).into(viewHolderOne.iv_item);
        } else if (itemViewType == VIEW_ITEM_TYPE_TWO) {
            int newPosition = position - 1;//position==1
            FoodBean.DataBean dataBean1 = list.get(newPosition);
            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            viewHolderTwo.tv_item.setText(dataBean.getTitle());
            Glide.with(context).load(dataBean.getPic()).into(viewHolderTwo.iv_item);
        } else {
            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            viewHolderTwo.tv_item.setText(dataBean.getTitle());
            Glide.with(context).load(dataBean.getPic()).into(viewHolderTwo.iv_item);
        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (position % 2 == 0) {
//            return VIEW_ITEM_TYPE_ONE;
//        } else {
//            return VIEW_ITEM_TYPE_TWO;
//        }

        int i = position % 3;

        if (i == 0) {
            return VIEW_ITEM_TYPE_ONE;
        } else if (i == 1) {
            return VIEW_ITEM_TYPE_TWO;
        } else {
            return VIEW_ITEM_TYPE_THREE;
        }
//
//        if (position == 0) {
//            return VIEW_ITEM_TYPE_TWO;
//        } else {
//            return VIEW_ITEM_TYPE_TWO;
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderOne extends RecyclerView.ViewHolder {
        private ImageView iv_item;
        private TextView tv_item;

        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            iv_item = itemView.findViewById(R.id.iv_item);
            tv_item = itemView.findViewById(R.id.tv_item);
        }
    }

    public class ViewHolderTwo extends RecyclerView.ViewHolder {
        private ImageView iv_item;
        private TextView tv_item;

        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);
            iv_item = itemView.findViewById(R.id.iv_item);
            tv_item = itemView.findViewById(R.id.tv_item);
        }
    }
}
