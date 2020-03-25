package com.anfly.viewpagerr;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

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

        //结合view实现导航
//        ArrayList<String> list = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            list.add("第" + i + "页");
//        }
//        VpViewAdapter adapter = new VpViewAdapter(this, list);
//        vp.setAdapter(adapter);


        //结合fragment实现导航
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {

            VpFragment vpFragment = new VpFragment();
            Bundle bundle = new Bundle();
            bundle.putString("key", "第" + i + "页");
            vpFragment.setArguments(bundle);

            fragments.add(vpFragment);
        }

        VpFragmentAdapter adapter = new VpFragmentAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);
    }
}
