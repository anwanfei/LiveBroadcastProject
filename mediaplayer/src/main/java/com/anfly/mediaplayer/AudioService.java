package com.anfly.mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.SeekBar;

import androidx.annotation.Nullable;

import java.io.IOException;

public class AudioService extends Service {

    private MediaPlayer mp;
    AudioBider audioBider = new AudioBider();

    class AudioBider extends Binder {
        public AudioService getServie() {
            return AudioService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return audioBider;
    }

    @Override
    public void onCreate() {
        mp = new MediaPlayer();
        super.onCreate();
    }

    /**
     * 开始播放
     *
     * @param path
     * @param seekBar
     */
    public void start(String path, SeekBar seekBar) {
        try {
            mp.reset();
            mp.setDataSource(path);
            mp.prepare();
            mp.start();
            updataSeekbar(seekBar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        mp.pause();
    }

    public void stop() {
        mp.stop();
    }

    public void goon() {
        mp.start();
    }

    public void seekTo(int progress) {
        mp.seekTo(progress);
    }

    private void updataSeekbar(final SeekBar seekBar) {
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
}
