package com.anfly.viewpagerfragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class FragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> list;

    public FragmentAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list = list;
    }

    /**
     * 获取条目内容
     *
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    /**
     * 获取条目个数
     *
     * @return
     */
    @Override
    public int getCount() {
        return list.size();
    }
}
