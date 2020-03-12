package com.anfly.mediaplayer;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_sdcard;
    private Button btn_net;
    private Button btn_raw;
    private Button btn_assets;
    private Button btn_pause;
    private Button btn_stop;
    private MediaPlayer mp;
    private String path = Environment.getExternalStorageDirectory() + "/Pictures/青花瓷-周杰伦.mp3";
    private String netPath = "http://music.163.com/song/media/outer/url?id=139894.mp3";
    private Button btn_goon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_sdcard = (Button) findViewById(R.id.btn_sdcard);
        btn_net = (Button) findViewById(R.id.btn_net);
        btn_raw = (Button) findViewById(R.id.btn_raw);
        btn_assets = (Button) findViewById(R.id.btn_assets);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_stop = (Button) findViewById(R.id.btn_stop);

        btn_sdcard.setOnClickListener(this);
        btn_net.setOnClickListener(this);
        btn_raw.setOnClickListener(this);
        btn_assets.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_stop.setOnClickListener(this);

        mp = new MediaPlayer();

        btn_goon = (Button) findViewById(R.id.btn_goon);
        btn_goon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sdcard:
                sdcard();
                break;
            case R.id.btn_net:
                net();
                break;
            case R.id.btn_raw:
                raw();
                break;
            case R.id.btn_assets:
                asset();
                break;
            case R.id.btn_pause:
                pause();
                break;
            case R.id.btn_stop:
                stop();
                break;
            case R.id.btn_goon:
                goon();
                break;
        }
    }

    private void asset() {

        try {
            if (mp.isPlaying()) {
                mp.release();
                mp.reset();
            }
            //获取assets管理器
            AssetManager assets = getAssets();

            //打开文件
            AssetFileDescriptor assetFileDescriptor = assets.openFd("qinghuaci.mp3");

            //设置资源
            mp.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());

            //开始播放
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void raw() {
        MediaPlayer mpCreate = MediaPlayer.create(this, R.raw.qinghuaci);
        mpCreate.start();
    }

    private void net() {
        try {
            if (mp.isPlaying()) {
                mp.release();
            }
            mp.setDataSource(netPath);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goon() {
        mp.start();
    }

    private void stop() {
        mp.stop();
    }

    private void pause() {
        mp.pause();
    }

    private void sdcard() {
        try {

            if (mp.isPlaying()) {
                mp.release();
            }
            //创建mediaplaer
            //设置资源
            mp.setDataSource(path);
            //准备
            mp.prepare();

            //设置循环播放
            mp.setLooping(true);

            //开始
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
