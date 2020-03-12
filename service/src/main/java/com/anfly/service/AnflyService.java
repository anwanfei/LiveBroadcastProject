package com.anfly.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class AnflyService extends Service {

    AnBinder anBinder = new AnBinder();

    class AnBinder extends Binder {
        public void call(String msg) {
            methodInService(msg);
        }
    }

    private void methodInService(String msg) {
        Log.e("TAG", msg);
    }

    /**
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String data = intent.getStringExtra("data");
        Log.e("TAG", "AnflyService onBind():" + data);
        return anBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG", "AnflyService onCreate()");
    }

    @Override
    public void onDestroy() {
        Log.e("TAG", "AnflyService onDestroy()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String data = intent.getStringExtra("data");

        Log.e("TAG", "AnflyService onStartCommand():" + data);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("TAG", "AnflyService onUnbind()");
        return super.onUnbind(intent);
    }
}
