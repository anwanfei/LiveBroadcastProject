package com.anfly.smartgirl;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SmartGirlAdapter.OnItemClickListener, SmartGirlAdapter.OnItemLongClickListener {

    private RecyclerView rv;
    private ArrayList<SmartBean.ResultsBean> list;
    private SmartGirlAdapter adapter;
    private String sgUrl = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/19";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(sgUrl);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = con.getInputStream();
                        String json = streamToString(inputStream);
                        SmartBean smartBean = new Gson().fromJson(json, SmartBean.class);
                        final List<SmartBean.ResultsBean> results = smartBean.getResults();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list.addAll(results);
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

    private void initView() {
        rv = (RecyclerView) findViewById(R.id.rv);

//        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        rv.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new SmartGirlAdapter(this, list);
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(MainActivity.this, "点击了" + list.get(position).getDesc(), Toast.LENGTH_SHORT).show();
        SmartBean.ResultsBean resultsBean = list.get(position);
        resultsBean.setUrl("");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemLongClick(int position) {
        Toast.makeText(MainActivity.this, "长按了" + list.get(position).getDesc(), Toast.LENGTH_SHORT).show();
    }
}
