package com.anfly.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anfly.finalproject.R;
import com.anfly.finalproject.bean.BannerBean;
import com.anfly.finalproject.bean.FoodBean;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<FoodBean.DataBean> list;
    private ArrayList<BannerBean.DataBean> banners;

    private int VIEW_TYPE_ONE = 1;
    private int VIEW_TYPE_TWO = 2;
    private final LayoutInflater inflater;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public HomeAdapter(Context context, ArrayList<FoodBean.DataBean> list, ArrayList<BannerBean.DataBean> banners) {
        this.context = context;
        this.list = list;
        this.banners = banners;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ONE) {
            View view = inflater.inflate(R.layout.item_banner, parent, false);
            return new ViewHolderOne(view);
        } else {
            View view = inflater.inflate(R.layout.item_list_home, parent, false);
            return new ViewHolderTwo(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int itemViewType = holder.getItemViewType();

        if (itemViewType == VIEW_TYPE_ONE) {
            ViewHolderOne viewHolderOne = (ViewHolderOne) holder;
            ArrayList<String> images = new ArrayList<>();
            ArrayList<String> tieles = new ArrayList<>();
            for (int i = 0; i < banners.size(); i++) {
                images.add(banners.get(i).getImagePath());
                tieles.add(banners.get(i).getTitle());
            }

            viewHolderOne.banner_home.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                    .setBannerTitles(tieles)
                    .setImages(images)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            Glide.with(context).load(path).into(imageView);
                        }
                    }).start();
        } else {
            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            viewHolderTwo.tv_list_home.setText(list.get(position - 1).getTitle());
            Glide.with(context).load(list.get(position - 1).getPic()).into(viewHolderTwo.iv_list_home);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position-1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_ONE;
        } else {
            return VIEW_TYPE_TWO;
        }
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        public Banner banner_home;

        public ViewHolderOne(View rootView) {
            super(rootView);
            this.banner_home = (Banner) rootView.findViewById(R.id.banner_home);
        }

    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {
        public ImageView iv_list_home;
        public TextView tv_list_home;

        public ViewHolderTwo(View rootView) {
            super(rootView);
            this.iv_list_home = (ImageView) rootView.findViewById(R.id.iv_list_home);
            this.tv_list_home = (TextView) rootView.findViewById(R.id.tv_list_home);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
