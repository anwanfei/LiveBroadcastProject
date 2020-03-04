package com.anfly.fragment;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class NavigationActivity extends AppCompatActivity {

    private FrameLayout fl_container;
    private RadioButton rb_home;
    private RadioButton rb_collection;
    private RadioGroup rg;
    private FragmentManager fm;
    private HomeFragment homeFragment;
    private CollectionFragment collectionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        initView();
        initListener();
    }

    private void initListener() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        fm.beginTransaction().show(homeFragment).hide(collectionFragment).commit();
                        break;
                    case R.id.rb_collection:
                        fm.beginTransaction().hide(homeFragment).show(collectionFragment).commit();
                        break;
                }
            }
        });
    }

    private void initView() {
        fl_container = (FrameLayout) findViewById(R.id.fl_container);
        rb_home = (RadioButton) findViewById(R.id.rb_home);
        rb_collection = (RadioButton) findViewById(R.id.rb_collection);
        rg = (RadioGroup) findViewById(R.id.rg);

        fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        homeFragment = new HomeFragment();
        collectionFragment = new CollectionFragment();
        transaction.add(R.id.fl_container, homeFragment).add(R.id.fl_container, collectionFragment).hide(collectionFragment).commit();
    }
}
