package com.anfly.madiaplayerr;

import android.content.ContentResolver;
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

    private Button btn_raw;
    private Button btn_sdcard;
    private Button btn_net;
    private Button btn_assets;
    private Button btn_start;
    private Button btn_pause;
    private Button btn_goon;
    private Button btn_stop;
    private String path = Environment.getExternalStorageDirectory() + "/Pictures/青花瓷-周杰伦.mp3";
    private String netPath = "http://music.163.com/song/media/outer/url?id=139894.mp3";
    private MediaPlayer player;
    private SeekBar seekbar_mp;
    private Button btn_pre;
    private Button btn_next;
    private RecyclerView rv_mp;
    private int positon = 0;
    private ArrayList<MusicBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initData() {
        list = new ArrayList<>();

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            if (name.endsWith("mp3")) {
                list.add(new MusicBean(name, path));
            }
        }

        rv_mp.setLayoutManager(new LinearLayoutManager(this));

        MpAdapter adapter = new MpAdapter(this, list);
        rv_mp.setAdapter(adapter);

        adapter.setOnItemClickListener(new MpAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                player(list.get(position).getPath());
            }
        });

    }

    private void player(String path) {
        try {
            player.reset();
            player.setDataSource(path);
            player.prepare();
            player.start();
            updataProgress();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        seekbar_mp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("TAG", "进度发生改变");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("TAG", "开始拖动");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("TAG", "结束拖动");
                player.seekTo(seekBar.getProgress());
            }
        });
    }

    private void updataProgress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (player.isPlaying()) {
                    try {
                        Thread.sleep(1000);
                        seekbar_mp.setMax(player.getDuration());
                        seekbar_mp.setProgress(player.getCurrentPosition());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void initView() {
        btn_raw = (Button) findViewById(R.id.btn_raw);
        btn_sdcard = (Button) findViewById(R.id.btn_sdcard);
        btn_net = (Button) findViewById(R.id.btn_net);
        btn_assets = (Button) findViewById(R.id.btn_assets);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_goon = (Button) findViewById(R.id.btn_goon);
        btn_stop = (Button) findViewById(R.id.btn_stop);

        btn_raw.setOnClickListener(this);
        btn_sdcard.setOnClickListener(this);
        btn_net.setOnClickListener(this);
        btn_assets.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_goon.setOnClickListener(this);
        btn_stop.setOnClickListener(this);

        player = new MediaPlayer();
        seekbar_mp = (SeekBar) findViewById(R.id.seekbar_mp);
        btn_pre = (Button) findViewById(R.id.btn_pre);
        btn_pre.setOnClickListener(this);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        rv_mp = (RecyclerView) findViewById(R.id.rv_mp);
        rv_mp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_raw:
                MediaPlayer.create(this, R.raw.qinghuaci).start();
                break;
            case R.id.btn_sdcard:
                sdcard();
                break;
            case R.id.btn_net:
                net();
                break;
            case R.id.btn_assets:
                assets();
                break;
            case R.id.btn_start:
                Intent intent = new Intent(this, ServiceActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_pause:
                player.pause();
                break;
            case R.id.btn_goon:
                player.start();
                break;
            case R.id.btn_stop:
                player.stop();
                break;
            case R.id.btn_pre:
                pre();
                break;
            case R.id.btn_next:
                next();
                break;
        }
    }

        private void next() {
            if (positon < list.size() - 1) {
                positon++;
            } else {
                positon = 0;
            }
            player(list.get(positon).getPath());
        }

        private void pre() {
            if (positon > 0) {
                positon--;
            } else {
                positon = list.size() - 1;
            }
            player(list.get(positon).getPath());
        }

    private void assets() {
        AssetManager assets = getAssets();
        try {
            AssetFileDescriptor assetFileDescriptor = assets.openFd("qinghuaci.mp3");
            player.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void net() {
        try {
            //设置资源
            player.setDataSource(netPath);

            //准备
            player.prepare();

            //开始
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sdcard() {
        try {
            //设置资源
            player.setDataSource(path);

            //准备
            player.prepare();

            //开始
            player.start();

            updataProgress();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
