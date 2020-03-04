package com.anfly.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class VpAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> list;

    public VpAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * 判断instantiateItem返回的object是否是一个view
     * 如果是view，放回true，否则返回false
     *
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /**
     * 移除视图
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);一定删除
        container.removeView((View) object);
    }

    /**
     * 创建视图
     * 获取视图中控件
     * 赋值
     * 添加到容器
     *
     * @param container
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vp, null);

        TextView tv = view.findViewById(R.id.tv);

        tv.setText(list.get(position));

        container.addView(view);
        return view;
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        super.finishUpdate(container);
    }
}
