package com.anfly.listviewr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class LvAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<FoodBean.DataBean> list;

    public LvAdapter(Context context, ArrayList<FoodBean.DataBean> list) {
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

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FoodBean.DataBean dataBean = list.get(position);
        Glide.with(context).load(dataBean.getPic()).into(holder.iv_lv);
        holder.tv_name.setText(dataBean.getTitle());

        return convertView;
    }

    class ViewHolder {
        public ImageView iv_lv;
        public TextView tv_name;

        public ViewHolder(View rootView) {
            this.iv_lv = (ImageView) rootView.findViewById(R.id.iv_lv);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        }

    }
}
