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

    private String[] arr = {"A1姜威",
            "A2刘占月",
            "A3陈宏志",
            "A4牛欣月",
            "A5徐雨欣",
            "A6王振敏",
            "A7王兴达",
            "A8刘增乙",
            "A9刘江峰",
            "A10袁文兵",
            "A11黎东东",
            "A12王志斌",
            "A13任永庆",
            "A14王辉",
            "A15许翔",
            "A16马帅毅",
            "A17张建龙",
            "A18李泽璇",
            "A19赵仟",
            "A20孙慧文",
            "A21李旭",
            "A22刘健",
            "A23尚霜霜",
            "A24田宇",
            "A25范亚军",
            "A26郝旭东",
            "A27朱茂源",
            "A28杨仕龙",
            "A29高晓倩",
            "A30曾祥瑞",
            "A31戚志强",
            "A32张蒙",
            "A33文勇齐",
            "A34卢治东",
            "A35翟晨晓",
            "A36宋亚辉",
            "A37李岩岩",
            "A38刘子瑞",
            "A39张文涛",
            "A40邱成彬",
            "B1张旭",
            "B2李宇航",
            "B3曹军英",
            "B4郑雄",
            "B5段宇昕",
            "B6崔华",
            "B7丁向东",
            "B8王炳惠",
            "B9姚咪咪",
            "B10王小龙",
            "B11李晨晨",
            "B12李欣",
            "B13张丰成",
            "B14薛婷婷",
            "B15陈志林",
            "B16彭张斌",
            "B17纪佳慧",
            "B18李冬瑞",
            "B19李大山",
            "B20何曦哲",
            "B21李靖保",
            "B22刘恩侠",
            "B23郝嘉誉",
            "B24侯春雨",
            "B25刘鑫",
            "B26张建新",
            "B27尹俊毅",
            "B28吕思迁",
            "B29李富凯",
            "B30王岳",
            "B31徐延笛",
            "B32王一帆",
            "B33尚晋栋",
            "B34李航洲",
            "B35李震",
            "B36赵福全",
            "B37孙宏炜",
            "B38牟洪磊"};

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
