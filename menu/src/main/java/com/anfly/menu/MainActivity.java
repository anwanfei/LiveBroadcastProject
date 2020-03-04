package com.anfly.menu;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private TextView tv_main;
    private LinearLayout ll;
    private NavigationView nv;
    private DrawerLayout dl;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initListener() {
        //头部点击事件
        View headerView = nv.getHeaderView(0);
        headerView.findViewById(R.id.iv_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击了头部", Toast.LENGTH_SHORT).show();
            }
        });

        //菜单的点击事件
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item1:
                        dl.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.item2:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item3:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        //代码显示和隐藏抽屉
        tv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.openDrawer(Gravity.LEFT);
            }
        });

        //dl监听事件
        dl.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                Log.e("TAG", "抽屉正在滑动");
                int right = drawerView.getRight();
                ll.setX(right);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                Log.e("TAG", "抽屉打开");
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                Log.e("TAG", "抽屉关闭");
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                Log.e("TAG", "抽屉状态改变：" + newState);
            }
        });

    }

    private void initView() {
        tv_main = (TextView) findViewById(R.id.tv_main);
        ll = (LinearLayout) findViewById(R.id.ll);
        nv = (NavigationView) findViewById(R.id.nv);
        dl = (DrawerLayout) findViewById(R.id.dl);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //设置logo
        toolbar.setLogo(R.mipmap.ic_launcher_round);

        //设置主标题
        toolbar.setTitle("主标题");
        toolbar.setTitleTextColor(Color.WHITE);

        //设置副标题
        toolbar.setSubtitle("副标题");
        toolbar.setSubtitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, toolbar, R.string.app_name, R.string.app_name);
        dl.addDrawerListener(toggle);

        toggle.syncState();

        //为tv_main注册上下文菜单
        registerForContextMenu(tv_main);
    }

    /**
     * 创建选项菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //第一种方法，menu.add
        menu.add(0, 0, 2, "扫一扫");
        menu.add(0, 1, 1, "添加好友");
        menu.add(0, 2, 0, "收付款");

        //第二种方法：menuInflator
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 选项菜单选中
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
            case R.id.item1:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.item2:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.item3:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //第一种方式
//        menu.add(0, 0, 0, "删除");
//        menu.add(0, 1, 0, "修改");
//        menu.add(0, 2, 0, "增加");

        //第二种方式
        getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.item2:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.item3:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
