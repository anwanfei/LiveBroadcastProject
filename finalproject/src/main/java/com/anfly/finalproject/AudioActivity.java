package com.anfly.finalproject;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anfly.finalproject.adapter.AudioAdapter;
import com.anfly.finalproject.bean.AudioBean;

import java.io.IOException;
import java.util.ArrayList;

public class AudioActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_start;
    private Button btn_pause;
    private Button btn_stop;
    private Button btn_goon;
    private Button btn_pre;
    private Button btn_next;
    private SeekBar seekbar_audio;
    private RecyclerView rv_audio;
    private String path;
    private MediaPlayer mediaPlayer;
    private int position;
    private ArrayList<AudioBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        seekbar_audio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                mediaPlayer.seekTo(progress);
            }
        });
    }

    private void updataProgress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    seekbar_audio.setMax(mediaPlayer.getDuration());
                    seekbar_audio.setProgress(mediaPlayer.getCurrentPosition());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initData() {
        list = new ArrayList<>();
        Cursor query = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (query.moveToNext()) {
            String name = query.getString(query.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            String path = query.getString(query.getColumnIndex(MediaStore.Audio.Media.DATA));
            if (name.endsWith("mp3")) {
                list.add(new AudioBean(name, path));
            }
        }

        rv_audio.setLayoutManager(new LinearLayoutManager(this));
        AudioAdapter adapter = new AudioAdapter(list, AudioActivity.this);
        rv_audio.setAdapter(adapter);

        adapter.setOnItemClickListener(new AudioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                play(list.get(position));
            }
        });

    }

    private void play(AudioBean audioBean) {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(audioBean.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            updataProgress();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_goon = (Button) findViewById(R.id.btn_goon);
        btn_pre = (Button) findViewById(R.id.btn_pre);
        btn_next = (Button) findViewById(R.id.btn_next);
        seekbar_audio = (SeekBar) findViewById(R.id.seekbar_audio);
        rv_audio = (RecyclerView) findViewById(R.id.rv_audio);

        btn_start.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_goon.setOnClickListener(this);
        btn_pre.setOnClickListener(this);
        btn_next.setOnClickListener(this);

        path = Environment.getExternalStorageDirectory() + "/Pictures/我的楼兰-云朵.mp3";
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                start();
                break;
            case R.id.btn_pause:
                mediaPlayer.pause();
                break;
            case R.id.btn_stop:
                mediaPlayer.stop();
                break;
            case R.id.btn_goon:
                mediaPlayer.start();
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
        if (position < list.size() - 1) {
            position++;
        } else {
            position = 0;
        }
        play(list.get(position));
    }

    private void pre() {
        if (position > 0) {
            position--;
        } else {
            position = list.size() - 1;
        }

        play(list.get(position));
    }

    private void start() {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
