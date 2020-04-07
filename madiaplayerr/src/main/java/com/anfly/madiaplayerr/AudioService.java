package com.anfly.madiaplayerr;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.SeekBar;

public class AudioService extends Service {

    private MediaPlayer mp;

    public AudioService() {
    }

    class AudioBinder extends Binder {
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
        mp = new MediaPlayer();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.release();
        mp = null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void play(String path, SeekBar seekBar) {
        try {
            mp.reset();
            mp.setDataSource(path);
            mp.prepare();
            mp.start();
            updataProgress(seekBar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updataProgress(final SeekBar seekBar) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp.isPlaying()) {
                    try {
                        Thread.sleep(1000);
                        seekBar.setMax(mp.getDuration());
                        seekBar.setProgress(mp.getCurrentPosition());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void pause() {
        if (mp.isPlaying()) {
            mp.pause();
        }
    }

    public void stop() {
        if (mp.isPlaying()) {
            mp.stop();
        }
    }

    public void goon() {
        mp.start();
    }

    public void seekTo(int progress) {
        mp.seekTo(progress);
    }
}
