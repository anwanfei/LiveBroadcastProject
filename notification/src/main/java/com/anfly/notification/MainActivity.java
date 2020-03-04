package com.anfly.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_send_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_send_notification = (Button) findViewById(R.id.btn_send_notification);

        btn_send_notification.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_notification:
                send();
                break;
        }
    }

    private void send() {

        String channelId = "1";
        String channelName = "an";

        //创建通知管理器
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //设置渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.setShowBadge(true);

            nm.createNotificationChannel(channel);
        }

        //获取activity的延时意图
        Intent intent = new Intent(this, PendingIntentActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //获取通知对象
        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("最新通知")
                .setContentText("我是最新通知内容")
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .build();

        //发送通知
        nm.notify(100, notification);
    }
}
