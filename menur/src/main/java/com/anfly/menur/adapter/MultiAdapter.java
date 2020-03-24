package com.anfly.menur.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anfly.menur.R;
import com.anfly.menur.bean.SmartGirlBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MultiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<SmartGirlBean.ResultsBean> list;
    private int VIEW_TYPE_ONE = 1;
    private int VIEW_TYPE_TWO = 2;
    private final LayoutInflater inflater;

    public MultiAdapter(Context context, ArrayList<SmartGirlBean.ResultsBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ONE) {
            View inflate = inflater.inflate(R.layout.item_one, parent, false);
            return new ViewHolderOne(inflate);
        } else {
            View inflate = inflater.inflate(R.layout.item_two, parent, false);
            return new ViewHolderTwo(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        SmartGirlBean.ResultsBean resultsBean = list.get(position);
        int itemViewType = holder.getItemViewType();
        if (itemViewType == VIEW_TYPE_ONE) {
            ViewHolderOne viewHolderOne = (ViewHolderOne) holder;
            Glide.with(context).load(resultsBean.getUrl()).into(viewHolderOne.iv_item1);
            viewHolderOne.tv_item1.setText(resultsBean.getCreatedAt());
        } else {
            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            Glide.with(context).load(resultsBean.getUrl()).into(viewHolderTwo.iv_item2);
            viewHolderTwo.tv_item2.setText(resultsBean.getCreatedAt());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return VIEW_TYPE_ONE;
        } else {
            return VIEW_TYPE_TWO;
        }
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public ImageView iv_item1;
        public TextView tv_item1;

        public ViewHolderOne(View rootView) {
            super(rootView);
            this.iv_item1 = (ImageView) rootView.findViewById(R.id.iv_item1);
            this.tv_item1 = (TextView) rootView.findViewById(R.id.tv_item1);
        }

    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {
        public ImageView iv_item2;
        public TextView tv_item2;

        public ViewHolderTwo(View rootView) {
            super(rootView);
            this.iv_item2 = (ImageView) rootView.findViewById(R.id.iv_item2);
            this.tv_item2 = (TextView) rootView.findViewById(R.id.tv_item2);
        }

    }
}
