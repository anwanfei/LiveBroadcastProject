package com.anfly.fragmentr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout fl_container;
    private Button btn_rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        fl_container = (FrameLayout) findViewById(R.id.fl_container);

        //获取碎片管理器
        FragmentManager fm = getSupportFragmentManager();

        //开启事务
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        //获取fragment对象
        AFragment aFragment = new AFragment();
        Bundle bundle = new Bundle();
        bundle.putString("a", "我是来自activity的数据");
        aFragment.setArguments(bundle);

        //替换容器中内容
        fragmentTransaction.add(R.id.fl_container, aFragment);

        //提交事务
        fragmentTransaction.commit();
        btn_rb = (Button) findViewById(R.id.btn_rb);
        btn_rb.setOnClickListener(this);
    }

    public void getMsgFromFramgent(String msg) {
        Log.e("TAG", "MainActivity getMsgFromFramgent()来自fragment的数据：" + msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rb:
                Intent intent = new Intent(this, RbActivity.class);
                startActivity(intent);
                break;
        }
    }
}
