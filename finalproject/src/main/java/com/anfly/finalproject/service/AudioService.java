package com.anfly.finalproject.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.SeekBar;

import java.io.IOException;

public class AudioService extends Service {

    private MediaPlayer mp;

    public AudioService() {
    }

    public class AudioBinder extends Binder {
        public AudioService getService() {
            return AudioService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new AudioBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mp = new MediaPlayer();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mp.release();
        mp = null;
        return super.onUnbind(intent);
    }

    public void play(String path, SeekBar seekBar) {
        mp.reset();
        try {
            mp.setDataSource(path);
            mp.prepare();
            mp.start();
            updataSeekbar(seekBar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updataSeekbar(final SeekBar seekBar) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (mp.isPlaying()) {
                        Thread.sleep(1000);
                        seekBar.setMax(mp.getDuration());
                        seekBar.setProgress(mp.getCurrentPosition());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void stop() {
        mp.stop();
    }

    public void pause() {
        mp.pause();
    }

    public void goon() {
        mp.start();
    }

    public void seekTo(int progress) {
        mp.seekTo(progress);
    }

}
