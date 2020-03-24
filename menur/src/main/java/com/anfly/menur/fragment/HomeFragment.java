package com.anfly.menur.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.anfly.menur.R;
import com.anfly.menur.adapter.HomeAdapter;
import com.anfly.menur.bean.BannerBean;
import com.anfly.menur.bean.FoodBean;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView rv;
    private String foodUrl = "http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=10&page=";
    String bannerUrl = "https://www.wanandroid.com/banner/json";
    private ArrayList<FoodBean.DataBean> list;
    private HomeAdapter adapter;
    private Gson gson;
    private int page = 1;
    private ArrayList<BannerBean.DataBean> banners;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
                    URL url = new URL(foodUrl + page);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = con.getInputStream();
                        String josn = streamToString(inputStream);
                        final FoodBean foodBean = gson.fromJson(josn, FoodBean.class);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                List<FoodBean.DataBean> data = foodBean.getData();
                                list.addAll(data);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                    HttpURLConnection con2 = (HttpURLConnection) new URL(bannerUrl).openConnection();
                    int responseCode2 = con2.getResponseCode();
                    if (responseCode2 == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = con2.getInputStream();
                        String josn = streamToString(inputStream);
                        final BannerBean bannerBean = gson.fromJson(josn, BannerBean.class);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                List<BannerBean.DataBean> data = bannerBean.getData();
                                banners.addAll(data);
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

        gson = new Gson();
        rv = view.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        rv.addItemDecoration(new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL));

        list = new ArrayList<>();
        banners = new ArrayList<>();
        adapter = new HomeAdapter(list, banners, getActivity());
        rv.setAdapter(adapter);
    }

}
