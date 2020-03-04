package com.anfly.exercise;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout ll;
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
        //抽屉监听
        dl.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                int right = drawerView.getRight();

                ll.setX(right);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        //头部点击事件
        View headerView = nv.getHeaderView(0);
        headerView.findViewById(R.id.iv_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "我是头部", Toast.LENGTH_SHORT).show();
            }
        });

        //导航菜单的点击事件
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item1:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item2:

                        break;
                    case R.id.item3:

                        break;
                    case R.id.item4:

                        break;
                }
                return false;
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ll = (LinearLayout) findViewById(R.id.ll);
        nv = (NavigationView) findViewById(R.id.nv);
        dl = (DrawerLayout) findViewById(R.id.dl);

        toolbar.setLogo(R.mipmap.ic_launcher_round);

        toolbar.setTitle("主标题");
        toolbar.setSubtitle("副标题");

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, toolbar, R.string.app_name, R.string.app_name);

        dl.addDrawerListener(toggle);

        //ActionBarDrawerToggle和toolbar结合使用，使开关按钮图标显示出来
        toggle.syncState();
    }
}
