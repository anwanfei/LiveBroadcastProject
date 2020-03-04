package com.anfly.viewpagerfragment;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

/**
 * viewpager结合fragment实现导航页面
 * <p>
 * 1.主界面布局
 * 2.创新一个fragment
 * 3.创建一个存放fragment的集合
 * 4.创建fragment适配器
 * 5.设置适配器
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initListener() {
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("vp", "滑动中=====position:" + position +
                        "   positionOffset:" + positionOffset + "   positionOffsetPixels:" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("vp", "显示页改变=====postion:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        Log.e("vp", "状态改变=====SCROLL_STATE_IDLE====静止状态");
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        Log.e("vp", "状态改变=====SCROLL_STATE_DRAGGING==滑动状态");
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        Log.e("vp", "状态改变=====SCROLL_STATE_SETTLING==滑翔状态");
                        break;
                }
            }
        });
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp);

        ArrayList<Fragment> list = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            PageFragment fragment = new PageFragment("第" + (i + 1) + "页面");
            list.add(fragment);
        }

        vp.setAdapter(new FragmentAdapter(getSupportFragmentManager(), list));

    }
}
