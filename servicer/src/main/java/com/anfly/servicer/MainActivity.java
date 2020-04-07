package com.anfly.servicer;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_start;
    private Button btn_stop;
    private Button btn_bind;
    private Button btn_unbind;
    private Intent intent;
    private AnflyCon con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_bind = (Button) findViewById(R.id.btn_bind);
        btn_unbind = (Button) findViewById(R.id.btn_unbind);

        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_bind.setOnClickListener(this);
        btn_unbind.setOnClickListener(this);

        intent = new Intent(this, AnflyService.class);
        intent.putExtra("a","activity数据");
        con = new AnflyCon();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startService(intent);
                break;
            case R.id.btn_stop:
                stopService(intent);
                break;
            case R.id.btn_bind:
                bindService(intent, con, Service.BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind:
                unbindService(con);
                break;
        }
    }

    class AnflyCon implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AnflyService.AnflyBinder anflyBinder = (AnflyService.AnflyBinder) service;
            anflyBinder.get("通过绑定方式把activity中数据传递到service中");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
