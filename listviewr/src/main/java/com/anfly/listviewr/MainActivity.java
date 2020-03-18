package com.anfly.listviewr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lv;
    private String foodUrl = "http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=10&page=";
    private int page = 1;
    private ArrayList<FoodBean.DataBean> list;
    private LvAdapter adapter;
    private boolean isBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            /**
             * @param view
             * @param scrollState
             * //scrollState 有三种类型
             * 1.SCROLL_STATE_IDLE 手指未触摸屏幕,且屏幕静止
             * 2.SCROLL_STATE_TOUCH_SCROLL 手指未离开屏幕滑动
             * 3.SCROLL_STATE_FLING 手指使劲滑动屏幕,然后手指离开屏幕,屏幕仍在滚动
             */
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        if (isBottom) {
                            page++;
                            initData();
                            isBottom = false;
                        }
                        break;
                }
            }
            /**
             * @param view
             * @param firstVisibleItem：可见页面第一个条目小标
             * @param visibleItemCount：可见页面数据个数
             * @param totalItemCount：条目总数
             */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                    isBottom = true;
                } else {
                    isBottom = false;
                }
            }
        });
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);

        View footer = LayoutInflater.from(this).inflate(R.layout.footer_layout, null);
        footer.findViewById(R.id.btn_load_more).setOnClickListener(this);
        lv.addFooterView(footer);

        footer.setVisibility(View.GONE);

        list = new ArrayList<>();
        adapter = new LvAdapter(this, list);
        lv.setAdapter(adapter);

    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(foodUrl + page);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = con.getInputStream();
                        String json = streamToString(inputStream);
                        final FoodBean foodBean = new Gson().fromJson(json, FoodBean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                List<FoodBean.DataBean> data = foodBean.getData();
                                list.addAll(data);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String streamToString(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int length = -1;
        byte[] bytes = new byte[1024];
        try {
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }

            outputStream.close();
            String result = outputStream.toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onClick(View v) {
        page++;
        initData();
    }
}
