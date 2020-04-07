package com.anfly.permissionr;

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

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_call_phone;
    private Button btn_call_phone_grantor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_call_phone = (Button) findViewById(R.id.btn_call_phone);

        btn_call_phone.setOnClickListener(this);
        btn_call_phone_grantor = (Button) findViewById(R.id.btn_call_phone_grantor);
        btn_call_phone_grantor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_call_phone:
                checkPhonePermission();
                break;
            case R.id.btn_call_phone_grantor:
                callPhoneGrantor();
                break;
        }
    }

    private void callPhoneGrantor() {
        PermissionsUtil.requestPermission(this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                callPhone();
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                Toast.makeText(MainActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.CALL_PHONE);
    }

    private void checkPhonePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            callPhone();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                } else {
                    Toast.makeText(MainActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:10086"));
        startActivity(intent);
    }
}
