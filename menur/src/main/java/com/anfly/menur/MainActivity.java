package com.anfly.menur;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.anfly.menur.adapter.VpMenuAdapter;
import com.anfly.menur.fragment.HomeFragment;
import com.anfly.menur.fragment.MutiLayoutFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar_menu;
    private DrawerLayout dl_menu;
    private NavigationView nv_menu;
    private LinearLayout ll_menu;
    private ViewPager vp;
    private TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initLIstener();
    }

    private void initLIstener() {
        //头部监听
        View headerView = nv_menu.getHeaderView(0);
        headerView.findViewById(R.id.iv_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击头部", Toast.LENGTH_SHORT).show();
            }
        });


        //侧滑监听
        nv_menu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_music:
                        Intent intent = new Intent(MainActivity.this, MusciActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.item_contacts:
                        callPhone();
                        break;
                    case R.id.item_close:
                        dl_menu.closeDrawer(Gravity.LEFT);
                        break;
                }
                return false;
            }
        });

        //dl监听
        dl_menu.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                Log.e("TAG", "MainActivity onDrawerSlide()");
                int right = drawerView.getRight();
                ll_menu.setX(right);
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

        //代码打抽屉
//        tv_open.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dl_menu.openDrawer(Gravity.LEFT);
//            }
//        });
    }

    private void callPhone() {

    }

    private void initView() {
//        tv_open = (TextView) findViewById(R.id.tv_open);
        toolbar_menu = (Toolbar) findViewById(R.id.toolbar_menu);
        dl_menu = (DrawerLayout) findViewById(R.id.dl_menu);
        nv_menu = (NavigationView) findViewById(R.id.nv_menu);
        ll_menu = (LinearLayout) findViewById(R.id.ll_menu);
        vp = (ViewPager) findViewById(R.id.vp);
        tab = (TabLayout) findViewById(R.id.tab);

        toolbar_menu.setLogo(R.mipmap.ic_launcher_round);
        toolbar_menu.setTitle("主标题");
        toolbar_menu.setSubtitle("副标题");
        toolbar_menu.setSubtitleTextColor(Color.WHITE);
        toolbar_menu.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar_menu);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl_menu, toolbar_menu, R.string.app_name, R.string.app_name);
        dl_menu.addDrawerListener(toggle);

        toggle.syncState();

//        registerForContextMenu(tv_open);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new MutiLayoutFragment());

        ArrayList<String> titles = new ArrayList<>();
        titles.add("首页");
        titles.add("多布局");
        VpMenuAdapter adapter = new VpMenuAdapter(getSupportFragmentManager(), fragments, titles);
        vp.setAdapter(adapter);
        tab.setupWithViewPager(vp);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //代码添加
//        menu.add(0, 0, 0, "扫一扫");
//        menu.add(0, 1, 0, "收付款");
//        menu.add(0, 2, 0, "摇一摇");
//        //menu添加
//        getMenuInflater().inflate(R.menu.munu_nv, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case 0:
//                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
//                break;
//            case 1:
//                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
//                break;
//            case 2:
//                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.item_music:
//                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.item_contacts:
//                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //代码添加
        menu.add(0, 0, 0, "扫一扫");
        menu.add(0, 1, 0, "收付款");
        menu.add(0, 2, 0, "摇一摇");
        //menu添加
        getMenuInflater().inflate(R.menu.munu_nv, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_music:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_contacts:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
