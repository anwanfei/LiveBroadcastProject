package com.anfly.radomname;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView tv_name;
    private ConstraintLayout cl;
    private int isStart;
    private Handler mHandler;
    private Timer mTimer = new Timer();

    private String[] arr = {"姜威",
            "刘占月",
            "陈宏志",
            "牛欣月",
            "徐雨欣",
            "王振敏",
            "王兴达",
            "刘增乙",
            "刘江峰",
            "袁文兵",
            "黎东东",
            "王志斌",
            "任永庆",
            "王辉",
            "许翔",
            "马帅毅",
            "张建龙",
            "李泽璇",
            "赵仟",
            "孙慧文",
            "李旭",
            "刘健",
            "尚霜霜",
            "田宇",
            "范亚军",
            "郝旭东",
            "朱茂源",
            "杨仕龙",
            "高晓倩",
            "曾祥瑞",
            "戚志强",
            "张蒙",
            "文勇齐",
            "卢治东",
            "翟晨晓",
            "宋亚辉",
            "李岩岩",
            "刘子瑞",
            "张文涛",
            "邱成彬",
            "张旭",
            "李宇航",
            "曹军英",
            "郑雄",
            "段宇昕",
            "崔华",
            "丁向东",
            "王炳惠",
            "姚咪咪",
            "王小龙",
            "李晨晨",
            "李欣",
            "张丰成",
            "薛婷婷",
            "陈志林",
            "彭张斌",
            "纪佳慧",
            "李冬瑞",
            "李大山",
            "何曦哲",
            "李靖保",
            "刘恩侠",
            "郝嘉誉",
            "侯春雨",
            "刘鑫",
            "张建新",
            "尹俊毅",
            "吕思迁",
            "李富凯",
            "王岳",
            "徐延笛",
            "王一帆",
            "尚晋栋",
            "李航洲",
            "李震",
            "赵福全",
            "孙宏炜",
            "牟洪磊"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        cl = (ConstraintLayout) findViewById(R.id.cl);
        mHandler = new Handler(new InnerCallback());
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStart == 0) {
                    start();
                } else {
                    stop();
                }
            }
        });
    }

    private void start() {
        isStart = 1;
        mTimer.schedule(new TimerTask() {
            int max = arr.length;
            int min = 0;

            @Override
            public void run() {
                if (isStart == 1) {
                    int num = (int) Math.round(Math.random() * (max - min) + min);
                    if (num >= max) {
                        num--;
                    }
                    //通过Message.obtain构造一个message，并通过Handler发送
                    mHandler.sendMessage(Message.obtain(mHandler, num));
                }
            }
        }, 0, 50);
    }

    private void stop() {
        isStart = 0;
    }

    private class InnerCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message msg) {
            //这里接收到sendMessage发送过来的消息，当前线程为UI线程
            int index = msg.what;
            tv_name.setText(arr[index]);
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        mTimer.cancel();
        super.onDestroy();
    }
}
