package com.anfly.fragment;

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
    private Button btn_add;
    private Button btn_remove;
    private Button btn_repalce;
    private Button btn_hide;
    private Button btn_show;
    private FragmentManager fm;
    private BFragment bFragment;
    private AFragment aFragment;
    private Button btn_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        fl_container = (FrameLayout) findViewById(R.id.fl_container);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        btn_remove = (Button) findViewById(R.id.btn_remove);
        btn_remove.setOnClickListener(this);
        btn_repalce = (Button) findViewById(R.id.btn_repalce);
        btn_repalce.setOnClickListener(this);
        btn_hide = (Button) findViewById(R.id.btn_hide);
        btn_hide.setOnClickListener(this);
        btn_show = (Button) findViewById(R.id.btn_show);
        btn_show.setOnClickListener(this);

        //1.获取碎片管理器
        fm = getSupportFragmentManager();

        //2.开启事务
        FragmentTransaction transaction = fm.beginTransaction();

        //3.获取fragment对象
        bFragment = new BFragment("我是activity的数据");
        aFragment = new AFragment();

        Bundle bundle = new Bundle();
        bundle.putString("k", "通过bundle传过来的数据");
        bFragment.setArguments(bundle);

        //4.替换container内容
        transaction.add(R.id.fl_container, bFragment);

        //5.提交事务
        transaction.commit();

        btn_msg = (Button) findViewById(R.id.btn_msg);
        btn_msg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                fm.beginTransaction().add(R.id.fl_container, aFragment).commit();
                break;
            case R.id.btn_remove:
                fm.beginTransaction().remove(aFragment).commit();
                break;
            case R.id.btn_repalce:
                fm.beginTransaction().replace(R.id.fl_container, aFragment).commit();
                break;
            case R.id.btn_hide:
                fm.beginTransaction().hide(aFragment).commit();
                break;
            case R.id.btn_show:
                fm.beginTransaction().show(aFragment).commit();
                break;
            case R.id.btn_msg:
                sendMsg();
                break;
        }
    }

    private void sendMsg() {

        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
    }

    public void show(MsgBean msgBean) {
        Log.e("TAG", msgBean.getName() + ":" + msgBean.getAge());
    }
}
