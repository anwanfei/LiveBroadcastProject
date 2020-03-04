package com.anfly.recyclerview;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView使用步骤
 * 1.添加依赖(版本问题注意)
 * 2.创建布局（宽高必须是充满的）
 * 3.找控件
 * 4.设置布局管理器（三种显示方式：线性布局、网格布局、瀑布流布局）
 * 5.获取数据（切换子线程的方法）
 * 6.创建适配器（*****）-- 重写三个，通过接口回调实现点击事件
 * 7.设置适配器
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private String foodUrl = "http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=10&page=";
    private ArrayList<FoodBean.DataBean> list;
    //    private RvAdapter adapter;
    private MultiLayoutAdapter adapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListenr();
    }

    private void initListenr() {
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * 滑动状态改变
             * @param recyclerView
             * @param newState
             */
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("TAG", "MainActivity onScrollStateChanged()" + newState);
                //获取布局管理
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                //获取最后最后一页最后条目的索引
                int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
                int firstVisibleItemPosition = lm.findFirstVisibleItemPosition();
                //获取总共的题目个数
                int itemCount = recyclerView.getAdapter().getItemCount();
                //获取当前页条目个数
                int childCount = recyclerView.getChildCount();

                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if (itemCount > 0 && itemCount == childCount + firstVisibleItemPosition) {
                            page++;
                            initData();
                        }
                        break;
                }
            }

            /**
             * 滑动监听
             * @param recyclerView
             * @param dx
             * @param dy
             */
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("TAG", "MainActivity onScrolled(),dx:" + dx + ",dy:" + dy);
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
                        String response = streamToString(inputStream);
                        FoodBean foodBean = new Gson().fromJson(response, FoodBean.class);
                        final List<FoodBean.DataBean> data = foodBean.getData();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                list.addAll(data);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }

                    //获取banners集合
                    //banners.addAll(bannner);
                    //adapter.notifyDataSetChanged();
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
        rv = (RecyclerView) findViewById(R.id.rv);

        //获取布局管理器并设置
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        StaggeredGridLayoutManager staggeredManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rv.setLayoutManager(linearLayoutManager);

        list = new ArrayList<>();

        //分割线
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //创建适配器
//        adapter = new RvAdapter(list, this);
        adapter = new MultiLayoutAdapter(list, this);

        //设置适配器
        rv.setAdapter(adapter);

    }
}
