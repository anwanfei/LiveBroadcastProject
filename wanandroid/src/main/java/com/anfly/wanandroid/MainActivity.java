package com.anfly.wanandroid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp;
    private TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp);
        tab = (TabLayout) findViewById(R.id.tab);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new NavigationFragment());
        fragments.add(new ProjectFragment());

        ArrayList<String> titles = new ArrayList<>();
        titles.add("首页");
        titles.add("导航");
        titles.add("项目");

        WanAdapter adapter = new WanAdapter(getSupportFragmentManager(), fragments, titles);

        vp.setAdapter(adapter);

        tab.setupWithViewPager(vp);
    }
}
