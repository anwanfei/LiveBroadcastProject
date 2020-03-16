package com.anfly.mediaplayer;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

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
    private SeekBar seekbar_audio;
    private RecyclerView rv;
    private Button btn_pre;
    private Button btn_next;
    private int position;
    private ArrayList<AudioBean> list;
    private Button btn_go_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initListener();
    }

    private void initListener() {
        seekbar_audio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * 进度发生改变回调
             * @param seekBar
             * @param progress
             * @param fromUser
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("TAG", "MainActivity onProgressChanged()");
            }

            /**
             * 开始拖动seekbar
             * @param seekBar
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("TAG", "MainActivity onStartTrackingTouch()");
            }

            /**
             * 停止拖动seekbar
             * @param seekBar
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("TAG", "MainActivity onStopTrackingTouch()");
                int progress = seekBar.getProgress();
                mp.seekTo(progress);
            }
        });
    }

    private void updataSeekbar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp.isPlaying()) {
                    try {
                        Thread.sleep(1000);
                        seekbar_audio.setMax(mp.getDuration());
                        seekbar_audio.setProgress(mp.getCurrentPosition());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
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
        seekbar_audio = (SeekBar) findViewById(R.id.seekbar_audio);
        rv = (RecyclerView) findViewById(R.id.rv);

        rv.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();

        Cursor query = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (query.moveToNext()) {
            String name = query.getString(query.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            String path = query.getString(query.getColumnIndex(MediaStore.Audio.Media.DATA));
            AudioBean audioBean = new AudioBean(name, path);
            if (audioBean.getName().endsWith("mp3")) {
                list.add(audioBean);
            }
        }

        AudioAdapter adapter = new AudioAdapter(this, list);
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener(new AudioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                try {
                    mp.reset();
                    mp.setDataSource(list.get(position).getPath());
                    mp.prepare();
                    mp.start();
                    updataSeekbar();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btn_pre = (Button) findViewById(R.id.btn_pre);
        btn_pre.setOnClickListener(this);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        btn_go_service = (Button) findViewById(R.id.btn_go_service);
        btn_go_service.setOnClickListener(this);
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
            case R.id.btn_pre:
                pre();
                break;
            case R.id.btn_next:
                next();
                break;
            case R.id.btn_go_service:
                Intent intent = new Intent(this, AudioServiceActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void next() {
        if (position < list.size() - 1) {
            position++;
        } else {
            position = 0;
        }
        playAudio();
    }

    private void pre() {
        if (position > 0) {
            position--;
        } else {
            position = list.size() - 1;
        }

        playAudio();
    }

    private void playAudio() {
        try {
            mp.reset();
            mp.setDataSource(list.get(position).getPath());
            mp.prepare();
            mp.start();
            updataSeekbar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void asset() {

        try {
            mp.reset();
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
            mp.reset();
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

            mp.reset();
            //创建mediaplaer
            //设置资源
            mp.setDataSource(path);
            //准备
            mp.prepare();

            //设置循环播放
            mp.setLooping(true);

            //开始
            mp.start();
            updataSeekbar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
