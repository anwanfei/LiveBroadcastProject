package com.anfly.finalproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.anfly.finalproject.adapter.VpAdapter;
import com.anfly.finalproject.fragment.CollectionFragment;
import com.anfly.finalproject.fragment.ElvFragment;
import com.anfly.finalproject.fragment.ExamSixFragment;
import com.anfly.finalproject.fragment.HomeFragment;
import com.anfly.finalproject.fragment.ProjectFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager vp;
    private TabLayout tab;
    private ConstraintLayout cl;
    private NavigationView nv;
    private DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initListener() {
        //头部监听
        View headerView = nv.getHeaderView(0);
        headerView.findViewById(R.id.iv_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击我脑袋", Toast.LENGTH_SHORT).show();
            }
        });

        //菜单的监听
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_audio:
                        startActivity(new Intent(MainActivity.this, AudioActivity.class));
                        break;
                    case R.id.item_contacts:
                        callPhone();
                        break;
                    case R.id.item_close:
                        dl.closeDrawer(Gravity.LEFT);
                        break;
                }
                return false;
            }
        });

        //代码打开菜单： dl.openDrawer(Gravity.LEFT);

        //dl监听：主界面随着菜单打开在x轴移动
        dl.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                Log.e("TAG", "MainActivity onDrawerSlide()");
                int right = drawerView.getRight();
                cl.setX(right);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                Log.e("TAG", "MainActivity onDrawerOpened()");
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                Log.e("TAG", "MainActivity onDrawerClosed()");
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                Log.e("TAG", "MainActivity onDrawerStateChanged()");
            }
        });

        //tab设置选中监听
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("TAG", "MainActivity onTabSelected()-tab被选中");
                toolbar.setTitle(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.e("TAG", "MainActivity onTabUnselected()-tab没有被选中");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.e("TAG", "MainActivity onTabReselected()-tab被重新选中");
            }
        });
    }

    private void callPhone() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            call();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            call();
        } else {
            Toast.makeText(MainActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:10086"));
        startActivity(intent);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        vp = (ViewPager) findViewById(R.id.vp);
        tab = (TabLayout) findViewById(R.id.tab);
        cl = (ConstraintLayout) findViewById(R.id.cl);
        nv = (NavigationView) findViewById(R.id.nv);
        dl = (DrawerLayout) findViewById(R.id.dl);

        toolbar.setLogo(R.mipmap.ic_loge);
        toolbar.setTitle("首页");
        toolbar.setSubtitle("终极项目");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);

        //设置菜单图片不正常的时候使用
        nv.setItemIconTintList(null);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, toolbar, R.string.app_name, R.string.app_name);
        dl.addDrawerListener(toggle);
        toggle.syncState();

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new ElvFragment());
        fragments.add(new CollectionFragment());
        fragments.add(new ProjectFragment());
        fragments.add(new ExamSixFragment());

        ArrayList<String> titles = new ArrayList<>();
        titles.add("首页");
        titles.add("二级");
        titles.add("收藏");
        titles.add("项目");
        titles.add("题六");

        VpAdapter vpAdapter = new VpAdapter(getSupportFragmentManager(), fragments, titles);
        vp.setAdapter(vpAdapter);
        tab.setupWithViewPager(vp);

        tab.getTabAt(0).setIcon(R.drawable.selector_home);
        tab.getTabAt(1).setIcon(R.drawable.selector_knowledge);
        tab.getTabAt(2).setIcon(R.drawable.selector_collection);
        tab.getTabAt(3).setIcon(R.drawable.selector_project);
        tab.getTabAt(4).setIcon(R.drawable.selector_project);
    }
}
