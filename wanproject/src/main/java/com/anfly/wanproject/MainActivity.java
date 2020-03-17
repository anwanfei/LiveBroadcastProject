package com.anfly.wanproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp;
    private TabLayout tab;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp);
        tab = (TabLayout) findViewById(R.id.tab);

        fragments = new ArrayList<>();
        fragments.add(new HomeFramgment());
        fragments.add(new NavigationFragment());
        fragments.add(new ProjectFramgment());

        ArrayList<String> titils = new ArrayList<>();
        titils.add("首页");
        titils.add("导航");
        titils.add("项目");

        WANAdapter wanAdapter = new WANAdapter(getSupportFragmentManager(), fragments, titils);
        vp.setAdapter(wanAdapter);
        tab.setupWithViewPager(vp);
    }
}
