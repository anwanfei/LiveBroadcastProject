package com.anfly.finalproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anfly.finalproject.R;
import com.anfly.finalproject.adapter.HomeAdapter;
import com.anfly.finalproject.bean.BannerBean;
import com.anfly.finalproject.bean.FoodBean;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements HomeAdapter.OnItemClickListener {

    String bannerUrl = "https://www.wanandroid.com/banner/json";
    private String foodUrl = "http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1";
    private RecyclerView rv;
    private SmartRefreshLayout srl;
    private ArrayList<BannerBean.DataBean> banners;
    private ArrayList<FoodBean.DataBean> list;
    private HomeAdapter adapter;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String bannerJson = getNetData(bannerUrl);
                    String foodJson = getNetData(foodUrl);
                    Gson gson = new Gson();
                    final BannerBean bannerBean = gson.fromJson(bannerJson, BannerBean.class);
                    final FoodBean foodBean = gson.fromJson(foodJson, FoodBean.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            banners.addAll(bannerBean.getData());
                            list.addAll(foodBean.getData());
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String getNetData(String netUrl) {
        try {
            URL url = new URL(netUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = con.getInputStream();
                return streamToString(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
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

    private void initView(View inflate) {
        rv = inflate.findViewById(R.id.rv);
        srl = inflate.findViewById(R.id.srl);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        banners = new ArrayList<>();
        list = new ArrayList<>();
        adapter = new HomeAdapter(getActivity(), list, banners);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), list.get(position).getTitle(), Toast.LENGTH_SHORT).show();
    }
}
