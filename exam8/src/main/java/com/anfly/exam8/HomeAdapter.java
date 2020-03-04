package com.anfly.exam8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<BannerBean.DataBean> banners;
    private ArrayList<FoodBean.DataBean> list;

    private int VIEW_TYPE_ONE = 1;
    private int VIEW_TYPE_TWO = 2;
    private final LayoutInflater inflater;

    public HomeAdapter(Context context, ArrayList<BannerBean.DataBean> banners, ArrayList<FoodBean.DataBean> list) {
        this.context = context;
        this.banners = banners;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ONE) {
            View view = inflater.inflate(R.layout.item_banner, null);
            return new ViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_list, null);
            return new ViewHolderT(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = holder.getItemViewType();

        if (itemViewType == VIEW_TYPE_ONE) {

            ArrayList<String> images = new ArrayList<>();
            for (int i = 0; i < banners.size(); i++) {
                images.add(banners.get(i).getImagePath());
            }

            ViewHolder viewHolder = (ViewHolder) holder;
            ((ViewHolder) holder).banner_item.setImages(images).setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context).load(path).into(imageView);
                }
            }).start();

        } else {

            ViewHolderT viewHolderT = (ViewHolderT) holder;

            FoodBean.DataBean dataBean = list.get(position - 1);

            Glide.with(context).load(dataBean.getPic()).into(viewHolderT.iv_item);
            viewHolderT.tv_item.setText(dataBean.getTitle());
        }
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Banner banner_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.banner_item = (Banner) itemView.findViewById(R.id.banner_item);
        }
    }

    class ViewHolderT extends RecyclerView.ViewHolder {
        public ImageView iv_item;
        public TextView tv_item;

        public ViewHolderT(View rootView) {
            super(rootView);
            this.iv_item = (ImageView) rootView.findViewById(R.id.iv_item);
            this.tv_item = (TextView) rootView.findViewById(R.id.tv_item);
        }

    }
}
