package com.anfly.tablayoutr;

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
        fragments.add(new CollectionFragment());

        ArrayList<String> titles = new ArrayList<>();
        titles.add("home");
        titles.add("Collection");

        TVFAdapter adapter = new TVFAdapter(getSupportFragmentManager(), fragments, titles);
        vp.setAdapter(adapter);
        tab.setupWithViewPager(vp);


//        tab.getTabAt(0).setIcon()
    }
}
