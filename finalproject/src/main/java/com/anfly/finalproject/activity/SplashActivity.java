package com.anfly.finalproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.anfly.finalproject.MainActivity;
import com.anfly.finalproject.R;
import com.anfly.finalproject.adapter.SplashAdapter;
import com.anfly.finalproject.fragment.SplashFragment;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_start;
    private ViewPager vp_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initListrter();
    }

    private void initListrter() {
        vp_splash.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    btn_start.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        btn_start = (Button) findViewById(R.id.btn_start);
        vp_splash = (ViewPager) findViewById(R.id.vp_splash);

        btn_start.setOnClickListener(this);

        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            fragments.add(new SplashFragment());
        }
        SplashAdapter adapter = new SplashAdapter(getSupportFragmentManager(), fragments);
        vp_splash.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
