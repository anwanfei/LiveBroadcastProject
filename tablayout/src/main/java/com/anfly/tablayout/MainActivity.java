package com.anfly.tablayout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * 实现ViewPager+Fragment+TabLayout经典案例
 * 1.创建布局：viewpager和tablayout，找控件
 * 2.创建fragment
 * 3.创建集合存放fragment
 * 4.创建adapter
 * 5.vp设置adapter
 * 6.vp和tab关联起来
 * 7.给tab设置title
 */
public class MainActivity extends AppCompatActivity {

    private TabLayout tab;
    private ViewPager vp;
    private ArrayList<Fragment> list;
    private ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tab = (TabLayout) findViewById(R.id.tab);
        vp = (ViewPager) findViewById(R.id.vp);

        list = new ArrayList<>();
        titles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PageFragment fragment = new PageFragment("fragment" + i);
            titles.add("tab" + i);
            list.add(fragment);
        }

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), list, titles);
        vp.setAdapter(adapter);

        tab.setupWithViewPager(vp);

        //动态添加tab
        tab.getTabAt(0).setText("TAB1").setIcon(R.mipmap.ic_launcher_round);
        tab.getTabAt(1).setText("TAB2").setIcon(R.mipmap.ic_launcher_round);
        tab.getTabAt(2).setText("TAB3").setIcon(R.mipmap.ic_launcher_round);
        tab.getTabAt(3).setText("TAB3").setIcon(R.mipmap.ic_launcher_round);
        tab.getTabAt(4).setText("TAB3").setIcon(R.mipmap.ic_launcher_round);
        tab.getTabAt(5).setText("TAB3").setIcon(R.mipmap.ic_launcher_round);
        tab.getTabAt(6).setText("TAB3").setIcon(R.mipmap.ic_launcher_round);
        tab.getTabAt(7).setText("TAB3").setIcon(R.mipmap.ic_launcher_round);
        tab.getTabAt(8).setText("TAB3").setIcon(R.mipmap.ic_launcher_round);
        tab.getTabAt(9).setText("TAB3").setIcon(R.mipmap.ic_launcher_round);

    }
}
