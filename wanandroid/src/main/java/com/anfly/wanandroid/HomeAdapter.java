package com.anfly.wanandroid;

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
    private ArrayList<BannerBean.DataBean> bannsers;
    private ArrayList<ArticleBean.DataBean.DatasBean> list;
    private Context context;
    private int VIEW_TYPE_ONE = 1;
    private int VIEW_TYPE_TWO = 2;
    private final LayoutInflater inflater;
    private OnitemClickListener onitemClickListener;

    public void setOnitemClickListener(OnitemClickListener onitemClickListener) {
        this.onitemClickListener = onitemClickListener;
    }

    public HomeAdapter(ArrayList<BannerBean.DataBean> bannsers, ArrayList<ArticleBean.DataBean.DatasBean> list, Context context) {
        this.bannsers = bannsers;
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ONE) {
            View view = inflater.inflate(R.layout.item_banner, null);
            return new ViewHolderOne(view);
        } else {
            View view = inflater.inflate(R.layout.item_list, null);
            return new ViewHolderTwo(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        int itemViewType = holder.getItemViewType();

        if (itemViewType == VIEW_TYPE_ONE) {
            ArrayList<String> images = new ArrayList<>();
            for (int i = 0; i < bannsers.size(); i++) {
                images.add(bannsers.get(i).getImagePath());
            }
            ViewHolderOne viewHolderOne = (ViewHolderOne) holder;
            viewHolderOne.banner.setImages(images).setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context).load(path).into(imageView);
                }
            }).start();
        } else {
            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            ArticleBean.DataBean.DatasBean datasBean = list.get(position - 1);
            viewHolderTwo.tv_title.setText(datasBean.getTitle());
            viewHolderTwo.tv_share_user.setText(datasBean.getShareUser());
            viewHolderTwo.tv_super_chapter_name.setText(datasBean.getSuperChapterName() + "/" + datasBean.getChapterName());
            viewHolderTwo.tv_share_date.setText(datasBean.getNiceShareDate());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onitemClickListener != null) {
                    onitemClickListener.onItemClick(position);
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
        public Banner banner;

        public ViewHolderOne(View rootView) {
            super(rootView);
            this.banner = (Banner) rootView.findViewById(R.id.banner);
        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {
        public TextView tv_share_user;
        public TextView tv_super_chapter_name;
        public TextView tv_title;
        public TextView tv_share_date;
        public ImageView iv_like;

        public ViewHolderTwo(View rootView) {
            super(rootView);
            this.tv_share_user = (TextView) rootView.findViewById(R.id.tv_share_user);
            this.tv_super_chapter_name = (TextView) rootView.findViewById(R.id.tv_super_chapter_name);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.iv_like = (ImageView) rootView.findViewById(R.id.iv_like);
            this.tv_share_date = (TextView) rootView.findViewById(R.id.tv_share_date);
        }
    }

    interface OnitemClickListener {
        void onItemClick(int position);
    }
}
