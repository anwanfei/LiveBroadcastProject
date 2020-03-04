package com.anfly.tablayoutframgment;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;

/**
 * fragment+tablayout实现点击tab切换对应fragment效果
 * 1.创建布局、找控件
 * 2.tab添加新tab并添加对应的标题
 * 3.获取碎片管理器，把两个fragment添加进去
 * 4.tab添加选中监听，在onTabSelected方法中根据位置（0,1）开启对应的事务
 */
public class MainActivity extends AppCompatActivity {

    private FrameLayout fl_container;
    private TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        fl_container = (FrameLayout) findViewById(R.id.fl_container);
        tab = (TabLayout) findViewById(R.id.tab);

        tab.addTab(tab.newTab().setText("Home"));
        tab.addTab(tab.newTab().setText("Collection"));

        final FragmentManager fm = getSupportFragmentManager();
        final HomeFragment homeFragment = new HomeFragment();
        final CollectionFragment collectionFragment = new CollectionFragment();
        fm.beginTransaction()
                .add(R.id.fl_container, homeFragment)
                .add(R.id.fl_container, collectionFragment)
                .hide(collectionFragment)
                .commit();

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fm.beginTransaction().hide(collectionFragment).show(homeFragment).commit();
                        break;
                    case 1:
                        fm.beginTransaction().show(collectionFragment).hide(homeFragment).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
