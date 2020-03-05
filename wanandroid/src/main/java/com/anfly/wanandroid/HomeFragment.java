package com.anfly.wanandroid;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnRefreshLoadMoreListener, HomeAdapter.OnitemClickListener {

    private RecyclerView rv;
    private SmartRefreshLayout srl;
    private String bannerUrl = "https://www.wanandroid.com/banner/json";
    private String articleUrl = "https://www.wanandroid.com/article/list/";
    private int page = 0;
    private ArrayList<BannerBean.DataBean> banners;
    private ArrayList<ArticleBean.DataBean.DatasBean> list;
    private HomeAdapter adapter;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        gson = new GsonBuilder().create();
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String bannserJson = getResponse(bannerUrl);
                String articleJson = getResponse(articleUrl + page + "/json");
                final BannerBean bannerBean = gson.fromJson(bannserJson, BannerBean.class);
                final ArticleBean articleBean = gson.fromJson(articleJson, ArticleBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        banners.addAll(bannerBean.getData());
                        list.addAll(articleBean.getData().getDatas());
                        adapter.notifyDataSetChanged();
                        srl.finishRefresh();
                        srl.finishLoadMore();
                    }
                });
            }
        }).start();
    }

    private String getResponse(String netUrl) {
        try {
            URL url = new URL(netUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = con.getInputStream();
                String json = streamToString(inputStream);
                return json;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        srl = view.findViewById(R.id.srl);

        banners = new ArrayList<>();
        list = new ArrayList<>();

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HomeAdapter(banners, list, getActivity());
        rv.setAdapter(adapter);

        srl.setOnRefreshLoadMoreListener(this);

        adapter.setOnitemClickListener(this);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        initData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 0;
        if (list != null && list.size() > 0) {
            list.clear();
            initData();
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), WebViewAcivity.class);
        intent.putExtra("link", list.get(position).getLink());
        startActivity(intent);
    }
}
