package com.anfly.tablayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> list;
    private ArrayList<String> titles;

    public FragmentAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> list, ArrayList<String> titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    public FragmentAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return titles.get(position);
//    }
}
