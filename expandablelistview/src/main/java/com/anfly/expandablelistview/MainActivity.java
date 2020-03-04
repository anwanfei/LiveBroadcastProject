package com.anfly.expandablelistview;

import android.os.Bundle;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String elvUrl = "https://www.wanandroid.com/tree/json";
    private ExpandableListView elv_elv;
    private ArrayList<ElvBean.DataBean> list;
    private ElvAdaspter elvAdaspter;

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
                    URL url = new URL(elvUrl);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    int responseCode = con.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = con.getInputStream();
                        String json = streamToString(inputStream);
                        final ElvBean elvBean = new Gson().fromJson(json, ElvBean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                List<ElvBean.DataBean> data = elvBean.getData();
                                list.addAll(data);
                                elvAdaspter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void initView() {
        elv_elv = (ExpandableListView) findViewById(R.id.elv_elv);

        list = new ArrayList<>();
        elvAdaspter = new ElvAdaspter(list, this);

        elv_elv.setAdapter(elvAdaspter);
    }

    /**
     * 输入流转换成json
     *
     * @param inputStream
     * @return
     */
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
}
