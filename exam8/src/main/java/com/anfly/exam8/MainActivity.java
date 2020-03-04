package com.anfly.exam8;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager vp;
    private TabLayout tab;
    private LinearLayout ll;
    private NavigationView nv;
    private DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        vp = (ViewPager) findViewById(R.id.vp);
        tab = (TabLayout) findViewById(R.id.tab);
        ll = (LinearLayout) findViewById(R.id.ll);
        nv = (NavigationView) findViewById(R.id.nv);
        dl = (DrawerLayout) findViewById(R.id.dl);

        toolbar.setLogo(R.mipmap.ic_launcher_round);
        toolbar.setTitle("首页");
        toolbar.setSubtitle("Exam8");

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, toolbar, R.string.app_name, R.string.app_name);

        dl.addDrawerListener(toggle);

        toggle.syncState();

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CollectionFragment());

        ArrayList<String> titles = new ArrayList<>();
        titles.add("首页");
        titles.add("收藏");

        ExamAdapter adapter = new ExamAdapter(getSupportFragmentManager(), titles, fragments);
        vp.setAdapter(adapter);
        tab.setupWithViewPager(vp);


    }
}
