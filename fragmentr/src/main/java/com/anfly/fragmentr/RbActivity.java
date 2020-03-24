package com.anfly.fragmentr;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class RbActivity extends AppCompatActivity {

    private FrameLayout fl_container;
    private RadioButton rb_a;
    private RadioButton rb_b;
    private RadioGroup rg;
    private FragmentManager fm;
    private AFragment aFragment;
    private BFragment bFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rb);
        initView();
        initListener();
    }

    private void initListener() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_a:
                        fm.beginTransaction().hide(bFragment).show(aFragment).commit();
                        break;
                    case R.id.rb_b:
                        fm.beginTransaction().hide(aFragment).show(bFragment).commit();
                        break;
                }
            }
        });
    }

    private void initView() {
        fl_container = (FrameLayout) findViewById(R.id.fl_container);
        rb_a = (RadioButton) findViewById(R.id.rb_a);
        rb_b = (RadioButton) findViewById(R.id.rb_b);
        rg = (RadioGroup) findViewById(R.id.rg);

        fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        aFragment = new AFragment();
        bFragment = new BFragment();
        fragmentTransaction.add(R.id.fl_container, aFragment).add(R.id.fl_container, bFragment)
                .hide(bFragment).show(aFragment).commit();
    }
}
