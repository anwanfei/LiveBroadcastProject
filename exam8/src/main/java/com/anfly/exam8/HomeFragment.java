package com.anfly.exam8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    String bannerUrl = "https://www.wanandroid.com/banner/json";
    private String foodUrl = "http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1";
    private RecyclerView rv;
    private ArrayList<BannerBean.DataBean> banners;
    private ArrayList<FoodBean.DataBean> list;
    private HomeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(bannerUrl);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = con.getInputStream();
                        String json = streamToString(inputStream);
                        final BannerBean bannerBean = new Gson().fromJson(json, BannerBean.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                List<BannerBean.DataBean> data = bannerBean.getData();
                                banners.addAll(data);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                    URL url2 = new URL(foodUrl);
                    HttpURLConnection con2 = (HttpURLConnection) url2.openConnection();
                    if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = con2.getInputStream();
                        String json2 = streamToString(inputStream);
                        final FoodBean foodBean = new Gson().fromJson(json2, FoodBean.class);
                        getActivity().runOnUiThread(new Runnable() {
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

    private void initView(View view) {

        rv = view.findViewById(R.id.rv);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        banners = new ArrayList<>();
        list = new ArrayList<>();

        adapter = new HomeAdapter(getActivity(), banners, list);

        rv.setAdapter(adapter);
    }
}
