package com.anfly.listview;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 手动加载更多
 * 1.使用Listview显示网络数据列表
 * 2.给Listview添加一个脚布局并找到脚布局的控件
 * 3.设置控件的点击事件，再加载一次数据
 * <p>
 * 手动加载更多分页数据
 * 1.修改接口，把page提出来
 * 2.定义page变量
 * 3.点击button的时候给page加1
 * <p>
 * 自动加载更多数据
 * 1.使用Listview显示网络数据列表(隐藏脚布局)
 * 2.判断屏幕是否滑到底部
 * 3.如果滑到底部，加载更多数据
 * <p>
 * 自动加载更多分页数据
 * 1.使用Listview显示网络数据列表(隐藏脚布局)
 * 2.判断屏幕是否滑到底部
 * 3.如果滑到底部，加载更多数据
 * 4.修改接口--把page的value提出来--定义变量page--加载更多数据的时候给page加1
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lv;
    private String foodUrl = "http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=10&page=";
    private ArrayList<FoodBean.DataBean> list;
    private FoodAdapter foodAdapter;
    private int page = 1;
    private boolean isBottom;
    private int position;

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
             *滑动状态改变
             * scrollState
             *  1. SCROLL_STATE_IDLE 手指未触摸屏幕,且屏幕静止
             *  2. SCROLL_STATE_TOUCH_SCROLL 手指未离开屏幕滑动
             *  3. SCROLL_STATE_FLING 手指使劲滑动屏幕,然后手指离开屏幕,屏幕仍在滚动
             * @param view
             * @param scrollState
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
             *滚动监听
             *
             * @param view
             * @param firstVisibleItem：可见页面第一条目索引
             * @param visibleItemCount：可见页面条目个数
             * @param totalItemCount：总共条目个数
             *
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
                                foodAdapter.notifyDataSetChanged();
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

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);

        //添加脚布局
        View view = LayoutInflater.from(this).inflate(R.layout.view_foot, null);
        lv.addFooterView(view);
        view.findViewById(R.id.btn_more).setOnClickListener(this);
        view.setVisibility(View.GONE);

        list = new ArrayList<>();
        foodAdapter = new FoodAdapter(this, list);

        lv.setAdapter(foodAdapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.position = position;
                return false;
            }
        });

        registerForContextMenu(lv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 0, 0, "删除");
        menu.add(0, 1, 0, "修改");
        menu.add(0, 2, 0, "增加");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                list.remove(position);
                foodAdapter.notifyDataSetChanged();
                break;
            case 1:
                FoodBean.DataBean dataBean = list.get(position);
                dataBean.setTitle("网络直播课程");
                foodAdapter.notifyDataSetChanged();
                break;
            case 2:
                FoodBean.DataBean dataBean1 = list.get(0);
                list.add(dataBean1);
                foodAdapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        page++;
        initData();
    }
}
