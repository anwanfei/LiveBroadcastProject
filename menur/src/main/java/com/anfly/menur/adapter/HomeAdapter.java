package com.anfly.menur.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anfly.menur.R;
import com.anfly.menur.bean.BannerBean;
import com.anfly.menur.bean.FoodBean;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<FoodBean.DataBean> list;
    private ArrayList<BannerBean.DataBean> banners;
    private Context context;
    private int VIEW_TYPE_ONE = 1;
    private int VIEW_TYPE_TWO = 2;

    public HomeAdapter(ArrayList<FoodBean.DataBean> list, ArrayList<BannerBean.DataBean> banners, Context context) {
        this.list = list;
        this.banners = banners;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ONE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_home_banner, parent, false);
            return new ViewHolderT(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_home_list, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = holder.getItemViewType();
        if (itemViewType == VIEW_TYPE_ONE) {
            ViewHolderT viewHolderT = (ViewHolderT) holder;
            ArrayList<String> images = new ArrayList<>();
            ArrayList<String> titile = new ArrayList<>();

            for (BannerBean.DataBean banner : banners) {
                images.add(banner.getImagePath());
                titile.add(banner.getTitle());
            }
            viewHolderT.banner_home.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                    .setImages(images)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            Glide.with(context).load(path).into(imageView);
                        }
                    }).setBannerTitles(titile).start();

        } else {
            ViewHolder viewHolder = (ViewHolder) holder;
            FoodBean.DataBean dataBean = list.get(position - 1);
            Glide.with(context).load(dataBean.getPic()).into(viewHolder.iv_home_item);
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_home_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_home_item = itemView.findViewById(R.id.iv_home_item);
        }
    }

    public class ViewHolderT extends RecyclerView.ViewHolder {
        public Banner banner_home;

        public ViewHolderT(@NonNull View itemView) {
            super(itemView);
            banner_home = itemView.findViewById(R.id.banner_home);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_ONE;
        } else {
            return VIEW_TYPE_TWO;
        }
    }
}
