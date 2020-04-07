package com.anfly.servicer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class AnflyService extends Service {
    public AnflyService() {
    }

    class AnflyBinder extends Binder {
        void get(String msg) {
            methodInSetvice(msg);
        }
    }

    private void methodInSetvice(String msg) {
        Log.e("TAG", msg);
    }

    @Override
    public IBinder onBind(Intent intent) {
        String a = intent.getStringExtra("a");
        Log.e("TAG", "AnflyService onBind()" + a);
        return new AnflyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("TAG", "AnflyService onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG", "AnflyService onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String a = intent.getStringExtra("a");
        Log.e("TAG", "AnflyService onStartCommand()" + a);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "AnflyService onDestroy()");
    }


}
