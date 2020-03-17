package com.anfly.mediaplayer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class AudioServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_start;
    private Button btn_pause;
    private Button btn_stop;
    private Button btn_goon;
    private String path = Environment.getExternalStorageDirectory() + "/Pictures/青花瓷-周杰伦.mp3";
    private SeekBar seekbar_audio;
    private AudioService audioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_service);
        initView();
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
                audioService.seekTo(progress);
            }
        });
    }

    private void initView() {
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_goon = (Button) findViewById(R.id.btn_goon);
        seekbar_audio = (SeekBar) findViewById(R.id.seekbar_audio);

        btn_start.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_goon.setOnClickListener(this);

        Intent intent = new Intent(this, AudioService.class);
        AudioServiceConnection con = new AudioServiceConnection();
        bindService(intent, con, BIND_AUTO_CREATE);

    }

    class AudioServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AudioService.AudioBinder audioBider = (AudioService.AudioBinder) service;
            audioService = audioBider.getServie();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                audioService.start(path, seekbar_audio);
                break;
            case R.id.btn_pause:
                audioService.pause();
                break;
            case R.id.btn_stop:
                audioService.stop();
                break;
            case R.id.btn_goon:
                audioService.goon();
                break;
        }
    }
}
