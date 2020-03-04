package com.anfly.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class FoodAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<FoodBean.DataBean> list;

    public FoodAdapter(Context context, ArrayList<FoodBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        FoodBean.DataBean dataBean = list.get(position);
        viewHolder.tv_item.setText(dataBean.getTitle());
        //加载网络图片
        Glide
                .with(context)
                .load(dataBean.getPic())
                .into(viewHolder.iv_item);

        return convertView;
    }

    class ViewHolder {
        public ImageView iv_item;
        public TextView tv_item;

        public ViewHolder(View rootView) {
            this.iv_item = (ImageView) rootView.findViewById(R.id.iv_item);
            this.tv_item = (TextView) rootView.findViewById(R.id.tv_item);
        }

    }
}
