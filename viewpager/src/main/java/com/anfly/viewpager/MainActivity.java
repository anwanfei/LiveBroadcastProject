package com.anfly.viewpager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

/**
 * viewpager结合view实现导航：
 *
 * 1.ViewPager布局
 * 2.适配器(*****)
 * 3.设置适配器
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add("第" + i + "页面");
        }

        vp.setAdapter(new VpAdapter(this, list));
    }
}
