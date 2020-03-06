package com.anfly.permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

/**
 * 动态获取危险权限总结
 * 1.在清单列表声明权限
 * 2.检查是否授权：ActivityCompat.checkSelfPermission()
 * 如果授权--执行操作
 * 如果没有--请求权限：ActivityCompat.requestPermissions
 * 3.重写onRequestPermissionsResult检查授权是否成功
 * 如果成功：执行操作
 * 如果失败：吐司或者重新授权
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_permisstion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_permisstion = (Button) findViewById(R.id.btn_permisstion);

        btn_permisstion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_permisstion:
                requestPermission();
                break;
        }
    }

    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            //该权限已经授予
            callPhone();
        } else {
            //该权限没有授予
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "获取权限成功", Toast.LENGTH_SHORT).show();
            callPhone();
        } else {
            Toast.makeText(MainActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:10086"));
        startActivity(intent);
    }
}
